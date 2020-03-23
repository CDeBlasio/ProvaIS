package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.Farmaco;
import Entity.PrincipioAttivo;

public class FarmaciDAO {
	
	public static void insertFarmaco (Farmaco farmaco)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "INSERT INTO FARMACI VALUES('"+farmaco.getCodice()+"',?,?,?,?,?,?);";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			
			stmt.setString(1, farmaco.getNome());
			stmt.setFloat(2, farmaco.getPrezzo());
			stmt.setInt(3, farmaco.getQuantita());
			stmt.setBoolean(4, farmaco.isPrescrivibile());
			stmt.setObject(5, farmaco.getPrincipiAttivi());
			stmt.setBoolean(6, farmaco.isGalenico());
			stmt.executeUpdate();
		}	
	}
	
	public static void editFarmaco (Farmaco farmaco, int quantita)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "UPDATE FARMACI SET QUANTITA="+quantita+" WHERE CODICE='"+farmaco.getCodice()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.executeUpdate();
		}	
	}
	
	public static void deleteFarmaco (Farmaco farmaco)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "DELETE FROM FARMACI WHERE CODICE='"+farmaco.getCodice()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.executeUpdate();
		}	
	}
	
	public static Farmaco selectFarmaco (Farmaco farmaco)throws SQLException{
		Connection conn=DBManager.getConnection();
		Farmaco farmaco1=null;
		String query = "SELECT * FROM FARMACI WHERE CODICE='"+farmaco.getCodice()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			try(ResultSet result = stmt.executeQuery()) {
				while(result.next()) {
					String codice = result.getString(1);
					String nome = result.getString(2);
					float prezzo= result.getFloat(3);
					int quantita = result.getInt(4);
					boolean prescrivibile = result.getBoolean(5);
					ArrayList<PrincipioAttivo> principi = (ArrayList<PrincipioAttivo>) result.getObject(6);
					boolean galenico = result.getBoolean(7);
					Farmaco farmacoResult=new Farmaco(codice, nome, prezzo, quantita, prescrivibile, principi, galenico);
					farmaco1=farmacoResult;
						}
					}
				}
		return farmaco1;
		}	
	
	public static ArrayList<Farmaco> selectAllFarmaci ()throws SQLException{
		Connection conn=DBManager.getConnection();
		ArrayList<Farmaco> farmaci=new ArrayList<Farmaco>();
		String query = "SELECT * FROM FARMACI;";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			try(ResultSet result = stmt.executeQuery()) {
				while(result.next()) {
					String codice = result.getString(1);
					String nome = result.getString(2);
					float prezzo = result.getFloat(3);
					int quantita = result.getInt(4);
					boolean prescrivibile = result.getBoolean(5);
					ArrayList<PrincipioAttivo> principi = (ArrayList<PrincipioAttivo>) result.getObject(6);
					boolean galenico = result.getBoolean(7);
					Farmaco farmaco=new Farmaco(codice, nome, prezzo, quantita, prescrivibile,principi, galenico);
					farmaci.add(farmaco);
						}
					}
				}
		return farmaci;
		}
	}

