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
public class FilesHandling2 {
    
	private static final String M="M";
	private static final String COMMIT="commit";
	private static final String DATE="Date";
	private static final String BK="BOOKKEEPER-";
	private static final String ZK="ZOOKEEPER-";
	private String projectChoosen="";
	
	//metodo per ottenere le classi java associate ai tickets Bug
	public void getFileNameJava(String fileResult, String pathGitLog,int selectorProject ) throws IOException {
		
	  String extensionJava=".java";
	  String lineLog; 
	  String nameJavaClass;
	  String ticket="";
	  int lung;
	  
	  
	  
	  Path path= Paths.get(fileResult);		
	  List<String> linesTicketsBugs =Files.readAllLines(path);
	  lung=linesTicketsBugs.size();
	  
	  
		
	  
	  if(selectorProject==1) {
		   projectChoosen=ZK;
	  }
	  if(selectorProject==2) {
		   projectChoosen=BK;
	  }
	  
	  
	  
	 try (
		 
	   FileReader fr=new FileReader(pathGitLog);
	   BufferedReader br=new BufferedReader(fr);
		 		  
	   FileWriter fwRes=new FileWriter(fileResult);
	   BufferedWriter  bwRes=new BufferedWriter(fwRes); ){
		 
	    for(int i=0;i<lung;i++) {
		   ticket=linesTicketsBugs.get(i);
		  
		   bwRes.write(ticket+"\n");
   	       bwRes.flush();
   	      
		   if(ticket.startsWith(DATE) ) {
			  continue;
		   }
	        
		  
		   while(   !(br.readLine().contains(ticket)   ) ) {
			//ciclo per trovare il particolare ticket bug
		   }
		
		
		  lineLog=br.readLine();
          while(  !(lineLog.startsWith(COMMIT)  || lineLog.contains(projectChoosen))  ) {
			
            if(lineLog.startsWith(M) && lineLog.contains(extensionJava)) {
        	  nameJavaClass=lineLog;
       	      bwRes.write(nameJavaClass+"\n");
       	      bwRes.flush();
       	      
       	      
            } 
            lineLog=br.readLine();
                	
		 }//while
		     
	  }//for   
	 }//try  
	 
	 
	 
}//fine metodo
	
	
	//metodo per associare una data ad un particolare ticket bug
	public void dateTickets(String pathTicketsFile, String pathGitLog, String fileResult,int selectorProject) throws IOException {
		String lineLog; 
		String data; 
		String ticket; 
		int lung;
		
		
		Path path= Paths.get(pathTicketsFile);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
				 
		
		if(selectorProject==1) {
			   projectChoosen=ZK;
		  }
		  if(selectorProject==2) {
			   projectChoosen=BK;
		  }
		
	try (	
		
		FileReader fr=new FileReader(pathGitLog);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fwRes=new FileWriter(fileResult);
		BufferedWriter bwRes=new BufferedWriter(fwRes) ){
		
		while( (lineLog=br.readLine() ) !=null ) {
			
			if (lineLog.startsWith(DATE)) {
				data=lineLog;
				
				bwRes.write(data+"\n");
				bwRes.flush();
				
			}
					
			if (lineLog.contains( projectChoosen ) ) {
				
				lineLog=projectStringTicket(lineLog);	
				
				
				for(int i=0;i<lung;i++) {
			  
				  String particularTicket = linesTicketsFile.get(i);	
				  if( lineLog.equals( particularTicket )  ) {
					  ticket=particularTicket;					  
					 
					  bwRes.write(ticket+"\n");	
					  bwRes.flush();
				  }
				}//for
				
				
			}//if
								
		}//while
	}//try
	
   	
		
}//fine metodo
	
	// metodo per gestire la parte numerica variabile di un perticolare ticket bug
	public String projectStringTicket(String s) {
		String p;
		int lungZK;
		int lungS;
		int i=0;
		int indice;
		int indice2;
		int diff=0;
		
		lungZK=projectChoosen.length();
		lungS=s.length();
		
		indice=s.indexOf(projectChoosen);
		
		indice2=indice+lungZK;
		
		if(indice2>lungS-4) {
			
			diff=lungS-indice2;
			p=s.substring(indice2, indice2+diff);
			return projectChoosen+p;
		}
		else {
						
		    p=s.substring(indice2, indice2+4);			
		}
		
		for( i=0;i<4;i++) {
			if(!Character.isDigit(p.charAt(i) ) ) {
				break;
			}
		}
		
		return projectChoosen+p.substring(0, i);
				
	}
	


}
