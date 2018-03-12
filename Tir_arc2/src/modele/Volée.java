package modele;

import java.util.ArrayList;

public class Volée {
	private String numéroVolée;
	private int resultVolée;
	private int totalVolée;
	
	ArrayList<Fleche> volée = new ArrayList<Fleche>();
	
	public void ajouterFleche(Fleche fleche) {
		volée.add(fleche);
	}
	
	public int totalDeLaVolée() {
		int total = 0;
		for (Fleche fleche : volée) {
			total += fleche.getPoints();
		}
		resultVolée = total;
		return total;
	}
	
	public ArrayList<Fleche> getAllArrow(){
		return volée;
	}

	public int getResultVolée() {
		return resultVolée;
	}

	public void setResultVolée(int resultVolée) {
		this.resultVolée = resultVolée;
	}

	public int getTotalVolée() {
		return totalVolée;
	}

	public void setTotalVolée(int totalVolée) {
		this.totalVolée = totalVolée;
	}

	public String getNuméroVolée() {
		return numéroVolée;
	}

	public void setNuméroVolée(String numéroVolée) {
		this.numéroVolée = numéroVolée;
	}

	@Override
	public String toString() {
		return "Volée [numéroVolée=" + numéroVolée + ", resultVolée=" + resultVolée + ", totalVolée=" + totalVolée
				+ ", volée=" + volée + "]";
	}

	public Volée(String numéroVolée) {
		super();
		this.numéroVolée = numéroVolée;
	}
	
	
}
