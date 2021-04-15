/**
 * 
 */
package vir;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;

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
		/*String inputCSV="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String zookeeperVersionInfo="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		String fileResult="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		FilesHandling fh=new FilesHandling();
		fh.createCSVfile(inputCSV);
	    fh.versionJavaclassPair(fileResult,zookeeperVersionInfo,inputCSV);	
		
		FilesHandling3 fh3=new FilesHandling3();
		//String zookeeperVersionInfo="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";		
		String affectedVersionsZookeperTickectsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\AffectedVersionsZookeperTickectsBug.csv";
		String ticketsBugWithAffectedVerANDdates="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\TicketsBugWithAffectedVerANDdates.txt";
		fh3.findVersionsWithDatesAndAssociateIndexVersion(affectedVersionsZookeperTickectsBug, zookeeperVersionInfo,ticketsBugWithAffectedVerANDdates);
		
		
		
		/*Proportion p=new Proportion();
		String datesTicketsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\DatesTicketsBug.txt";
		String datesFVOVTickectsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\DatesFVOVTickectsBug.txt";
		//String fileInfoProject="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		p.Find_FV_OV(datesTicketsBug, zookeeperVersionInfo,datesFVOVTickectsBug);*/
		
		//Proportion2 p2=new Proportion2();
		//p2.calculatePTicketsWithAffectedVersion();
		
		
		Logger.getLogger("MyLogger").info("fine");
	}

}
