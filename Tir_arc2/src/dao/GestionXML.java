package dao;

import java.io.File;
import java.io.IOException;

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
				
				// Cr�ation de la balise session qui contiendra tout les �lement
				Element sess = document.createElement("session");
					// cr�ation de la balise info qui contiendra toute les info de la session
					Element sessionInfo = document.createElement("info");
						//cr�ation des balise d'information
						Element sessionNom = document.createElement("nom");
						Element sessionCal = document.createElement("cal");
						Element sessionNbrDeVolee = document.createElement("nbrdevolee");
						Element sessionNbrDeFleche = document.createElement("nbrdefleche");
						
						//ajout des balise d'information dans la balise info et leurs ajoute dleurs contenu
						sessionInfo.appendChild(sessionNom).setTextContent(session.getNom());
						sessionInfo.appendChild(sessionCal).setTextContent(session.getDate());
						sessionInfo.appendChild(sessionNbrDeVolee).setTextContent(String.valueOf(session.getNbrDeVol�e()));
						sessionInfo.appendChild(sessionNbrDeFleche).setTextContent(String.valueOf(session.getNbrDeFl�che()));
					
					//ajout de la balise info et son contenu dans la balise session
					sess.appendChild(sessionInfo);
					
					
					//G�n�ration du nombre de vol�e et de fl�che n�cessaire a l'�criture du fichier
					int nbrVolee = session.getNbrDeVol�e();
					int nbrFleche = session.getNbrDeFl�che();
					
					for (int i = 1; i <= nbrVolee; i++) {
						
						Element volee = document.createElement("volee");
						volee.setAttribute("numero", String.valueOf(i));
						
						for (int j = 1; j <= nbrFleche; j++) {
							
							Element fleche = document.createElement("fleche");
							fleche.setAttribute("numero", String.valueOf(j));
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
		// "  |  "
		return liste;
	}
	
	public String getCal() {
		
		return "";
	}
	
	public GestionXML() {
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\";
		this.file = new File(filePath);
	}
	
	public GestionXML(Session session) {
		super();
		this.session = session;
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + session.getNom() + ".xml";
		this.file = new File(filePath);
	}
	
	public GestionXML(String nomSession) {
		super();
		this.filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + nomSession + ".xml";
		this.file = new File(filePath);
	}
}
