package modele;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Session {
	private String nom;
	private DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// use System.out.println(formatDate.format(cal.getTime())); to get the good dateformat
	private Calendar cal;
	private int nbrDeVolée;
	private int nbrDeFlèche;
	
	ArrayList<Volée> session = new ArrayList<Volée>();
	
	public void ajouterVolée(Volée volée) {
		session.add(volée);
	}

	public int totalDeLaSession() {
		int total = 0;
		for (Volée volée : session) {
			total += volée.totalDeLaVolée();
		}
		return total;
	}
	public ArrayList<Fleche> getAllArrow() {
		ArrayList<Fleche> allArrow = new ArrayList<Fleche>();
		for(Volée volée : session) {
			for(Fleche fleche: volée.getAllArrow()) {
				allArrow.add(fleche);
			}
			allArrow.add(new Fleche(volée.getResultVolée()));
			allArrow.add(new Fleche(volée.getTotalVolée()));
		}
		return allArrow;
	}
	public String getDate() {
		String date = formatDate.format(cal.getTime());
		return date;
	}
	
	
	public int getNbrDeVolée() {
		return nbrDeVolée;
	}

	public void setNbrDeVolée(int nbrDeVolée) {
		this.nbrDeVolée = nbrDeVolée;
	}

	public int getNbrDeFlèche() {
		return nbrDeFlèche;
	}

	public void setNbrDeFlèche(int nbrDeFlèche) {
		this.nbrDeFlèche = nbrDeFlèche;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Calendar getCal() {
		return cal;
	}

	@Override
	public String toString() {
		return "Session [nom=" + nom + ", cal=" + cal + ", session=" + session + "]";
	}

	public Session(String nom, int nbrDeVolée, int nbrDeFlèche) {
		super();
		this.nom = nom;
		this.nbrDeVolée = nbrDeVolée;
		this.nbrDeFlèche =  nbrDeFlèche;
		this.cal = Calendar.getInstance();
	}

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
