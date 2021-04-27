 /**
 * 
 */
package metrics;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DB;

/**
 * @author Virgilio
 *
 */
public class NRmetric {

	public void calculateNR() throws IOException, SQLException {
		
		var nr=0;	
		String javaClass;
		String commit;
		String dataCommit;
				
		ResultSet rsJavaClasses;
		ResultSet rsJavaClasses2;
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var queryJavaClasses=" SELECT \"NameClass\",COUNT(\"NameClass\") "
				+ "FROM \"ListJavaClasses\"   "
				+ "WHERE \"NameClass\" LIKE '%.java'  "
				+ "GROUP BY \"NameClass\" ";
		
		try( var stat=conn.prepareStatement(queryJavaClasses) 			 
					                                           ){
		  rsJavaClasses=stat.executeQuery();
		  
		 
          while( rsJavaClasses.next() ) {
        	
        	 javaClass=rsJavaClasses.getString("NameClass");
        	 
        	 var queryJavaClasses2=" SELECT *  "
     				+ "FROM \"ListJavaClasses\"    "
     				+ "WHERE \"NameClass\" = '"+javaClass+"'  "
     				+ "ORDER BY \"DataCommit\"  ASC   ";
     		
        	 try(var stat2=conn.prepareStatement(queryJavaClasses2) ){
  			   rsJavaClasses2=stat2.executeQuery();
  			   
  			   			   
  			   while(rsJavaClasses2.next()) {
  				
  				   
  				 commit=rsJavaClasses2.getString("Commit");
  				 dataCommit=rsJavaClasses2.getString("DataCommit");
  				   
  				 nr=nr+1;  
  				 
  				 var queryUpdate=" UPDATE \"ListJavaClasses\" "
	      		         + "SET \"NR\" = "+nr+"  "
	    		         + "WHERE  \"NameClass\" = '"+javaClass+"' AND  "
	    		         + "        \"Commit\"='"+commit+"'  AND   "
	                     + "        \"DataCommit\"='"+dataCommit+"'    ";
			           					
		         try(var statUpdate=conn.prepareStatement(queryUpdate)){
		            statUpdate.executeUpdate();
		         }//try
  				   
  			   }//while
  			   
  			   nr=0;
  			 }//try
        	 
     		      	         						
			
		}//while
		
	
	}//try	
		
		
		
		
}//fine metodo

	
}
