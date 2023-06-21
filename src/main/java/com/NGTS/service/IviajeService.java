package com.NGTS.service;

import java.util.List;

import com.NGTS.dto.ngtsRutaDto;

public interface IviajeService {
	public List<ngtsRutaDto> getViajeMinimo(String origen, String destino);
}
