package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class KtpModel {
    private Integer id;
    private String nomorKtp;
    private String namaLengkap;
    private String alamat;
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    
    // Business logic methods
    public boolean isValid() {
        return nomorKtp != null && nomorKtp.matches("\\d{16}") &&
               namaLengkap != null && !namaLengkap.trim().isEmpty() &&
               alamat != null && !alamat.trim().isEmpty() &&
               tanggalLahir != null &&
               jenisKelamin != null && !jenisKelamin.trim().isEmpty();
    }
    
    public String getFormattedNomorKtp() {
        if (nomorKtp == null || nomorKtp.length() != 16) {
            return nomorKtp;
        }
        return nomorKtp.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3-$4");
    }
}
