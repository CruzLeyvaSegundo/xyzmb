package webTmo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webServicesTMO.StatusMangaRequest;
import webServicesTMO.WebServicesTmo;

public class WebBibliotecaTmo {
	
	private WebDriver driver;
	private Retrofit retrofit;
	private WebServicesTmo webServiceTmo;
	
	public WebBibliotecaTmo() {
		// Se instancia la ubicacion de geckodriver
		System.setProperty("webdriver.gecko.driver", "browsers//geckodriver.exe");
		this.driver =  new FirefoxDriver();
		driver.get("https://www.tumangaonline.com/biblioteca");
		retrofit= new Retrofit.Builder()
					.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		webServiceTmo = retrofit.create(WebServicesTmo.class);
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
	
	public String createXpathElement(int i,int op) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[2]/div["+i+"]/div/";
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
				break;
		}
		return result;
	}
	
	//Si el carrusel llego a su fin entonces class= "disabled" en el boton next '>'
	public boolean isEnableNextButtonCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/ul/li[13]";
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
			sleep(2);
		}		
	}
	
	private boolean existsElement(String xPath) {
	    try {
	        driver.findElement(By.xpath(xPath));
	    } catch (NoSuchElementException e) {
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
	public void goNextItemCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/ul/li[13]/a";
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				WebElement e= driver.findElement(By.xpath(xPathButton));
				e.click();
				break;
			}
			sleep(2);
		}
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
	public void acceptCookies(){
		driver.findElement(By.xpath("//*[@id=\"cookie1\"]/button")).click();
	}
	
	//Ordena la lista de mangas desde los recien publicados hasta a las entradas mas antiguas
	public void orderBibliotecaManga() {
        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[1]/div/div/div/select")));
        dropdown.selectByValue("fechaCreacion");
        driver.findElement(By.xpath("//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[1]/div/div/div/span/button")).click();
        sleep(5);
	}
	private void insertStatusManga(StatusManga statusManga) {
		//Testing metodo POST con retrofit
		StatusMangaRequest reqStatus = new StatusMangaRequest("tmoMangaStatus",statusManga);
		Call<StatusMangaRequest> statusMangaCallPost = webServiceTmo.setStatusManga(reqStatus);
		statusMangaCallPost.enqueue(new Callback<StatusMangaRequest>(){
			public void onFailure(Call<StatusMangaRequest> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<StatusMangaRequest> call, Response<StatusMangaRequest> response) {
				// TODO Auto-generated method stub
					System.out.println("INSERT CORRECT!!");	
			}
		});		
	}
	public void getMangasBiblioteca() {

		////Acepta la cokies de la web
        acceptCookies();
             
        //Ordenamos las entradas de la biblioteca de mangas
        orderBibliotecaManga();
        
		while(true){
			int umbralErrMax = 0;
			for(int i = 1;i<=20 ; i++) {		
				//Titulo y urlManga
				String xPathTittle =  createXpathElement(i,1);
				String xPathTipo =    createXpathElement(i,2);
				String xPathRating =  createXpathElement(i,3);
				String xPathPortada = createXpathElement(i,4);
		        
				for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
				{
					try {
						WebElement eTitle = driver.findElement(By.xpath(xPathTittle));	
						WebElement eTipo = driver.findElement(By.xpath(xPathTipo));
						WebElement eRating = driver.findElement(By.xpath(xPathRating));
						WebElement ePortada = driver.findElement(By.xpath(xPathPortada));
						
						String title = eTitle.getText();
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
						StatusManga statusManga = new StatusManga(keyManga,"-",tipo,title,portada,
																	urlManga,rating,mas18,"-");
						statusManga.printStatusManga();
						//insertStatusManga(statusManga);    ---------------------
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
						umbralErrMax++;
						sleep(2);
						if(k==10)
							System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
					}
				}
				if(umbralErrMax>=30)
					break;
			}
			if(isEnableNextButtonCarrusel())
				goNextItemCarrusel();
			else
				break;
			sleep(8);
		}
		closeBiblioteca();
		apagarPC();
		
	}
	public void closeBiblioteca() {
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
