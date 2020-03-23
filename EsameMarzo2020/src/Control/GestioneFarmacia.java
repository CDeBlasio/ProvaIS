package Control;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.ClientiDAO;
import Database.FarmaciDAO;
import Database.PrincipiDAO;
import Entity.ClienteRegistrato;
import Entity.Farmaco;
import Entity.PrincipioAttivo;

public class GestioneFarmacia {

	
	public void inserisciFarmaco(Farmaco farmaco) throws SQLException {
		FarmaciDAO.insertFarmaco(farmaco);
	}
	
	public void modificaFarmaco(Farmaco farmaco, int quantita) throws SQLException {
			FarmaciDAO.editFarmaco(farmaco, quantita);
		}
	
	public void eliminaFarmaco(Farmaco farmaco) throws SQLException {
			FarmaciDAO.deleteFarmaco(farmaco);
		}
	
	public Farmaco visualizzaFarmaco(Farmaco farmaco) throws SQLException {
		return FarmaciDAO.selectFarmaco(farmaco);
	}
	
	public void inserisciPrincipio(PrincipioAttivo principio) throws SQLException {
		PrincipiDAO.insertPrincipioAttivo(principio);
	}
	
	public void modificaPrincipio(PrincipioAttivo principio, int quantita) throws SQLException {
			PrincipiDAO.editPrincipioAttivo(principio, quantita);;
		}
	
	public PrincipioAttivo visualizzaPrincipio(PrincipioAttivo principio) throws SQLException {
		return PrincipiDAO.selectPrincipio(principio);
	}
	
	
	public ArrayList<Farmaco> visualizzaFarmaci() throws SQLException {
			return FarmaciDAO.selectAllFarmaci();
		}
	
	public ArrayList<PrincipioAttivo> visualizzaPrincipi() throws SQLException {
		return PrincipiDAO.selectAllPrincipiAttivi();
	}
	
	public ArrayList<Farmaco> acquistaFarmaci(ArrayList<Farmaco> farmaci, ClienteRegistrato cliente, String ricetta) throws SQLException {
			ArrayList<Farmaco> carrello=new ArrayList<Farmaco>();
			for(int i=0; i<farmaci.size(); i++) {
				if(farmaci.get(i).isGalenico()) {
					ArrayList<PrincipioAttivo> principi=farmaci.get(i).getPrincipiAttivi();
					int j=0;
					PrincipioAttivo prin=PrincipiDAO.selectPrincipio(principi.get(j));
					while(j<principi.size() && prin!=null && farmaci.get(i).getQuantita()*principi.get(j).getDose()<=prin.getQuantita()) {
						prin=PrincipiDAO.selectPrincipio(principi.get(j));
						j++;
						}
						if(j==principi.size()) {
							if(farmaci.get(i).isPrescrivibile() && ricetta!="") {
								farmaci.get(i).setPrezzo(0);
							}
							carrello.add(farmaci.get(i));
						}
					}
				else {
					Farmaco farm=FarmaciDAO.selectFarmaco(farmaci.get(i));
					if(farm!=null && farm.getQuantita()>=farmaci.get(i).getQuantita()) {
						if(farm.isPrescrivibile() && ricetta!="") {
							farmaci.get(i).setPrezzo(0);
						}
						carrello.add(farmaci.get(i));
					}
				}
			}
			
			for(int i=0; i<carrello.size(); i++) {
				System.out.println("CARRELLO: "+carrello.get(i).getCodice()+carrello.get(i).getNome()+carrello.get(i).getQuantita()+"$"+carrello.get(i).getPrezzo());
			}
			if(autorizzaAcquisto(carrello,"AAA")) {
				ClienteRegistrato clienteResult=ClientiDAO.selectCliente(cliente);
					String carta=clienteResult.getCartaDiCredito();
					float totale=calcolaTotale(carrello);
					boolean risposta= effettuaTransazione(carta, totale);
					if(risposta) {
						aggiornaQuantita(carrello);
					}
			}
			
			return carrello;
			
		}
	
	public boolean autorizzaAcquisto(ArrayList<Farmaco> farmaci, String ricetta) {
		return true;
	}
	
	public boolean effettuaTransazione(String carta, float totale) {
		return true;
	}
	
	public void aggiornaQuantita(ArrayList<Farmaco> carrello) throws SQLException {
		for(int i=0; i<carrello.size(); i++) {
			if(carrello.get(i).isGalenico()) {
				ArrayList<PrincipioAttivo> principi=carrello.get(i).getPrincipiAttivi();
				for(int j=0; j<principi.size(); j++) {
					PrincipioAttivo principio=PrincipiDAO.selectPrincipio(principi.get(j));
					principio.setQuantita(principio.getQuantita()-carrello.get(i).getQuantita()*principi.get(j).getDose());
					PrincipiDAO.editPrincipioAttivo(principio, principio.getQuantita());
				}
			}
			else {
				Farmaco farmaco=FarmaciDAO.selectFarmaco(carrello.get(i));
				farmaco.setQuantita(farmaco.getQuantita()-carrello.get(i).getQuantita());
				FarmaciDAO.editFarmaco(farmaco, farmaco.getQuantita());
			}
		}
	}
	
	public float calcolaTotale(ArrayList<Farmaco> carrello) {
		float totale=0;
		for(int i=0; i<carrello.size(); i++) {
			totale=totale+carrello.get(i).getPrezzo()*carrello.get(i).getQuantita();
		}
		return totale;
	}
}
