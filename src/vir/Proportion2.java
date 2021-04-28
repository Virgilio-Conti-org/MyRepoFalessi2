/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DB;
import helper.Help;


/**
 * @author Virgilio
 *
 */
public class Proportion2 {

	private int fixV;
	private int openV;
	private int injectedV;
	private DB db;
	
	public void calculatePticketsWithoutAffectedVersion() throws SQLException, IOException{
			
		ResultSet rsTicketsNOaffectedVerion;
		ResultSet rsTicketsWITHaffectedVerion;
		
		var help=new Help();
		var movWindow=help.numberOfTicketsBug()/100;
		var count=0;
		var p=0.0;
		
		if(movWindow==0) {
			return;
		}
		
		var  proportion1=new Proportion();
		String ticketID;
		
		var queryTicketsNOaffectedVerion="SELECT * FROM \"Tickect_FV_OV\"  "
				                           +"WHERE \"ProportionValue\" IS null  "  				 	                  
				                           +"ORDER BY \"DateOpenVersion\" DESC ";
		
			
		Connection con;
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
		
		
	try(var stat=con.prepareStatement(queryTicketsNOaffectedVerion) ){
		rsTicketsNOaffectedVerion=stat.executeQuery();
		
		
        while( rsTicketsNOaffectedVerion.next() ) {
			
        	
        	
		    ticketID = rsTicketsNOaffectedVerion.getString("TicketBugID");
		    
			fixV=rsTicketsNOaffectedVerion.getInt("FV");
			openV=rsTicketsNOaffectedVerion.getInt("OP");
			var dateOpenV=rsTicketsNOaffectedVerion.getString("DateOpenVersion");
			
			
			var queryTicketsWITHaffectedVerion="SELECT * FROM \"TicketWithAffectedVersion\"  "
					  +"WHERE \"VersionDate\" <= '"+dateOpenV+"'  "
            	      +"ORDER BY \"VersionDate\" DESC ";
			
			try(var stat2=con.prepareStatement(queryTicketsWITHaffectedVerion) ){
				rsTicketsWITHaffectedVerion=stat2.executeQuery();
				
				while(rsTicketsWITHaffectedVerion.next() && count<movWindow) {
					
					p=p+(rsTicketsWITHaffectedVerion.getInt("ProportionValue"));
					
					count=count+1;
				}//while
				
			}//try
			
			if(count==0) {
				
				return;
			}
			
			p=p/count;
			
			injectedV=proportion1.calculateIV(p, fixV, openV);
			var dataAffVer=getDateFromVersionIndex(injectedV);
			
			count=0;
			p=0;
			var queryUpdate="UPDATE \"Tickect_FV_OV\"  "
	                   +" SET  \"ProportionValue\" ="+p+", "
	                   +"      \"AffectedVersion\"= "+injectedV+", "
	                   +"      \"DateAffectedVersion\" = '"+dataAffVer+"'  "
			           +" WHERE \"TicketBugID\" =  '"+ticketID +"'  ";
			
			try(var statUpdate=con.prepareStatement(queryUpdate)){
			    statUpdate.executeUpdate();
			}//try
			
		}//while
		
	}//try
	     	
		
}//fine metodo
	
	public void calculatePTicketsWithAffectedVersion() throws SQLException, IOException {
		
		ResultSet rs;
		String ticketID;
		String dataVersion;
		
		var prop=new Proportion();
		Double p;
		
		var query="SELECT * FROM \"Tickect_FV_OV\" as t_FV_OV " + 
				 
				"JOIN \"TicketWithAffectedVersion\" AS twAV " + 
				"ON   t_FV_OV.\"TicketBugID\" = twAV.\"TicketBugID\" "+
			    "JOIN \"ZookeeperVersionsInfo\" AS zk  "+
			    "ON  zk.\"DateVersion\" = twAV.\"VersionDate\" ";
		
		var queryUpdate="";
		var queryUpdate2="";
		
		Connection con;
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
		
			
	try(var stat=con.prepareStatement(query) ){
		rs=stat.executeQuery();
	
	
		while( rs.next() ) {
			ticketID=rs.getString("TicketBugID");
			fixV=rs.getInt("FV");
			openV=rs.getInt("OP");
			injectedV=rs.getInt("Index");
			dataVersion=rs.getString("VersionDate");
			
			p=prop.calculateP(fixV, openV, injectedV);
			
			if(p<=0) {
				continue;
			}
			
			queryUpdate="UPDATE \"TicketWithAffectedVersion\" "+ 
					" SET \"ProportionValue\"= "+p+ 
					" WHERE \"TicketBugID\"=  '"+ticketID +"'";
						
			try(var statUpdate=con.prepareStatement(queryUpdate)){
			    statUpdate.executeUpdate();
			}  
			
			queryUpdate2="UPDATE \"Tickect_FV_OV\" "+ 
					"  SET \"ProportionValue\" = "+p+" , " +
					"      \"DateAffectedVersion\" = '"+dataVersion+"' , "+
					"      \"AffectedVersion\" = '"+injectedV+"'   "+
					"  WHERE \"TicketBugID\" =  '"+ticketID +"'";
			try(var statUpdate2=con.prepareStatement(queryUpdate2)){
			    statUpdate2.executeUpdate();
			} 
			
		}//while
	}//try	
		
	                
	
}//fine metodo
	
	
	public String getDateFromVersionIndex(int indexVersion) throws SQLException, IOException {
		
		ResultSet rs;
		var data = "*";
		var queryDate="SELECT * FROM \"ZookeeperVersionsInfo\"  "
                +"WHERE \"Index\" = "+indexVersion+"  " ; 				 	                  
         
		Connection con;
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
		
		try(var stat=con.prepareStatement(queryDate) ){
			rs=stat.executeQuery();
			
			while(rs.next()) {
			  data=rs.getString("DateVersion");	
			}
					
			
		}		

		con.close();
		return data;
				
	}//fine metodo
	
	
}
