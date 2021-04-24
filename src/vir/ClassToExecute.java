/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import commands.CommandGitShow;
import metrics.CHGSETSIZEmetric;
import metrics.CHURNmetric;
import metrics.LOCADDEDmectric;
import metrics.LOCmetric;

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
	public static void main(String[] args) throws InterruptedException, IOException, SQLException, ParseException   {
		/*String name="TestApacheCuratorCompatibility.java";
		String name2="FatJarMain.java";*/
		
		
		CHGSETSIZEmetric ch=new CHGSETSIZEmetric();
		ch.calculateChgSetSize();
		System.out.println("fine1");	
		
		/*CHURNmetric cr=new CHURNmetric();
		cr.calculateChurn();
		System.out.println("fine2");*/
		
		/*LOCADDEDmectric la=new LOCADDEDmectric();
		la.calculateLocAdded();
		System.out.println("fine3");*/
		
		System.out.println("fine");	
		//Logger.getLogger("MyLogger").info("fine");
		
	}

}
