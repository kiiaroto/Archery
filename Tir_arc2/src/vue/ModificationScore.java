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
import modele.Vol�e;

public class ModificationScore {
	List<String> listeNombres = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	HashMap<String, JLabel> labelMap = new HashMap<String, JLabel>();
	HashMap<String, JTextField> textFieldMap = new HashMap<String, JTextField>();
	HashMap<String, Vol�e> vol�eMap = new HashMap<String, Vol�e>();
	
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
	String values = "";
	
	String nomSession;
	
	int totalVol�e = 0;
	int r�sultatVol�e = 0;
	int totalPreviousVol�e = 0;
	int defaultArrowNumber = 0;
	
	int nombreDeFl�che = 0;
	int nombreDeVol�e = 0;
	
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
			for (int i = 1; i <= nombreDeVol�e; i++) {
				// Cr�er le String qui servira de cl� dans la map (vol�e1, vol�e2, vol�e3, ...)
				nameVol�e = vol�e + String.valueOf(i);
				
				vol�eMap.put(nameVol�e, new Vol�e(nameVol�e));
				
				
				
				// G�nere par vol�e le nombre de textField correspondant au nombre de fleche.
				for (int j = 1; j <= nombreDeFl�che; j++) {
					// reset le String � la bonne vol�e (vol�e1, vol�e2, vol�e3, ...)
					num�roDeFleche = nameVol�e;
					// Ajout le num�ro de la fleche a la vol�e (vol�e1arrow1, vol�e1arrow2, vol�e1arrow3, ...)
					num�roDeFleche += arrow + String.valueOf(j);
					
					// Si le getText r�cup�re une valeur qui existe dans le tableau de nombre, alors on cr�er une nouvelle fleche avec cette valeurs comme points
					if (listeNombres.contains(textFieldMap.get(num�roDeFleche).getText())) {
						vol�eMap.get(nameVol�e).ajouterFleche(new Fleche(Integer.parseInt(textFieldMap.get(num�roDeFleche).getText())));
					}
					//Sinon, on cr�er une fleche de valeur 0
					else {
						vol�eMap.get(nameVol�e).ajouterFleche(new Fleche(0));
					}
					

				}
				
				// G�nere le label du resultat de la ligne
				//Pr�paration du String qui servira de cl� pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
				nameR�sultatVol�e = resultLigne + String.valueOf(i);
				// Donne a r�sultatVol�e la valeur du r�sultat de la vol�e
				r�sultatVol�e = vol�eMap.get(nameVol�e).totalDeLaVol�e();
				// Change le label nomm� (resultatLigne1, resultatLigne2, resultatLigne3 ...) et lui attribut le text a affich� qui correspond au r�sultat de la vol�e
				labelMap.get(nameR�sultatVol�e).setText(String.valueOf(r�sultatVol�e));

				
				// R�cup�re le total de l'ancienne vol�e et lui ajoute le r�sultat de la derni�re vol�e ajout�
				// si c'est la 1ere vol�e, initialise le total a la m�me valeur que le r�sultat
				if(i == 1) {
					totalVol�e = r�sultatVol�e;
				}
				else {
					// R�cup�re le total de l'ancienne vol�e et lui ajoute le r�sultat de la derni�re vol�e ajout�
					totalVol�e = r�sultatVol�e + totalPreviousVol�e;
				}
				
				
				// G�nere le label du total
				//Pr�paration du String qui servira de cl� pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
				nameTotalVol�e = totalLigne + String.valueOf(i);
				//Sauvegarde dans la vol�e, le total des points a cette vol�e la
				vol�eMap.get(nameVol�e).setTotalVol�e(totalVol�e);
				// Change le label nomm� (totalLigne1, totalLigne2, totalLigne3 ...) et lui attribut le text a affich� qui correspond au total de la vol�e
				labelMap.get(nameTotalVol�e).setText(String.valueOf(totalVol�e));
				// sauvegarde du total de la vol�e pour le r�utilis� plus tard
				totalPreviousVol�e = totalVol�e;
				
			}
		}
	};
	
	public void affiche(int nombreDeVol�e, int nombreDeFl�che, String nomSession, ArrayList<Fleche> arrowList) {
		// D�fini nombreDeFl�che et nombreDeVol�e avec this. pour les r�utiliser dans l'actionListener
		this.nombreDeFl�che = nombreDeFl�che;
		this.nombreDeVol�e = nombreDeVol�e;
		this.nomSession = nomSession;
		
		
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
						
						JTextField txtField = new JTextField(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()));
						txtField.getDocument().addDocumentListener(docListener);
						// Ajoute � la map le TextField avec comme cl� "num�roDeFleche" (vol�e1arrow1, vol�e1arrow2, vol�e1arrow3, ...)
						textFieldMap.put(num�roDeFleche, txtField);
						// Ajoute le TextField a la fenetre
						grille.add(textFieldMap.get(num�roDeFleche));
						defaultArrowNumber++;
					}
					
					// G�nere le label du resultat de la ligne
					//Pr�paration du String qui servira de cl� pour le resultat de la ligne (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					nameR�sultatVol�e = resultLigne + String.valueOf(i);
					// Ajoute � la map le Label avec comme text "nameR�sultatVol�e" (resultatLigne1, resultatLigne2, resultatLigne3 ...)
					labelMap.put(nameR�sultatVol�e, new JLabel(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameR�sultatVol�e));
					defaultArrowNumber++;
					
					// G�nere le label du total
					//Pr�paration du String qui servira de cl� pour le total de la ligne (totalLigne1, totalLigne2, totalLigne3 ...)
					nameTotalVol�e = totalLigne + String.valueOf(i);
					// Ajoute � la map le Label avec comme text "nameVol�e" (totalLigne1, totalLigne2, totalLigne3 ...)
					labelMap.put(nameTotalVol�e, new JLabel(String.valueOf(arrowList.get(defaultArrowNumber).getPoints()), JLabel.CENTER));
					// Ajoute le label a la fenetre
					grille.add(labelMap.get(nameTotalVol�e));
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
			Session session = new Session(nomSession, nombreDeVol�e, nombreDeFl�che);
			Session v�rification = sessions.getSession(nomSession);
			
			if(v�rification == null) {
				for (int i = 1; i <= nombreDeVol�e; i++) {
					// Cr�er le String qui servira de cl� dans la map (vol�e1, vol�e2, vol�e3, ...)
					nameVol�e = vol�e + String.valueOf(i);
					
					session.ajouterVol�e(vol�eMap.get(nameVol�e));
				}
				sessions.ajouterSession(session);
			}
			else {
				sessions.supprimerSession(nomSession);
				for (int i = 1; i <= nombreDeVol�e; i++) {
					// Cr�er le String qui servira de cl� dans la map (vol�e1, vol�e2, vol�e3, ...)
					nameVol�e = vol�e + String.valueOf(i);
					
					session.ajouterVol�e(vol�eMap.get(nameVol�e));
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