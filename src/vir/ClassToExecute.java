/**
 * 
 */
package vir;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import metrics.CHGSETSIZEmetric;
import metrics.CHURNmetric;
import metrics.LOCADDEDmectric;
import metrics.NAUTHmetric;
import metrics.NFIXmetric;
import metrics.NRmetric;

/**
 * @author Virgilio
 *
 */
public class ClassToExecute {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws SQLException  
	 * @throws ParseException 
	 *
	 *
	 */
	public static void main(String[] args) throws IOException, SQLException, InterruptedException, ParseException    {
		
		var log=Logger.getLogger("MyLogger");
		
		var prop=new Properties();		
		try(var fr = new FileReader("config")
			                                        ){
		   prop.load(fr);
	    }
		
		
		var fileTickBugWithDates=prop.getProperty("fileTicketsBugWithFVOVdates");
		var csvAffVerTickBug=prop.getProperty("fileAffectedVersionsZkTickectsBug");
		var infoProg=prop.getProperty("fileInfoProg");
		
		List<String> list;
		
		var rs=new ReleasesHandling();
		rs.findFixVersionOpenVesion(fileTickBugWithDates, infoProg);
		
		list=rs.findInjectedVersionsWithDatesAndAssociateIndexVersion(csvAffVerTickBug, infoProg);
		rs.writeInjectedVersionsInDB(list);
		log.info("fine ReleasesHandling");
		
		var p2=new Proportion2();
		p2.calculatePTicketsWithAffectedVersion();
		p2.calculatePticketsWithoutAffectedVersion();
		log.info("fine Proportion");
		
		var bg=new Buggy();
		bg.buggy();
		log.info("fine Buggy");
		
		var ch=new CHGSETSIZEmetric();
		ch.calculateChgSetSize();
		ch.claculateMaxAndAvgChgSetSize();
		log.info("fine CHGSETSIZEmetric");
		
		var cr=new CHURNmetric();
		cr.calculateChurn();
		cr.calculateMaxAndAvgChurn();
		log.info("fine CHURNmetric");
		
		var la=new LOCADDEDmectric();
		la.calculateLocAdded();
		la.calculateMaxAndAvgLocAdded();
		log.info("fine LOCADDEDmectric");

		
		var nau=new NAUTHmetric();
		nau.caculateNAUTH();
		log.info("fine NAUTHmetric");
		
		var nf=new NFIXmetric();
		nf.calculateNFIX();
		log.info("fine NFIXmetric");
		
		var nr=new NRmetric();
		nr.calculateNR();
		log.info("fine NRmetric");
		
		log.info("fine");
		
	}

}
