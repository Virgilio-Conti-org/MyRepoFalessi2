/**
 * 
 */
package metrics;

import java.io.IOException;

import commands.CommandFind;


/**
 * @author Virgilio
 *
 */
public class LOCmetric {

	public int loc(String path) throws InterruptedException, IOException {
		String locString;
		
		var cf=new CommandFind();
		
		locString=cf.find(path);
		return getLOCfromStringLoc(locString);
		
	}//fine metodo
	
	//metodo che estrae il numero LOC dalla stringa del comando find
	public int getLOCfromStringLoc(String str) {
		int lung;
		String[] buffer;
		
		buffer=str.split(" ");
		lung=buffer.length;
		
		return Integer.parseInt(buffer[lung-1]) ;
		
	}
	
}
