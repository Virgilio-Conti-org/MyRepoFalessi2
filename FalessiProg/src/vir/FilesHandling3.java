/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Virgilio
 *
 */
public class FilesHandling3 {

	
	
	//metodo che trova le affected versions che hanno una data e poi associa  
	//a queste l'indice numerico corrispondente
	public void Find_Versions_With_Dates_and_Associate_IndexVersion(String PathFileCSV, String FileInfoProject,String Filedest) throws IOException {
		String[] info= {"","","",""};
		String LineFileCSV;
		int lung;
		int i,j;
		int versioneTrovata=0;  //0=versione non trovata  1=versione trovata
		
		FileReader frCSV=new FileReader(PathFileCSV);
		BufferedReader brCSV=new BufferedReader(frCSV);
		
		
		FileWriter fwDest=new FileWriter(Filedest);
		BufferedWriter bwDest=new BufferedWriter(fwDest);
		
		Path path= Paths.get(FileInfoProject);		
		List<String> linesTicketsFile =Files.readAllLines(path);
		lung=linesTicketsFile.size();
		
		//String[] DatesVersions = new String[lung-1];
		String[] Versions = new String[lung-1];
		String[] VersionName = new String[lung-1];
		String[] BuffSplit;
		
		for( i=1;i<lung;i++) {
			info=linesTicketsFile.get(i).split(",");
			Versions[i-1]=info[0];
			VersionName[i-1]=info[2];
			//DatesVersions[i-1]=info[3];			
			//System.out.println(Versions[i-1]+" "+DatesVersions[i-1]);
		}//for
		
		 LineFileCSV=brCSV.readLine();//get rid of first line
		 while( (LineFileCSV=brCSV.readLine() ) !=null ) {
				
			 BuffSplit=LineFileCSV.split(",");
			 int lungBS=BuffSplit.length;
			 //System.out.println("bf "+BuffSplit[0]+"  bf lung "+BuffSplit.length);
				for(i=0;i<lungBS;i++) {
					//System.out.println("i= "+i);
					for(j=0;j<lung-1;j++) {
						//String bf=BuffSplit[index+i];
						//String v=VersionName[j];
						//System.out.println("bf "+BuffSplit[0]+"  bf lung "+BuffSplit.length);
						//System.out.println("j= "+j+" lung "+VersionName.length);
						if(BuffSplit[i].equals(VersionName[j])) {
							
							versioneTrovata=1;
							LineFileCSV=BuffSplit[0]+","+VersionName[j]+","+Versions[j];						
							
							bwDest.write(LineFileCSV+"\n");
							bwDest.flush();
							break;
						}
					}//for
					if(versioneTrovata==1) {
						versioneTrovata=0;
						break;
					}
					
				}//for
					
	          }//while
	
		    brCSV.close();
			bwDest.close(); 
		    
		
	}
}
