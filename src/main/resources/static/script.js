$(document).ready(function() {
    const API_BASE_URL = 'http://localhost:8080/ktp';
    
    // Load all KTP data when page loads
    loadKtpData();
    
    // Form submit event
    $('#ktpForm').on('submit', function(e) {
        e.preventDefault();
        
        // Validate form first
        if (!validateForm()) {
            return false;
        }
        
        const ktpId = $('#ktpId').val();
        const ktpData = {
            nomorKtp: $('#nomorKtp').val(),
            namaLengkap: $('#namaLengkap').val(),
            alamat: $('#alamat').val(),
            tanggalLahir: $('#tanggalLahir').val(),
            jenisKelamin: $('#jenisKelamin').val()
        };
        
        if (ktpId) {
            // Update existing KTP
            updateKtp(ktpId, ktpData);
        } else {
            // Create new KTP
            createKtp(ktpData);
        }
    });
    
    // Cancel button event
    $('#cancelBtn').on('click', function() {
        resetForm();
    });
    
    // Load KTP data function
    function loadKtpData() {
        $.ajax({
            url: API_BASE_URL,
            method: 'GET',
            success: function(data) {
                displayKtpData(data);
            },
            error: function(xhr, status, error) {
                showAlert('danger', 'Gagal memuat data KTP: ' + error);
            }
        });
    }
    
    // Display KTP data in table
    function displayKtpData(ktpList) {
        const tbody = $('#ktpTableBody');
        tbody.empty();
        
        if (ktpList.length === 0) {
            tbody.append('<tr><td colspan="7" class="text-center">Tidak ada data KTP</td></tr>');
            return;
        }
        
        ktpList.forEach(function(ktp) {
            const row = `
                <tr>
                    <td>${ktp.id}</td>
                    <td>${ktp.nomorKtp}</td>
                    <td>${ktp.namaLengkap}</td>
                    <td>${ktp.alamat}</td>
                    <td>${formatDate(ktp.tanggalLahir)}</td>
                    <td>${ktp.jenisKelamin}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-edit" data-id="${ktp.id}">Edit</button>
                        <button class="btn btn-danger btn-sm btn-delete" data-id="${ktp.id}">Hapus</button>
                    </td>
                </tr>
            `;
            tbody.append(row);
        });
        
        // Add event listeners for edit and delete buttons
        $('.btn-edit').on('click', function() {
            const id = $(this).data('id');
            editKtp(id);
        });
        
        $('.btn-delete').on('click', function() {
            const id = $(this).data('id');
            deleteKtp(id);
        });
    }
    
    // Create new KTP
    function createKtp(ktpData) {
        $.ajax({
            url: API_BASE_URL,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(ktpData),
            success: function(data) {
                showAlert('success', 'Data KTP berhasil ditambahkan');
                resetForm();
                loadKtpData();
            },
            error: function(xhr, status, error) {
                const errorMessage = xhr.responseJSON ? xhr.responseJSON : error;
                showAlert('danger', 'Gagal menambah data KTP: ' + errorMessage);
            }
        });
    }
    
    // Edit KTP
    function editKtp(id) {
        $.ajax({
            url: `${API_BASE_URL}/${id}`,
            method: 'GET',
            success: function(ktp) {
                $('#ktpId').val(ktp.id);
                $('#nomorKtp').val(ktp.nomorKtp);
                $('#namaLengkap').val(ktp.namaLengkap);
                $('#alamat').val(ktp.alamat);
                $('#tanggalLahir').val(ktp.tanggalLahir);
                $('#jenisKelamin').val(ktp.jenisKelamin);
                
                $('#formTitle').text('Edit Data KTP');
                $('#submitBtn').text('Update');
                $('#cancelBtn').show();
                
                // Scroll to form
                $('html, body').animate({
                    scrollTop: $('#ktpForm').offset().top - 100
                }, 500);
            },
            error: function(xhr, status, error) {
                showAlert('danger', 'Gagal mengambil data KTP: ' + error);
            }
        });
    }
    
    // Update KTP
    function updateKtp(id, ktpData) {
        $.ajax({
            url: `${API_BASE_URL}/${id}`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(ktpData),
            success: function(data) {
                showAlert('success', 'Data KTP berhasil diperbarui');
                resetForm();
                loadKtpData();
            },
            error: function(xhr, status, error) {
                const errorMessage = xhr.responseJSON ? xhr.responseJSON : error;
                showAlert('danger', 'Gagal memperbarui data KTP: ' + errorMessage);
            }
        });
    }
    
    // Delete KTP
    function deleteKtp(id) {
        if (confirm('Apakah Anda yakin ingin menghapus data KTP ini?')) {
            $.ajax({
                url: `${API_BASE_URL}/${id}`,
                method: 'DELETE',
                success: function(data) {
                    showAlert('success', 'Data KTP berhasil dihapus');
                    loadKtpData();
                },
                error: function(xhr, status, error) {
                    const errorMessage = xhr.responseJSON ? xhr.responseJSON : error;
                    showAlert('danger', 'Gagal menghapus data KTP: ' + errorMessage);
                }
            });
        }
    }
    
    // Reset form
    function resetForm() {
        $('#ktpForm')[0].reset();
        $('#ktpId').val('');
        $('#formTitle').text('Tambah Data KTP');
        $('#submitBtn').text('Simpan');
        $('#cancelBtn').hide();
    }
    
    // Show alert
    function showAlert(type, message) {
        const alertHtml = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        
        $('#alertContainer').html(alertHtml);
        
        // Auto dismiss after 5 seconds
        setTimeout(function() {
            $('.alert').alert('close');
        }, 5000);
    }
    
    // Format date
    function formatDate(dateString) {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    }
    
    // Validate form
    function validateForm() {
        const nomorKtp = $('#nomorKtp').val().trim();
        const namaLengkap = $('#namaLengkap').val().trim();
        const alamat = $('#alamat').val().trim();
        const tanggalLahir = $('#tanggalLahir').val();
        const jenisKelamin = $('#jenisKelamin').val();
        
        // Check if all fields are filled
        if (!nomorKtp) {
            showAlert('warning', 'Nomor KTP harus diisi');
            $('#nomorKtp').focus();
            return false;
        }
        
        if (!namaLengkap) {
            showAlert('warning', 'Nama lengkap harus diisi');
            $('#namaLengkap').focus();
            return false;
        }
        
        if (!alamat) {
            showAlert('warning', 'Alamat harus diisi');
            $('#alamat').focus();
            return false;
        }
        
        if (!tanggalLahir) {
            showAlert('warning', 'Tanggal lahir harus diisi');
            $('#tanggalLahir').focus();
            return false;
        }
        
        if (!jenisKelamin) {
            showAlert('warning', 'Jenis kelamin harus dipilih');
            $('#jenisKelamin').focus();
            return false;
        }
        
        // Validate nomor KTP (must be exactly 16 digits)
        if (!/^\d{16}$/.test(nomorKtp)) {
            showAlert('warning', 'Nomor KTP harus 16 digit angka');
            $('#nomorKtp').focus();
            return false;
        }
        
        return true;
    }
    
    // Add form validation before submit
    $('#ktpForm').on('submit', function(e) {
        if (!validateForm()) {
            e.preventDefault();
            return false;
        }
    });
    
    // Add real-time validation for nomor KTP
    $('#nomorKtp').on('input', function() {
        // Remove non-numeric characters
        this.value = this.value.replace(/[^0-9]/g, '').slice(0, 16);
        
        // Real-time validation feedback
        const value = this.value;
        if (value.length > 0 && value.length < 16) {
            $(this).removeClass('is-valid').addClass('is-invalid');
        } else if (value.length === 16) {
            $(this).removeClass('is-invalid').addClass('is-valid');
        } else {
            $(this).removeClass('is-valid is-invalid');
        }
    });
    
    // Add real-time validation for required fields
    $('#namaLengkap, #alamat, #tanggalLahir, #jenisKelamin').on('input change', function() {
        const value = $(this).val().trim();
        if (value) {
            $(this).removeClass('is-invalid').addClass('is-valid');
        } else {
            $(this).removeClass('is-valid').addClass('is-invalid');
        }
    });
    
    // Clear validation on focus
    $('#nomorKtp, #namaLengkap, #alamat, #tanggalLahir, #jenisKelamin').on('focus', function() {
        $(this).removeClass('is-invalid is-valid');
    });
    
    // Add character limit for nama lengkap
    $('#namaLengkap').attr('maxlength', '100');
    
    // Add character limit for alamat
    $('#alamat').attr('maxlength', '255');
});
