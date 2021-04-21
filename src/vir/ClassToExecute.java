/**
 * 
 */
package vir;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Virgilio
 *
 */
public class ClassToExecute {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 *
	 *
	 */
	public static void main(String[] args) throws InterruptedException, IOException   {
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
		Logger.getLogger("MyLogger").info("fine");
		
	}

}
