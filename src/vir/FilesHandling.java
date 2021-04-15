/**
 * 
 */
package vir;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class FilesHandling {
        
	//metodo per associare una versione ad una classe java
	public void versionJavaclassPair(String fileRes, String projectInfo, String fileCSVdest) throws IOException, ParseException{
		
		String[] info; 
		String lineFileRes;
		String dataJavaClass="/"; 	
		String version;
		String nameJavaClass; 
		int indexDataJavaClassVersion;
		int lung=0; 
	
		
		Path path= Paths.get(projectInfo);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		String[] datesVersions= new String[lung-1];
		String[] versions     = new String[lung-1];
		
			
		for(int i=1;i<lung;i++) {
			info=linesTicketsFile.get(i).split(",");
			versions[i-1]=info[0];
			datesVersions[i-1]=info[3];
			
		}
		
		
		
		try (
		  FileReader fr=new FileReader(fileRes);
		  BufferedReader br=new BufferedReader(fr);
			
		  FileWriter fwCSV=new FileWriter(fileCSVdest,true);
		  BufferedWriter bwCSV=new BufferedWriter(fwCSV)  ){
			 
			 while( (lineFileRes=br.readLine() ) !=null ) {
					
				if(lineFileRes.startsWith("Date") ) {
					dataJavaClass=lineFileRes.substring(8,18);
					
				}
				
				if( lineFileRes.startsWith("M") ) {			
				    nameJavaClass=lineFileRes.substring(2);
					indexDataJavaClassVersion=dateBeforeDate(dataJavaClass, datesVersions);					
					version=versions[indexDataJavaClassVersion];
					
									
					bwCSV.write(version+","+nameJavaClass+"\n");
					bwCSV.flush();
					
				}
								
					
	          }//while
		}//try
		
			 
				
		
	}
	
	//metodo per ottere la data subito precedente rispetto ad una data di riferimento
	public int dateBeforeDate(String myDate, String[] dates) throws ParseException {
		
		int lung; 
		int i=0;
		
		lung=dates.length;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Date inputDate=sdf.parse(myDate);
		Date date;
		
		
		for( i=0;i<lung;i++) {
			date=sdf.parse( dates[i] );
			
			if(inputDate.after(date)) {
				
			}
		    
			else {
				break;
			}
		}//for
					
		if(i==0){
		  i=0;
		  return i;
		}
		
		return i-1;
	}
	
	//metodo per creare un file csv 
	public void createCSVfile(String path) throws FileNotFoundException {
		
		FileOutputStream fileW= new FileOutputStream(path);
		PrintWriter pw =new PrintWriter(fileW);
		
		pw.println("Version,NameJavaClass,1,2,3,4,5,6,7,8,9,10,11,12,Buggy,Buggy_p");
		pw.flush();
		pw.close();
		
	}
	
	//metodo che restituisce la data che permette di eliminare la metà delle releases 
	public String  getRidOf50Relases( String percorso) throws IOException {
		int lung; 
		String line;
		String data;
		String[] s;
		
		Path path= Paths.get(percorso);
		List<String> linesFile =Files.readAllLines(path);
		
		lung=linesFile.size();
		if(lung%2==1) {
			lung=lung-1;
			lung=lung/2;
		}
		else {
		   lung=lung/2;
		}
		line=linesFile.get(lung);
		s=line.split(",");
		data=s[3];
		
		return data;
		
	}
	
	//metodo che conta quanti tickets di tipo bug ci sono in un progetto
	public int numberOfTicketsBug(String filepathTicketsBug) throws IOException {
		int count=0;
		
		FileReader fr=new FileReader(filepathTicketsBug);
		BufferedReader br=new BufferedReader(fr);
		
	   try {	
		   while( br.readLine()  !=null ) {
			   count=count+1;
		   }		
		   return count;
	   }//try
	   
	   finally {
		 br.close();
	   }
		
}//fine metodo
		
	
}
