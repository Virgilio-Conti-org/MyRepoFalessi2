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
import database.DB;
import helper.Help2;

/**
 * @author Virgilio
 *
 */
public class CHURNmetric {
    
	public void calculateChurn() throws IOException, InterruptedException, SQLException {
		
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
		
		ResultSet rsChurn;
		
		var queryClasses="SELECT * FROM \"ListJavaClasses\" "
				      + " WHERE \"NameClass\" LIKE '%.java' ";
					
		try(var stat=conn.prepareStatement(queryClasses) ){
			rsChurn=stat.executeQuery();
		
				
        while( rsChurn.next() ) {
        	
			var fileName=rsChurn.getString("NameClass");
		    var commit = rsChurn.getString("Commit");
		    
		    listFiles=cmdgitShow.commandGitShow(commit);		    
			var size=listFiles.size();
			
			
			for(var i=(size-1);i>=0;i--) {
				
				
				if(listFiles.get(i).contains(fileName)) {
					foundFile=true;
				}
				
				bufferSplit=listFiles.get(i).split("\t");
				
				if((bufferSplit.length) ==3 ) {
					buffSplitHasRightLenght=true;
				}
				if(foundFile && buffSplitHasRightLenght) {
					bufferSplit=listFiles.get(i).split("\t");
					
					
					locAdded=bufferSplit[0];
					locDeleted=bufferSplit[1];
					
					
					locAdded=specialCaseChurnValuselocAdded(locAdded);
					locDeleted=specialCaseChurnValuselocDeleted(locDeleted);
					
					churn=Integer.parseInt(locAdded)-Integer.parseInt(locDeleted);
					
				
					
					var queryUpdChurn="UPDATE \"ListJavaClasses\"  "+
			                   "SET  \"Churn\"= "+churn+
					          " WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
					           		 " \"Commit\"= '"+commit+"'   " ;
					
					try(var statUpd=conn.prepareStatement(queryUpdChurn)){
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
	
	
	public void calculateMaxAndAvgChurn() throws SQLException, IOException {
		var h2=new Help2();
		var churnMax=0;
		var churnAvg=0;
		var churn=0;
		List<Integer> churnSizes=new ArrayList<>();
				
		ResultSet rsOccorrenzeFiles;
		ResultSet rsDataForCalculationChurn;
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		
		var query1="SELECT \"NameClass\",COUNT(\"NameClass\") "+
				  "FROM \"ListJavaClasses\"  "+
				  "WHERE \"NameClass\" like '%.java'  "+
				  "GROUP BY \"NameClass\"   ";			
				      
		
		
		try(var stat=conn.prepareStatement(query1) ){
		  rsOccorrenzeFiles=stat.executeQuery();
		
		  		
          while( rsOccorrenzeFiles.next() ) {
                    	      	  
			 var nomeFile=rsOccorrenzeFiles.getString("NameClass");
			 
			
			 var query2=" SELECT * "+
			 	   " FROM \"ListJavaClasses\"  "+
			 	   " WHERE \"NameClass\" = '"+nomeFile+"'    "+
			 	   " ORDER BY \"NameClass\" , \"DataCommit\"  ASC ";
			 		
			 
			 try(var stat2=conn.prepareStatement(query2) ){
			   rsDataForCalculationChurn=stat2.executeQuery();
			   		   
			   
			   while(rsDataForCalculationChurn.next()) {
				   
				  var commit=rsDataForCalculationChurn.getString("Commit");
				  churn=rsDataForCalculationChurn.getInt("Churn");  
				   
				  churnSizes.add(churn);
				  
				  churnMax=h2.findMax(churnSizes);
				  churnAvg=h2.findAvg(churnSizes);
				  
				  var queryUpdate="UPDATE \"ListJavaClasses\" "+
				                "SET \"MaxChurn\"="+churnMax+" , "+
						        "    \"AvgChurn\"="+churnAvg+"  "+
						        "WHERE  \"NameClass\"='"+nomeFile+"'  AND "+
						        "       \"Commit\"='"+commit+"' ";
				  
				  try(var statUpdate=conn.prepareStatement(queryUpdate) ){
					  
					   statUpdate.executeUpdate();
				  }
				  
			    }//while interno
			 }//try
			 
			 
			 
			
		}//while estreno
		
	}//try esterno
		
		
       
		
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
