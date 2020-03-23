package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.PrincipioAttivo;

public class PrincipiDAO {
	
	public static void insertPrincipioAttivo (PrincipioAttivo principio)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "INSERT INTO PRINCIPI VALUES('"+principio.getCodice()+"',?,?,?,?);";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			
			stmt.setString(1, principio.getNome());
			stmt.setInt(2, principio.getQuantita());
			stmt.setString(3, principio.getTipo());
			stmt.setInt(4, principio.getDose());
			stmt.executeUpdate();
		}	
	}
	
	public static void editPrincipioAttivo (PrincipioAttivo principio, int quantita)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "UPDATE PRINCIPI SET QUANTITA="+quantita+" WHERE CODICE='"+principio.getCodice()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.executeUpdate();
		}	
	}
	
	
	public static PrincipioAttivo selectPrincipio (PrincipioAttivo principio)throws SQLException{
		Connection conn=DBManager.getConnection();
		PrincipioAttivo principio1=null;
		String query = "SELECT * FROM PRINCIPI WHERE CODICE='"+principio.getCodice()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			try(ResultSet result = stmt.executeQuery()) {
				while(result.next()) {
					String codice = result.getString(1);
					String nome = result.getString(2);
					int quantita = result.getInt(3);
					String tipo = result.getString(4);
					int dose = result.getInt(5);
					PrincipioAttivo principioResult=new PrincipioAttivo(codice, nome, quantita, tipo, dose);
					principio1=principioResult;
						}
					}
				}
		return principio1;
		}	
	
	public static ArrayList<PrincipioAttivo> selectAllPrincipiAttivi ()throws SQLException{
		Connection conn=DBManager.getConnection();
		ArrayList<PrincipioAttivo> principi=new ArrayList<PrincipioAttivo>();
		String query = "SELECT * FROM PRINCIPI;";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			try(ResultSet result = stmt.executeQuery()) {
				while(result.next()) {
					String codice = result.getString(1);
					String nome = result.getString(2);
					int quantita = result.getInt(3);
					String tipo = result.getString(4);
					int dose = result.getInt(5);
					PrincipioAttivo principio=new PrincipioAttivo(codice, nome, quantita, tipo, dose);
					principi.add(principio);
						}
					}
				}
		return principi;
		}

}
