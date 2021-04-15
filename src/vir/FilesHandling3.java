/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class FilesHandling3 {

	
	
	//metodo che trova le affected versions che hanno una data e poi associa  
	//a queste l'indice numerico corrispondente
	public void findVersionsWithDatesAndAssociateIndexVersion(String pathFileCSV, String fileInfoProject,String filedest) throws IOException {
		String[] info;
		String lineFileCSV;
		int lung;
		int i;
		int j;
		int versioneTrovata=0;  //0=versione non trovata  1=versione trovata
		
		Path path= Paths.get(fileInfoProject);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		String[] versions = new String[lung-1];
		String[] versionName = new String[lung-1];
		String[] buffSplit;
		
		for( i=1;i<lung;i++) {
			info=linesTicketsFile.get(i).split(",");
			versions[i-1]=info[0];
			versionName[i-1]=info[2];
			
		}//for
		
        
		FileReader frCSV=null;
		BufferedReader brCSV=null;
				
		FileWriter fwDest=null;
		BufferedWriter bwDest=null;
		
	try {
		 frCSV=new FileReader(pathFileCSV);
		 brCSV=new BufferedReader(frCSV);
				
		 fwDest=new FileWriter(filedest);
		 bwDest=new BufferedWriter(fwDest); 	
		
		 
		 brCSV.readLine();//get rid of first line
		 while( (lineFileCSV=brCSV.readLine() ) !=null ) {
				
			 buffSplit=lineFileCSV.split(",");
			 int lungBS=buffSplit.length;
			 
				for(i=0;i<lungBS;i++) {
					
					for(j=0;j<lung-1;j++) {
						
						if(buffSplit[i].equals(versionName[j])) {
							
							versioneTrovata=1;
							lineFileCSV=buffSplit[0]+","+versionName[j]+","+versions[j];						
							
							bwDest.write(lineFileCSV+"\n");
							bwDest.flush();
							break;
						}
					}//for
					if(versioneTrovata==1) {
						versioneTrovata=0;
						break;
					}
					
				}//for
					
	          }//while
		}//try
		
		finally {
			brCSV.close();
			bwDest.close(); 
		}
		    
		    		
	}//fine metodo
}
