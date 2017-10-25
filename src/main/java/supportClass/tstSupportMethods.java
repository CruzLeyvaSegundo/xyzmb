package supportClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webTmo.Capitulo;

public class tstSupportMethods {

	public static void main(String[] args) {
	    //List<Capitulo> caps = new ArrayList<Capitulo>();
	    //caps.add(new Capitulo(8.5,"8.50","lolxD"));//caps.add(new Capitulo(9,"9.00","pacmon"));
	    //caps.add(new Capitulo(0,"0.00","lolxD"));caps.add(new Capitulo(5,"5.00","Soy un clon"));
	    //caps.add(new Capitulo(1.01,"1.01","Soy un clonx2"));caps.add(new Capitulo(6,"6.00","pacmon"));
	    SupportMethods s = new SupportMethods();
	    /*System.out.println("Capitulos totales: "+caps.size());
		for (Capitulo e : caps) {
			e.printCapitulo();
		}
	    System.out.println("Antes del ordenamiento\n");
	    s.quickSortCaps(caps, 0,caps.size()-1);
	    System.out.println("Despues del ordenamiento\n");
	    System.out.println("Capitulos totales: "+caps.size());
		for (Capitulo e : caps) {
			e.printCapitulo();
		}*/

	     /*System.out.println(Double.parseDouble(s.matchString("[0-9]+.[0-9][0-9]", "T:- Cap. 15.05")) +
	    		 "  lenght "+s.matchString("[0-9]+.[0-9][0-9] ?", "T:- Cap. 15.05").length());*/
	    int nroCaps = Integer.valueOf(s.matchString("[0-9]+","188 publicados"));
	    System.out.println("Capitulos totales: "+nroCaps+"x");
	     //System.out.println(s.generateTextSearch("Dungeon ni Deai wo Motomeru no wa Machigatte","Danmachi, Is It Wrong to Try to Pick Up Girls in a Dungeon?,")+"x");
	     

	}

}
