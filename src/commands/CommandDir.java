/**
 * 
 */
package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Virgilio
 *
 */
public class CommandDir {

	public String dir(String fileJavaName,String pathRepoZookeeper) throws InterruptedException, IOException {
		
	   
	   String lineOK;
	   var pathJavaClass="";
		
	   var pb=new ProcessBuilder();
	
       var fromFolder=new File(pathRepoZookeeper);
		                                        
	   pb.directory( fromFolder);
       pb.command("cmd.exe","/c","dir",fileJavaName, "/b","/s");
       pb.redirectErrorStream(true);	
       
	   var process= pb.start();

    		
	
       try(var isOK= process.getInputStream();		
	       var isr=new InputStreamReader( isOK );		
	       var brOK=new BufferedReader(isr);
		                                              ){
	
	      
	
	     while( (lineOK=brOK.readLine()) !=null) {
	      	 pathJavaClass=pathJavaClass.concat(lineOK);
	    }
	
      }//try 
	
		
	    process.waitFor();
	  
	   
	    return pathJavaClass;
	    
	    
	
	}//fine metodo
	
	
	
}
