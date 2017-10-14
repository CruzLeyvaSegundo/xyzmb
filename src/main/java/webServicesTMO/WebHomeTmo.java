package webServicesTMO;

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


public class WebHomeTmo {
	
	private WebDriver driver;
	
	public WebHomeTmo() {
		// Se instancia la ubicacion de geckodriver
		System.setProperty("webdriver.gecko.driver", "browsers//geckodriver.exe");
		this.driver =  new FirefoxDriver();
		driver.get("https://www.tumangaonline.com/home");
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
		String result="//*[@id=\"page-content\"]/div/div[2]/div[1]/div[5]/div[2]/div["+i+"]/div";
        if(i%2==0)// i es par
        	result+="[1]";	   
        
		switch(op)
		{
			case 1: //XpathTittle
				result += "/div/div[1]/h4/a";
				break;
			case 2: //NroCap
				result += "/div/div[2]/div[3]/a/h3";
				break;
			case 3: //XpathTipo
				result += "/div/div[3]/div";
				break;
			case 4: //XpathUrlLector
				result += "/div/div[2]/div[3]/a";
		}
		return result;
	}
	
	//Si el carrusel llego a su fin entonces class= "disabled" en el boton next '>'
	public boolean isEnableNextButtonCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[1]/div[5]/div[5]/ul/li[13]";
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
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[1]/div[5]/div[5]/ul/li[13]/a";
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
		sleep(2);
	}
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
	public void getMangasHomeUpdate() {

		////Acepta la cokies de la web
        acceptCookies();
                
		while(true){
			int umbralErrMax = 0;
			for(int i = 1;i<=50 ; i++) {		
				//Titulo y urlManga
				String xPathTittle =  createXpathElement(i,1);
				String xPathNroCap =  createXpathElement(i,2);
				String xPathTipo =  createXpathElement(i,3);
				String xPathUrlLector = createXpathElement(i,4);

				for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
				{
					try {
						WebElement eTitle = driver.findElement(By.xpath(xPathTittle));	
						WebElement eNroCap = driver.findElement(By.xpath(xPathNroCap));
						WebElement eTipo = driver.findElement(By.xpath(xPathTipo));
						WebElement eUrlLector = driver.findElement(By.xpath(xPathUrlLector));
						
						String title = validateText(eTitle.getText());
						String urlManga = eTitle.getAttribute("href");
						String urlLector = eUrlLector.getAttribute("href");
						String keyManga=(urlManga.substring(41,urlManga.length())).replace('/', '_');
						String nroCap = eNroCap.getText();
						nroCap=nroCap.substring(5, nroCap.length());
						String tipo = eTipo.getText();
						String mas18 = "no";
						//String status = "-";
						//String revision = "-";
						if(isMas18(title)) //Verifica si el contenido es +18
						{
							mas18 = "si";
							title = title.substring(0,title.length()-3);
						}				
						//sleep(0.4);
						UpdateManga updateManga = new UpdateManga(keyManga,tipo,nroCap,
														urlManga,urlLector,mas18);
						System.out.println("\nkeyManga: "+updateManga.getKeyManga() + 
											"\ntipo: "+updateManga.getTipoManga() + 
											"\ntituloBamba: "+title+ 
											"\nnroCap: "+updateManga.getNroCap() + 
											"\nurlManga: "+updateManga.getUrlManga() +		
											"\nurlLector: "+updateManga.getUrlLector()+	
											"\nmas18: "+updateManga.getMas18() + "\n");
						//insertStatusManga(statusManga);    ---------------------
						updateManga.checkIsExistManga();
						if(keyManga.compareTo("mangas_%7B%7Bupload.capitulo.tomo.manga.id%7D%7D_%7B%7Bupload.capitulo.tomo.manga.nombreUrl%7D%7D")==0 ||
							tipo.compareTo("{{upload.capitulo.tomo.manga.tipo}}")==0 ||
							nroCap.compareTo("{upload.capitulo.numCapitulo}}")==0 ||
							urlManga.compareTo("https://www.tumangaonline.com/biblioteca/mangas/%7B%7Bupload.capitulo.tomo.manga.id%7D%7D/%7B%7Bupload.capitulo.tomo.manga.nombreUrl%7D%7D")==0 ||
							urlLector.compareTo("https://www.tumangaonline.com/lector/%7B%7Bupload.capitulo.tomo.manga.nombreUrl%7D%7D/%7B%7Bupload.capitulo.tomo.manga.id%7D%7D/%7B%7Bupload.capitulo.numCapitulo%7D%7D/%7B%7Bupload.scanlation.id%7D%7D")==0)
							sleep(2); //Valida error de un manga cargado a medias
						else
							break;
					}
					catch(NoSuchElementException e) {
						System.out.println("\n                  REPITIENDO LECTURA...");
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
		//closeHomeUpdate();
		//apagarPC();
		
	}
	public void closeHomeUpdate() {
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
