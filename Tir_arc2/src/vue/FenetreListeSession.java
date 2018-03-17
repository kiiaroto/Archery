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
			
			// Cr�er les 2 textField pour les options
			JTextField nom = new JTextField();
			JTextField vol�e = new JTextField();
			JTextField fleche = new JTextField();
			
			//Cr�er un tableau d'objet afin de remplir la JOptionPane (un text, un input, un autre text, un autre input)
			Object[] message = {
				"Nom de votre Session", nom,
			    "Nombre de vol�es:", vol�e,
			    "Nombre de fl�ches:", fleche
			};
			
			// Ser de param�tre a la boucle, si Vrai, fin de boucle, si faux, continue la boucle
			boolean optionsOK = false;
			
			while (!optionsOK) {
				// Option prendra une certaine valeur si OK est s�l�ctionner
				//Cr�er une fenetre avec comme element les String et TextField cr�er pr�cedement, et comme titre "Option" avec un seul bouton OK
				int option = JOptionPane.showConfirmDialog(null, message, "Option", JOptionPane.DEFAULT_OPTION);

				// Si le bouton OK est clicker
				if (option == JOptionPane.OK_OPTION) {
					// Tente de transformer les textfield en Entier, affiche la fenetre, et arrete la boucle.
				    try {
				    	String nomSession = nom.getText();
				    	// Envoi a Exception e1 si le nom est laiss� blanc
				        if (nomSession.equals("")) {
				        	throw new Exception(nomSession);
				        }
				        // Envoi a Exception e1 si la Session existe deja
				        if (sessions.getSession(nomSession) != null) {
				        	throw new Exception(nomSession);
				        }
				        int nbrVol�e = Integer.parseInt(vol�e.getText());
				        int nbrFl�che = Integer.parseInt(fleche.getText());
				        
				        FenetreAjoutScore fenetre = new FenetreAjoutScore();
						//nombre de vol�e, nombre de fl�che
				        fenetre.affiche(nbrVol�e, nbrFl�che, nomSession);
				        optionsOK = true;
				        
				        // Ferme et D�truit la fenetreDesSession
						fenetreDesSession.dispose();
				   }
				    // Si les textField ne son pas des entier, ont affiche un message d'erreur et on recommence la boucle
				   catch (NumberFormatException e1) {
		               JOptionPane.showMessageDialog(vol�e, "Merci de ne choisir que des nombre", "Try again", JOptionPane.ERROR_MESSAGE);
				   } catch (Exception e1) {
					// TODO Auto-generated catch block
					   // si le nom de la session est laiss� blanc ou si la session existe d�ja envoie le message d'erreur.
					   JOptionPane.showMessageDialog(vol�e, "Merci de choisir un nom de Session valide et qui n'existe pas d�j�", "Try again", JOptionPane.ERROR_MESSAGE);
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
				// Cr�er une une fenetre de confirmation pour supprimer une Session
				int option = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer: " + listeSession.getSelectedValue(), "�tes vous sur?", JOptionPane.YES_NO_OPTION);
				
				// Si le bouton YES est clicker
				if (option == JOptionPane.YES_OPTION) {
					
					//Supprime le fichier de sauvegarde XML de la session
					GestionXML xml = new GestionXML(sessions.getSession(listeSession.getSelectedValue().substring(24)));
					xml.deleteXmlFile();
					
					// Supprime la session sans prendre en compte la date (substring 24)
					sessions.supprimerSession(listeSession.getSelectedValue().substring(24));
					// Suprimme la S�l�ction Session de la Jlist
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
