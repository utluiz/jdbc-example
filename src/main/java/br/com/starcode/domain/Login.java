package br.com.starcode.domain;

import java.util.Date;

public class Login {

	private int codigo;
	private String email;
	private String nome;
	private String senha;
	private Date dateTime;
	private int tentativasLogin;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public int getTentativasLogin() {
		return tentativasLogin;
	}
	public void setTentativasLogin(int tentativasLogin) {
		this.tentativasLogin = tentativasLogin;
	}
	
}
