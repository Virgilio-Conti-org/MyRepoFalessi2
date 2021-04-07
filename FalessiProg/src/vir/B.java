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
public class B {
    
	private final String M="M";
	private final String commit="commit";
	private final String Date="Date";
	//private final String bk="BOOKKEEPER-";
	private final String zk="ZOOKEEPER-";
	
	public void GetFileName(String fileResult, String pathGitLog ) throws IOException {
		
	  String ext=".java";String lineLog; String NameJavaClass;String ticket="";int lung;
	  
	  FileReader fr=new FileReader(pathGitLog);
	  BufferedReader br=new BufferedReader(fr);
	  
	  Path path= Paths.get(fileResult);		
	  List<String> linesTicketsBugs =Files.readAllLines(path);
	  lung=linesTicketsBugs.size();
	  
	  FileWriter fwRes=new FileWriter(fileResult);
	  BufferedWriter bwRes=new BufferedWriter(fwRes);
		
	  
	  
	  for(int i=0;i<lung;i++) {
		  ticket=linesTicketsBugs.get(i);
		  
		  bwRes.write(ticket+"\n");
   	      bwRes.flush();
   	      
		  if(ticket.startsWith(Date) ) {
			  continue;
		  }
	        
		  
		  while(   !((lineLog=br.readLine()).contains(ticket))   ) {
			//ciclo per trovare il ticket bug
		  }
		
		  //System.out.println("* "+lineLog);
		  lineLog=br.readLine();
          while(  !(lineLog.startsWith(commit)  || lineLog.contains(zk))  ) {
			
            if(lineLog.startsWith(M) && lineLog.contains(ext)) {
        	  NameJavaClass=lineLog;
       	      bwRes.write(NameJavaClass+"\n");
       	      bwRes.flush();
       	      //System.out.println(NameJavaClass);
       	      
            } 
            lineLog=br.readLine();
                	
		 }//while
		     
	  }//for   
	  
	  
		br.close();
		bwRes.close();
		
	}
	
	public void Date_Tickets(String pathTicketsFile, String pathGitLog, String fileResult) throws IOException {
		String lineLog; String data; String ticket; int lung;
		
		
		Path path= Paths.get(pathTicketsFile);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		
		FileReader fr=new FileReader(pathGitLog);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fwRes=new FileWriter(fileResult);
		BufferedWriter bwRes=new BufferedWriter(fwRes);
		
		while( (lineLog=br.readLine() ) !=null ) {
			
			if (lineLog.startsWith(Date)) {
				data=lineLog;
				//System.out.println(data);
				bwRes.write(data+"\n");
				bwRes.flush();
				
			}
					
			if (lineLog.contains( zk ) ) {
				
				lineLog=ZK_string_ticket(lineLog);	
				//System.out.println(" ** "+lineLog);
				
				for(int i=0;i<lung;i++) {
			  
				  String particularTicket = linesTicketsFile.get(i);	
				  if( lineLog.equals( particularTicket )  ) {
					  ticket=particularTicket;					  
					  //System.out.println("  "+ticket);
					  bwRes.write(ticket+"\n");	
					  bwRes.flush();
				  }
				}//for
				
				
			}//if
								
		}//while
	
		br.close();
		bwRes.close();
		
	}
	
	public String ZK_string_ticket(String s) {
		String p; int lung_zk;int lung_s;int i=0;;int indice;
		int diff=0;int indice2;
		
		lung_zk=zk.length();
		lung_s=s.length();
		
		indice=s.indexOf(zk);
		
		indice2=indice+lung_zk;
		
		if(indice2>lung_s-4) {
			
			diff=lung_s-indice2;
			p=s.substring(indice2, indice2+diff);
			return p=zk+p;
		}
		else {
						
		    p=s.substring(indice2, indice2+4);			
		}
		
		for( i=0;i<4;i++) {
			if(!Character.isDigit(p.charAt(i) ) ) {
				break;
			}
		}
		
		p=zk+p.substring(0, i);;
		
		return p;
	}
	
	


}
