/**
 * 
 */
package metrics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class NAUTHmetric {

	public int nauth(String pathLogGit, String JavaClassName) throws FileNotFoundException, IOException {
		String lineFile;
		String author;
		boolean isCommitted;
		List<String> listAuthors=new ArrayList<>();
		List<String> listAuthorsNoDuplicates=new ArrayList<>();
		
		
		try (var fr=new FileReader(pathLogGit);
			 var br=new BufferedReader(fr);
						                                   ){
						 			
		    while( (lineFile=br.readLine() ) !=null ) {
								
				if(lineFile.startsWith("Author ") ) {
					
				   author=lineFile.substring(8); 				
				   isCommitted=checkIfAuthorCommittedJavaClass(author, br, JavaClassName);
				   
				   if(isCommitted) {
					  listAuthors.add(author); 
				   }
				  
			     }//if esterno
							
								
			}//while
	    }//try
		
		listAuthorsNoDuplicates=eliminaDuplicati(listAuthors);
		
		return listAuthorsNoDuplicates.size();
		
		
		
		
}//fine metodo
	
	
	public boolean checkIfAuthorCommittedJavaClass(String str,BufferedReader br, String javaClassName) {
		   
	   var lineFile=str;
		   
	   while(!lineFile.startsWith("commit")) {
			 if(lineFile.contains(javaClassName)) {
				return true;	 
			 }		   
	   }
	   
	  return false;
		   
}//fine metodo
	
	public  List<String> eliminaDuplicati(List<String> autori){
		
		
		for(var i=0;i<autori.size();i++) {
			var elem=autori.get(i);
			
			for(var j=i+1;j<autori.size();j++) {
				if( autori.contains(elem) ) {
					
					autori.remove(j);
				}
				
			}//for interno
			
		}//for esterno
		
		
        return autori;
		
	}//fine metodo
	
}
