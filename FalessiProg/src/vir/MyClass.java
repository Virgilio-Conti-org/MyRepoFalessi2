/**
 * 
 */
package vir;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Virgilio
 *
 */
public class MyClass {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		//String InputCSV="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String ZOOKEEPERVersionInfo="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		/*String FileResult="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		A a=new A();
		a.CreateCSVfile(InputCSV);
	    a.Version_Javaclass_pair(FileResult,ZOOKEEPERVersionInfo,InputCSV);	*/
		
		/*C c=new C();
		//String FileInfoProject="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";		
		String AffectedVersionsZookeperTickectsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\AffectedVersionsZookeperTickectsBug.csv";
		String TicketsBugWithAffectedVerANDdates="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\TicketsBugWithAffectedVerANDdates.txt";
		c.Find_Versions_With_Dates_and_Associate_IndexVersion(AffectedVersionsZookeperTickectsBug, ZOOKEEPERVersionInfo,TicketsBugWithAffectedVerANDdates);
		*/
		
		
		Proportion p=new Proportion();
		String DatesTicketsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\DatesTicketsBug.txt";
		String DatesFVOVTickectsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\DatesFVOVTickectsBug.txt";
		//String FileInfoProject="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		p.Find_FV_OV(DatesTicketsBug, ZOOKEEPERVersionInfo,DatesFVOVTickectsBug);
		System.out.print("fine");
	}

}
