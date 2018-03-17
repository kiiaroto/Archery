package dao;

import java.io.File;
import java.io.IOException;

public class GestionXML {
	
	public boolean createXmlFile(String fileName) {
		
		String filePath = System.getenv().get("HOMEPATH") + "\\Documents\\Tir Arc\\" + fileName + ".xml";
		
		File file = new File(filePath);
		
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
}
