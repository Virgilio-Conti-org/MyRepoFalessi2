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
		
	   var errore="";
	   String lineErrore;
	   String lineOK;
	   var pathJavaClass="";
		
	   var pb=new ProcessBuilder();
	
       var fromFolder=new File(pathRepoZookeeper);
		                                        
	   pb.directory( fromFolder);
       pb.command("cmd.exe","/c","dir",fileJavaName, "/b","/s");
    	
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
