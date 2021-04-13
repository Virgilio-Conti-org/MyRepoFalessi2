/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @author Virgilio
 *
 */
public class ClassToExecute {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		//int SelectorProject;  //1=bookeeper   2=zookeeper
		String InputCSV="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String ZOOKEEPERVersionInfo="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		String FileResult="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		FilesHandling fh=new FilesHandling();
		fh.CreateCSVfile(InputCSV);
	    fh.Version_Javaclass_pair(FileResult,ZOOKEEPERVersionInfo,InputCSV);	
		
		FilesHandling3 fh3=new FilesHandling3();
		//String ZOOKEEPERVersionInfo="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";		
		String AffectedVersionsZookeperTickectsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\AffectedVersionsZookeperTickectsBug.csv";
		String TicketsBugWithAffectedVerANDdates="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\TicketsBugWithAffectedVerANDdates.txt";
		fh3.Find_Versions_With_Dates_and_Associate_IndexVersion(AffectedVersionsZookeperTickectsBug, ZOOKEEPERVersionInfo,TicketsBugWithAffectedVerANDdates);
		
		
		
		/*Proportion p=new Proportion();
		String DatesTicketsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\DatesTicketsBug.txt";
		String DatesFVOVTickectsBug="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\DatesFVOVTickectsBug.txt";
		//String FileInfoProject="D:\\Libri\\Universit�\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		p.Find_FV_OV(DatesTicketsBug, ZOOKEEPERVersionInfo,DatesFVOVTickectsBug);*/
		
		Proportion2 p2=new Proportion2();
		p2.Calculate_P_Tickets_With_Affected_Version();
		
		System.out.println("fine");
	}

}
