package com.example.demo.util;

public class ValidationUtil {
    
    public static boolean isValidNomorKtp(String nomorKtp) {
        return nomorKtp != null && nomorKtp.matches("\\d{16}");
    }
    
    public static boolean isValidJenisKelamin(String jenisKelamin) {
        return "Laki-laki".equals(jenisKelamin) || "Perempuan".equals(jenisKelamin);
    }
    
    public static String formatNomorKtp(String nomorKtp) {
        if (nomorKtp == null || nomorKtp.length() != 16) {
            return nomorKtp;
        }
        return nomorKtp.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3-$4");
    }
}
