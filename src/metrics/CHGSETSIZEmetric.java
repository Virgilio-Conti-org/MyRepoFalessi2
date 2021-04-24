/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vir.DB;
import vir.Help2;

/**
 * @author Virgilio
 *
 */
public class CHGSETSIZEmetric {

	public void calculateChgSetSize() throws SQLException, IOException {
		
		var chgSetSize=0;		
		String commit;
		
		
		ResultSet rs;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var query=" SELECT \"Commit\", COUNT(\"Commit\")  "+
				  "FROM \"ListJavaClasses\"  "+
				  "GROUP BY \"Commit\" ";
		
		
		try(var stat=conn.prepareStatement(query) ){
			rs=stat.executeQuery();
		
		  
          while( rs.next() ) {
        	
        	  commit=rs.getString("Commit");
        	  chgSetSize=rs.getInt("count");
        	  
		      var queryUpdate="UPDATE \"ListJavaClasses\"  "+
		                   " SET  \"ChgSetSize\"= "+chgSetSize+
				           " WHERE  \"Commit\"= '"+commit+"'  ";
				           		
				
				try(var statUpdate=conn.prepareStatement(queryUpdate)){
			      statUpdate.executeUpdate();
				}//try
		      	
            						
			
		}//while
		
	}//try
		
				
		
}//fine metodo
	
	
	public void maxAndAvgChgSetSize() throws SQLException, IOException {
		var h2=new Help2();
		var chgSetSizeMax=0;
		var chgSetSizeAvg=0;
		var chgSetSize=0;
		List<Integer> chgSetSizes=new ArrayList<>();
		
				
		ResultSet rsOccorrenzeFiles;
		ResultSet rsDataForCalculation;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var query="SELECT \"NameClass\",COUNT(\"NameClass\") "+
				  "FROM \"ListJavaClasses\"  "+
				  "WHERE \"NameClass\" like '%.java'  "+
				  "GROUP BY \"NameClass\"   ";			
				      
		
		
		try(var stat=conn.prepareStatement(query) ){
		  rsOccorrenzeFiles=stat.executeQuery();
		
			
          while( rsOccorrenzeFiles.next() ) {
        	
			 var fileName=rsOccorrenzeFiles.getString("NameClass");
			 
			
			 var query2=" SELECT * "+
			 	   " FROM \"ListJavaClasses\"  "+
			 	   " WHERE \"NameClass\" = '"+fileName+"'    "+
			 	   " ORDER BY \"NameClass\" , \"DateCommit\"  ASC ";
			 		
			 try(var stat2=conn.prepareStatement(query2) ){
			   rsDataForCalculation=stat2.executeQuery();
			   
			   var commit=rsDataForCalculation.getString("Commit");
			   chgSetSize=rsDataForCalculation.getInt("ChgSetSize"); 
			   
			   while(rsDataForCalculation.next()) {
				  chgSetSizes.add(chgSetSize);
				  
				  chgSetSizeMax=h2.findMax(chgSetSizes);
				  chgSetSizeAvg=h2.findAvg(chgSetSizes);
				  
				  var queryUpd="UPDATE \"ListJavaClasses\" "+
				                "SET \"MaxChgSetSize\"="+chgSetSizeMax+" , "+
						        "    \"AvgChgSetSize\"="+chgSetSizeAvg+"  "+
						        "WHERE  \"NameClass\"='"+fileName+"'  AND "+
						        "       \"Commit\"='"+commit+"' ";
				  
				  try(var statUpd=conn.prepareStatement(queryUpd) ){
					   statUpd.executeUpdate();
				  }
				  
			    }//while interno
			 }//try
			 
			 
			 
			
		}//while estreno
		
	}//try esterno
		
		
		
}//fine metodo

	
	
	
	
}
