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
public class LOCADDEDmectric {
    
	
	public void calculateLocAdded() throws IOException, InterruptedException, SQLException {
		
		List<String> listFiles=new ArrayList<>();
		String[] bufferSplit;
		var locAddedString="/";
		var locAdded=0;
		
		var foundFile=false;
		var buffSplitHasRightLenght=false; 
		
		var cmdgitShow=new  CommandGitShow();
		
		var db=new DB();
		var conn=db.connectToDBtickectBugZookeeper();
		ResultSet rsLocAdded;
		
		var queryForClasses="SELECT * FROM \"ListJavaClasses\" "+
				 " WHERE \"NameClass\" LIKE '%.java' ";
			
		
		try(var stat=conn.prepareStatement(queryForClasses) ){
			rsLocAdded=stat.executeQuery();
		
				
        while( rsLocAdded.next() ) {
        	
			var fileName=rsLocAdded.getString("NameClass");
		    var commit = rsLocAdded.getString("Commit");
		    
		    listFiles=cmdgitShow.commandGitShow(commit);		   
			var size=listFiles.size();
			
			for(var i=(size-1);i>=0;i--) {
				
				if(listFiles.get(i).contains(fileName) ) {
					foundFile=true;
				}
				
				bufferSplit=listFiles.get(i).split("\t");
				
				if((bufferSplit.length) ==3 ) {
					buffSplitHasRightLenght=true;
				}
				
				if(foundFile && buffSplitHasRightLenght) {
					
					bufferSplit=listFiles.get(i).split("\t");
					
					
					locAddedString=bufferSplit[0];
					
					locAddedString=specialCaseLOCaddedValue(locAddedString);
					
					locAdded=Integer.parseInt(locAddedString);
					
					var queryUpd="UPDATE \"ListJavaClasses\"  "+
			                   "SET  \"LOCadded\"= "+locAdded+
					           "WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
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
	
	
	public void calculateMaxAndAvgLocAdded() throws SQLException, IOException {
		var h2=new Help2();
		var locAddedMax=0;
		var locAddedAvg=0;
		var locAdded=0;
		List<Integer> locAddedSizes=new ArrayList<>();
				
		ResultSet rsOccorrenzeFiles;
		
		ResultSet rsDataCalculation;
		
		var database=new DB();		
		var con= database.connectToDBtickectBugZookeeper();
		
		var query="SELECT \"NameClass\",COUNT(\"NameClass\") "+
				  "FROM \"ListJavaClasses\"  "+
				  "WHERE \"NameClass\" like '%.java'  "+
				  "GROUP BY \"NameClass\"   ";			
				      
		
		
		try(var stat=con.prepareStatement(query) ){
		  rsOccorrenzeFiles=stat.executeQuery();
		
		  		
          while( rsOccorrenzeFiles.next() ) {
        	          	
			 var nameFile=rsOccorrenzeFiles.getString("NameClass");
			 
			
			 var secondQuery=" SELECT * "+
			 	   " FROM \"ListJavaClasses\"  "+
			 	   " WHERE \"NameClass\" = '"+nameFile+"'    "+
			 	   " ORDER BY \"NameClass\" , \"DataCommit\"  ASC ";
			 		
			 try(var stat2=con.prepareStatement(secondQuery) ){
				 
			   rsDataCalculation=stat2.executeQuery();
			    
			   
			   while(rsDataCalculation.next()) {
				   
				  var commit=rsDataCalculation.getString("Commit");
				  locAdded=rsDataCalculation.getInt("LOCadded");
				   
				  locAddedSizes.add(locAdded);
				  
				  locAddedMax=h2.findMax(locAddedSizes);				  
				  locAddedAvg=h2.findAvg(locAddedSizes);
				  
				  var queryUPD="UPDATE \"ListJavaClasses\" "+
				                "SET \"MaxLOCadded\"="+locAddedMax+" , "+
						        "    \"AvgLOCadded\"="+locAddedAvg+"  "+
						        "WHERE  \"NameClass\"='"+nameFile+"'  AND "+
						        "       \"Commit\"='"+commit+"' ";
				  
				  try(var statUpd=con.prepareStatement(queryUPD) ){
					   statUpd.executeUpdate();
				  }
				  
			    }//while interno
			 }//try
			 
			 		 
			
		}//while estreno
		
	}//try esterno
		
		
		
}//fine metodo
	
    //metodo che elimina il caso LocAdded = "-"  
	public String specialCaseLOCaddedValue(String locAddedString) {
		
		if(locAddedString.equals("-")) {
			locAddedString="0";
		}
		return locAddedString;
		
	}//fine metodo
	
}	
