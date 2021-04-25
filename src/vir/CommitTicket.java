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
public class CommitTicket {

	
	public void findPair(String pathFileLogGit) throws IOException, SQLException {
		var commit="";
		var date="";
		var ticket="";
		String lineFile;
		Help help= new Help();
		
		Connection con;		
        DB db=new DB();
        
       con =db.connectToDBtickectBugZookeeper();
        
           
		try (
		  var fr=new FileReader(pathFileLogGit);
		  var br=new BufferedReader(fr);
			                                          ){
			 			
			 while( (lineFile=br.readLine() ) !=null ) {
					
				
				if(lineFile.startsWith("commit") ) {
					commit=lineFile.substring(7);
										
				}
				
				if(lineFile.startsWith("Date") ) {
					date=lineFile.substring(8,18);
					
				}
				
				if(lineFile.contains("ZOOKEEPER-")  ) {
					ticket=help.projectStringTicket(lineFile);
					String queryInsert="INSERT INTO \"CommitTickets\" ( \"Commit\" ,\"TicketID\" ,\"Data\")  "+
							"VALUES ( '"+commit+"' ,'"+ticket+"','"+date+"' )";
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert) ){
					statUpdate.executeUpdate();
					}
				}								
					
	          }//while
		}//try
		
		
	}
}
