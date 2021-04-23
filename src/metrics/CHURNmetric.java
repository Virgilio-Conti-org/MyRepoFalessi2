/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commands.CommandGitShow;
import vir.DB;
import vir.Help2;

/**
 * @author Virgilio
 *
 */
public class CHURNmetric {
    
	public void CalculateChurn() throws IOException, InterruptedException, SQLException {
		
		List<String> listFiles=new ArrayList<>();
		String[] bufferSplit;		
		var locAdded="/";
		var locDeleted="/";
		var churn=0;
	
		var foundFile=false;
		var buffSplitHasRightLenght=false; 
		
		var cmdgitShow=new  CommandGitShow();
	
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		ResultSet rs;
		
		var query="SELECT * FROM \"ListJavaClasses\" "
				+ " WHERE \"NameClass\" LIKE '%.java' ";
			
		
		try(var stat=conn.prepareStatement(query) ){
			rs=stat.executeQuery();
		
				
        while( rs.next() ) {
        	
			var fileName=rs.getString("NameClass");
		    var commit = rs.getString("Commit");
		    
		    listFiles=cmdgitShow.commandGitShow(commit);
		    //System.out.println(listFiles);		    
			var size=listFiles.size();
			
			
			for(var i=0;i<size;i++) {
				
				
				if(listFiles.get(i).contains(fileName)) {
					foundFile=true;
				}
				
				bufferSplit=listFiles.get(i).split("\t");
				
				if((bufferSplit.length) ==3 ) {
					buffSplitHasRightLenght=true;
				}
				if(foundFile && buffSplitHasRightLenght) {
					bufferSplit=listFiles.get(i).split("\t");
					System.out.println(listFiles.get(i)+" "+commit+"  "+locAdded+"  "+locDeleted+" "+bufferSplit.length);
					
					locAdded=bufferSplit[0];
					locDeleted=bufferSplit[1];
					//var fileName=bufferSplit[2];
					
					locAdded=specialCaseChurnValuselocAdded(locAdded);
					locDeleted=specialCaseChurnValuselocDeleted(locDeleted);
					
					churn=Integer.parseInt(locAdded)-Integer.parseInt(locDeleted);
					
				
					
					var queryUpd="UPDATE \"ListJavaClasses\"  "+
			                   "SET  \"Churn\"= "+churn+
					          " WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
					           		 " \"Commit\"= '"+commit+"'   " ;
					
					try(var statUpd=conn.prepareStatement(queryUpd)){
						statUpd.executeUpdate();
					}
					
					foundFile=false;
					buffSplitHasRightLenght=false;
					break;
				}//if
				
			}//for
		    			
		}//while
		
	}//try
		
		
			 
}//fine metodo 
	
	
	public void maxAndAvgChurn() throws SQLException, IOException {
		var h2=new Help2();
		var churnMax=0;
		var churnAvg=0;
		List<Integer> churnSizes=new ArrayList<>();
		
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
		    var churn = rs.getInt("Churn");
			
		    
		    if(!fileName.equals(previousFile)) {
		    	previousFile=fileName;
		    	
		    	churnMax=h2.findMax(churnSizes);
		    	churnAvg=h2.findAvg(churnSizes);
		    	
		    	var queryUpdate="UPDATE \"ListJavaClasses\"  "+
		                   "SET  \"MaxChurn\"= "+churnMax+" ,"+
		                   "     \"AvgChurn\"= "+churnAvg+" "+
				           "WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
				           		 " \"DateCommit\"= '"+dateCommit+"'   " ;
				
				try(var statUpdate=conn.prepareStatement(queryUpdate)){
			      statUpdate.executeUpdate();
				}
		    	
		    	churnSizes.clear();
		    	churnMax=0;
		    	churnAvg=0;
		    }//if
		    
		    if(fileName.equals(previousFile)) {
		    	churnSizes.add(churn);
		    	
		    }//if
						
			
		}//while
		
    }//try
		
}//fine metodo
	
	//metodo che elimina il caso LocAdded = "-"  	
public String specialCaseChurnValuselocAdded(String locAdded) {
		
	if(locAdded.equals("-")) {
		return "0";
	}
	return locAdded;
					
}//fine metodo


//metodo che elimina il caso LocDeleted = "-"  
public String specialCaseChurnValuselocDeleted(String locDeleted) {
	
	if(locDeleted.equals("-")) {
		return "0";
	}
	return locDeleted;
	
}//fine metodo

	
}
