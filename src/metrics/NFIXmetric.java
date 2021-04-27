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
public class NFIXmetric {

	public void calculateNFIX() throws SQLException, IOException {
		
		var nfix=0;	
		var javaFile="";
		String commit;
		String dataCommit;
		
		
		ResultSet rsJavaClasses;
		ResultSet rsNfix;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var queryJavaClasses=" SELECT \"NameClass\",COUNT(\"NameClass\") "
				+ "FROM \"ListJavaClasses\"   "
				+ "WHERE \"NameClass\" LIKE '%.java'  "
				+ "GROUP BY \"NameClass\" ";
		
				
		try(var stat=conn.prepareStatement(queryJavaClasses) ){
			rsJavaClasses=stat.executeQuery();
		
		  
          while( rsJavaClasses.next() ) {
        	
        	  javaFile=rsJavaClasses.getString("NameClass");
        
        	  
        	  var queryNfix=" SELECT *  "
     				 + "FROM \"CommitTickets\"  AS ct  "
     				 + "JOIN \"TickectBugProject\" AS tbp  "
     				 + "ON  ct.\"TicketID\" = tbp.\"TicketBugID\"  "
     				 + "JOIN \"ListJavaClasses\" AS jc  "
     				 + "ON jc.\"Commit\" = ct.\"Commit\"  "
     				 + "WHERE jc.\"NameClass\" =  '"+javaFile+"'  "
     				 + "ORDER BY  jc.\"DataCommit\"  ASC ";
        	  
        	  try(var stat2=conn.prepareStatement(queryNfix) ){
     			   rsNfix=stat2.executeQuery();
     			   
     			   			   
     			 while(rsNfix.next()) {
     				
     				   
     				commit=rsNfix.getString("Commit");
     			    dataCommit=rsNfix.getString("DataCommit");
     				   
     				 nfix=nfix+1;  
     				 
     				 var queryUpdate=" UPDATE \"ListJavaClasses\" "
   	      		         + "SET \"Nfix\" = "+nfix+"  "
   	    		         + "WHERE  \"NameClass\" = '"+javaFile+"' AND  "
   	    		         + "        \"Commit\"='"+commit+"'  AND   "
   	                     + "        \"DataCommit\"='"+dataCommit+"'    ";
   			           					
   		            try(var statUpdate=conn.prepareStatement(queryUpdate)){
   		               statUpdate.executeUpdate();
   		            }//try
     				   
     			 }//while
     			   
     			 nfix=0;
     	     }//try
        	  
        	         	         	         	             						
			
		}//while
		
	}//try
		
		
}//fine metodo
	
	
}
