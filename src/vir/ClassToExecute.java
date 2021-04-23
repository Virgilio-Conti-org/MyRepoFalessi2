/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
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
	 *
	 *
	 */
	public static void main(String[] args) throws InterruptedException, IOException, SQLException   {
		/*String name="TestApacheCuratorCompatibility.java";
		String name2="FatJarMain.java";
		String path;
		String locString;
		int loc;
		
		Help2 h2=new Help2();
		path=h2.getJavaPath(name2);
		
		System.out.println("in mezzo "+path );
		
		LOCmetric m= new LOCmetric();
		System.out.println("in mezzo " );
		locString=m.loc(path);
		loc=m.getLOCfromStringLoc(locString);
		
		
		
		
		System.out.println("locString = "+locString);
		System.out.println("loc = "+loc);*/
		
		/*CHGSETSIZEmetric ch=new CHGSETSIZEmetric();
		ch.calculateChgSetSize();
		System.out.print("fine 1");*/
		
		/*CHURNmetric cr=new CHURNmetric();
		cr.CalculateChurn();
		System.out.print("fine 2");*/
		
		LOCADDEDmectric lm=new LOCADDEDmectric();
		lm.calculateLocAdded();
		System.out.print("fine 3");
		//Logger.getLogger("MyLogger").info("fine");
		
		/*List<String> l=new ArrayList<>();
		String com="b4b73a37d43413efdf50427f96aab2720af11baf";
		
		CommandGitShow c=new CommandGitShow();
		l=c.commandGitShow(com);
		System.out.println(l);*/
		//Logger.getLogger("MyLogger").info("fine");
		
	}

}
