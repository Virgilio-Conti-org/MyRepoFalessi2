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
public class CommandFind {

	public String find(String path) throws IOException, InterruptedException {
		
		
		String lineOK;
		var stringLOC="";
	    var pb=new ProcessBuilder();
	    
	    pb.command("find", "/c","/v","  \"\"  ",path);
	    pb.redirectErrorStream(true);
		
		var process= pb.start();
		
		try(var isOK= process.getInputStream();		
			var isr=new InputStreamReader( isOK );		
			var brOK=new BufferedReader(isr);
					                                              ){
				
				
			while( (lineOK=brOK.readLine()) !=null) {
					stringLOC=stringLOC.concat(lineOK);
				}
				
		}//try 
				
					
			process.waitFor();
				
			
			return stringLOC;
		
		
	}
}
