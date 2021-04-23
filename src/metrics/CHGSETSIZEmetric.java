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
		List<String> listFileWithSameCommit=new ArrayList<>();
		var previousCommit="/";
		String commit;
		String fileName;
		
		ResultSet rs;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var query="SELECT * FROM \"ListJavaClasses\"  "+
				 " ORDER BY \"Commit\"   "; 				
				      
		
		
		try(var stat=conn.prepareStatement(query) ){
			rs=stat.executeQuery();
		
		
		rs.next();
		
	    fileName=rs.getString("NameClass");
		previousCommit=rs.getString("Commit");
		
		listFileWithSameCommit.add(fileName);
    	chgSetSize=listFileWithSameCommit.size();
		
		
        while( rs.next() ) {
        	
        	fileName=rs.getString("NameClass");
		    commit = rs.getString("Commit");
		    
            if(commit.equals(previousCommit)) {
		    	
		    	listFileWithSameCommit.add(fileName);
		    	chgSetSize=listFileWithSameCommit.size();
		    }//if
            
		    
            else {
		     	
		       for(var j=0;j<listFileWithSameCommit.size();j++)	{
		    	var queryUpdate="UPDATE \"ListJavaClasses\"  "+
		                   "SET  \"ChgSetSize\"= "+chgSetSize+
				           "WHERE \"NameClass\"= '"+listFileWithSameCommit.get(j) +"'" +" AND "+
				           		 " \"Commit\"= '"+previousCommit+"'   " ;
				
				try(var statUpdate=conn.prepareStatement(queryUpdate)){
			      statUpdate.executeUpdate();
				}//try
		       }//for	
            	
            	previousCommit=commit;
		    	listFileWithSameCommit.clear();
		    	
		    	listFileWithSameCommit.add(fileName);
		    	chgSetSize=0;
		    }//if
		    
		    
						
			
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
