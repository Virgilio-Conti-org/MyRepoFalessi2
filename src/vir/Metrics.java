/**
 * 
 */
package vir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Virgilio
 *
 */
public class Metrics {

	public void loc() throws InterruptedException, IOException {
		//String p="D:\\p.txt";
		var pathrepo="D:/Libri/Università/Falessi/Repo/RepoZookeeper/zookeeper";
		//git ls-files | grep \"\\.java$\" | xargs wc -l	
		
	  //Process proc=Runtime.getRuntime().exec("git -C "+pathrepo+" ls-files ");	
	  //Process proc=Runtime.getRuntime().exec("find /c /v \"\" "+p);	
		Process proc=Runtime.getRuntime().exec("git -C "+pathrepo+" --no-pager log --numstat --all");
	  var output=new StringBuilder();
		
		
	  var isr=new InputStreamReader(proc.getInputStream());
	  var br=new BufferedReader(isr);
	  
	  String line;
	  while( (line=br.readLine()) !=null) {
		  output.append(line+"\n");
		  //System.out.println(output);
	  }//while
	  
	  int exit=proc.waitFor();
	  if(exit==0) {
		  System.out.println("comando eseguito ok");
		  System.out.println(output);
		  //System.exit(0);
	  }
	  else {
		  System.out.println("errore avvenuto  exit = "+exit);
		  System.out.println(output);
	  }
		
	}//fine metodo
	
}
