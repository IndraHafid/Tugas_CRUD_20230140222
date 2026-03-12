package com.example.demo.mapper;

import com.example.demo.dto.KtpRequestDto;
import com.example.demo.dto.KtpResponseDto;
import com.example.demo.entity.Ktp;
import org.springframework.stereotype.Component;

@Component
public class KtpMapper {
    
    public Ktp toEntity(KtpRequestDto dto) {
        if (dto == null) {
            return null;
        }
        
        Ktp ktp = new Ktp();
        ktp.setNomorKtp(dto.getNomorKtp());
        ktp.setNamaLengkap(dto.getNamaLengkap());
        ktp.setAlamat(dto.getAlamat());
        ktp.setTanggalLahir(dto.getTanggalLahir());
        ktp.setJenisKelamin(dto.getJenisKelamin());
        return ktp;
    }
    
    public KtpResponseDto toResponseDto(Ktp entity) {
        if (entity == null) {
            return null;
        }
        
        KtpResponseDto dto = new KtpResponseDto();
        dto.setId(entity.getId());
        dto.setNomorKtp(entity.getNomorKtp());
        dto.setNamaLengkap(entity.getNamaLengkap());
        dto.setAlamat(entity.getAlamat());
        dto.setTanggalLahir(entity.getTanggalLahir());
        dto.setJenisKelamin(entity.getJenisKelamin());
        return dto;
    }
}
