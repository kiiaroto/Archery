package dao;

import java.io.File;
import java.io.IOException;

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
import org.xml.sax.SAXException;

import modele.Session;

public class GestionXML {
	
	private Session session;
	private String filePath;
	
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
					//TODO
					
					
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

	public void writeXmlFile() {
		//TODO
	}
	
	public void readXmlFile() {
		//TODO
	}
	
	public void deleteXmlFile() {
		
		if (file.exists()) {
			file.delete();
		}
		
	}

	public GestionXML(Session session) {
		super();
		this.session = session;
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + session.getNom() + ".xml";
		this.file = new File(filePath);
	}
	
	
}
