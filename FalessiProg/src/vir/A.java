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
public class A {
        
	
	public void Version_Javaclass_pair(String fileRes, String ProjectInfo, String FileCSVdest) throws IOException, ParseException{
		int lung=0; 
		String[] info= {"","","",""}; 
		String lineFileRes;
		String DataJavaClass="/"; 
		int IndexDataJavaClassVersion;
		String Version;
		String NameJavaClass; 
		
		
		FileReader fr=new FileReader(fileRes);
		BufferedReader br=new BufferedReader(fr);
		
		Path path= Paths.get(ProjectInfo);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		String[] DatesVersions= new String[lung-1];
		String[] Versions     = new String[lung-1];
		
		FileWriter fwCSV=new FileWriter(FileCSVdest,true);
		BufferedWriter bwCSV=new BufferedWriter(fwCSV);
		
		for(int i=1;i<lung;i++) {
			info=linesTicketsFile.get(i).split(",");
			Versions[i-1]=info[0];
			DatesVersions[i-1]=info[3];
			//System.out.println(Versions[i-1]+" "+DatesVersions[i-1]);
		}
			
			 while( (lineFileRes=br.readLine() ) !=null ) {
					
				if(lineFileRes.startsWith("Date") ) {
					DataJavaClass=lineFileRes.substring(8,18);
					//System.out.print("DATA "+DataJavaClass+"  ");
				}
				
				if( lineFileRes.startsWith("M") ) {			
				    NameJavaClass=lineFileRes.substring(2);
					IndexDataJavaClassVersion=DateBefore_Date(DataJavaClass, DatesVersions);					
					Version=Versions[IndexDataJavaClassVersion];
					
					bwCSV.write(Version+","+NameJavaClass+"\n");
					bwCSV.flush();
					//System.out.print("DATA "+DataJavaClass+"  ");
					//System.out.print("DATA BEF "+db+"  ");
					//System.out.println("Ver "+Version+" -  JavaClass - "+NameJavaClass );
				}
								
					
	          }//while
			 
		br.close();	
		bwCSV.close();			
		System.out.print(" ok ");
	}
	
	public int DateBefore_Date(String MyDate, String[] Dates) throws ParseException {
		
		int lung; int i=0;
		
		lung=Dates.length;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Date inputDate=sdf.parse(MyDate);
		Date date=new Date();
		
		
		for( i=0;i<lung;i++) {
			date=sdf.parse( Dates[i] );
			
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
	
	public void CreateCSVfile(String path) throws FileNotFoundException {
		
		FileOutputStream fileW= new FileOutputStream(path);
		PrintWriter pw =new PrintWriter(fileW);
		
		pw.println("Version,NameJavaClass,1,2,3,4,5,6,7,8,9,10,11,12,Buggy,Buggy_p");
		pw.flush();
		pw.close();
		
	}
	
	
	public void  GetRidOf50Relases( String percorso) throws IOException {
		int lung; 
		String line,data;
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
		System.out.println(data);
	}
	
	
	
}
