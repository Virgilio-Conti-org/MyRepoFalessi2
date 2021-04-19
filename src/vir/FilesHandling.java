/**
 * 
 */
package vir;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class FilesHandling {
        
	//metodo per associare una versione ad una classe java
	public void versionJavaclassPair(String fileLogGit,String projectInfo) throws IOException, ParseException, SQLException{
		
		String[] info; 
		String line;
		var dataCommit="/"; 	
		String version;
		var commit="";
		List<String> nameFiles; 
		int indexDataJavaClassVersion;
		var lung=0; 
	    Help help=new Help();
	    Connection con;

		DB db=new DB();
		String queryInsert;
		
		var path= Paths.get(projectInfo);		
		List<String> linesProjectInfoFile =Files.readAllLines(path);
		lung=linesProjectInfoFile.size();
		
		var datesVersions= new String[lung-1];
		var versions     = new String[lung-1];
		
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
			
		for(var i=1;i<lung;i++) {
			info=linesProjectInfoFile.get(i).split(",");
			versions[i-1]=info[0];
			datesVersions[i-1]=info[3];
			
		}
		
		
		
		try (
		  var fr=new FileReader(fileLogGit);
		  var br=new BufferedReader(fr);			
		                                           ){
			 
			 while( (line=br.readLine() ) !=null ) {
					
				 if(line.startsWith("commit") ) {
						commit=line.substring(9);
						
					}
				 
				 
				 if(line.startsWith("Date") ) {
					dataCommit=line.substring(8,18);
					
				 }
				
			   				
				if( line.contains("ZOOKEEPER-") ) {	
				 					  
				    nameFiles=searchAndGetFileNames(line,br);
				    indexDataJavaClassVersion=help.dateBeforeDate(dataCommit, datesVersions);					
					version=versions[indexDataJavaClassVersion];
				    
				    for(String file:nameFiles) {
								
					 queryInsert="INSERT INTO \"ListJavaClasses\" (NameClass,Commit,DataCommit,Version)  " + 
							"VALUES ('"+file+"' ,'"+commit+"' ,'"+dataCommit+"' ,"+version+" ,) ";
							
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert)){
					    statUpdate.executeUpdate();
					}//try
					
					
				  }//for
				}//if
			  				
					
	          }//while
		}//try
		
			 		
	}//fine metodo
	
	
	public List<String> searchAndGetFileNames(String str,BufferedReader br) throws IOException {
		List<String> namefiles=new ArrayList<>(); 
		
		while(!str.equals("")) {
			
		   if(str.startsWith("M")) {
		       namefiles.add(str.substring(2));
		       	    	       	      
		   } 
		   
	    str=br.readLine();   	      
	       		
		}//while
		
		return namefiles;
	}//fine metodo
		
	
	
	
}
