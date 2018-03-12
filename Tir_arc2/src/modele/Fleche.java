package modele;

public class Fleche {
	
	private int points;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Fleche [points=" + points + "]";
	}

	public Fleche(int points) {
		super();
		this.points = points;
	}

	public Fleche() {
		super();
	}
	
}
