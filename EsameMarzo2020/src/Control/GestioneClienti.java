package Control;

import java.sql.SQLException;

import Database.ClientiDAO;
import Entity.ClienteRegistrato;

public class GestioneClienti {
	
	public void inserisciCliente(ClienteRegistrato cliente) throws SQLException {
		ClientiDAO.insertCliente(cliente);;
	}
	
	public void modificaCliente(ClienteRegistrato cliente, String cartaDiCredito) throws SQLException {
			ClientiDAO.editCliente(cliente, cartaDiCredito);;
		}
	
	public void eliminaCliente(ClienteRegistrato cliente) throws SQLException {
			ClientiDAO.deleteCliente(cliente);
		}

}
