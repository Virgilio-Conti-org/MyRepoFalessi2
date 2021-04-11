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
		/*Connection con;
		Statement stat;
		ResultSet rs;*/
		
		
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
