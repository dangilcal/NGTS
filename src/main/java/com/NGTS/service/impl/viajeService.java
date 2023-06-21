package com.NGTS.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.NGTS.dto.ngtsRutaDto;
import com.NGTS.service.IviajeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class viajeService implements IviajeService{
	
	private final static String JSON_RUTA = "src/main/resources/static/ZGTS_rutas.json";

	private Map<String, List<ngtsRutaDto>> RutaMinima(ngtsRutaDto[] rutas) {
		Map<String, List<ngtsRutaDto>> grafo = new HashMap<>();

        for (ngtsRutaDto ruta : rutas) {
            if (!grafo.containsKey(ruta.getOrigen())) {
                grafo.put(ruta.getOrigen(), new ArrayList<>());
            }else if(!grafo.containsKey(ruta.getDestino())) {
            	grafo.put(ruta.getDestino(), new ArrayList<>());
            }
            grafo.get(ruta.getOrigen()).add(ruta);
        }
        return grafo;
    }
	
	
	
	 public List<ngtsRutaDto> getViajeMinimo(String origen, String destino) {
		 
		 ObjectMapper objectMapper = new ObjectMapper();
		 try {
			File file = new File(JSON_RUTA);
			ngtsRutaDto[] rutas = objectMapper.readValue(file, ngtsRutaDto[].class);
			Map<String, List<ngtsRutaDto>> grafo = RutaMinima(rutas);
			
	        Set<String> visitados = new HashSet<>();

	        Map<String, Integer> precio = new HashMap<>();

	        Map<String, ngtsRutaDto> previos = new HashMap<>();
			
	        for (String localidad : grafo.keySet()) {
	            if (localidad.equals(origen)) {
	            	precio.put(localidad, 0);
	            } else {
	            	precio.put(localidad, 9999);
	            }
	        }
	        
	        while (visitados.size() < grafo.size()) {
	            String nodoMinCoste = null;
	            int minCoste = 9999;
	            for (String localidad : grafo.keySet()) {
	                if (!visitados.contains(localidad) && precio.get(localidad) < minCoste) {
	                    minCoste = precio.get(localidad);
	                    nodoMinCoste = localidad;
	                }
	            }
	            visitados.add(nodoMinCoste);

	            List<ngtsRutaDto> rutasDesdeActual = grafo.get(nodoMinCoste);
	            if (rutasDesdeActual != null) {
	                for (ngtsRutaDto ruta : rutasDesdeActual) {
	                    String localidadDestino = ruta.getDestino();
	                    int costeHastaDestino = precio.get(nodoMinCoste) + ruta.getCoste();
	                    	if (costeHastaDestino < precio.get(localidadDestino)) {
	                    		precio.put(localidadDestino, costeHastaDestino);
	                    		previos.put(localidadDestino, ruta);
	                    	}
	                }
	            }
	        }
	        List<ngtsRutaDto> caminoMinimo = new ArrayList<>();
	        ngtsRutaDto rutaPrevia = previos.get(destino);
	        while (rutaPrevia != null) {
	            caminoMinimo.add(0, rutaPrevia);
	            String localidadOrigenPrevio = rutaPrevia.getOrigen();
	            rutaPrevia = previos.get(localidadOrigenPrevio);
	        }
			return caminoMinimo;
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	    }
	 
}
