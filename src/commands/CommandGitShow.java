/**
 * 
 */
package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class CommandGitShow {

public List<String> commandGitShow(String commit) throws IOException, InterruptedException {
		
		List<String> errore=new ArrayList<>();
		String lineErrore;
		
		List<String> resultOK=new ArrayList<>();
		String lineOK;
	
		
		var prop=new Properties();		
		try(var fr = new FileReader("config")
			                                        ){
		   prop.load(fr);
	    }
		var pathRepoZookeeper=prop.getProperty("pathRepoZookeeper");
		
		
	    var pb=new ProcessBuilder();
	    
	    var fromFolder=new File(pathRepoZookeeper);
	    pb.directory( fromFolder);
	    pb.command("git", "--no-pager", "show",commit,"--numstat");
	    pb.redirectErrorStream(true);
		
		var process= pb.start();
		
		try(/*var isErrore =process.getErrorStream();
			var isrErrore=new InputStreamReader( isErrore );		
			var brErrore=new BufferedReader(isrErrore);*/
				
			var isOK= process.getInputStream();		
			var isr=new InputStreamReader( isOK );		
			var brOK=new BufferedReader(isr);
					                                              ){
			
			/*while( (lineErrore=brErrore.readLine()) !=null) {
				errore.add(lineErrore);
			}*/
				
			while( (lineOK=brOK.readLine()) !=null) {				
					resultOK.add(lineOK);
								
			}//while
				
						
			int exit=process.waitFor();
				
			//if(exit==0) {
				
				return resultOK;
			/*}
			else {
				
				return errore;
			}*/
		}//try 
		
	}//fine metodo
	
}
