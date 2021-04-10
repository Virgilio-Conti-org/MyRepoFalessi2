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
		//String InputCSV="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String ZOOKEEPERVersionInfo="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		/*String FileResult="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		A a=new A();
		a.CreateCSVfile(InputCSV);
	    a.Version_Javaclass_pair(FileResult,ZOOKEEPERVersionInfo,InputCSV);	*/
		
		/*C c=new C();
		//String FileInfoProject="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";		
		String AffectedVersionsZookeperTickectsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\AffectedVersionsZookeperTickectsBug.csv";
		String TicketsBugWithAffectedVerANDdates="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\TicketsBugWithAffectedVerANDdates.txt";
		c.Find_Versions_With_Dates_and_Associate_IndexVersion(AffectedVersionsZookeperTickectsBug, ZOOKEEPERVersionInfo,TicketsBugWithAffectedVerANDdates);
		*/
		
		
		Proportion p=new Proportion();
		String DatesTicketsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\DatesTicketsBug.txt";
		String DatesFVOVTickectsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\DatesFVOVTickectsBug.txt";
		//String FileInfoProject="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		p.Find_FV_OV(DatesTicketsBug, ZOOKEEPERVersionInfo,DatesFVOVTickectsBug);
		System.out.print("fine");
	}

}
