/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import metrics.CHGSETSIZEmetric;
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
		
		List<Integer> l=Arrays.asList(1,1,12,1,1,1,1);
		
		
		CHGSETSIZEmetric chg= new CHGSETSIZEmetric();
		Help2 h2=new Help2();
		//chg.chgSize();
		int max=h2.findMax(l);
		int avg=h2.findAvg(l);
		System.out.println("max ="+max);
		System.out.println("avg ="+avg);
		
		Logger.getLogger("MyLogger").info("fine");
		
	}

}
