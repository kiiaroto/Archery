package modele;

import java.util.ArrayList;

public class Vol�e {
	private String num�roVol�e;
	private int resultVol�e;
	private int totalVol�e;
	
	ArrayList<Fleche> vol�e = new ArrayList<Fleche>();
	
	public void ajouterFleche(Fleche fleche) {
		vol�e.add(fleche);
	}
	
	public int totalDeLaVol�e() {
		int total = 0;
		for (Fleche fleche : vol�e) {
			total += fleche.getPoints();
		}
		resultVol�e = total;
		return total;
	}
	
	public ArrayList<Fleche> getAllArrow(){
		return vol�e;
	}

	public int getResultVol�e() {
		return resultVol�e;
	}

	public void setResultVol�e(int resultVol�e) {
		this.resultVol�e = resultVol�e;
	}

	public int getTotalVol�e() {
		return totalVol�e;
	}

	public void setTotalVol�e(int totalVol�e) {
		this.totalVol�e = totalVol�e;
	}

	public String getNum�roVol�e() {
		return num�roVol�e;
	}

	public void setNum�roVol�e(String num�roVol�e) {
		this.num�roVol�e = num�roVol�e;
	}

	@Override
	public String toString() {
		return "Vol�e [num�roVol�e=" + num�roVol�e + ", resultVol�e=" + resultVol�e + ", totalVol�e=" + totalVol�e
				+ ", vol�e=" + vol�e + "]";
	}

	public Vol�e(String num�roVol�e) {
		super();
		this.num�roVol�e = num�roVol�e;
	}
	
	
}
