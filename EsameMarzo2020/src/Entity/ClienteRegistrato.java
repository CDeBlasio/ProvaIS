package Entity;

import java.awt.Image;
import java.util.Date;

public class ClienteRegistrato {
	private String nome;
	private String cognome;
	private String dataDiNascita;
	private String cf;
	private Image tesseraSanitaria;
	private String cartaDiCredito;
	
	public ClienteRegistrato(String cf,String nome, String cognome, String dataDiNascita,  Image tesseraSanitaria, String cartaDiCredito) {
		this.setNome(nome);
		this.setCognome(cognome);
		this.setDataDiNascita(dataDiNascita);
		this.setCf(cf);
		this.tesseraSanitaria=tesseraSanitaria;
		this.setCartaDiCredito(cartaDiCredito);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(String dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Image getTesseraSanitaria() {
		return tesseraSanitaria;
	}

	public void setTesseraSanitaria(Image tesseraSanitaria) {
		this.tesseraSanitaria = tesseraSanitaria;
	}

	public String getCartaDiCredito() {
		return cartaDiCredito;
	}

	public void setCartaDiCredito(String cartaDiCredito) {
		this.cartaDiCredito = cartaDiCredito;
	}
}
