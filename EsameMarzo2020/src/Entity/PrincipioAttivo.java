package Entity;

import java.io.Serializable;

public class PrincipioAttivo implements Serializable {
	private String codice;
	private String nome;
	private int quantita;
	private String tipo;
	private int dose;
	
	public PrincipioAttivo(String codice, String nome, int quantita, String tipo, int dose) {
		this.setCodice(codice);
		this.setNome(nome);
		this.setQuantita(quantita);
		this.setTipo(tipo);
		this.dose=dose;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDose() {
		return dose;
	}

	public void setDose(int dose) {
		this.dose = dose;
	}

}
