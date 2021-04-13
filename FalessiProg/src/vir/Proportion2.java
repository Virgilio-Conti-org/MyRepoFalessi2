/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

/**
 * @author Virgilio
 *
 */
public class Proportion2 {

	private int FV,OV,IV;
	private DB db;
	
	public void Calculate_P_Tickets_Without_Affected_Version() throws SQLException, IOException{
		Connection con;
		PreparedStatement stat, stat2,statUpdate;
		ResultSet rsTicketsNOaffectedVerion,rsTicketsWITHaffectedVerion;
		int movWindow=0, count=0;
		int P=0;
		Proportion  proportion1=new Proportion();
		String TicketID;
		
		String queryTicketsNOaffectedVerion="SELECT * " + 
				"FROM \"Tickect_FV_OV\" as t_FV_OV" + 
				"WHERE NOT EXISTS ( SELECT * " + 
				"                   FROM \"TicketWithAffectedVersion\" AS twAV" + 
				"                   WHERE t_FV_OV.\"TicketBugID\"=twAV.\"TicketBugID\" )"+
				"ORDER BY \"DateFixVersion\" DESC ";
		
		String queryTicketsWITHaffectedVerion="SELECT * " + 
				      "FROM \"TicketWithAffectedVersion\" as twAV "+
				      "JOIN \"ZookeeperVersionsInfo\" as zk"+
				      "on twAV.\"VersionName\"=zk.\"VersionName\" "+
				      "ORDER BY zk.\"DateVersion\" DESC ";
		
		
		
		db=new DB();
		con=db.connectToDB_TickectBugZookeeper();
		stat=con.prepareStatement(queryTicketsNOaffectedVerion);
		stat2=con.prepareStatement(queryTicketsWITHaffectedVerion);
		
		rsTicketsNOaffectedVerion=stat.executeQuery();
		rsTicketsWITHaffectedVerion=stat2.executeQuery();
		
		while( rsTicketsNOaffectedVerion.next() ) {
			
		    TicketID = rsTicketsNOaffectedVerion.getString("TicketBugID");
		    
			FV=rsTicketsNOaffectedVerion.getInt("FV");
			OV=rsTicketsNOaffectedVerion.getInt("OP");
							
			while(rsTicketsWITHaffectedVerion.next() && count<movWindow) {
				P=P+rsTicketsWITHaffectedVerion.getInt("ProportionValue");
				
			}
			P=P/movWindow;
			IV=proportion1.Calculate_IV(P, FV, OV);
			count=0;
			
			String queryUpdate="UPDATE Tickect_FV_OV"+
	                   "SET  \"ProportionValue\" ="+P+", \"AffectedVersion\"= "+IV+
			           "WHERE \"TicketBugID\"=  '"+TicketID +"'";
			
			statUpdate=con.prepareStatement(queryUpdate);
		    statUpdate.executeUpdate();
		}//while
		
		
	}
	
	public void Calculate_P_Tickets_With_Affected_Version() throws SQLException, IOException {
		
		PreparedStatement stat,statUpdate;
		ResultSet rs;
		String TicketID;
		
		Proportion prop=new Proportion();
		int p;
		
		String query="SELECT * " + 
				"FROM \"Tickect_FV_OV\" as t_FV_OV " + 
				"JOIN \"TicketWithAffectedVersion\" AS twAV " + 
				"ON   t_FV_OV.\"TicketBugID\" = twAV.\"TicketBugID\" ";
		
		String queryUpdate="";
		
		Connection con;
		db=new DB();
		con=db.connectToDB_TickectBugZookeeper();
		stat=con.prepareStatement(query);
		
		
		rs=stat.executeQuery();
		
		while( rs.next() ) {
			TicketID=rs.getString("TicketBugID");
			FV=rs.getInt("FV");
			OV=rs.getInt("OP");
			IV=rs.getInt("AffectedVersion");
			p=prop.Calculate_p(FV, OV, IV);
			//System.out.println("fv "+FV+" ov "+OV+" iv "+IV+" p "+p);
			//System.out.println("TicketID "+TicketID);
			queryUpdate="UPDATE \"TicketWithAffectedVersion\" " + 
					"SET \"ProportionValue\"= " +p+ 
					"WHERE \"TicketBugID\"=  '"+TicketID +"'";
			
			statUpdate=con.prepareStatement(queryUpdate);
			statUpdate.executeUpdate();
		}
		
		
	}
}
