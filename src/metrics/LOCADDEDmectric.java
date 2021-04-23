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
		    System.out.println(listFiles);
			var size=listFiles.size();
			
			for(var i=0;i<size;i++) {
				
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
	
	
	public void maxAndAvgLocAdded() throws SQLException, IOException {
		var h2=new Help2();
		var locAddedMax=0;
		var locAddedAvg=0;
		List<Integer> locAddedSizes=new ArrayList<>();
		
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
		    var locAdded = rs.getInt("LOCadded");
			
		    
		    if(!fileName.equals(previousFile)) {
		    	previousFile=fileName;
		    	
		    	locAddedMax=h2.findMax(locAddedSizes);
		    	locAddedAvg=h2.findAvg(locAddedSizes);
		    	
		    	var queryUpdate="UPDATE \"ListJavaClasses\"  "+
		                   "SET  \"MaxLOCAdded\"= "+locAddedMax+" ,"+
		                   "     \"AvgLOCAdded\"= "+locAddedAvg+" "+
				           "WHERE \"NameClass\"= '"+fileName +"'" +" AND "+
				           		 " \"DateCommit\"= '"+dateCommit+"'   " ;
				
				try(var statUpdate=conn.prepareStatement(queryUpdate)){
			      statUpdate.executeUpdate();
				}
		    	
				locAddedSizes.clear();
				locAddedMax=0;
				locAddedAvg=0;
		    }//if
		    
		    if(fileName.equals(previousFile)) {
		    	locAddedSizes.add(locAdded);
		    	
		    }//if
						
			
		}//while
	
		
    }//try
		
}//fine metodo
	
    //metodo che elimina il caso LocAdded = "-"  
	public String specialCaseLOCaddedValue(String locAddedString) {
		
		if(locAddedString.equals("-")) {
			locAddedString="0";
		}
		return locAddedString;
		
	}//fine metodo
	
}	
