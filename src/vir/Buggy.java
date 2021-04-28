/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import database.DB;

/**
 * @author Virgilio
 *
 */
public class Buggy {

	public void buggy() throws SQLException, IOException, ParseException {
		
		String javaClassName;
		String commit;
		String dataCommit;
		String dataOpenVersion;
		String dataAffectedVersion;
		
		var check1=false;
		var check2=false;
		
		var sdf= new SimpleDateFormat("yyyy-MM-dd");
				
		
		ResultSet rsBuggy;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		
		var queryBuggy=" SELECT *  "
			       	 + "FROM \"CommitTickets\"  AS ct "
			       	 + "JOIN \"Tickect_FV_OV\"  AS fvov  "
			       	 + "ON ct.\"TicketID\" = fvov.\"TicketBugID\"  "
			       	 + "JOIN \"ListJavaClasses\"  AS ljc  "
			       	 + "ON  ljc.\"Commit\" = ct.\"Commit\"  "
			       	 + "WHERE ljc.\"NameClass\"  LIKE '%.java'  "
			       	 + "      AND fvov.\"DateAffectedVersion\" is not null "
			       	 + "      AND fvov.\"ProportionValue\" >= 1     ";
		
				
		try(var stat=conn.prepareStatement(queryBuggy) ){
			  rsBuggy=stat.executeQuery();
			
			 
	          while( rsBuggy.next() ) {
	        	  
	        	  
	        	  javaClassName=rsBuggy.getString("NameClass");
	        	  commit=rsBuggy.getString("Commit");
	        	  dataCommit=rsBuggy.getString("DataCommit");
	        	  var dataCom =sdf.parse(dataCommit);
	        	  
	        	  dataOpenVersion=rsBuggy.getString("DateOpenVersion");
	        	  var dataOV =sdf.parse(dataOpenVersion);
	        			  
	        	  dataAffectedVersion=rsBuggy.getString("DateAffectedVersion");
	        	  var dataIV =sdf.parse(dataAffectedVersion);
	        	  
	        	  if(dataCom.after(dataIV) && dataCom.before(dataOV) ) {
	        		  check1=true;
	        	  }
                  if(dataCom.equals(dataIV) || dataCom.equals(dataOV) ) {
                	  check2=true;
	        	  }
                  if(check1 || check2) {
                	  
                	  
	        		  var queryUpdate="UPDATE \"ListJavaClasses\"  "
				        	  + "SET \"Buggy\" = 'yes'  "
				        	  + "WHERE \"NameClass\" = '"+javaClassName+"'  AND  "
				        	  + "      \"Commit\" = '"+commit+"'      "; 
	        		  
	        		  try(var statUpd=conn.prepareStatement(queryUpdate) ){
	        			  statUpd.executeUpdate();
	        		  }	  
		        	   
	        		 check1=false;
	        		 check2=false;
		          }//if
	        	  
	          }//while
		           
		
	     }//try
	
}//fine metodoo
	
	
}
