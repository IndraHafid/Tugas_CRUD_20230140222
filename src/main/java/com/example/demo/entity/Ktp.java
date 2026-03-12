package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ktp")
@Data @NoArgsConstructor @AllArgsConstructor
public class Ktp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nomor KTP harus diisi")
    @Pattern(regexp = "\\d{16}", message = "Nomor KTP harus 16 digit angka")
    @Column(unique = true, nullable = false, length = 16)
    private String nomorKtp;

    @NotBlank(message = "Nama lengkap harus diisi")
    @Size(max = 100, message = "Nama lengkap maksimal 100 karakter")
    @Column(nullable = false)
    private String namaLengkap;

    @NotBlank(message = "Alamat harus diisi")
    @Size(max = 255, message = "Alamat maksimal 255 karakter")
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String alamat;

    @NotNull(message = "Tanggal lahir harus diisi")
    @Past(message = "Tanggal lahir harus di masa lalu")
    @Column(name = "tanggalLahir", nullable = false)
    private LocalDate tanggalLahir;

    @NotBlank(message = "Jenis kelamin harus diisi")
    @Pattern(regexp = "^(Laki-laki|Perempuan)$", message = "Jenis kelamin harus Laki-laki atau Perempuan")
    @Column(nullable = false)
    private String jenisKelamin;
}
