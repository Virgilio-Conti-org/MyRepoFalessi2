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
	public double calculateP(int fv, int ov,int iv) {
	
	//paramertri	
	//fv=fixVersion ov=openigVersion  iv=InjectedVersion	
		
	//intero che conterrà il valore proportion calcolato
		double p; 
			
		if(fv==ov) {
			p=1.0;
			return p;
		}
		
		if(ov==1) {
			p=1.0;
			return p;
		}
		
		double dbFV=(double) fv;
		//formula per il calcolo del valore proportion
		p=(dbFV-iv)/(fv-ov);
		
		return p;
		
	}
	
	//metodo che calcola il valore Injected Version 
	public int calculateIV(double p,int fv, int ov) {
		//paramertri	
		//p=valore proportion fv=fixVersion ov=openigVersion  
		
		//intero che conterrà il valore proportion calcolato
		var iv=0;
		int newP=(int) Math.round(p);
		//formula per il calcolo della InjectedVersion
		iv=  fv-(fv-ov)* newP   ;
		
		return iv;
		
	}
	
	
	
	
}
