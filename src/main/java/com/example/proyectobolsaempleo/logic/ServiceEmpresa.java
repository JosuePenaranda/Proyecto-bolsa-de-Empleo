package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Service("serviceEmpresa")
public class ServiceEmpresa {
    @Autowired
    private EmpresaRepository empresaRepository;

}
