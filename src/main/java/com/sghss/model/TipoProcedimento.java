package com.sghss.model;

public enum TipoProcedimento {
	CONSULTA(CodigoProcedimento.PROC001), EXAME(CodigoProcedimento.PROC002), CIRURGIA(CodigoProcedimento.PROC003),
	INTERNACAO(CodigoProcedimento.PROC004), VACINA(CodigoProcedimento.PROC005), RETORNO(CodigoProcedimento.PROC006),
	TERAPIA(CodigoProcedimento.PROC007);

	private final CodigoProcedimento codigoPadrao;

	TipoProcedimento(CodigoProcedimento codigoPadrao) {
		this.codigoPadrao = codigoPadrao;
	}

	public CodigoProcedimento getCodigoPadrao() {
		return codigoPadrao;
	}
}