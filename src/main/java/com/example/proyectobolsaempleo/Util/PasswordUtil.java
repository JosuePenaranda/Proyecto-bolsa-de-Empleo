package com.example.proyectobolsaempleo.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Encriptar contraseña
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    // Verificar contraseña
    public static boolean verificarPassword(String passwordIngresada, String hashGuardado) {
        return encoder.matches(passwordIngresada, hashGuardado);
    }
}