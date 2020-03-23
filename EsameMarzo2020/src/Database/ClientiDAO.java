package Database;

import java.awt.Image;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.ClienteRegistrato;

public class ClientiDAO {
	
	public static void insertCliente (ClienteRegistrato cliente)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "INSERT INTO CLIENTI VALUES('"+cliente.getCf()+"',?,?,?,?,?);";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCognome());
			stmt.setString(3, cliente.getDataDiNascita());
			stmt.setObject(4, cliente.getTesseraSanitaria());
			stmt.setString(5, cliente.getCartaDiCredito());
			stmt.executeUpdate();
		}	
	}
	
	public static void editCliente (ClienteRegistrato cliente, String cartaDiCredito)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "UPDATE CLIENTI SET CARTADICREDITO="+cartaDiCredito+" WHERE CF='"+cliente.getCf()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.executeUpdate();
		}	
	}
	
	public static void deleteCliente (ClienteRegistrato cliente)throws SQLException{
		Connection conn=DBManager.getConnection();
		String query = "DELETE FROM CLIENTI WHERE CF='"+cliente.getCf()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.executeUpdate();
		}	
	}
	
	public static ClienteRegistrato selectCliente (ClienteRegistrato cliente)throws SQLException{
		Connection conn=DBManager.getConnection();
		ClienteRegistrato cliente1=null;
		String query = "SELECT * FROM CLIENTI WHERE CF='"+cliente.getCf()+"';";
		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			try(ResultSet result = stmt.executeQuery()) {
				while(result.next()) {
					String nome=result.getString(1);
					String cognome=result.getString(2);
					String data=result.getString(3);
					String cf=result.getString(4);
					Image tessera=(Image) result.getObject(5);
					String carta=result.getString(6);
					ClienteRegistrato clienteResult=new ClienteRegistrato(nome, cognome, data, cf, tessera, carta);
					cliente1=clienteResult;
						}
					}
				}
		return cliente1;
		}	

}
