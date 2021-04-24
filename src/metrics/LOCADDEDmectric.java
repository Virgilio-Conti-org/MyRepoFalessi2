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
		ResultSet rs;
		
		var query="SELECT * FROM \"ListJavaClasses\" "+
				 " WHERE \"NameClass\" LIKE '%.java' ";
			
		
		try(var stat=conn.prepareStatement(query) ){
			rs=stat.executeQuery();
		
				
        while( rs.next() ) {
        	
			var fileName=rs.getString("NameClass");
		    var commit = rs.getString("Commit");
		    
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
					System.out.println(listFiles.get(i)+" "+commit+"  "+locAdded+"  "+bufferSplit.length);
					
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
	
	
	public void maxAndAvgLocAdded() throws SQLException, IOException {
		var h2=new Help2();
		var locAddedMax=0;
		var locAddedAvg=0;
		var locAdded=0;
		List<Integer> locAddedSizes=new ArrayList<>();
		
		
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
			   locAdded=rsDataForCalculation.getInt("LOCadded"); 
			   
			   while(rsDataForCalculation.next()) {
				  locAddedSizes.add(locAdded);
				  
				  locAddedMax=h2.findMax(locAddedSizes);				  
				  locAddedAvg=h2.findAvg(locAddedSizes);
				  
				  var queryUpd="UPDATE \"ListJavaClasses\" "+
				                "SET \"LOCaddedMax\"="+locAddedMax+" , "+
						        "    \"LOCaddedAvg\"="+locAddedAvg+"  "+
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
	
    //metodo che elimina il caso LocAdded = "-"  
	public String specialCaseLOCaddedValue(String locAddedString) {
		
		if(locAddedString.equals("-")) {
			locAddedString="0";
		}
		return locAddedString;
		
	}//fine metodo
	
}	
