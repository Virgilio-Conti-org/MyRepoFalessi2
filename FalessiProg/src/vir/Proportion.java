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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class Proportion {

	private String FV,OV,IV;
	
	//metodo che calcola il valore P secondo il metodo proportion 
	public int Calculate_p(int fv, int ov,int iv,String FilePathTickectsBug) throws IOException {
		int p=0;  int windowWidth;int nBugs;
		A a=new A();
		
		nBugs=a.NumberOfTicketsBug(FilePathTickectsBug);
		windowWidth=nBugs/100;  //numero di bugs che corrisponde a 1% dei bugs totali 
				
		
		if(fv==ov) {
			p=0;
			return p;
		}
		
		if(ov==1) {
			p=1;
			return p;
		}
		
		p=(fv-iv)/(fv-ov);
		return p;
		
	}
	
	//metodo che calcola il valore Injected Version 
	public int Calculate_IV(int p,int fv, int ov) {
		int iv=0;
		
		iv=fv-(fv-ov)*p;
		
		return iv;
		
	}
	
	// metodo per ottenere i valori di Fixed Version e Opening Version
	public void Find_FV_OV(String FileInfoTicketsBug, String FileInfoProject, String FileDest) throws IOException, ParseException {
		int lung;
		String ResolutionDate; Date Res_date;
		String CreatedDate; Date Created_date;
		String DateLimit;  Date dateMax;               //to get rid of 50% of the releases
		String[] info= {"","","",""}; 
		String lineFile;
		A a=new A();
		String VersioneFix;
		String VersioneOpen;
		int IndexFixVersion;
		int IndexOpenVersion;
		
		Path path= Paths.get(FileInfoProject);		
		List<String> TicketsBugs =Files.readAllLines(path);
	    lung=TicketsBugs.size();
		
	    String[] DatesVersions= new String[lung-1];
		String[] Versions     = new String[lung-1];
		
		FileReader fr=new FileReader(FileInfoTicketsBug);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fw=new FileWriter(FileDest);
		BufferedWriter bw=new BufferedWriter(fw);
		
		DateLimit=a.GetRidOf50Relases(FileInfoProject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateMax=sdf.parse(DateLimit);
		
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
			   Res_date=sdf.parse(ResolutionDate);
			   
			   CreatedDate=buffSlipt[2];                 
			   Created_date=sdf.parse(CreatedDate);
			   
			   /*if(Res_date.after(dateMax) || Created_date.after(dateMax)) {
				   continue;
			   }*/
			   
			   IndexFixVersion=a.DateBefore_Date(ResolutionDate, DatesVersions);
			   IndexOpenVersion=a.DateBefore_Date(CreatedDate, DatesVersions);
			   
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
