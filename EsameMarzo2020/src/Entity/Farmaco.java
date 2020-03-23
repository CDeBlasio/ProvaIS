package Entity;

import java.util.ArrayList;

public class Farmaco {
	private String codice;
	private String nome;
	private float prezzo;
	private int quantita;
	private boolean prescrivibile;
	private ArrayList<PrincipioAttivo> principiAttivi;
	private boolean galenico;
	
	public Farmaco(String codice, String nome, float prezzo, int quantita, boolean prescrivibile, ArrayList<PrincipioAttivo> principiAttivi, boolean galenico) {
		this.codice=codice;
		this.nome=nome;
		this.prescrivibile=prescrivibile;
		this.prezzo=prezzo;
		this.quantita=quantita;
		this.principiAttivi=principiAttivi;
		this.galenico=galenico;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isPrescrivibile() {
		return prescrivibile;
	}

	public void setPrescrivibile(boolean prescrivibile) {
		this.prescrivibile = prescrivibile;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public ArrayList<PrincipioAttivo> getPrincipiAttivi() {
		return principiAttivi;
	}

	public void setPrincipiAttivi(ArrayList<PrincipioAttivo> principiAttivi) {
		this.principiAttivi = principiAttivi;
	}

	public boolean isGalenico() {
		return galenico;
	}

	public void setGalenico(boolean galenico) {
		this.galenico = galenico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
