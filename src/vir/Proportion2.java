/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
		Connection con;
		
		ResultSet rsTicketsNOaffectedVerion;
		ResultSet rsTicketsWITHaffectedVerion;
		
		Help help=new Help();
		var movWindow=help.numberOfTicketsBug()/100;
		var count=0;
		int p=0;
		
		if(movWindow==0) {
			return;
		}
		
		Proportion  proportion1=new Proportion();
		String ticketID;
		
		String queryTicketsNOaffectedVerion="SELECT * FROM \"Tickect_FV_OV\" as t_FV_OV" + 
				 
				"WHERE NOT EXISTS ( SELECT * " + 
				"                   FROM \"TicketWithAffectedVersion\" AS twAV" + 
				"                   WHERE t_FV_OV.\"TicketBugID\"=twAV.\"TicketBugID\" )"+
				"ORDER BY \"DateFixVersion\" DESC ";
		
		String queryTicketsWITHaffectedVerion="SELECT * FROM \"TicketWithAffectedVersion\" as twAV "+ 
				     
				      "JOIN \"ZookeeperVersionsInfo\" as zk"+
				      "on twAV.\"VersionName\"=zk.\"VersionName\" "+
				      "ORDER BY zk.\"DateVersion\" DESC ";
		
		
		
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
		
		
	try(PreparedStatement stat=con.prepareStatement(queryTicketsNOaffectedVerion) ){
		rsTicketsNOaffectedVerion=stat.executeQuery();
	}
	     
	try(PreparedStatement stat2=con.prepareStatement(queryTicketsWITHaffectedVerion) ){
		rsTicketsWITHaffectedVerion=stat2.executeQuery();
	}
				
		
		while( rsTicketsNOaffectedVerion.next() ) {
			
		    ticketID = rsTicketsNOaffectedVerion.getString("TicketBugID");
		    
			fixV=rsTicketsNOaffectedVerion.getInt("FV");
			openV=rsTicketsNOaffectedVerion.getInt("OP");
							
			while(rsTicketsWITHaffectedVerion.next() && count<movWindow) {
				p=p+rsTicketsWITHaffectedVerion.getInt("ProportionValue");
				count=count+1;
			}
			p=p/movWindow;
			injectedV=proportion1.calculateIV(p, fixV, openV);
			count=0;
			
			String queryUpdate="UPDATE Tickect_FV_OV"+
	                   "SET  \"ProportionValue\" ="+p+", \"AffectedVersion\"= "+injectedV+
			           "WHERE \"TicketBugID\"=  '"+ticketID +"'";
			
			try(PreparedStatement statUpdate=con.prepareStatement(queryUpdate)){
		    statUpdate.executeUpdate();
			}
		}//while
		
		
	}
	
	public void calculatePTicketsWithAffectedVersion() throws SQLException, IOException {
		
		ResultSet rs;
		String ticketID;
		
		Proportion prop=new Proportion();
		int p;
		
		String query="SELECT * FROM \"Tickect_FV_OV\" as t_FV_OV " + 
				 
				"JOIN \"TicketWithAffectedVersion\" AS twAV " + 
				"ON   t_FV_OV.\"TicketBugID\" = twAV.\"TicketBugID\" ";
		
		var queryUpdate="";
		
		Connection con;
		db=new DB();
		con=db.connectToDBtickectBugZookeeper();
		
			
	try(PreparedStatement stat=con.prepareStatement(query) ){
		rs=stat.executeQuery();
	}
	
		while( rs.next() ) {
			ticketID=rs.getString("TicketBugID");
			fixV=rs.getInt("FV");
			openV=rs.getInt("OP");
			injectedV=rs.getInt("AffectedVersion");
			p=prop.calculateP(fixV, openV, injectedV);
			
			queryUpdate="UPDATE \"TicketWithAffectedVersion\" " + 
					"SET \"ProportionValue\"= " +p+ 
					"WHERE \"TicketBugID\"=  '"+ticketID +"'";
			
			try(PreparedStatement statUpdate=con.prepareStatement(queryUpdate)){
			    statUpdate.executeUpdate();
			}    
		}//while
		
		
	}
}
