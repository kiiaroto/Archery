package modele;

import javax.swing.DefaultListModel;

public class ListeDesSession {

	static public DefaultListModel<Session> listeDesSession = new DefaultListModel<Session>();
	
	public void ajouterSession(Session session) {
		listeDesSession.addElement(session);
	}
	
	public void supprimerSession(String nomSession) {
		
		//System.out.println("try to delete :" + nomSession);
		for(int index = 0;index<listeDesSession.getSize(); index++) {
			if (listeDesSession.get(index).getNom().equals(nomSession)) {
				listeDesSession.removeElementAt(index);
				//System.out.println("session supprimer");
			}
		}
	}
	
	public Session getSession(String nomSession) {
		Session s = null;
		for(int index = 0;index<listeDesSession.getSize(); index++) {
			if (listeDesSession.get(index).getNom().equals(nomSession)) {
				s = listeDesSession.get(index);
			}
		}
		return s;
	}
	
	public DefaultListModel<String> nomDeTouteLesSessions() {
		DefaultListModel<String> nomDesSessions = new DefaultListModel<String>();
		for(int index = 0; index < listeDesSession.getSize(); index++) {
			String nom = listeDesSession.get(index).getDate() + "  |  " + listeDesSession.get(index).getNom();
			nomDesSessions.addElement(nom);
		}
		return nomDesSessions;
	}
	
	public ListeDesSession() {

	}
	
}
