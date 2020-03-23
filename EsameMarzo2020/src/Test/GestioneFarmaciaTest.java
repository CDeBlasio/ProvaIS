package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Control.GestioneClienti;
import Control.GestioneFarmacia;
import Database.DBManager;
import Entity.ClienteRegistrato;
import Entity.Farmaco;
import Entity.PrincipioAttivo;

class GestioneFarmaciaTest {
	
	GestioneFarmacia gestione;
	GestioneClienti gestionec;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		
try {
			
			Connection conn = DBManager.getConnection();
			
			String query;
			
			query = "CREATE TABLE CLIENTI("
					+" CF VARCHAR(30) PRIMARY KEY,"
					+" NOME VARCHAR(30),"
					+" COGNOME VARCHAR(30),"
					+" DATADINASCITA VARCHAR(30),"
					+" TESSERA IMAGE,"
					+" CARTA VARCHAR(30)"+
					");";
			
			try(PreparedStatement stmt = conn.prepareStatement(query)) {
				
				stmt.executeUpdate();
			}
			
			query = "CREATE TABLE PRINCIPI("
					+" CODICE VARCHAR(30) PRIMARY KEY,"
					+" NOME VARCHAR(30) NOT NULL,"
					+" QUANTITA INT,"
					+" TIPO VARCHAR(30),"
					+" DOSE INT"+
					");";
			
			try(PreparedStatement stmt = conn.prepareStatement(query)) {
							
							stmt.executeUpdate();
						}
			
			query = "CREATE TABLE FARMACI("
					+" CODICE VARCHAR(30) PRIMARY KEY,"
					+" NOME VARCHAR(30) NOT NULL,"
					+" PREZZO FLOAT,"
					+" QUANTITA INT,"
					+" PRESCRIVIBILE BOOLEAN,"
					+" PRINCIPIO OBJECT,"
					+" GALENICO BOOLEAN"+
					");";
			
			try(PreparedStatement stmt = conn.prepareStatement(query)) {
							
							stmt.executeUpdate();
						}
			System.out.println("Inizializzazione effettuata!!!");
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		
		
		try {
			
			Connection conn = DBManager.getConnection();
			
			String query;
			
			query = "DROP TABLE CLIENTI; DROP TABLE PRINCIPI; DROP TABLE FARMACI;";

			
			try(PreparedStatement stmt = conn.prepareStatement(query)) {
				
				stmt.executeUpdate();
			}
			
			
			System.out.println("Rimozione DB completata.");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		gestione = new GestioneFarmacia();
		gestionec = new GestioneClienti();
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		
		gestione = null;
		
		Connection conn = DBManager.getConnection();
		
		String query = "DELETE FROM CLIENTI;";
		
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			
			stmt.executeUpdate();
		}
				
		String query2 = "DELETE FROM PRINCIPI;";

		try(PreparedStatement stmt2 = conn.prepareStatement(query2)) {
			
			stmt2.executeUpdate();
		}
		
		String query3 = "DELETE FROM FARMACI;";

		try(PreparedStatement stmt3 = conn.prepareStatement(query3)) {
			
			stmt3.executeUpdate();
		}
	}

	@Test
	void test011FarmacoPresenteGalenicoPrescrivibileConRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 500, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.get(0).getCodice(), farmaco1ac.getCodice());
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 350);
	}
	
	@Test
	void test021FarmacoPresenteGalenicoPrescrivibileSenzaRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 500, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.get(0).getCodice(), farmaco1ac.getCodice());
		assertEquals(gestione.calcolaTotale(i), 33);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 350);
	}
	
	@Test
	void test031FarmacoPresenteGalenicoNonPrescrivibile() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 500, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.get(0).getCodice(), farmaco1ac.getCodice());
		assertEquals(gestione.calcolaTotale(i), 33);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 350);
	}
	
	@Test
	void test041FarmacoNonPresenteGalenico() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 30, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), true);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 30);
	}
	
	@Test
	void test051FarmacoPresenteCommericialePrescrivibileConRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		Farmaco farmaco=new Farmaco("A1", "Tachipirina",11, 50, true, principi, false);
		gestione.inserisciFarmaco(farmaco);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, false);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaFarmaco(farmaco).getQuantita(), 47);
	}
	
	@Test
	void test061FarmacoPresenteCommericialePrescrivibileSenzaRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		Farmaco farmaco=new Farmaco("A1", "Tachipirina",11, 50, true, principi, false);
		gestione.inserisciFarmaco(farmaco);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, false);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 33);
		assertEquals(gestione.visualizzaFarmaco(farmaco).getQuantita(), 47);
	}
	
	@Test
	void test071FarmacoPresenteCommericialeNonPrescrivibile() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		Farmaco farmaco=new Farmaco("A1", "Tachipirina",11, 50, false, principi, false);
		gestione.inserisciFarmaco(farmaco);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi, false);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 33);
		assertEquals(gestione.visualizzaFarmaco(farmaco).getQuantita(), 47);
	}
	
	@Test
	void test081FarmacoNonPresenteCommericiale() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		Farmaco farmaco=new Farmaco("A1", "Tachipirina",11, 2, false, principi, false);
		gestione.inserisciFarmaco(farmaco);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi, false);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), true);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaFarmaco(farmaco1ac).getQuantita(), 2);
	}
	
	@Test
	void test091FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoPresentePrescrivibileConRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 200, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 50);
	}
	
	@Test
	void test101FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoPresentePrescrivibileSenzaRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 200, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 43);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 50);
	}
	
	@Test
	void test111FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoPresenteNonPrescrivibileConRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 200, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 33);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 50);
	}
	
	@Test
	void test121FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoPresenteNonPrescrivibileSenzaRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 200, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 43);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 50);
	}
	
	
	@Test
	void test131FarmacoPresenteCommericialeNonPrescrivibile1FarmacoGalenicoPresenteNonPrescrivibile() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 200, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, false, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, false, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 43);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 50);
	}
	
	@Test
	void test141FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoNonPresenteConRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 20, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 20);
	}
	
	@Test
	void test151FarmacoPresenteCommericialePrescrivibile1FarmacoGalenicoNonPresenteSenzaRicetta() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, true, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, true, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 10);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 0);
	}
	
	@Test
	void test161FarmacoPresenteCommericialeNonPrescrivibile1FarmacoGalenicoNonPresente() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 20, false, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, false, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), false);
		assertEquals(gestione.calcolaTotale(i), 10);
		assertEquals(gestione.visualizzaFarmaco(farmaco2).getQuantita(), 18);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 0);
	}
	
	
	@Test
	void test171FarmacoNonPresenteCommericiale1FarmacoGalenicoNonPresente() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		Farmaco farmaco2=new Farmaco("A2", "Zyloric",5, 1, false, principi, false);
		gestione.inserisciFarmaco(farmaco2);
		
		ArrayList<PrincipioAttivo> principi1=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio11= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi1.add(principio11);
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, false, principi1, true);
		Farmaco farmaco2ac=new Farmaco("A2", "Zyloric",5, 2, false, principi, false);
		acquisto.add(farmaco1ac);
		acquisto.add(farmaco2ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), true);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaFarmaco(farmaco2ac).getQuantita(), 1);
		assertEquals(gestione.visualizzaPrincipio(principio1).getQuantita(), 0);
	}
	
	@Test
	void test181FarmacoGalenicoPrescrivibileConRicetta2PrincipiPresenti() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 500, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		PrincipioAttivo principio2= new PrincipioAttivo("P2", "Allopurinolo", 500, "Antigottosi", 0);
		gestione.inserisciPrincipio(principio2);
		
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.get(0).getCodice(), farmaco1ac.getCodice());
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 350);
		assertEquals(gestione.visualizzaPrincipio(principio2).getQuantita(), 440);
	}
	
	@Test
	void test191FarmacoGalenicoPrescrivibileConRicetta2Principi1PresenteNonPresente() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 20, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		PrincipioAttivo principio2= new PrincipioAttivo("P2", "Allopurinolo", 500, "Antigottosi", 0);
		gestione.inserisciPrincipio(principio2);
		
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), true);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 20);
		assertEquals(gestione.visualizzaPrincipio(principio2).getQuantita(), 500);
	}
	
	@Test
	void test201FarmacoGalenicoPrescrivibileConRicetta2PrincipiNonPresenti() throws SQLException {
		ClienteRegistrato cliente= new ClienteRegistrato("DBLCIllABF7788", "Ciro", "De Blasio","21/01/1998", null, "AAAAA" );
		String ricetta="AAA";
		gestionec.inserisciCliente(cliente);
		
		PrincipioAttivo principio1= new PrincipioAttivo("P1", "Paracetamolo", 20, "Analgesico", 0);
		gestione.inserisciPrincipio(principio1);
		
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		PrincipioAttivo principio= new PrincipioAttivo("P1", "Paracetamolo", 0, "Analgesico", 50);
		principi.add(principio);
		
		PrincipioAttivo principio2= new PrincipioAttivo("P2", "Allopurinolo", 10, "Antigottosi", 0);
		gestione.inserisciPrincipio(principio2);
		
		PrincipioAttivo principio22= new PrincipioAttivo("P2", "Allopurinolo", 0, "Antigottosi", 20);
		principi.add(principio22);
		
		
		ArrayList<Farmaco> acquisto=new ArrayList<Farmaco>();
		Farmaco farmaco1ac=new Farmaco("A1", "Tachipirina",11, 3, true, principi, true);
		acquisto.add(farmaco1ac);
		ArrayList<Farmaco> i=gestione.acquistaFarmaci(acquisto, cliente, ricetta);
		assertEquals(i.isEmpty(), true);
		assertEquals(gestione.calcolaTotale(i), 0);
		assertEquals(gestione.visualizzaPrincipio(principio).getQuantita(), 20);
		assertEquals(gestione.visualizzaPrincipio(principio2).getQuantita(), 10);
	}

}
