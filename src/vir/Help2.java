/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class Help2 {

	public String getJavaPath(String fileJavaName) throws IOException, InterruptedException {
		var errore="";
		String lineErrore;
		String lineOK;
		var pathJavaClass="";
		
		Properties prop=new Properties();
	
		try(var fr = new FileReader("config")
			                                        ){
		   prop.load(fr);
	    }
		String pathRepoZookeeper=prop.getProperty("pathRepoZookeeper");
		
		
		
		ProcessBuilder pb=new ProcessBuilder();
		
	    File fromFolder=new File(pathRepoZookeeper);
			                                        
		pb.directory( fromFolder);
	    pb.command("cmd.exe","/c","dir",fileJavaName, "/b","/s");
	    	
		Process process= pb.start();
	
        		
		
	try(InputStream isErrore =process.getErrorStream();
		InputStreamReader isrErrore=new InputStreamReader( isErrore );		
		BufferedReader brErrore=new BufferedReader(isrErrore);
		
		InputStream isOK= process.getInputStream();		
		InputStreamReader isr=new InputStreamReader( isOK );		
		BufferedReader brOK=new BufferedReader(isr);
			                                              ){
		
		while( (lineErrore=brErrore.readLine()) !=null) {
			errore=errore.concat(lineErrore);
		}
		
		while( (lineOK=brOK.readLine()) !=null) {
			pathJavaClass=pathJavaClass.concat(lineOK);
		}
		
	 }//try 
		
			
		int exit=process.waitFor();
		
		if(exit==0) {
			return pathJavaClass;
		}
		else {
			return errore;
		}
		
				
	}//fine metodo
	
}
