/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Virgilio
 *
 */
public class Autori {

	public void findAuthorCommitPair(String pathLogGitFile) throws SQLException, IOException {
		var commit="/";
		var date="/";
		var autore="/";	
		var count=0;
		String lineFile;
		
		Connection con;		
        DB db=new DB();
        
       con =db.connectToDBtickectBugZookeeper();
        
       
        
		try (
		  var fr=new FileReader(pathLogGitFile);
		  var br=new BufferedReader(fr);
			                                          ){
			 			
			 while( (lineFile=br.readLine() ) !=null ) {
					
				if(lineFile.startsWith("commit") ) {
					commit=lineFile.substring(8);
					count=count+1;
					
				}
				
				if(lineFile.startsWith("Date") ) {
					date=lineFile.substring(5);
					count=count+1;
				}
				
				if(lineFile.startsWith("Author") ) {
					autore=lineFile.substring(8);
					count=count+1;
				}
				
				if(count==3) {
					String queryInsert="INSERT INTO \"Autori\" (Name,Commit,Data)  "+
							"VALUES ( '"+autore+"' ,' "+commit+" ',' "+date+" ' )";
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert) ){
					statUpdate.executeUpdate();
					count=0;
					}//try
					
				}//if								
					
	          }//while
		}//try
		
		
	}//fine metodo
}
