import com.example.demo.entity.Ktp;
import com.example.demo.model.KtpModel;
import com.example.demo.repository.KtpRepository;
import com.example.demo.service.KtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KtpServiceImpl implements KtpService {

    @Autowired
    private KtpRepository ktpRepository;

    @Override
    public List<Ktp> getAllKtp() {
        return ktpRepository.findAll();
    }

    @Override
    public Optional<Ktp> getKtpById(Integer id) {
        return ktpRepository.findById(id);
    }

    @Override
    public Ktp createKtp(Ktp ktp) {
        // Validate using model
        KtpModel model = convertToModel(ktp);
        if (!model.isValid()) {
            throw new RuntimeException("Data KTP tidak valid");
        }
        
        if (ktpRepository.existsByNomorKtp(ktp.getNomorKtp())) {
            throw new RuntimeException("Nomor KTP sudah ada: " + ktp.getNomorKtp());
        }
        return ktpRepository.save(ktp);
    }

    @Override
    public Ktp updateKtp(Integer id, Ktp ktp) {
        // Validate using model
        KtpModel model = convertToModel(ktp);
        if (!model.isValid()) {
            throw new RuntimeException("Data KTP tidak valid");
        }
        
        Optional<Ktp> existingKtp = ktpRepository.findById(id);
        if (existingKtp.isEmpty()) {
            throw new RuntimeException("Data KTP dengan id " + id + " tidak ditemukan");
        }

        Ktp updatedKtp = existingKtp.get();
        
        // Check if nomorKtp is being changed and if it already exists
        if (!updatedKtp.getNomorKtp().equals(ktp.getNomorKtp()) && 
            ktpRepository.existsByNomorKtp(ktp.getNomorKtp())) {
            throw new RuntimeException("Nomor KTP sudah ada: " + ktp.getNomorKtp());
        }

        updatedKtp.setNomorKtp(ktp.getNomorKtp());
        updatedKtp.setNamaLengkap(ktp.getNamaLengkap());
        updatedKtp.setAlamat(ktp.getAlamat());
        updatedKtp.setTanggalLahir(ktp.getTanggalLahir());
        updatedKtp.setJenisKelamin(ktp.getJenisKelamin());

        return ktpRepository.save(updatedKtp);
    }

    @Override
    public void deleteKtp(Integer id) {
        if (!ktpRepository.existsById(id)) {
            throw new RuntimeException("Data KTP dengan id " + id + " tidak ditemukan");
        }
        ktpRepository.deleteById(id);
    }
    
    // Private conversion methods
    private KtpModel convertToModel(Ktp ktp) {
        return new KtpModel(
            ktp.getId(),
            ktp.getNomorKtp(),
            ktp.getNamaLengkap(),
            ktp.getAlamat(),
            ktp.getTanggalLahir(),
            ktp.getJenisKelamin()
        );
    }
    
    // Private validation method
    private void validateKtpData(Ktp ktp) {
        if (ktp.getNomorKtp() == null || ktp.getNomorKtp().trim().isEmpty()) {
            throw new RuntimeException("Nomor KTP harus diisi");
        }
        
        if (!ktp.getNomorKtp().matches("\\d{16}")) {
            throw new RuntimeException("Nomor KTP harus 16 digit angka");
        }
        
        if (ktp.getNamaLengkap() == null || ktp.getNamaLengkap().trim().isEmpty()) {
            throw new RuntimeException("Nama lengkap harus diisi");
        }
        
        if (ktp.getAlamat() == null || ktp.getAlamat().trim().isEmpty()) {
            throw new RuntimeException("Alamat harus diisi");
        }
        
        if (ktp.getTanggalLahir() == null) {
            throw new RuntimeException("Tanggal lahir harus diisi");
        }
        
        if (ktp.getJenisKelamin() == null || ktp.getJenisKelamin().trim().isEmpty()) {
            throw new RuntimeException("Jenis kelamin harus diisi");
        }
        
        if (!ktp.getJenisKelamin().equals("Laki-laki") && !ktp.getJenisKelamin().equals("Perempuan")) {
            throw new RuntimeException("Jenis kelamin harus Laki-laki atau Perempuan");
        }
    }
}
