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
	private String pathFileCredentials="D:\\Libri\\Università\\Falessi\\postgresSql\\CredenzialiPostrgres.txt";
	private String urlDB_Zookeeper="jdbc:postgresql://localhost:5432/TickectBubDB";
	private String urlBD_Bookeeper="jdbc:postgresql://localhost:5432/";
	
	
	private FileReader fr;
	private BufferedReader br;
	
	private String regex=":";
	
	public Connection connectToDB_TickectBugZookeeper() throws SQLException, IOException {
		String user,password;
		
		
		user=getUserID();
		password=getPws();
		Connection con= DriverManager.getConnection(urlDB_Zookeeper,user,password);
		
		return con;
	}
	
	public Connection  connectToDB_TickectBugBookkeeper() throws SQLException, IOException {
		String user,password;
		
		user=getUserID();
		password=getPws();
		Connection con= DriverManager.getConnection(urlBD_Bookeeper,user,password);
		
		return con;
	}
	
	
    public String getUserID() throws IOException{
    	String user_id;
    	String temp;
    	String[] aux= {"",""};
    	fr=new FileReader(pathFileCredentials);
        br=new BufferedReader(fr);
        
        while( !(temp=br.readLine()).contains(userID) ) {
        	aux=temp.split(regex);
        }
        
        user_id=aux[1];
		return user_id;
	}
	
	public String getPws() throws IOException{
		String password;
		fr=new FileReader(pathFileCredentials);
	    br=new BufferedReader(fr);
	    String temp;
	    String[] aux= {"",""};
	    
        while(!(temp=br.readLine()).contains(pws)) {
        	aux=temp.split(regex);
        }
	    
        password=aux[1];
		return password;
	}
}
