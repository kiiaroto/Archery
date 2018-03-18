package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modele.Fleche;
import modele.ListeDesSession;
import modele.Session;
import modele.Volée;

public class GestionXML {
	
	private Session session;
	private String filePath;
	private String nomSession;
	
	private File file;
	
	public boolean createXmlFile() {
		
		if (!file.exists()) {
			
			try {
				
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
				String folderPath = filePath.substring(0, filePath.lastIndexOf("\\"));
				File folder = new File(folderPath);
				folder.mkdirs();
				
				try {
					
					file.createNewFile();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					return false;
				}
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try {
				
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();
				
				// Création de la balise session qui contiendra tout les élement
				Element sess = document.createElement("session");
					// création de la balise info qui contiendra toute les info de la session
					Element sessionInfo = document.createElement("info");
						//création des balise d'information
						Element sessionNom = document.createElement("nom");
						Element sessionCal = document.createElement("cal");
						Element sessionNbrDeVolee = document.createElement("nbrdevolee");
						Element sessionNbrDeFleche = document.createElement("nbrdefleche");
						
						//ajout des balise d'information dans la balise info et leurs ajoute dleurs contenu
						sessionInfo.appendChild(sessionNom).setTextContent(session.getNom());
						sessionInfo.appendChild(sessionCal).setTextContent(session.getDate());
						sessionInfo.appendChild(sessionNbrDeVolee).setTextContent(String.valueOf(session.getNbrDeVolée()));
						sessionInfo.appendChild(sessionNbrDeFleche).setTextContent(String.valueOf(session.getNbrDeFlèche()));
					
					//ajout de la balise info et son contenu dans la balise session
					sess.appendChild(sessionInfo);
					
					
					//Génération du nombre de volée et de flèche nécessaire a l'écriture du fichier
					int nbrVolee = session.getNbrDeVolée();
					int nbrFleche = session.getNbrDeFlèche();
					
					for (int i = 1; i <= nbrVolee; i++) {
						
						Element volee = document.createElement("volee");
						volee.setAttribute("numero", String.valueOf(i));
						
						for (int j = 1; j <= nbrFleche; j++) {
							
							Element fleche = document.createElement("fleche");
							fleche.setAttribute("numero", String.valueOf(j));
							fleche.setTextContent(" ");
							volee.appendChild(fleche);
							
						}
						
						sess.appendChild(volee);
					}
					
					
				// ajoute tout au document (balise session et son contenu)
				document.appendChild(sess);
					
				TransformerFactory transFactory = TransformerFactory.newInstance();
				try {
						
					Transformer trans = transFactory.newTransformer();
					trans.setOutputProperty(OutputKeys.INDENT, "yes");
					trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
					
					DOMSource source = new DOMSource(document);
					StreamResult result = new StreamResult(file);
					
					trans.transform(source, result);
						
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}

	public void writeXmlFile(HashMap<String, Volée> voléeMap) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<Fleche> listeDesFleches;
		int conteur;
		
		String voleeBuildedName = "";
		//volée1arrow2
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filePath);
			
			Element racine = document.getDocumentElement();
			
			NodeList voleeNodeList = racine.getElementsByTagName("volee");
			
			for (int i = 0; i < voleeNodeList.getLength(); i++) {
				conteur = 0;
				voleeBuildedName = "volée" + String.valueOf(i + 1);
				listeDesFleches = voléeMap.get(voleeBuildedName).getAllArrow();
				
				Node voleeNode = voleeNodeList.item(i);
				//System.out.println(i);
				if (voleeNode.getNodeType() == Node.ELEMENT_NODE) {
					
					NodeList flecheNodeList = voleeNode.getChildNodes();

					for (int j = 0; j < flecheNodeList.getLength(); j++) {
						Node flecheNode = flecheNodeList.item(j);
						if (flecheNode.getNodeType() == Node.ELEMENT_NODE) {
							
//							System.out.println("----------------------");
//							System.out.println(flecheNode.getAttributes().item(0));
//							System.out.println(listeDesFleches.get(conteur).getPoints());

							flecheNode.setTextContent(String.valueOf(listeDesFleches.get(conteur).getPoints()));
							conteur++;
						}
					}
				}
			}
			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			try {
				
				Transformer trans = transFactory.newTransformer();
				trans.setOutputProperty(OutputKeys.INDENT, "yes");
				trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(filePath);
				
				trans.transform(source, result);
				
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readXmlFile() {
		//TODO
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ListeDesSession listeDesSessions = new ListeDesSession();
		session = new Session();
		session.setNom(nomSession);
		
		int nbrVolee = 0;
		int resultVolee = 0;
		int totalVolee = 0;
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filePath);
			Element racine = document.getDocumentElement();
			NodeList voleeNodeList = racine.getElementsByTagName("volee");
			
			NodeList nbrDeVolee = racine.getElementsByTagName("nbrdevolee");
			Node nbrVol = nbrDeVolee.item(0);
			session.setNbrDeVolée(Integer.parseInt(nbrVol.getTextContent()));
			
			NodeList nbrDeFleche = racine.getElementsByTagName("nbrdefleche");
			Node nbrFle = nbrDeFleche.item(0);
			session.setNbrDeFlèche(Integer.parseInt(nbrFle.getTextContent()));
			
			
			for (int i = 0; i < voleeNodeList.getLength(); i++) {
				Node voleeNode = voleeNodeList.item(i);
				
				if (voleeNode.getNodeType() == Node.ELEMENT_NODE) {
					nbrVolee++;
					resultVolee = 0;
					NodeList flecheNodeListe = voleeNode.getChildNodes();
					
					Volée volee = new Volée("volée" + String.valueOf(nbrVolee));
					
					for (int j = 0; j < flecheNodeListe.getLength(); j++) {
						Node flecheNode = flecheNodeListe.item(j);
						
						if(flecheNode.getNodeType() == Node.ELEMENT_NODE) {
							Fleche fleche = new Fleche(Integer.parseInt(flecheNode.getTextContent()));
							resultVolee += fleche.getPoints();
							totalVolee += fleche.getPoints();
							volee.ajouterFleche(fleche);
						}
					}
					volee.setResultVolée(resultVolee);
					volee.setTotalVolée(totalVolee);
					
					session.ajouterVolée(volee);
				}
			}
			
			listeDesSessions.ajouterSession(session);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void deleteXmlFile() {
		
		if (file.exists()) {
			file.delete();
		}
		
	}
	
	public DefaultListModel<String> getAllXmlList() {
		DefaultListModel<String> liste = new DefaultListModel<String>();
		String name = null;
		if (file.isDirectory()) {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try {
				
				DocumentBuilder builder = factory.newDocumentBuilder();
				;
				
				
				for (String s : file.list()) {
					Document document = builder.parse(filePath + s);
					
					Element racine = document.getDocumentElement();
					
					NodeList nodeList = racine.getElementsByTagName("cal");
					Node node = nodeList.item(0);
					name = node.getTextContent() + "  |  ";
					
					nodeList = racine.getElementsByTagName("nom");
					node = nodeList.item(0);
					name += node.getTextContent();
					
					liste.addElement(name);
					
				}
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return liste;
	}
	
	
	public GestionXML() {
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\";
		this.file = new File(filePath);
	}
	
	public GestionXML(Session session) {
		super();
		this.session = session;
		this.nomSession = session.getNom();
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + session.getNom() + ".xml";
		this.file = new File(filePath);
	}
	
	public GestionXML(String nomSession) {
		super();
		this.nomSession = nomSession;
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + nomSession + ".xml";
		this.file = new File(filePath);
	}
}
