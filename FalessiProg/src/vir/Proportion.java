/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.FileReader;
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
	
	public void Calculate_p(int fv, int ov) {
		int p=0; int iv; int percentage=10;
		
		
		if(fv==ov) {
			p=0;
		}
		
		
	}
	
	public void Calculate_IV(int p,int fv, int ov) {
		int iv=0;
		
		iv=fv-(fv-ov)*p;
		
		//return iv;
		
	}
	
	public void Get_FV_OV(String FileInfoTicketsBug, String FileInfoProject) throws IOException, ParseException {
		int lung;
		String ResolutionDate; Date Res_date;
		String CreatedDate; Date Created_date;
		String DateLimit;  Date dateMax;                 //to get rid of 50% of the releases
		String[] info= {"","","",""}; 
		String lineFile;
		A a=new A();
		int IndexFixVersion, VersioneFix;
		int IndexOpenVersion, VersioneOpen;
		
		Path path= Paths.get(FileInfoProject);		
		List<String> TicketsBugs =Files.readAllLines(path);
	    lung=TicketsBugs.size();
		
	    String[] DatesVersions= new String[lung-1];
		String[] Versions     = new String[lung-1];
		
		FileReader fr=new FileReader(FileInfoTicketsBug);
		BufferedReader br=new BufferedReader(fr);
	    
		DateLimit=a.GetRidOf50Relases(FileInfoProject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateMax=sdf.parse(DateLimit);
		
	    for(int i=1;i<lung;i++) {
			info=TicketsBugs.get(i).split(",");
			Versions[i-1]=info[0];
			DatesVersions[i-1]=info[3].substring(0, 10);
			//System.out.println(Versions[i-1]+" "+DatesVersions[i-1]);
		}//for
	    
	    
	    
		while( (lineFile=br.readLine() ) !=null ) {
			   ResolutionDate=getResolutionDate(lineFile);
			   Res_date=sdf.parse(ResolutionDate);
			   
			   CreatedDate=getCreatedDate(lineFile);
			   Created_date=sdf.parse(CreatedDate);
			   
			   if(Res_date.after(dateMax) || Created_date.after(dateMax)) {
				   continue;
			   }
			   
			   IndexFixVersion=a.DateBefore_Date(ResolutionDate, DatesVersions);
			   IndexOpenVersion=a.DateBefore_Date(CreatedDate, DatesVersions);
			   
			   VersioneFix=Integer.parseInt( Versions[IndexFixVersion] );
			   VersioneOpen=Integer.parseInt(Versions[IndexOpenVersion] );
			   
			   System.out.println("RD= "+ResolutionDate+" DateBef= "+DatesVersions[IndexFixVersion]+"  Ver= "+VersioneFix);
			   System.out.println("CD= "+CreatedDate+" DateBef= "+DatesVersions[IndexOpenVersion]+"  Ver= "+VersioneOpen);
			   
			   //System.out.println("RD= "+ResolutionDate+"  CD "+CreatedDate);
			}
	    
	   
	    br.close();
	}
	
	
	  public String getResolutionDate(String Date) {
		  String ResolutionDate;
		  int index, offset=17;
		  
		  index=Date.indexOf("resolutiondate")+offset;
		  ResolutionDate=Date.substring(index, index+10);
		  
		  return ResolutionDate;
			
	  }
	  
	  public String getCreatedDate(String Date) {
		  String CreatedDate; 
          int index, offset=10;
		  
		  index=Date.indexOf("created")+offset;
		  CreatedDate=Date.substring(index, index+10);
		  
		  return CreatedDate;
	  }
}
