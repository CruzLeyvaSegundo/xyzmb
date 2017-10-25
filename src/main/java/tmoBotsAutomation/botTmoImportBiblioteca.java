package tmoBotsAutomation;

import webServicesTMO.WebBibliotecaTmo;

public class botTmoImportBiblioteca {

	public static void main(String[] args) {
		
		//Guardar todo con formato UTF-8
		//		System.out.println("BEARĀ NO MICHI - ベアラーの道");
		
		//Cargamso la pagina clase que nos permite interactuar con la pagina BiblioteTmo
        WebBibliotecaTmo webBiblioteca = new WebBibliotecaTmo();       
   
        // Comenta el titulo de la pagina visatada
        System.out.println("Page title is: " + webBiblioteca.getTitle());
        
        //Se almacena el html de la pagina visitada en un archivo .txt
        /*System.out.println("Guardando html en un archivo...");
        String html = webBiblioteca.getHtmlContent();
        String ruta = "C:\\Users\\segun\\Desktop\\ListManganime\\prueba.txt";
        webBiblioteca.writeTextFile(html,ruta);
        System.out.println("Html guardado!!");*/
         
        //Se recorre el carrusel de los mangas publicados hasta la fecha dentro de la biblioteca
        System.out.println("Empieza el bucle para recorrer el carrusel");
        
        webBiblioteca.getMangasBiblioteca();  
        System.out.println("Se termino de recorrer el carrusel de mangas");
	}

}
