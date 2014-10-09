package br.com.starcode.domain;

import java.util.Date;

public class Turma {

	private Integer id;
	private String descricao;
	private Date inicioAulas;
	
	public Turma() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getInicioAulas() {
		return inicioAulas;
	}

	public void setInicioAulas(Date inicioAulas) {
		this.inicioAulas = inicioAulas;
	}
	
}
