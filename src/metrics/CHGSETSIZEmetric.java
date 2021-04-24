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
		
		  var c=0;
          while( rs.next() ) {
        	  c=c+1;
        	  System.out.println(c);
        	
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
		List<Integer> chgSetSizes=new ArrayList<>();
		
		var previousFile="/";
		
		ResultSet rs;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var query="SELECT * FROM \"ListJavaClasses\"  "+
				 " ORDER BY \"NameClass\" , \"DateCommit\" ASC "; 				
				      
		
		
		try(var stat=conn.prepareStatement(query) ){
			rs=stat.executeQuery();
		
		
		rs.next();
		previousFile=rs.getString("NameClass");
		
		
        while( rs.next() ) {
        	
			var fileName=rs.getString("NameClass");
			var dateCommit=rs.getString("DateCommit");
		    var chgSetSize = rs.getInt("ChgSetSize");
			
		    
		    if(!fileName.equals(previousFile)) {
		    	previousFile=fileName;
		    	
		    	chgSetSizeMax=h2.findMax(chgSetSizes);
		    	chgSetSizeAvg=h2.findAvg(chgSetSizes);
		    	
		    	var queryUpdate="UPDATE \"ListJavaClasses\"  "+
		                   "SET  \"MaxChgSetSize\"= "+chgSetSizeMax+" ,"+
		                   "     \"AvgChgSetSize\"= "+chgSetSizeAvg+" "+
				           "WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
				           		 " \"DateCommit\"= '"+dateCommit+"'   " ;
				
				try(var statUpdate=conn.prepareStatement(queryUpdate)){
			      statUpdate.executeUpdate();
				}
		    	
		    	chgSetSizes.clear();
		    	chgSetSizeMax=0;
		    	chgSetSizeAvg=0;
		    }//if
		    
		    if(fileName.equals(previousFile)) {
		    	chgSetSizes.add(chgSetSize);
		    	
		    }//if
						
			
		}//while
		
	}//try
		
		
		
}//fine metodo

	
	
	
	
}
