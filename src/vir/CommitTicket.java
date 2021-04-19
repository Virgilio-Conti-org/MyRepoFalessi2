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
		String commit="";
		String date="";
		String ticket="";
		String lineFile;
		
		Connection con;		
        DB db=new DB();
        
       con =db.connectToDBtickectBugZookeeper();
        
           
		try (
		  FileReader fr=new FileReader(pathFileLogGit);
		  BufferedReader br=new BufferedReader(fr);
			                                          ){
			 			
			 while( (lineFile=br.readLine() ) !=null ) {
					
				
				if(lineFile.startsWith("commit") ) {
					commit=lineFile.substring(8);
										
				}
				
				if(lineFile.startsWith("Date") ) {
					date=lineFile.substring(5);
					
				}
				
				if(lineFile.contains("ZOOKEEPER-")  ) {
					String queryInsert="INSERT INTO \"CommitTickets\" (Commit,TicketID,Data)  "+
							"VALUES ( '"+commit+"' ,' "+ticket+" ',' "+date+" ' )";
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert) ){;
					statUpdate.executeUpdate();
					}
				}								
					
	          }//while
		}//try
		
		
	}
}
