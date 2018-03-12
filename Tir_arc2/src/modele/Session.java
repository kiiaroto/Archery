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
	private int nbrDeVol�e;
	private int nbrDeFl�che;
	
	ArrayList<Vol�e> session = new ArrayList<Vol�e>();
	
	public void ajouterVol�e(Vol�e vol�e) {
		session.add(vol�e);
	}

	public int totalDeLaSession() {
		int total = 0;
		for (Vol�e vol�e : session) {
			total += vol�e.totalDeLaVol�e();
		}
		return total;
	}
	public ArrayList<Fleche> getAllArrow() {
		ArrayList<Fleche> allArrow = new ArrayList<Fleche>();
		for(Vol�e vol�e : session) {
			for(Fleche fleche: vol�e.getAllArrow()) {
				allArrow.add(fleche);
			}
			allArrow.add(new Fleche(vol�e.getResultVol�e()));
			allArrow.add(new Fleche(vol�e.getTotalVol�e()));
		}
		return allArrow;
	}
	public String getDate() {
		String date = formatDate.format(cal.getTime());
		return date;
	}
	
	
	public int getNbrDeVol�e() {
		return nbrDeVol�e;
	}

	public void setNbrDeVol�e(int nbrDeVol�e) {
		this.nbrDeVol�e = nbrDeVol�e;
	}

	public int getNbrDeFl�che() {
		return nbrDeFl�che;
	}

	public void setNbrDeFl�che(int nbrDeFl�che) {
		this.nbrDeFl�che = nbrDeFl�che;
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

	public Session(String nom, int nbrDeVol�e, int nbrDeFl�che) {
		super();
		this.nom = nom;
		this.nbrDeVol�e = nbrDeVol�e;
		this.nbrDeFl�che =  nbrDeFl�che;
		this.cal = Calendar.getInstance();
	}

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
