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
import java.text.ParseException;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class Proportion {

	String fixV;
	String openV;
	String injectV;
	
	
	//metodo che calcola il valore P secondo il metodo proportion 
	public int calculateP(int fv, int ov,int iv) {
	
	//paramertri	
	//fv=fixVersion ov=openigVersion  iv=InjectedVersion	
		
	//intero che conterrà il valore proportion calcolato
		int p=0; 
		
		
		if(fv==ov) {
			p=1;
			return p;
		}
		
		if(ov==1) {
			p=1;
			return p;
		}
		
		//formula per il calcolo del valore proportion
		p=(fv-iv)/(fv-ov);
		return p;
		
	}
	
	//metodo che calcola il valore Injected Version 
	public int calculateIV(int p,int fv, int ov) {
		//paramertri	
		//p=valore proportion fv=fixVersion ov=openigVersion  
		
		//intero che conterrà il valore proportion calcolato
		int iv=0;
		
		//formula per il calcolo della InjectedVersion
		iv=fv-(fv-ov)*p;
		
		return iv;
		
	}
	
	// metodo per ottenere i valori di Fixed Version e Opening Version
	public void findFixVersionOpenVesion(String fileInfoTicketsBug, String fileInfoProject, String fileDest) throws IOException, ParseException {
		
		String resolutionDate; 
		String createdDate; 
		String[] info; 
		String[] buffSlipt;
		String lineFile;
		FilesHandling fh=new FilesHandling();
		String versioneFix;
		String versioneOpen;
		int indexFixVersion;
		int indexOpenVersion;
		int lung;
		
		Path path= Paths.get(fileInfoProject);		
		List<String> ticketsBugs =Files.readAllLines(path);
	    lung=ticketsBugs.size();
		
	    String[] datesVersions= new String[lung-1];
		String[] versions     = new String[lung-1];
		
		
	    for(int i=1;i<lung;i++) {
			info=ticketsBugs.get(i).split(",");
			versions[i-1]=info[0];
			datesVersions[i-1]=info[3].substring(0,10);
			
		}//for
	        
	   
	   
	   try( 
		   FileReader fr=new FileReader(fileInfoTicketsBug);
		   BufferedReader br=new BufferedReader(fr);
			
		   FileWriter fw=new FileWriter(fileDest);
		   BufferedWriter bw=new BufferedWriter(fw) ){
		   
		while( (lineFile=br.readLine() ) !=null ) {
			buffSlipt=lineFile.split(",");
			
			   resolutionDate=buffSlipt[1];              				   
			   createdDate=buffSlipt[2];                 
			  			   		   
			   indexFixVersion=fh.dateBeforeDate(resolutionDate, datesVersions);
			   indexOpenVersion=fh.dateBeforeDate(createdDate, datesVersions);
			   
			   versioneFix= versions[indexFixVersion] ;
			   versioneOpen=versions[indexOpenVersion] ;
		
			   bw.write(lineFile+","+versioneFix+","+versioneOpen+"\n");
			   bw.flush();
			   
			}
				
	   }//try
	   
	   
	   
	}//fine metodo
	
	
}
