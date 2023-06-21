package com.NGTS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.NGTS.dto.ngtsRutaDto;
import com.NGTS.service.IviajeService;
import com.NGTS.service.impl.viajeService;

@RestController
public class viajeController {

	@Autowired
	private IviajeService viajeService;
	
	private final static String RUTA_MINIMA = "/rutaMinima/{origen}/{destino}";
	
	@GetMapping(RUTA_MINIMA)
    public List<ngtsRutaDto> obtenerRutaMinima(@PathVariable String origen, @PathVariable String destino) {
        return viajeService.getViajeMinimo(origen, destino);
    }
}
