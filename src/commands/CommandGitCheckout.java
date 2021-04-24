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
		
		List<String> resultOK=new ArrayList<>();
		String lineOK;
	
	    var pb=new ProcessBuilder();
	    
	    pb.command("git", "checkout", commit,"-- "+fileToRecover);
	    pb.redirectErrorStream(true);
		
		var process= pb.start();
		
		try(var isOK= process.getInputStream();		
			var isr=new InputStreamReader( isOK );		
			var brOK=new BufferedReader(isr);
					                                              ){
				
				
			while( (lineOK=brOK.readLine()) !=null) {				
				resultOK.add(lineOK);
									
			}//while
				
		}//try 
				
					
			process.waitFor();
				
			
			return resultOK;
			
		
	}//fine metodo
	
	
}
