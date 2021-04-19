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

	public void FindAuthorCommitPair(String pathLogGitFile) throws SQLException, IOException {
		String commit="/";
		String date="/";
		String autore="/";	
		int count=0;
		String lineFile;
		Connection con;
		PreparedStatement statUpdate;
        DB db=new DB();
        
       con =db.connectToDBtickectBugZookeeper();
        
       
        
		try (
		  FileReader fr=new FileReader(pathLogGitFile);
		  BufferedReader br=new BufferedReader(fr);
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
					
					statUpdate=con.prepareStatement(queryInsert);
					statUpdate.executeUpdate();
					count=0;
				}								
					
	          }//while
		}//try
		
		
	}//fine metodo
}
