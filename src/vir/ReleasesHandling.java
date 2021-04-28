/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import database.DB;
import helper.Help;

/**
 * @author Virgilio
 *
 */
public class ReleasesHandling {

	
	
	//metodo che trova le affected versions che hanno una data e poi associa  
	//a queste l'indice numerico corrispondente
	public List<String> findInjectedVersionsWithDatesAndAssociateIndexVersion(String pathFileCSV, String fileInfoProject) throws IOException {
		String[] info;
		String lineFileCSV;
		int lung;
		int i;
		int j;
		var versioneTrovata=0;  //0=versione non trovata  1=versione trovata
		List<String> result=new ArrayList<>();
		
		var path= Paths.get(fileInfoProject);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		var versions = new String[lung-1];
		var versionName = new String[lung-1];
		var versionDate = new String[lung-1];
		String[] buffSplit;
		
		for( i=1;i<lung;i++) {
			info=linesTicketsFile.get(i).split(",");
			versions[i-1]=info[0];//indice numerico intero
			versionName[i-1]=info[2];
			versionDate[i-1]=info[3].substring(0,10);
		}//for
		
        
		
	try (
		var frCSV=new FileReader(pathFileCSV);
		var brCSV=new BufferedReader(frCSV);				
		                                                ){	
		
		 
		 lineFileCSV=brCSV.readLine();//get rid of first line
		 while( (lineFileCSV=brCSV.readLine() ) !=null ) {
				
			 buffSplit=lineFileCSV.split(",");
			 int lungBS=buffSplit.length;
			 
				for(i=0;i<lungBS;i++) {
					
					for(j=0;j<lung-1;j++) {//ciclo sulle date del fileInfoProgetto
						
						if(buffSplit[i].equals(versionName[j])) {
							
							versioneTrovata=1;
							lineFileCSV=buffSplit[0]+","+versionDate[j]+","+versions[j];						
							result.add(lineFileCSV);							
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
		
	return result;	
			    		
	}//fine metodo
	
	
	
	// metodo per ottenere i valori di Fixed Version e Opening Version
    public void findFixVersionOpenVesion(String fileInfoTicketsBug, String fileInfoProject) throws IOException, ParseException, SQLException {
			
		String resolutionDate; 
		String createdDate; 
		String ticket;
		String[] info; 
		String[] buffSlipt;
		String lineFile;
		String versioneFix;
		String versioneOpen;
		int indexFixVersion;
		int indexOpenVersion;
		int lung;
		
		var help=new Help();
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		
		var path= Paths.get(fileInfoProject);		
		List<String> ticketsBugs =Files.readAllLines(path);
	    lung=ticketsBugs.size();
			
	    var datesVersions= new String[lung-1];
		var versions     = new String[lung-1];
			
			
	   for(var i=1;i<lung;i++) {
		  info=ticketsBugs.get(i).split(",");
		  versions[i-1]=info[0];
	   	  datesVersions[i-1]=info[3].substring(0,10);
				
		}//for
		        
		   
		   
		 try( 
		   var fr=new FileReader(fileInfoTicketsBug);
		   var br=new BufferedReader(fr);				
		                                            ){
			   
			while( (lineFile=br.readLine() ) !=null ) {
			  	
			   buffSlipt=lineFile.split(",");
			   ticket=buffSlipt[0];
			   
			   resolutionDate=buffSlipt[1];              				   
			   createdDate=buffSlipt[2];                 
				  			   		   
			   indexFixVersion=help.dateBeforeDate(resolutionDate, datesVersions);
			   indexOpenVersion=help.dateBeforeDate(createdDate, datesVersions);
				   
			   versioneFix= versions[indexFixVersion] ;
			   versioneOpen=versions[indexOpenVersion] ;
			
			   var queryUPD="INSERT INTO  \"Tickect_FV_OV\"  "
						+" (\"TicketBugID\", \"DateFixVersion\", \"DateOpenVersion\", \"FV\", "
					    +"  \"OP\", \"ProportionValue\", \"AffectedVersion\", \"DateAffectedVersion\" )  "
					    +" VALUES ('"+ticket+"','"+resolutionDate+"','"+createdDate+"',"
					    +"         '"+versioneFix+"','"+versioneOpen+"',null,null,null)";
							
			   
			   try(var statUpdate=conn.prepareStatement(queryUPD)){
				    statUpdate.executeUpdate();
				}
			   
				   
			}//while
					
		  }//try
		   
		   
		   
}//fine metodo
	
	
	
	
	public void writeInjectedVersionsInDB(List<String> dataInjectedVersions) throws SQLException, IOException {
		
		String[] buffer;
		String ticket;
		String dateInjectedVersion;
		String injectedVersion; //indice numerico intero
		
		var db=new DB();
		var con=db.connectToDBtickectBugZookeeper();
		
		
		for(var i=0;i<dataInjectedVersions.size() ;i++) {
		  buffer=dataInjectedVersions.get(i).split(",");
		  ticket=buffer[0];
		  dateInjectedVersion=buffer[1];
		  injectedVersion=buffer[2];
		  
		  
		  var queryUPD="INSERT INTO \"TicketWithAffectedVersion\"  "
				+" (\"TicketBugID\",\"VersionDate\",\"AffectedVersion\",\"ProportionValue\")  "
		  		+"VALUES ( '"+ticket+"','"+dateInjectedVersion+"','"+injectedVersion+"', null ) ";
				 
		      		  
		try(var stat=con.prepareStatement(queryUPD) ){
			stat.executeUpdate();
		}
		
	  }//for
	}//fine metodo
	
}
