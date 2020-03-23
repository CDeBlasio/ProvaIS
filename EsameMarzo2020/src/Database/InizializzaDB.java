package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InizializzaDB {

	public static void main(String[] args) {
		
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
}
