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
		/*String pathcsv="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String p="D:\Libri\Università\Falessi\Repo\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		String fileResult="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		A a=new A();
		a.CreateCSVfile(pathcsv);
	    a.Version_Javaclass_pair(fileResult,p,pathcsv);	*/
		
		
		
		Proportion p=new Proportion();
		String FileInfoTicketsBug="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\DatesTicketsBug.txt";
		String FileInfoProject="D:\\Libri\\Università\\Falessi\\Repo\\RepoZookeeper\\ZOOKEEPERVersionInfo.csv";
		p.Get_FV_OV(FileInfoTicketsBug, FileInfoProject);
	}

}
