package Boundary;

import java.sql.SQLException;
import java.util.ArrayList;

import Control.GestioneClienti;
import Control.GestioneFarmacia;
import Entity.ClienteRegistrato;
import Entity.Farmaco;
import Entity.PrincipioAttivo;

public class Main {

	public static void main(String[] args) throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		GestioneFarmacia gestione=new GestioneFarmacia();
		GestioneClienti gestionec=new GestioneClienti();
		
		gestionec.inserisciCliente(cliente);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 500, "Analgesico", 0);
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo",0 , "Analgesico", 50);
		
		principi1.add(principio11);
		Farmaco farmaco1=new Farmaco("A1", "Tachipirina",15, 20, true, principi1, true);
		gestione.inserisciFarmaco(farmaco1);
		gestione.inserisciPrincipio(principio1);
		String ricetta="AAA";
		ArrayList<PrincipioAttivo> principi2=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio2= new PrincipioAttivo("P2", "Allopurinolo", 100, "Antigottosi", 0);
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi2.add(principio22);
		principi2.add(principio11);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, false, principi2, false);
		gestione.inserisciFarmaco(farmaco2);
		gestione.inserisciPrincipio(principio2);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",15, 2, true, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, false, principi2, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		ArrayList<Farmaco> farm=gestione.visualizzaFarmaci();
		ArrayList<PrincipioAttivo> prin=gestione.visualizzaPrincipi();
		for(int j=0; j<farm.size(); j++) {
			System.out.println(farm.get(j).getCodice()+farm.get(j).getNome()+farm.get(j).getQuantita());
		}
		for(int j=0; j<prin.size(); j++) {
			System.out.println(prin.get(j).getCodice()+prin.get(j).getNome()+prin.get(j).getQuantita());
		}
		System.out.println(i);
		
		
	}

}
