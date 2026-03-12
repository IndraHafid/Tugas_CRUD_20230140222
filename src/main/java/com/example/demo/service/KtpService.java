package com.example.demo.service;

import com.example.demo.entity.Ktp;
import java.util.List;
import java.util.Optional;

public interface KtpService {
    List<Ktp> getAllKtp();
    Optional<Ktp> getKtpById(Integer id);
    Ktp createKtp(Ktp ktp);
    Ktp updateKtp(Integer id, Ktp ktp);
    void deleteKtp(Integer id);
}
