/**
 * 
 */
package metrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author Virgilio
 *
 */
public class LOCmetric {

	public String loc(String path) throws InterruptedException, IOException {
		
		var errore="";
		String lineErrore;
		String lineOK;
		var stringLOC="";
	   ProcessBuilder pb=new ProcessBuilder();
	    
	    pb.command("find", "/c","/v","  \"\"  ",path);
	    
		
		Process process= pb.start();
		
		try(var isErrore =process.getErrorStream();
			var isrErrore=new InputStreamReader( isErrore );		
			var brErrore=new BufferedReader(isrErrore);
				
			var isOK= process.getInputStream();		
			var isr=new InputStreamReader( isOK );		
			var brOK=new BufferedReader(isr);
					                                              ){
				
			while( (lineErrore=brErrore.readLine()) !=null) {
				errore=errore.concat(lineErrore);
			}
				
			while( (lineOK=brOK.readLine()) !=null) {
					stringLOC=stringLOC.concat(lineOK);
				}
				
			 }//try 
				
					
			int exit=process.waitFor();
				
			if(exit==0) {
				return stringLOC;
			}
			else {
				return errore;
			}
		
	}//fine metodo
	
	//metodo che estrae il numero LOC dalla stringa del comando find
	public int getLOCfromStringLoc(String str) {
		int lung;
		String[] buffer;
		
		buffer=str.split(" ");
		lung=buffer.length;
		
		return Integer.parseInt(buffer[lung-1]) ;
		
	}
	
}
