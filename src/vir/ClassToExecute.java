/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
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
	 *
	 *
	 */
	public static void main(String[] args) throws IOException, SQLException, InterruptedException    {
		
		Logger log=Logger.getLogger("MyLogger");
				
		
		
				
		CHGSETSIZEmetric ch=new CHGSETSIZEmetric();
		ch.calculateChgSetSize();
		ch.claculateMaxAndAvgChgSetSize();
		log.info("fine1");
		
		CHURNmetric cr=new CHURNmetric();
		cr.calculateChurn();
		cr.calculateMaxAndAvgChurn();
		log.info("fine2");
		
		LOCADDEDmectric la=new LOCADDEDmectric();
		la.calculateLocAdded();
		la.calculateMaxAndAvgLocAdded();
		log.info("fine3");

		
		NAUTHmetric nau=new NAUTHmetric();
		nau.caculateNAUTH();
		log.info("fine4");
		
		NFIXmetric nf=new NFIXmetric();
		nf.calculateNFIX();
		log.info("fine5");
		
		NRmetric nr=new NRmetric();
		nr.calculateNR();
		log.info("fine6");
		
		
	}

}
