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
		String p;
		var projectChoosen=""; 
		var lungProjectChoosen=0;
		int lungStr;
		var i=0;
		int indice;
		int indice2;
		var diff=0;
		
		if(str.contains("ZOOKEEPER-")) {
			projectChoosen="ZOOKEEPER-";
			lungProjectChoosen=projectChoosen.length();
			
		}
		if(str.contains("BOOKKEEPER-")) {
			projectChoosen="BOOKKEEPER-";
			lungProjectChoosen=projectChoosen.length();
		}
		
		
		lungStr=str.length();
		
		indice=str.indexOf(projectChoosen);
		
		indice2=indice+lungProjectChoosen;
		
		if(indice2>lungStr-4) {
			
			diff=lungStr-indice2;
			p=str.substring(indice2, indice2+diff);
			return projectChoosen+p;
		}
		else {
						
		    p=str.substring(indice2, indice2+4);			
		}
		
		for( i=0;i<4;i++) {
			if(!Character.isDigit(p.charAt(i) ) ) {
				break;
			}
		}
		
		return projectChoosen+p.substring(0, i);
				
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
		var s="";
		
		var prop= new Properties();
		
		try(var f=new FileReader("config") ){
		   prop.load(f);
		}
		var path=prop.getProperty("pathFileTicketsBug");
		
				
	   try(var fr=new FileReader(path);
		   var br=new BufferedReader(fr)	   
			                                   ){
			                                      		   			                          
			while( (s=br.readLine()) !=null ) {
				count=count+1;
				s=s.concat("s");
			}
		   
	   }//try
	   
	   return count;
		
}//fine metodo
	
	   
}
