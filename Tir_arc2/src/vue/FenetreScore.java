package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Fleche;
import modele.ListeDesSession;
import modele.Session;
import modele.Vol�e;

public class FenetreScore {
	// faire une fenetre qui affiche les score sans possibiliter de modification avec un bouton retour
	
	HashMap<String, JLabel> labelMap = new HashMap<String, JLabel>();
	HashMap<String, Vol�e> vol�eMap = new HashMap<String, Vol�e>();
	ArrayList<Fleche> arrowList;
	
	ListeDesSession sessions = new ListeDesSession();
	
	JFrame maFenetre;
	
	String textHeader = "";
	String textVol�e = "";
	String pointSpace = ".";
	String nameVol�e = "";
	String num�roDeFleche = "";
	String vol�e = "vol�e";
	String arrow = "arrow";
	String resultLigne = "resultatLigne";
	String totalLigne = "totalLigne";
	String nameR�sultatVol�e = "";
	String nameTotalVol�e = "";
	
	String nomSession;
	
	int totalVol�e = 0;
	int r�sultatVol�e = 0;
	int totalPreviousVol�e = 0;
	int defaultNumberArrow = 0;
	
	int nombreDeFl�che = 0;
	int nombreDeVol�e = 0;
	
	public void affiche(Session session) {
		// D�fini nombreDeFl�che et nombreDeVol�e avec this. pour les r�utiliser dans l'actionListener
		this.nombreDeFl�che = session.getNbrDeFl�che();
		this.nombreDeVol�e = session.getNbrDeVol�e();
		this.nomSession = session.getNom();
		
		// On r�cup�re une liste de toutes les fleche (resultat et total inclus)
		this.arrowList = new ArrayList<Fleche>();
		arrowList = session.getAllArrow();
		
		
		maFenetre = new JFrame(nomSession);
		maFenetre.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout(10,10));
			JPanel grille = new JPanel();
			grille.setLayout(new GridLayout(nombreDeVol�e + 1, nombreDeFl�che + 3, 10, 0));
				//ligne 1
				JLabel vol�e = new JLabel("Vol�e");
				grille.add(vol�e);
				
				// G�nere en fonction du nombre de fleche choisis les labels 1 2 3 4 5 6 au dessus des textfields
				for (int i = 1; i <= nombreDeFl�che; i++) {
					// text head va contenir des num�ro allant de 1 a NombreDeFleche
					textHeader = String.valueOf(i);
					// insert dans la map a la position textHeader(1, 2, 3 ...) un label contenant le text(1, 2, 3, ...)
					labelMap.put(textHeader, new JLabel(textHeader, JLabel.CENTER));
					// ajoute dans la fenetre le label cr�er pr�cedemment
					grille.add(labelMap.get(textHeader));
				}
				
				JLabel ligne = new JLabel("ligne", JLabel.CENTER);
				grille.add(ligne);
				JLabel total = new JLabel("Total", JLabel.CENTER);
				grille.add(total);
				
				// G�nere en fonction du nombreDeVol�e le nombre de ligne requis pour le bon nombre de vol�e
				for (int i = 1; i <= nombreDeVol�e; i++) {
					//Cr�er le string de text a afficehr pour le num�ro de la vol�e (1., 2., 3. ...)
					textVol�e = String.valueOf(i) + pointSpace;
					// Cr�er le String qui servira de cl� dans la map (vol�e1, vol�e2, vol�e3, ...)
					nameVol�e = this.vol�e + String.valueOf(i);
					// Ajoute � la map le Label avec comme text "textVol�e"(1., 2., 3. ...) et comme cl� "nameVol�e" (vol�e1, vol�e2, vol�e3, ...)
					labelMap.put(nameVol�e, new JLabel(textVol�e, JLabel.RIGHT));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameVol�e));
					
					
					// G�nere par vol�e le nombre de textField correspondant au nombre de fleche.
					for (int j = 1; j <= nombreDeFl�che; j++) {
						// reset le String � la bonne vol�e (vol�e1, vol�e2, vol�e3, ...)
						num�roDeFleche = nameVol�e;
						// Ajout le num�ro de la fleche a la vol�e (vol�e1arrow1, vol�e1arrow2, vol�e1arrow3, ...)
						num�roDeFleche += this.arrow + String.valueOf(j);
						// Ajoute � la map le TextField avec comme cl� "num�roDeFleche" (vol�e1arrow1, vol�e1arrow2, vol�e1arrow3, ...)
						labelMap.put(num�roDeFleche, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
						// Ajoute le TextField a la fenetre
						grille.add(labelMap.get(num�roDeFleche));
						
						defaultNumberArrow++;
					}
					
					// G�nere le label du resultat de la ligne
					//Pr�paration du String qui servira de cl� pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					nameR�sultatVol�e = resultLigne + String.valueOf(i);
					// Ajoute � la map le Label avec comme text le r�sultat de la vol�e (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					labelMap.put(nameR�sultatVol�e, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameR�sultatVol�e));
					
					defaultNumberArrow++;
					
					// G�nere le label du total
					//Pr�paration du String qui servira de cl� pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
					nameTotalVol�e = totalLigne + String.valueOf(i);
					// Ajoute � la map le Label avec comme text le total de la vol�e (totalLigne1, totalLigne2, totalLigne3 ...)
					labelMap.put(nameTotalVol�e, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameTotalVol�e));
					defaultNumberArrow++;
					
				}

				
			p1.add(grille, BorderLayout.CENTER);
			
			JPanel grilleButton = new JPanel();
			grilleButton.setLayout(new GridLayout(1, 2));
				
				Modifier modifier = new Modifier();
				JButton modifierButton = new JButton("Modifier");
				modifierButton.addActionListener(modifier);
				grilleButton.add(modifierButton);
			
				Retourner retour = new Retourner();
				JButton retourButton = new JButton("Retour");
				retourButton.addActionListener(retour);
				grilleButton.add(retourButton);
				
				
			
			
			p1.add(grilleButton, BorderLayout.SOUTH);

		maFenetre.add(p1, BorderLayout.CENTER);
		
		
		maFenetre.pack();
		maFenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Add window listener pour revenir a la fenettre pr�c�dente en fermant la fenetre avec la croix en haut a droite (windowClosing)
		maFenetre.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//Ferme la fenetre des score
				maFenetre.dispose();
				// cr�er et affiche la fenetre avec la liste des sessions
				FenetreListeSession fenetreListeSession = new FenetreListeSession();
				fenetreListeSession.affiche();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		maFenetre.setVisible(true);
		
	}
	
	public class Modifier implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			ModificationScore modificationScore = new ModificationScore();
			modificationScore.affiche(nombreDeVol�e, nombreDeFl�che, nomSession, arrowList);
			
			maFenetre.dispose();
		}
		
	}
	
	public class Retourner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//Ferme la fenetre des score
			maFenetre.dispose();
			// cr�er et affiche la fenetre avec la liste des sessions
			FenetreListeSession fenetreListeSession = new FenetreListeSession();
			fenetreListeSession.affiche();
		}
		
	}

}
