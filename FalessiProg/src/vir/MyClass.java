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
		String pathcsv="D:\\Libri\\UniversitÓ\\Falessi\\Repo\\RepoZookeeper\\InputCSV.csv";
		String p="D:\\Libri\\UniversitÓ\\Falessi\\ZOOKEEPER\\ZOOKEEPERVersionInfo.csv";
		String fileResult="D:\\Libri\\UniversitÓ\\Falessi\\Repo\\RepoZookeeper\\FileResult.txt";
		A a=new A();
		a.CreateCSVfile(pathcsv);
	    a.Version_Javaclass_pair(fileResult,p,pathcsv);	
			
		
	}

}
