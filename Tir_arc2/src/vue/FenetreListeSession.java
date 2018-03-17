package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.GestionXML;
import modele.ListeDesSession;


public class FenetreListeSession {
	
	ListeDesSession sessions = new ListeDesSession();
	DefaultListModel<String> nomDesSessions;
	JList<String> listeSession;
	JFrame fenetreDesSession;
	
	public void affiche() {
		
		nomDesSessions = sessions.nomDeTouteLesSessions();
		
		fenetreDesSession = new JFrame("Session");
		fenetreDesSession.setLayout(new BorderLayout(10, 10));
		
		JPanel panelDesSession = new JPanel();
		panelDesSession.setLayout(new BorderLayout(10, 10));
			JLabel titre = new JLabel("Liste de vos session:");
			panelDesSession.add(titre, BorderLayout.NORTH);
			
			listeSession = new JList<String>(nomDesSessions);
			JScrollPane scrollListeSession = new JScrollPane(listeSession);
			panelDesSession.add(scrollListeSession, BorderLayout.CENTER);
			
			JPanel gridButton = new JPanel();
			gridButton.setLayout(new GridLayout(1, 3));
			
				NouvelleSession listenerNouveau = new NouvelleSession();
				JButton nouveau = new JButton("Nouveau");
				nouveau.addActionListener(listenerNouveau);
				gridButton.add(nouveau);
				
				ConsulterSession consulterSession = new ConsulterSession();
				JButton consulter = new JButton("Consulter");
				consulter.addActionListener(consulterSession);
				gridButton.add(consulter);
				
				SupprimerSession listenerSupprimer = new SupprimerSession();
				JButton supprimer = new JButton("Supprimer");
				supprimer.addActionListener(listenerSupprimer);
				gridButton.add(supprimer);
				
			panelDesSession.add(gridButton, BorderLayout.SOUTH);

		
		fenetreDesSession.add(panelDesSession, BorderLayout.CENTER);

		fenetreDesSession.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetreDesSession.pack();
		fenetreDesSession.setVisible(true);
	}
	
	public class NouvelleSession implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			// Créer les 2 textField pour les options
			JTextField nom = new JTextField();
			JTextField volée = new JTextField();
			JTextField fleche = new JTextField();
			
			//Créer un tableau d'objet afin de remplir la JOptionPane (un text, un input, un autre text, un autre input)
			Object[] message = {
				"Nom de votre Session", nom,
			    "Nombre de volées:", volée,
			    "Nombre de flèches:", fleche
			};
			
			// Ser de paramètre a la boucle, si Vrai, fin de boucle, si faux, continue la boucle
			boolean optionsOK = false;
			
			while (!optionsOK) {
				// Option prendra une certaine valeur si OK est séléctionner
				//Créer une fenetre avec comme element les String et TextField créer précedement, et comme titre "Option" avec un seul bouton OK
				int option = JOptionPane.showConfirmDialog(null, message, "Option", JOptionPane.DEFAULT_OPTION);

				// Si le bouton OK est clicker
				if (option == JOptionPane.OK_OPTION) {
					// Tente de transformer les textfield en Entier, affiche la fenetre, et arrete la boucle.
				    try {
				    	String nomSession = nom.getText();
				    	// Envoi a Exception e1 si le nom est laissé blanc
				        if (nomSession.equals("")) {
				        	throw new Exception(nomSession);
				        }
				        // Envoi a Exception e1 si la Session existe deja
				        if (sessions.getSession(nomSession) != null) {
				        	throw new Exception(nomSession);
				        }
				        int nbrVolée = Integer.parseInt(volée.getText());
				        int nbrFlèche = Integer.parseInt(fleche.getText());
				        
				        FenetreAjoutScore fenetre = new FenetreAjoutScore();
						//nombre de volée, nombre de flèche
				        fenetre.affiche(nbrVolée, nbrFlèche, nomSession);
				        optionsOK = true;
				        
				        // Ferme et Détruit la fenetreDesSession
						fenetreDesSession.dispose();
				   }
				    // Si les textField ne son pas des entier, ont affiche un message d'erreur et on recommence la boucle
				   catch (NumberFormatException e1) {
		               JOptionPane.showMessageDialog(volée, "Merci de ne choisir que des nombre", "Try again", JOptionPane.ERROR_MESSAGE);
				   } catch (Exception e1) {
					// TODO Auto-generated catch block
					   // si le nom de la session est laissé blanc ou si la session existe déja envoie le message d'erreur.
					   JOptionPane.showMessageDialog(volée, "Merci de choisir un nom de Session valide et qui n'existe pas déjà", "Try again", JOptionPane.ERROR_MESSAGE);
				}
				} else {
					// si on click sur autre chose que OK, on stop la boucle et ferme le programme
				    optionsOK = true;
				    
				}
			}
			
		}

	}
	
	public class SupprimerSession implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if (!listeSession.isSelectionEmpty()) {
				// Créer une une fenetre de confirmation pour supprimer une Session
				int option = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer: " + listeSession.getSelectedValue(), "Êtes vous sur?", JOptionPane.YES_NO_OPTION);
				
				// Si le bouton YES est clicker
				if (option == JOptionPane.YES_OPTION) {
					
					//Supprime le fichier de sauvegarde XML de la session
					GestionXML xml = new GestionXML(sessions.getSession(listeSession.getSelectedValue().substring(24)));
					xml.deleteXmlFile();
					
					// Supprime la session sans prendre en compte la date (substring 24)
					sessions.supprimerSession(listeSession.getSelectedValue().substring(24));
					// Suprimme la Séléction Session de la Jlist
					nomDesSessions.removeElementAt(listeSession.getSelectedIndex());
				}
				
			}
		}
	}
	
	public class ConsulterSession implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (listeSession.isSelectionEmpty()) {
				System.out.println("selectionEmpty");
			}
			else {
							
				FenetreScore fenetreScore = new FenetreScore();
				fenetreScore.affiche(sessions.getSession(listeSession.getSelectedValue().substring(24)));
							
							
				fenetreDesSession.dispose();
			}
			
		}
	}
}
