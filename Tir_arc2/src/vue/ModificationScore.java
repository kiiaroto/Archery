package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import modele.Fleche;
import modele.ListeDesSession;
import modele.Session;
import modele.Volée;

public class ModificationScore {
	List<String> listeNombres = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	HashMap<String, JLabel> labelMap = new HashMap<String, JLabel>();
	HashMap<String, JTextField> textFieldMap = new HashMap<String, JTextField>();
	HashMap<String, Volée> voléeMap = new HashMap<String, Volée>();
	
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
	String values = "";
	
	String nomSession;
	
	int totalVolée = 0;
	int résultatVolée = 0;
	int totalPreviousVolée = 0;
	int defaultArrowNumber = 0;
	
	int nombreDeFlèche = 0;
	int nombreDeVolée = 0;
	
	DocumentListener docListener = new DocumentListener() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			calcul();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			calcul();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			calcul();
		}
		
		public void calcul() {
			for (int i = 1; i <= nombreDeVolée; i++) {
				// Créer le String qui servira de clé dans la map (volée1, volée2, volée3, ...)
				nameVolée = volée + String.valueOf(i);
				
				voléeMap.put(nameVolée, new Volée(nameVolée));
				
				
				
				// Génere par volée le nombre de textField correspondant au nombre de fleche.
				for (int j = 1; j <= nombreDeFlèche; j++) {
					// reset le String à la bonne volée (volée1, volée2, volée3, ...)
					numéroDeFleche = nameVolée;
					// Ajout le numéro de la fleche a la volée (volée1arrow1, volée1arrow2, volée1arrow3, ...)
					numéroDeFleche += arrow + String.valueOf(j);
					
					// Si le getText récupère une valeur qui existe dans le tableau de nombre, alors on créer une nouvelle fleche avec cette valeurs comme points
					if (listeNombres.contains(textFieldMap.get(numéroDeFleche).getText())) {
						voléeMap.get(nameVolée).ajouterFleche(new Fleche(Integer.parseInt(textFieldMap.get(numéroDeFleche).getText())));
					}
					//Sinon, on créer une fleche de valeur 0
					else {
						voléeMap.get(nameVolée).ajouterFleche(new Fleche(0));
					}
					

				}
				
				// Génere le label du resultat de la ligne
				//Préparation du String qui servira de clé pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
				nameRésultatVolée = resultLigne + String.valueOf(i);
				// Donne a résultatVolée la valeur du résultat de la volée
				résultatVolée = voléeMap.get(nameVolée).totalDeLaVolée();
				// Change le label nommé (resultatLigne1, resultatLigne2, resultatLigne3 ...) et lui attribut le text a affiché qui correspond au résultat de la volée
				labelMap.get(nameRésultatVolée).setText(String.valueOf(résultatVolée));

				
				// Récupère le total de l'ancienne volée et lui ajoute le résultat de la dernière volée ajouté
				// si c'est la 1ere volée, initialise le total a la même valeur que le résultat
				if(i == 1) {
					totalVolée = résultatVolée;
				}
				else {
					// Récupère le total de l'ancienne volée et lui ajoute le résultat de la dernière volée ajouté
					totalVolée = résultatVolée + totalPreviousVolée;
				}
				
				
				// Génere le label du total
				//Préparation du String qui servira de clé pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
				nameTotalVolée = totalLigne + String.valueOf(i);
				//Sauvegarde dans la volée, le total des points a cette volée la
				voléeMap.get(nameVolée).setTotalVolée(totalVolée);
				// Change le label nommé (totalLigne1, totalLigne2, totalLigne3 ...) et lui attribut le text a affiché qui correspond au total de la volée
				labelMap.get(nameTotalVolée).setText(String.valueOf(totalVolée));
				// sauvegarde du total de la volée pour le réutilisé plus tard
				totalPreviousVolée = totalVolée;
				
			}
		}
	};
	
	public void affiche(int nombreDeVolée, int nombreDeFlèche, String nomSession, ArrayList<Fleche> arrowList) {
		// Défini nombreDeFlèche et nombreDeVolée avec this. pour les réutiliser dans l'actionListener
		this.nombreDeFlèche = nombreDeFlèche;
		this.nombreDeVolée = nombreDeVolée;
		this.nomSession = nomSession;
		
		
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
						
						JTextField txtField = new JTextField(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()));
						txtField.getDocument().addDocumentListener(docListener);
						// Ajoute à la map le TextField avec comme clé "numéroDeFleche" (volée1arrow1, volée1arrow2, volée1arrow3, ...)
						textFieldMap.put(numéroDeFleche, txtField);
						// Ajoute le TextField a la fenetre
						grille.add(textFieldMap.get(numéroDeFleche));
						defaultArrowNumber++;
					}
					
					// Génere le label du resultat de la ligne
					//Préparation du String qui servira de clé pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					nameRésultatVolée = resultLigne + String.valueOf(i);
					// Ajoute à la map le Label avec comme text "nameRésultatVolée" (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					labelMap.put(nameRésultatVolée, new JLabel(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameRésultatVolée));
					defaultArrowNumber++;
					
					// Génere le label du total
					//Préparation du String qui servira de clé pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
					nameTotalVolée = totalLigne + String.valueOf(i);
					// Ajoute à la map le Label avec comme text "nameVolée" (totalLigne1, totalLigne2, totalLigne3 ...)
					labelMap.put(nameTotalVolée, new JLabel(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameTotalVolée));
					defaultArrowNumber++;
					
				}

				
			p1.add(grille, BorderLayout.CENTER);
			
			JPanel grilleButton = new JPanel();
			grilleButton.setLayout(new GridLayout(1, 2));
				
				Sauvegarder sauvegarde = new Sauvegarder();
				JButton sauvegardeButton = new JButton("Sauvegarder");
				sauvegardeButton.addActionListener(sauvegarde);
				grilleButton.add(sauvegardeButton);
				
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
				FenetreScore fenetreScore = new FenetreScore();
				fenetreScore.affiche(sessions.getSession(nomSession));
							
							
				maFenetre.dispose();
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
	
	
	public class Sauvegarder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Session session = new Session(nomSession, nombreDeVolée, nombreDeFlèche);
			Session vérification = sessions.getSession(nomSession);
			
			if(vérification == null) {
				for (int i = 1; i <= nombreDeVolée; i++) {
					// Créer le String qui servira de clé dans la map (volée1, volée2, volée3, ...)
					nameVolée = volée + String.valueOf(i);
					
					session.ajouterVolée(voléeMap.get(nameVolée));
				}
				sessions.ajouterSession(session);
			}
			else {
				sessions.supprimerSession(nomSession);
				for (int i = 1; i <= nombreDeVolée; i++) {
					// Créer le String qui servira de clé dans la map (volée1, volée2, volée3, ...)
					nameVolée = volée + String.valueOf(i);
					
					session.ajouterVolée(voléeMap.get(nameVolée));
				}
				sessions.ajouterSession(session);
			}
			
		}
		
	}
	
	
	public class Retourner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
				FenetreScore fenetreScore = new FenetreScore();
				fenetreScore.affiche(sessions.getSession(nomSession));
							
							
				maFenetre.dispose();
			
			
		
		}
		
	}
}