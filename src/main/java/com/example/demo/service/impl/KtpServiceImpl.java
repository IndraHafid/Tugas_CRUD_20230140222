package com.example.demo.service.impl;

import com.example.demo.entity.Ktp;
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
        if (ktpRepository.existsByNomorKtp(ktp.getNomorKtp())) {
            throw new RuntimeException("Nomor KTP sudah ada: " + ktp.getNomorKtp());
        }
        return ktpRepository.save(ktp);
    }

    @Override
    public Ktp updateKtp(Integer id, Ktp ktp) {
        Optional<Ktp> existingKtp = ktpRepository.findById(id);
        if (existingKtp.isEmpty()) {
            throw new RuntimeException("Data KTP dengan id " + id + " tidak ditemukan");
        }

        Ktp updatedKtp = existingKtp.get();
        
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
}
