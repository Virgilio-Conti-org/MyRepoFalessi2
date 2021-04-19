/**
 * 
 */
package vir;


/**
 * @author Virgilio
 *
 */
public class Proportion {

	String fixV;
	String openV;
	String injectV;
	
	
	//metodo che calcola il valore P secondo il metodo proportion 
	public int calculateP(int fv, int ov,int iv) {
	
	//paramertri	
	//fv=fixVersion ov=openigVersion  iv=InjectedVersion	
		
	//intero che conterrà il valore proportion calcolato
		var p=0; 
		
		
		if(fv==ov) {
			p=1;
			return p;
		}
		
		if(ov==1) {
			p=1;
			return p;
		}
		
		//formula per il calcolo del valore proportion
		p=(fv-iv)/(fv-ov);
		return p;
		
	}
	
	//metodo che calcola il valore Injected Version 
	public int calculateIV(int p,int fv, int ov) {
		//paramertri	
		//p=valore proportion fv=fixVersion ov=openigVersion  
		
		//intero che conterrà il valore proportion calcolato
		var iv=0;
		
		//formula per il calcolo della InjectedVersion
		iv=fv-(fv-ov)*p;
		
		return iv;
		
	}
	
	
	
	
}
