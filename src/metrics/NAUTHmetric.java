/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.DB;

/**
 * @author Virgilio
 *
 */
public class NAUTHmetric {

	public void caculateNAUTH() throws  IOException, SQLException {
		
		String javaClass;
		String autore;
		String commit;
		String dataCommit;
		var nAuth=0;
		List<String> listAuthors=new ArrayList<>();
		
		
		ResultSet rsJavaClasses;
		ResultSet rsJavaClasses2;
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var queryJavaClasses=" SELECT \"NameClass\",COUNT(\"NameClass\") "
				+ "FROM \"ListJavaClasses\"   "
				+ "WHERE \"NameClass\" LIKE '%.java'  "
				+ "GROUP BY \"NameClass\" ";
		
		try(var stat=conn.prepareStatement(queryJavaClasses) ){
			rsJavaClasses=stat.executeQuery();
		
		  
          while( rsJavaClasses.next() ) {
        	
        	 javaClass=rsJavaClasses.getString("NameClass");
        	 
        	 // \"NameClass\",\"Name\",\"Commit\",\"DataCommit\" 
        	 var queryJavaClasses2=" SELECT  *  "   
     				+ "FROM \"ListJavaClasses\"  AS jc  "
        			+ "JOIN \"Autori\"  AS auth  "
     				+ "ON  jc.\"Commit\"  =  auth.\"Commit\"    "
     				+ "WHERE jc.\"NameClass\" = '"+javaClass+"'  "
     				+ "ORDER BY jc.\"DataCommit\"  ASC   ";
     		
        	 try(var stat2=conn.prepareStatement(queryJavaClasses2) ){
  			   rsJavaClasses2=stat2.executeQuery();
  			   
  			   			   
  			   while(rsJavaClasses2.next()) {
  				   
  				     				   
  				   autore=rsJavaClasses2.getString("Name");
  				   commit=rsJavaClasses2.getString("Commit");
  				   dataCommit=rsJavaClasses2.getString("DataCommit");
  				   
  				   listAuthors.add(autore);  				   
  				   listAuthors=eliminaDuplicati(listAuthors);
  				   
  				   nAuth=listAuthors.size();
  				   
  				 var queryUpdate=" UPDATE \"ListJavaClasses\"  "
	      		         + "SET \"NAuth\" = "+nAuth+"  "
	    		         + "WHERE   \"NameClass\" = '"+javaClass+"' AND  "
	    		         + "        \"Commit\" = '"+commit+"'  AND   "
	                     + "        \"DataCommit\" = '"+dataCommit+"'    ";
			           					
		         try(var statUpdate=conn.prepareStatement(queryUpdate)){
		            statUpdate.executeUpdate();
		         }//try
  				   
  			   }//while
  			   
  			   listAuthors.clear();
  			   
  			 }//try
        	 
       		      	         						
			
		}//while
		
	}//try
		
		
}//fine metodo
	
	
	
	
	public  List<String> eliminaDuplicati(List<String> autori){
			
        return autori.stream().distinct().collect(Collectors.toList());
		
	}//fine metodo
	
}
