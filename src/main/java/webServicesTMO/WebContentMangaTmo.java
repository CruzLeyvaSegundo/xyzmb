package webServicesTMO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public String createXpathElement(int op) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[2]/div["+"]/div/";
		switch(op)
		{
			case 1: //XpathTittle
				result += "div[1]/div/div/a";
				break;
			case 2: //XpathTipo
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 3: //XpathRating
				result += "div[2]/div[1]/div[1]/span";
				break;
			case 4: //XpathPortada
				result += "div[2]/div[1]/a/img";
			case 5: //XpathTittle
				result += "div[1]/div/div/a";
				break;
			case 6: //XpathTipo
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 7: //XpathRating
				result += "div[2]/div[1]/div[1]/span";
				break;
			case 8: //XpathPortada
				result += "div[2]/div[1]/a/img";
			case 9: //XpathTittle
				result += "div[1]/div/div/a";
				break;
			case 10: //XpathTipo
				result += "div[2]/div[1]/div[2]/span";
				break;
			case 11: //XpathRating
				result += "div[2]/div[1]/div[1]/span";
				break;
		}
		return result;
	}	
	public String createXpathElement(int i,int op) {
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
        ContentManga contentManga = new ContentManga();      /* 
		int umbralErrMaxText = 0;
		String xPathTittle =  createXpathElement(1);
		String xPathTipo =    createXpathElement(2);
		String xPathRating =  createXpathElement(3);
		String xPathPortada = createXpathElement(4);		      
		for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
		{
			try {
					WebElement eTitle = driver.findElement(By.xpath(xPathTittle));	
					WebElement eTipo = driver.findElement(By.xpath(xPathTipo));
					WebElement eRating = driver.findElement(By.xpath(xPathRating));
					WebElement ePortada = driver.findElement(By.xpath(xPathPortada));
						
					String title = validateText(eTitle.getText());
					String urlManga = eTitle.getAttribute("href");
					String keyManga=(urlManga.substring(41,urlManga.length())).replace('/', '_');
					String tipo = eTipo.getText();
					String rating = eRating.getText();
					String portada = ePortada.getAttribute("bn-lazy-src");
					String mas18 = "no";
					//String status = "-";
					//String revision = "-";
					if(isMas18(title)) //Verifica si el contenido es +18
					{
						mas18 = "si";
						title = title.substring(4,title.length());
					}				
					sleep(0.4);
					StatusManga statusManga = new StatusManga(keyManga,"no",tipo,title,portada,
																	urlManga,rating,mas18,"-");
					statusManga.printStatusManga();
					insertStatusManga(statusManga);   
					if(portada!=null && rating!=null && tipo!=null)
						break;
					else
					{
						System.out.println("\n         REPITIENDO LECTURA...");
						sleep(2);	
					}
				}
				catch(NoSuchElementException e) {
					System.out.println("error: "+ e);
					umbralErrMaxText++;
					sleep(2);
					if(k==10)
						System.out.println("Necesita revisar los servicios web");
				}
			if(umbralErrMaxText>=30)
			{
				System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
				break;
			}
		}*/
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
				String xPathNroCap =    createXpathElement(i,1);
				String xPathTDescripcionCap =  createXpathElement(i,2);

			        
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
								//System.out.println("\nNroCap: "+orderCap+"\nDescripcionCap: "+descripcionCap);	
								contentManga.printCapitulosManga();
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
			System.out.println("Fin ListCarrusel");
			if(isEnableNextButtonCarrusel(last))
				goNextItemCarrusel(last);
			else
				break;
			support.sleep(8);
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
