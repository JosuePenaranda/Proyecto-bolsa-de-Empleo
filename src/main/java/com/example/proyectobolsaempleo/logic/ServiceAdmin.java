package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Service("serviceAdmin")
public class ServiceAdmin {
    @Autowired
    private AdminRepository adminRepository;

}