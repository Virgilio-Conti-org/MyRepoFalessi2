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

import commands.CommandGitShow;
import database.DB;
import helper.Help;

/**
 * @author Virgilio
 *
 */
public class JavaClassesHandling {
        
	//metodo per associare una versione ad una classe java
	public void versionJavaClassPair(String fileLogGit,String projectInfo) throws IOException, ParseException, SQLException, InterruptedException{
		
		String[] info; 
		String line;		 	
		String version;
		var commit="";
		var dataCommit="/";
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
			datesVersions[i-1]=info[3].substring(0, 10);
			
		}
		
		
		
		try (
		  var fr=new FileReader(fileLogGit);
		  var br=new BufferedReader(fr);			
		                                           ){
			 
			 while( (line=br.readLine() ) !=null ) {
					
				 if(line.startsWith("commit") ) {
						commit=line.substring(7);
						
					}
				 
				 
				 if(line.startsWith("Date") ) {
					dataCommit=line.substring(8,18);
					
				 }
				
			   				
				if( line.contains("ZOOKEEPER-") ) {	
									  
				    nameFiles=searchAndGetFileNames(commit);
				    indexDataJavaClassVersion=help.dateBeforeDate(dataCommit, datesVersions);					
					version=versions[indexDataJavaClassVersion];
					
					
				    for(var i=0;i<nameFiles.size();i++) {
								
					 queryInsert="INSERT INTO \"ListJavaClasses\" ( \"NameClass\" , \"Commit\" , \"DataCommit\" , \"Version\")  " + 
							"VALUES ('"+nameFiles.get(i)+"' ,'"+commit+"' ,'"+dataCommit+"' ,'"+version+"' ) ";
							
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert)){
					    statUpdate.executeUpdate();
					}//try interno
					
					
				  }//for
				}//if
			  				
					
	          }//while
		}//try
		
			 		
	}//fine metodo
	
	
	public List<String> searchAndGetFileNames(String commit) throws IOException, InterruptedException {
		List<String> commandResult;
		var sizeResult=0;
		
		List<String> nameFiles=new ArrayList<>();
		
		
		String[] buffSplit;
		String line;
		int i;
		
		CommandGitShow cmdShow=new CommandGitShow();
		
		commandResult=cmdShow.commandGitShow(commit);
		sizeResult=commandResult.size();
		
		
		i=sizeResult-1;
		while(!(line=commandResult.get(i)).equals("") && i>=0) {
		   
		   buffSplit=line.split("\t");	
	       if( (buffSplit.length) ==3 ) {
	    	   
	    	   var file=buffSplit[2];
		       nameFiles.add(file);
		       	    	       	      
		   } 
	       
	       i=i-1;
		}   
	      	      
		
		return nameFiles;
	}//fine metodo
		
	
	
	
}
