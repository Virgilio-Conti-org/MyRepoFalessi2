/**
 * 
 */
package vir;


import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * @author Virgilio
 *
 */
public class DB {
    
	private String config="config";
	
	private String urlDBzookeeper="jdbc:postgresql://localhost:5432/TickectBugDB";
	private String urlDBbookeeper="jdbc:postgresql://localhost:5432/";
			
	private FileReader fr;
		
	
	//metodo per la connessione verso il database con i dati di Zookeeper
	public Connection connectToDBtickectBugZookeeper() throws SQLException, IOException {
		String user;
		String password;
			
		user=getUserID();
		password=getPws();
	
		return  DriverManager.getConnection(urlDBzookeeper,user,password);
		
		 
	}
	
	//metodo per la connessione verso il database con i dati di Bookkeeper
	public Connection  connectToDBtickectBugBookkeeper() throws SQLException, IOException {
		String user;
		String password;
		
		user=getUserID();
		password=getPws();
				
		return DriverManager.getConnection(urlDBbookeeper,user,password);
	}
	
	//metodo per reperire user id da file
    public String getUserID() throws IOException{
    	    	
    	fr=new FileReader(config);
    	Properties property=new Properties();
		property.load(fr);
	
 
		return property.getProperty("userid");
	}
	
  //metodo per reperire password da file
	public String getPws() throws IOException{
			
		fr=new FileReader(config);
		Properties property=new Properties();
		property.load(fr);
	
 
		return property.getProperty("password");
	    	
	}
	
	
	
}
