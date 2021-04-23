/**
 * 
 */
package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class CommandGitCheckout {

public List<String> checkout(String commit, String fileToRecover) throws IOException, InterruptedException {
		
		List<String> errore=new ArrayList<>();
		String lineErrore;
		
		List<String> resultOK=new ArrayList<>();
		String lineOK;
	
	    var pb=new ProcessBuilder();
	    
	    pb.command("git", "checkout", commit,"-- "+fileToRecover);
	    
		
		var process= pb.start();
		
		try(var isErrore =process.getErrorStream();
			var isrErrore=new InputStreamReader( isErrore );		
			var brErrore=new BufferedReader(isrErrore);
				
			var isOK= process.getInputStream();		
			var isr=new InputStreamReader( isOK );		
			var brOK=new BufferedReader(isr);
					                                              ){
				
			while( (lineErrore=brErrore.readLine()) !=null) {
				errore.add(lineErrore);
			}
				
			while( (lineOK=brOK.readLine()) !=null) {
				
					resultOK.add(lineOK);
				
					
			}//while
				
		}//try 
				
					
			int exit=process.waitFor();
				
			if(exit==0) {
				return resultOK;
			}
			else {
				return errore;
			}
		
		
	}//fine metodo
	
	
}
