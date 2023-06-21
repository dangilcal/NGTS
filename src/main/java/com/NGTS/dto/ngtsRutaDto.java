package com.NGTS.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ngtsRutaDto implements Serializable{
	private String origen;
    private String destino;
    private int coste;
}
