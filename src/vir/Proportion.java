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
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class Proportion {

	String FV,OV,IV;
	
	
	//metodo che calcola il valore P secondo il metodo proportion 
	public int Calculate_p(int fv, int ov,int iv) throws IOException {
	
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
	public int Calculate_IV(int p,int fv, int ov) {
		//paramertri	
		//p=valore proportion fv=fixVersion ov=openigVersion  
		
		//intero che conterrà il valore proportion calcolato
		int iv=0;
		
		//formula per il calcolo della InjectedVersion
		iv=fv-(fv-ov)*p;
		
		return iv;
		
	}
	
	// metodo per ottenere i valori di Fixed Version e Opening Version
	public void Find_FV_OV(String FileInfoTicketsBug, String FileInfoProject, String FileDest) throws IOException, ParseException {
		
		String ResolutionDate; //Date Res_date;
		String CreatedDate; //Date Created_date;
		//String DateLimit;  //Date dateMax;               //to get rid of 50% of the releases
		String[] info= {"","","",""}; 
		String lineFile;
		FilesHandling fh=new FilesHandling();
		String VersioneFix;
		String VersioneOpen;
		int IndexFixVersion;
		int IndexOpenVersion;
		int lung;
		
		Path path= Paths.get(FileInfoProject);		
		List<String> TicketsBugs =Files.readAllLines(path);
	    lung=TicketsBugs.size();
		
	    String[] DatesVersions= new String[lung-1];
		String[] Versions     = new String[lung-1];
		
		FileReader fr=new FileReader(FileInfoTicketsBug);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fw=new FileWriter(FileDest);
		BufferedWriter bw=new BufferedWriter(fw);
		
		//DateLimit=a.GetRidOf50Relases(FileInfoProject);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//dateMax=sdf.parse(DateLimit);
		
	    for(int i=1;i<lung;i++) {
			info=TicketsBugs.get(i).split(",");
			Versions[i-1]=info[0];
			DatesVersions[i-1]=info[3].substring(0,10);
			//System.out.println(Versions[i-1]+" "+DatesVersions[i-1]);
		}//for
	    
	    
	    String[] buffSlipt= {"","",""};
		while( (lineFile=br.readLine() ) !=null ) {
			buffSlipt=lineFile.split(",");
			   ResolutionDate=buffSlipt[1];              
			 //  Res_date=sdf.parse(ResolutionDate);
			   
			   CreatedDate=buffSlipt[2];                 
			  // Created_date=sdf.parse(CreatedDate);
			   
			   /*if(Res_date.after(dateMax) || Created_date.after(dateMax)) {
				   continue;
			   }*/
			   
			   IndexFixVersion=fh.DateBefore_Date(ResolutionDate, DatesVersions);
			   IndexOpenVersion=fh.DateBefore_Date(CreatedDate, DatesVersions);
			   
			   VersioneFix= Versions[IndexFixVersion] ;
			   VersioneOpen=Versions[IndexOpenVersion] ;
		
			   bw.write(lineFile+","+VersioneFix+","+VersioneOpen+"\n");
			   bw.flush();
			   //System.out.println("RD= "+ResolutionDate+" DateBef= "+DatesVersions[IndexFixVersion]+"  Ver= "+VersioneFix);
			   //System.out.println("CD= "+CreatedDate+" DateBef= "+DatesVersions[IndexOpenVersion]+"  Ver= "+VersioneOpen);
			   
			   //System.out.println("RD= "+ResolutionDate+"  CD "+CreatedDate);
			}
	    
	    br.close();
	    bw.close();
	}
	
	
}
