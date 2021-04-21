 /**
 * 
 */
package metrics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Virgilio
 *
 */
public class NRmetric {

	public int nr(String pathLogGit,String javaClassName) throws FileNotFoundException, IOException {
		String lineFile;
		var nr=0;
		
		try (var fr=new FileReader(pathLogGit);
		     var br=new BufferedReader(fr);
					                                   ){
					 			
			  while( (lineFile=br.readLine() ) !=null ) {
							
				  if(lineFile.startsWith("Author ") ) {
					  lineFile=br.readLine();
					  
					  nr=checkIfJavaClassIsCommitted(lineFile, br, javaClassName,nr);
					  	  
				  }
						
							
			 }//while
		 }//try
		
		return nr;
		
		
	}//fine metodo
	
	
   public int checkIfJavaClassIsCommitted(String str,BufferedReader br, String javaClassName,int nr) {
	   
	   var lineFile=str;
	   
	   while(!lineFile.startsWith("commit")) {
			  if(lineFile.contains(javaClassName)) {
				 nr=nr+1; 
			  }
	   
	   }
	   return nr;
	   
   }//fine metodo
	
}
