/**
 * 
 */
package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Virgilio
 *
 */
public class CommandGitShow {

public String commandGitShow(String commit) throws IOException, InterruptedException {
		
		var errore="";
		String lineErrore;
		String lineOK;
		var stringLOC="";
	    var pb=new ProcessBuilder();
	    
	    pb.command("git", "--no-pager", "show",commit,"--numstat");
	    
		
		var process= pb.start();
		
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
	
}
