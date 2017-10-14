package webServicesTMO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import supportClass.SupportMethods;
import webTmo.Capitulo;
import webTmo.ContentManga;
import webTmo.StatusManga;

public class WebContentMangaTmo {
	
	private SupportMethods support;
	private WebDriver driver;
	private Retrofit retrofit;
	private WebServicesTmo webServiceTmo;
	
	public WebContentMangaTmo(){
		// Se instancia la ubicacion de geckodriver
		System.setProperty("webdriver.gecko.driver", "browsers//geckodriver.exe");
		this.driver =  new FirefoxDriver();
		driver.get("https://www.tumangaonline.com/biblioteca/mangas/10457/Black-Clover");
		retrofit= new Retrofit.Builder()
					.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		webServiceTmo = retrofit.create(WebServicesTmo.class);
		support = new SupportMethods();
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	public String getHtmlContent() {
		return driver.getPageSource().toString();
	}
	

	
	

	





	private String urlPortada;
	private String esMas18;
	private String rakingManga;//Puesto de popularidad del manga con respecto a los demas
	private String ratingManga;
	public String createXpathElement(int op) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[2]/div["+"]/div/";
		switch(op)
		{
			case 1: //XpathTipo
				result += "div[1]/div/div/a";
				break;
			case 2: //XpathTitle
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 3: //XpathAutor
				result += "div[2]/div[1]/div[1]/span";
				break;
			case 4: //XpathDescripcion
				result += "div[2]/div[1]/a/img";
			case 5: //XpathDemografia 
				result += "div[1]/div/div/a";
				break;
			case 6: //XpathEstadoManga
				result += "div[2]/div[1]/div[1]/span";
				break;
			case 7: //XpathFechaPublicacion
				result += "div[2]/div[1]/a/img";
			case 8: //XpathPeridiocidadManga
				result += "div[1]/div/div/a";
				break;
			case 9: //XpathUrlManga;
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 10: //XpathUrlPortada
				result += "div[2]/div[1]/div[1]/span";
				break;
			case 11: //XpathUrlRakingManga;
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 12: //XpathUrlRatingManga
				result += "div[2]/div[1]/div[1]/span";
				break;			
		}
		return result;
	}
	public String createXpathElementGenero(int i) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div["+i+"]/";
		return result;
	}	
	public String createXpathElementCap(int i,int op) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div["+i+"]/";
		switch(op)
		{
			case 1: //XpathDescripcionCap
				result += "div[1]/h3";			
				break;
			case 2: //XpathNroCap
				result += "div[1]/h3/small";
				break;
		}
		return result;
	}
	
	//Si el carrusel llego a su fin entonces class= "disabled" en el boton next '>'
	public boolean isEnableNextButtonCarrusel(int last) {
		String xPathButton = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div["+(last)+"]/ul/li[2]";
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				if(driver.findElement(By.xpath(xPathButton))
						.getAttribute("class").toString().contains("disabled"))
				{
					return false;
				}
				else
				{
					//System.out.println("Loop Carrusel "+ element.getAttribute("class").toString());   
					return true;
				}
			}
			support.sleep(2);
		}		
	}
	
	private boolean existsElement(String xPath) {
	    try {
	        driver.findElement(By.xpath(xPath));
	    } catch (NoSuchElementException e) {
	    	System.out.println("Elemento inexistente: " + e);
	        return false;
	    }
	    return true;
	}
	
	public boolean isMas18(String title) {
		if(title.contains("+18"))
			return true;
		return false;
	}
	
	//Se mueven al siguiente item del carrusel
	public void goNextItemCarrusel(int last) {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div["+(last)+"]/ul/li[2]/a";
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				WebElement e= driver.findElement(By.xpath(xPathButton));
				e.click();
				break;
			}
			support.sleep(2);
		}
	}
	
	public void acceptCookies(){		
		driver.findElement(By.xpath("//*[@id=\"cookie1\"]/button")).click();
	}
	
	//Ordena la lista de mangas desde los recien publicados hasta a las entradas mas antiguas
	public void goFinalCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div[26]/ul/li[10]/a";
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				WebElement e= driver.findElement(By.xpath(xPathButton));
				e.click();
				break;
			}
			support.sleep(2);
		}		
	}
	/*
	private void insertContentManga(StatusManga reqStatus) {
		//Testing metodo POST con retrofit
		Call<StatusManga> statusMangaCallPost = webServiceTmo.setStatusManga(reqStatus);
		statusMangaCallPost.enqueue(new Callback<StatusManga>(){
			public void onFailure(Call<StatusManga> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<StatusManga> call, Response<StatusManga> response) {
				// TODO Auto-generated method stub
					System.out.println("INSERT CORRECT!!");	
			}
		});		
	}*/
	public String validateText(String text) {
		String result="";
		int size = text.length();
		for(int i=0;i<size;i++)
		{
			char c=text.charAt(i);
			if(c=='\"')
				result+="\\\"";
			else
				result+=c;
		}			
		return result;
	}
	public void getContentsMangas() {

		////Acepta la cokies de la web
        acceptCookies();
        ContentManga contentManga = new ContentManga();  
        System.out.println("Empieza la recoleccion de generos");    
		int lastG = 60;    
		for(int i = 1;i<=30 ; i++) {	// 30 generos porsiacaso :u	

			String xPathGenero =   createXpathElementGenero(i);
			for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
			{
				try {
						WebElement eGenero = driver.findElement(By.xpath(xPathGenero));

						String genero = validateText(eGenero.getText());	

						support.sleep(0.4);
						//System.out.println("\nNroCap: "+nroCap+"\nDescripcionCap: "+descripcionCap);	
						if(genero!="")
						{
							contentManga.addGenero(genero);
							System.out.println("\nGenero: "+genero);	
							//contentManga.printCapitulosManga();
							break;
						}
						else
						{
							System.out.println("\n         REPITIENDO LECTURA...");
							support.sleep(2);	
						}
					}
					catch(NoSuchElementException e) {
						if(k==10){
							System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
							lastG=i;
						}
						System.out.println("error: "+k+" ---" +e);

						support.sleep(2);
					}
			}
			if(lastG != 30)
				break;
		}	
		System.out.println("Verificacion de generoValidado");
		if(contentManga.isExistBanGeneros()) {
			//Actualizar es status a 'ban' en la tabla de StatusMangas
		}
		else if(contentManga.isExistGeneroGenderBender()){
			//Actualizar es status a 'investigar' en la tabla de StatusMangas
		}
		else // Todo salio bien
		{	
	        System.out.println("Empieza la recoleccion de informacion primaria");
			int umbralErrMaxText = 0;
			String XpathTipo =  createXpathElement(1);
			String XpathTitle =  createXpathElement(2);
			String XpathAutor =  createXpathElement(3);
			String XpathDescripcion =  createXpathElement(4);
			String XpathDemografia =  createXpathElement(5);
			String XpathEstadoManga =  createXpathElement(6);
			String XpathFechaPublicacion =  createXpathElement(7);
			String XpathPeridiocidadManga =  createXpathElement(8);
			String XpathUrlManga =  createXpathElement(9);
			String XpathUrlPortada =  createXpathElement(10);
			String XpathUrlRakingManga =  createXpathElement(11);
			String XpathUrlRatingManga =  createXpathElement(12);
			for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
			{
				try {
					
						WebElement eTipo = driver.findElement(By.xpath(XpathTipo));	
						WebElement eTitle =  driver.findElement(By.xpath(XpathTitle));	
						WebElement eAutor =  driver.findElement(By.xpath(XpathAutor));	
						WebElement eDescripcion =  driver.findElement(By.xpath(XpathDescripcion));	
						WebElement eDemografia =  driver.findElement(By.xpath(XpathDemografia));	
						WebElement eEstadoManga =  driver.findElement(By.xpath(XpathEstadoManga));	
						WebElement eFechaPublicacion =  driver.findElement(By.xpath(XpathFechaPublicacion));	
						WebElement ePeridiocidadManga =  driver.findElement(By.xpath(XpathPeridiocidadManga));	
						WebElement eUrlManga =  driver.findElement(By.xpath(XpathUrlManga));	
						WebElement eUrlPortada =  driver.findElement(By.xpath(XpathUrlPortada));	
						WebElement eRakingManga =  driver.findElement(By.xpath(XpathUrlRakingManga));	
						WebElement eRatingManga =  driver.findElement(By.xpath(XpathUrlRatingManga));	
	
						
						String tipo = eTipo.getText();
						String title = validateText(eTitle.getText());	
						String autor = "";	
						String descripcion ;	
						String demografia ;	
						String estadoManga ;	
						String fechaPublicacion ;	
						String peridiocidadManga ;	
						String urlManga = eTitle.getAttribute("href");;	
						String urlPortada = "" ;	
						String rakingManga = eRakingManga.getText();	
						String ratingManga = eRatingManga.getText();	
	
						String keyManga=(urlManga.substring(41,urlManga.length())).replace('/', '_');
						String mas18 = "no";
	
						if(isMas18(title)) //Verifica si el contenido es +18
						{
							mas18 = "si";
							title = title.substring(4,title.length());
						}				
						support.sleep(0.4);
	
						if(title!="" && autor!="" && (urlPortada!=null || urlPortada!="") )
							break;
						else
						{
							System.out.println("\n         REPITIENDO LECTURA...");
							support.sleep(2);	
						}
					}
					catch(NoSuchElementException e) {
						System.out.println("error: "+ e);
						umbralErrMaxText++;
						support.sleep(2);
						if(k==10)
							System.out.println("Necesita revisar los servicios web");
					}
				if(umbralErrMaxText>=30)
				{
					System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
					break;
				}
			}
	        System.out.println("Informacion obtenida");
			System.out.println("Empieza el bucle para recorrer el carrusel de capitulos");
			
			//Nos posicionamos al final del carrusel
			goFinalCarrusel();
			System.out.println("Empezamos desde el final del carrusel");
			while(true){
				//Code
				System.out.println("Inicio ListCarrusel");
				int umbralErrMax = 0;
				int last = 26;
				for(int i = 1;i<=25 ; i++) {		
					//Titulo y urlManga
					String xPathNroCap =    createXpathElementCap(i,1);
					String xPathTDescripcionCap =  createXpathElementCap(i,2);
	
				        
					for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
					{
						try {
								WebElement eNroCap = driver.findElement(By.xpath(xPathNroCap));
								WebElement eDescripcionCap = driver.findElement(By.xpath(xPathTDescripcionCap));	
								
	
								String nroCap = validateText(eNroCap.getText());	
								nroCap = support.matchString("[0-9]+.[0-9][0-9]", nroCap);
								
								String descripcionCap = validateText(eDescripcionCap.getText());
	
								support.sleep(0.4);
								//System.out.println("\nNroCap: "+nroCap+"\nDescripcionCap: "+descripcionCap);	
								if(nroCap!="")
								{
									Double orderCap = Double.parseDouble(nroCap);
									contentManga.addCapitulo(new Capitulo(orderCap,nroCap,descripcionCap));
									System.out.println("\nNroCap: "+orderCap+"\nDescripcionCap: "+descripcionCap);	
									//contentManga.printCapitulosManga();
									break;
								}
								else
								{
									System.out.println("\n         REPITIENDO LECTURA...");
									support.sleep(2);	
								}
							}
							catch(NoSuchElementException e) {
								if(k==10){
									System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
									last=i;
								}
								System.out.println("error: "+k+" ---" +e);
								umbralErrMax++;
								support.sleep(2);
							}
					}
					if(umbralErrMax>=30 || last !=26)
						break;
				}		
				contentManga.printCapitulosManga();
				System.out.println("Fin ListCarrusel");
				if(isEnableNextButtonCarrusel(last))
					goNextItemCarrusel(last);
				else
					break;
				support.sleep(8);
			}
		}
		closeContentManga();
		//apagarPC();
		
	}
	public void closeContentManga() {
		driver.close();
		
	}
	
	public void apagarPC() {
        try {

            // Determinar en qué SO estamos
            String so = System.getProperty("os.name");

            String comando;

            // Comando para Linux
            if (so.equals("Linux"))
            comando = "sudo shutdown -h +2";

            // Comando para Windows
            else
            comando = "cmd /c shutdown -s -t 120 -f"; //tiempo en segundos

            // Ejcutamos el comando
            Process p = Runtime.getRuntime().exec(comando);

            BufferedReader stdError = new BufferedReader(new InputStreamReader(
            p.getErrorStream()));
            System.out.println("Su ordenar se apagar en breve");
            // Leemos los errores si los hubiera
            String s;
            while ((s = stdError.readLine()) != null) {
            	JOptionPane.showMessageDialog(null, s);
	        	}
	        }
	        catch (IOException e) {
	            JOptionPane.showMessageDialog(null, e);
	        }
	}
}
