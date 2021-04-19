/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author Virgilio
 *
 */
public class Help {

	//metodo per ottere la data subito precedente rispetto ad una data di riferimento	
public int dateBeforeDate(String myDate, String[] dates) throws ParseException {
		
		int lung; 
		var i=0;
		
		lung=dates.length;
		
		var sdf = new SimpleDateFormat("yyyy-MM-dd");	
		var inputDate=sdf.parse(myDate);
		Date date;
		
		
		for( i=0;i<lung;i++) {
			date=sdf.parse( dates[i] );
			
			if(inputDate.after(date)) {
				//if per trovare la data precedente di inputDate
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


//metodo per gestire la parte numerica variabile di un perticolare ticket bug
	public String projectStringTicket(String str) {
		var ticket="/";
		var regex="";
		if(str.contains("ZOOKEEPER-")) {
			regex="ZOOKEEPER-\\d{0,4}";
		}
		
		if(str.contains("BOOKKEEPER-")) {
			regex="BOOKKEEPER-\\d{0,4}";
		}
		
		var pattern=Pattern.compile(regex);
		var match=pattern.matcher(str);
		
		if(match.find()) {
			ticket=match.group(str);
		}
		
		return ticket;
				
	}//fine metodo



	
	//metodo per creare un file csv 
	public void createCSVfile(String path) throws FileNotFoundException {
		
		var fileW= new FileOutputStream(path);
		var pw =new PrintWriter(fileW);
		
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
		
		var path= Paths.get(percorso);
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
	public int numberOfTicketsBug() throws IOException {
		var count=0;
		var prop= new Properties();
		String path=prop.getProperty("pathFileTicketsBug");
		
		var fr=new FileReader(path);
		var br=new BufferedReader(fr);
		
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
