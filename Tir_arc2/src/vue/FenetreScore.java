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
import modele.Volée;

public class FenetreScore {
	// faire une fenetre qui affiche les score sans possibiliter de modification avec un bouton retour
	
	HashMap<String, JLabel> labelMap = new HashMap<String, JLabel>();
	HashMap<String, Volée> voléeMap = new HashMap<String, Volée>();
	ArrayList<Fleche> arrowList;
	
	ListeDesSession sessions = new ListeDesSession();
	
	JFrame maFenetre;
	
	String textHeader = "";
	String textVolée = "";
	String pointSpace = ".";
	String nameVolée = "";
	String numéroDeFleche = "";
	String volée = "volée";
	String arrow = "arrow";
	String resultLigne = "resultatLigne";
	String totalLigne = "totalLigne";
	String nameRésultatVolée = "";
	String nameTotalVolée = "";
	
	String nomSession;
	
	int totalVolée = 0;
	int résultatVolée = 0;
	int totalPreviousVolée = 0;
	int defaultNumberArrow = 0;
	
	int nombreDeFlèche = 0;
	int nombreDeVolée = 0;
	
	public void affiche(Session session) {
		// Défini nombreDeFlèche et nombreDeVolée avec this. pour les réutiliser dans l'actionListener
		this.nombreDeFlèche = session.getNbrDeFlèche();
		this.nombreDeVolée = session.getNbrDeVolée();
		this.nomSession = session.getNom();
		
		// On récupère une liste de toutes les fleche (resultat et total inclus)
		this.arrowList = new ArrayList<Fleche>();
		arrowList = session.getAllArrow();
		
		
		maFenetre = new JFrame(nomSession);
		maFenetre.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout(10,10));
			JPanel grille = new JPanel();
			grille.setLayout(new GridLayout(nombreDeVolée + 1, nombreDeFlèche + 3, 10, 0));
				//ligne 1
				JLabel volée = new JLabel("Volée");
				grille.add(volée);
				
				// Génere en fonction du nombre de fleche choisis les labels 1 2 3 4 5 6 au dessus des textfields
				for (int i = 1; i <= nombreDeFlèche; i++) {
					// text head va contenir des numéro allant de 1 a NombreDeFleche
					textHeader = String.valueOf(i);
					// insert dans la map a la position textHeader(1, 2, 3 ...) un label contenant le text(1, 2, 3, ...)
					labelMap.put(textHeader, new JLabel(textHeader, JLabel.CENTER));
					// ajoute dans la fenetre le label créer précedemment
					grille.add(labelMap.get(textHeader));
				}
				
				JLabel ligne = new JLabel("ligne", JLabel.CENTER);
				grille.add(ligne);
				JLabel total = new JLabel("Total", JLabel.CENTER);
				grille.add(total);
				
				// Génere en fonction du nombreDeVolée le nombre de ligne requis pour le bon nombre de volée
				for (int i = 1; i <= nombreDeVolée; i++) {
					//Créer le string de text a afficehr pour le numéro de la volée (1., 2., 3. ...)
					textVolée = String.valueOf(i) + pointSpace;
					// Créer le String qui servira de clé dans la map (volée1, volée2, volée3, ...)
					nameVolée = this.volée + String.valueOf(i);
					// Ajoute à la map le Label avec comme text "textVolée"(1., 2., 3. ...) et comme clé "nameVolée" (volée1, volée2, volée3, ...)
					labelMap.put(nameVolée, new JLabel(textVolée, JLabel.RIGHT));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameVolée));
					
					
					// Génere par volée le nombre de textField correspondant au nombre de fleche.
					for (int j = 1; j <= nombreDeFlèche; j++) {
						// reset le String à la bonne volée (volée1, volée2, volée3, ...)
						numéroDeFleche = nameVolée;
						// Ajout le numéro de la fleche a la volée (volée1arrow1, volée1arrow2, volée1arrow3, ...)
						numéroDeFleche += this.arrow + String.valueOf(j);
						// Ajoute à la map le TextField avec comme clé "numéroDeFleche" (volée1arrow1, volée1arrow2, volée1arrow3, ...)
						labelMap.put(numéroDeFleche, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
						// Ajoute le TextField a la fenetre
						grille.add(labelMap.get(numéroDeFleche));
						
						defaultNumberArrow++;
					}
					
					// Génere le label du resultat de la ligne
					//Préparation du String qui servira de clé pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					nameRésultatVolée = resultLigne + String.valueOf(i);
					// Ajoute à la map le Label avec comme text le résultat de la volée (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					labelMap.put(nameRésultatVolée, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameRésultatVolée));
					
					defaultNumberArrow++;
					
					// Génere le label du total
					//Préparation du String qui servira de clé pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
					nameTotalVolée = totalLigne + String.valueOf(i);
					// Ajoute à la map le Label avec comme text le total de la volée (totalLigne1, totalLigne2, totalLigne3 ...)
					labelMap.put(nameTotalVolée, new JLabel(String.valueOf(arrowList.get(defaultNumberArrow).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameTotalVolée));
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
		// Add window listener pour revenir a la fenettre précédente en fermant la fenetre avec la croix en haut a droite (windowClosing)
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
				// créer et affiche la fenetre avec la liste des sessions
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
			modificationScore.affiche(nombreDeVolée, nombreDeFlèche, nomSession, arrowList);
			
			maFenetre.dispose();
		}
		
	}
	
	public class Retourner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//Ferme la fenetre des score
			maFenetre.dispose();
			// créer et affiche la fenetre avec la liste des sessions
			FenetreListeSession fenetreListeSession = new FenetreListeSession();
			fenetreListeSession.affiche();
		}
		
	}

}
