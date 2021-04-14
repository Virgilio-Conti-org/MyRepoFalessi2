/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


/**
 * @author Virgilio
 *
 */
public class DB {
    
	private String userID="USERID";
	private String pws="PWS";
	
	private String urlDBzookeeper="jdbc:postgresql://localhost:5432/TickectBugDB";
	private String urlDBbookeeper="jdbc:postgresql://localhost:5432/";
			
	private FileReader fr;
	private BufferedReader br;
	
	private String regex=":";

	private ConstantsAndPaths constants;	
	
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
		
		Connection con= DriverManager.getConnection(urlDBbookeeper,user,password);
		
		return con;
	}
	
	//metodo per reperire user id da file
    public String getUserID() throws IOException{
    	String userid="";
    	String temp;
    	String[] aux;
    	
    	constants=new ConstantsAndPaths();
    	
    	fr=new FileReader(constants.getPathFileCredentials());
        br=new BufferedReader(fr);
        
        while( !(temp=br.readLine()).contains(userID) ) {
        	
        	
        }
        aux=temp.split(regex);
         
        userid=aux[1];
 
		return userid;
	}
	
  //metodo per reperire password da file
	public String getPws() throws IOException{
		String password;
		
		constants=new ConstantsAndPaths();	
		
		fr=new FileReader(constants.getPathFileCredentials());
	    br=new BufferedReader(fr);
	    String temp;
	    String[] aux;
	    
        while(!(temp=br.readLine()).contains(pws)) {
        	
        }
        
        aux=temp.split(regex);
        password=aux[1];
      
		return password;
	}
	
	
	
}
