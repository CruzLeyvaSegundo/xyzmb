package supportClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webTmo.Capitulo;

public class SupportMethods {
	
	public SupportMethods() {}
	
    private int partition(List<Capitulo> caps, int left, int right)
    {  
        int l = left, r = right;  
        int m =(left+right)/2;
        Capitulo pivot = new Capitulo(caps.get(m).getOrderCap(),caps.get(m).getNroCap(),caps.get(m).getDescripcionCap());  
        while (l <= r) 
        {        
            while (caps.get(l).getOrderCap() < pivot.getOrderCap())             
                    l++;
            while (caps.get(r).getOrderCap() > pivot.getOrderCap())
                    r--;
            if (l <= r) {  
                    Capitulo tmp = new Capitulo(caps.get(l).getOrderCap(),caps.get(l).getNroCap(),caps.get(l).getDescripcionCap());    
                    /*System.out.println("Antes de todo: Stepp 1  con 'l': "+l+" y 'r': "+r+" \n");
                    for (Capitulo e : caps) { 
            			e.printCapitulo();
            		}*/
                    caps.add(l, caps.get(r));
                    caps.remove(l+1);
                    caps.remove(r);
                    caps.add(r,tmp);
                    /*System.out.println("Despues de añadir un cap en 'l' : "+l+" Stepp 2\n");
                    for (Capitulo e : caps) {
            			e.printCapitulo();
            		}*/
                    /*System.out.println("Despues de añadir un cap en 'r' : "+r+" Stepp 3\n");
            		for (Capitulo e : caps) {
            			e.printCapitulo();
            		}*/
                    l++;
                    r--;
            }
        }
        return l;
    }
 
    public void quickSortCaps(List<Capitulo> caps, int left, int right) {
        int index = partition(caps, left, right);
        //System.out.println("Index: "+index+" Left: "+left+"  Right: "+right+"\n");
        if (left < index - 1)
        	quickSortCaps(caps, left, index - 1);
        if (index < right)
        	quickSortCaps(caps, index, right);
    }
    
	public void writeTextFile(String text,String ruta) {
        File archivo = new File(ruta); 
        BufferedWriter bw;
        try {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(text);
			bw.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	public String matchString(String regex,String text) {
	     Pattern p = Pattern.compile(regex);
	     Matcher m = p.matcher(text);   // get a matcher object
	     if (m.find( )) 
	         return m.group(0);
	     else 
	         return "";
	}
	public void sleep(double timeS) {
		int t=(int)(timeS*1000);
    	try {
        	Thread.sleep(t);
        } catch (Exception e) {
        	// Mensaje en caso de que falle
        	System.out.println("error sleep");
        }
	}
	public String validateText(String text) {
		String result="";
		int size = text.length();
		for(int i=0;i<size;i++)
		{
			char c=text.charAt(i);
			if(c=='\"')
				result+="\\\"";
			else if(c=='\'')
				result+="\\\'";
			else if(c=='á' || c=='à')
				result+='a';
			else if(c=='é' || c=='è')
				result+='e';
			else if(c=='í' || c=='ì')
				result+='i';
			else if(c=='ó' || c=='ò')
				result+='o';
			else if(c=='ú' || c=='ù')		
				result+='u';
			else
				result+=c;
		}			
		return result;
	}
}
