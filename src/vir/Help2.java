/**
 * 
 */
package vir;

import java.util.List;


/**
 * @author Virgilio
 *
 */
public class Help2 {

	
		
	public int findMax(List<Integer> numbers) {
		var max=numbers.get(0);
		int temp;
		
		for(var i=1;i<numbers.size();i++) {
			temp=numbers.get(i);
			if(temp>max) {
				max=temp;
			}
			
		}
		
		
		return max;
	}//fine metodo
	
    public int findAvg(List<Integer> numbers) {
 
        var size=numbers.size();
		var sum=0;
        
		if(size==0) {
			return -100;
		}
		
        for(var i=0;i<size;i++) {
			sum=sum+numbers.get(i);
		}
        
        
		return sum/size;
	}//fine metodo
	
	
}
