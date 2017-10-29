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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import supportClass.SupportMethods;
import webTmo.DataManga;
import webTmo.StatusManga;


public class WebHomeTmo {
	
	private WebDriver driver;
	private SupportMethods support;
	private Retrofit retrofit;
	private WebServicesTmo webServiceTmo;

	
	public WebHomeTmo() {
		// Se instancia la ubicacion de geckodriver
		System.setProperty("webdriver.gecko.driver", "browsers//geckodriver.exe");
		this.driver =  new FirefoxDriver();
		support = new SupportMethods();
		retrofit= new Retrofit.Builder()
				.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		webServiceTmo = retrofit.create(WebServicesTmo.class);
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
				break;
			case 5: //XpathScanlation
				result += "/div/div[2]/div[1]/p/a/small";
				break;
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
			support.sleep(2);
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
			support.sleep(2);
		}
	}
	public void acceptCookies(){
		driver.findElement(By.xpath("//*[@id=\"cookie1\"]/button")).click();
		support.sleep(2);
	}

	private StatusManga getStatusMangaItem(String keyManga) {
		//Testing metodo POST con retrofit
		Call<StatusManga> statusMangaCallPost = webServiceTmo.getItemStatusManga(keyManga);
		StatusManga st =null;
		try {
			st = (statusMangaCallPost.execute()).body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!st.isNull())
			return st;
		else
			return null;
	}
	
	private DataManga getDataMangaItem(String varData) {
		//Testing metodo POST con retrofit
		Call<DataManga> dataMangaCallGET = webServiceTmo.getDataMangaItem(varData);
		DataManga dm =null;
		try {
			dm = (dataMangaCallGET.execute()).body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!dm.isNull())
			return dm;
		else
			return null;
	}
	private boolean insertUpdateManga(UpdateManga reqStatus) {
		//Testing metodo POST con retrofit
		Call<String> statusMangaCallPost = webServiceTmo.setUpdateManga(reqStatus);
		try {
			String status = (statusMangaCallPost.execute()).body();
			if(status.contains("200"))
				return true;
			else 
				return false;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;	
		}	

	}
	private boolean insertStatusManga(StatusManga reqStatus) {
		//Testing metodo POST con retrofit
		Call<String> statusMangaCallPost = webServiceTmo.setStatusManga(reqStatus);
		try {
			String status = (statusMangaCallPost.execute()).body();
			if(status.contains("200"))
				return true;
			else 
				return false;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;	
		}	
	}
	private void insertDataValue(DataManga reqStatus) {
		//Testing metodo POST con retrofit
		Call<String> statusMangaCallPost = webServiceTmo.setDataValue(reqStatus);
		statusMangaCallPost.enqueue(new Callback<String>(){
			public void onFailure(Call<String> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("Error insertar Data");
			}
			public void onResponse(Call<String> call, Response<String> response) {
				// TODO Auto-generated method stub
					System.out.println("INSERT CORRECT!!");	
			}
		});		
	}
	
	public void getMangasHomeUpdate() {

		////Acepta la cokies de la web
        acceptCookies();   
        DataManga dPageUpdate = getDataMangaItem("nroPagesUpdate");
        DataManga dItemsLastPageUpdate = getDataMangaItem("nroItemsLastPageUpdate");
        DataManga dWardUpdateHome = getDataMangaItem("wardUpdateHome");
        int page = dPageUpdate.getIntegerValue(), nroMangasUpdate = dItemsLastPageUpdate.getIntegerValue(),error=0,first=0;
        boolean end=false;
        String wardUpdateHome = dWardUpdateHome.getValueManga();
		while(true){
			int umbralErrMax = 0;
			for(int i = 1;i<=50 && !end; i++) {		
				//Titulo y urlManga
				String xPathTittle =  createXpathElement(i,1);
				String xPathNroCap =  createXpathElement(i,2);
				String xPathTipo =  createXpathElement(i,3);
				String xPathUrlLector = createXpathElement(i,4);
				String xPathScanlation = createXpathElement(i,5);
				for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
				{
					try {
						WebElement eTitle = driver.findElement(By.xpath(xPathTittle));	
						WebElement eNroCap = driver.findElement(By.xpath(xPathNroCap));
						WebElement eTipo = driver.findElement(By.xpath(xPathTipo));
						WebElement eScanlation = driver.findElement(By.xpath(xPathScanlation));;
						WebElement eUrlLector = driver.findElement(By.xpath(xPathUrlLector));
						
						String title = support.validateText(eTitle.getText());
						String urlManga = eTitle.getAttribute("href");
						String urlLector = eUrlLector.getAttribute("href");
						String keyMangaFalso=support.matchString("_[0-9]*_.*", urlManga.substring(41,urlManga.length()).replace('/', '_'));
						String nroCap = eNroCap.getText();
						nroCap=nroCap.substring(5, nroCap.length());
						String tipo = eTipo.getText();
						String keyMangaReal = (tipo+"s"+keyMangaFalso).toLowerCase();
						String scanlation = eScanlation.getText();
						urlManga = urlManga.substring(0,41)+tipo.toLowerCase()+"s"+keyMangaFalso.replace('_', '/'); // 100% real no feik
						String keyUpdate=support.validateText((tipo+"s"+support.matchString("_[0-9]*_",keyMangaFalso)
						+nroCap.replace('.', '-')+"_"+scanlation.replace(" ","")).toLowerCase());
						//sleep(0.4);
						String mas18 = "no";
						if(isMas18(title)) //Verifica si el contenido es +18
						{
							mas18 = "si";
							title = title.substring(0,title.length()-3);
						}		
						UpdateManga updateManga = new UpdateManga(keyUpdate,nroCap,scanlation,
								keyMangaReal,tipo,urlManga,urlLector,"no",String.valueOf(page));
						//insertStatusManga(statusManga);    ---------------------
						//updateManga.checkIsExistManga();				
						if(keyMangaReal.contains("%7B%7Bupload.capitulo.tomo") ||
							tipo.contains("upload.capitulo.tomo")||
							nroCap.contains("{upload.capitulo.numCapitulo}}") ||
							urlManga.contains("%7B%7Bupload.capitulo.tomo")||
							urlLector.contains("%7B%7Bupload.capitulo"))
							support.sleep(2); //Valida error de un manga cargado a medias
						else
						{
							if(keyMangaFalso.compareTo("__")!=0)
							{
								if(wardUpdateHome.compareToIgnoreCase(keyUpdate)==0)
								{
									end=true;
									break;
								}
								if(first==0)
								{
									insertDataValue(new DataManga("wardUpdateHome",keyUpdate));
									first=1;
								}
								StatusManga st = getStatusMangaItem(keyMangaReal);
								if(st==null) //Agregar a StatusManga
								{
									//Get nroPages
									DataManga dPageStatus = getDataMangaItem("nroPagesStatus");
									//Get nroItemsLastPage
									DataManga dItemsLastPageStatus = getDataMangaItem("nroItemsLastPageStatus");
									if(dItemsLastPageStatus.getIntegerValue()==100)
									{
										dPageStatus.masUno();
										dItemsLastPageStatus.setValueManga("0");					
									}
									st = new StatusManga(keyMangaReal,"no",tipo,title,"-","-",
											urlManga,"-",mas18,"nuevo",dPageStatus.getValueManga(),"-");
									System.out.println("Nuevo MANGA para agregar ");
									
									if(insertStatusManga(st))
									{
										dItemsLastPageStatus.masUno();
										insertDataValue(dItemsLastPageStatus);
										if(dItemsLastPageStatus.getIntegerValue()==1)
											insertDataValue(dPageStatus);
									}
									st.printStatusManga();
								}
								else if(st.getRevisionManga().contains("no") && st.getStatusManga().contains("-"))
									System.out.println("\nEste manga aun no ha sido revisado!!!.  KeyManga: "+ st.getTituloManga());
								else if(!st.getRevisionManga().contains("no") && st.getStatusManga().contains("ok"))// Quedar pendiente actualizar los mangas
								{
									System.out.println("\ntituloReal: "+ st.getTituloManga());
									System.out.println("\nkeyUpdate: "+updateManga.getUpdateKey() + 
										"\nnkeyManga: "+updateManga.getKeyManga() + 
										"\ntipo: "+updateManga.getTipoManga() + 
										"\ntituloBamba: "+title+ 
										"\nnroCap: "+updateManga.getNroCap() + 
										"\nurlManga: "+updateManga.getUrlManga() +		
										"\nurlLector: "+updateManga.getUrlLector()+
										"\nScanlation: "+updateManga.getScanlation() + 
										"\nmas18: "+mas18+
										"\npage: "+page+"\n");
									if(insertUpdateManga(updateManga))
									{
										nroMangasUpdate++;
										System.out.println("\n nroMangasUpdate: "+ nroMangasUpdate);
										if(nroMangasUpdate==100)
										{
											page++;
											nroMangasUpdate=0;
										}
									}
									else
									{
										error++;
										insertDataValue(new DataManga("error",String.valueOf(error)));
									}
									
								}
								else
								{
									System.out.println("Este manga ha sido baneado o aun no esta validado. KeyManga: "+ keyMangaReal);
								}
								
							}	
							else {
								System.out.println("MANGA NULLL ");
							}
							break;
						}
					}
					catch(NoSuchElementException e) {
						System.out.println("\n                  REPITIENDO LECTURA...");
						System.out.println("error: "+ e);
						umbralErrMax++;
						support.sleep(2);
						if(k==10)
							System.out.println("Revisa la pagina tumangaonline.com por posibles problemas externos");
					}
				}
				if(umbralErrMax>=30)
					break;
			}
			if(isEnableNextButtonCarrusel() && !end)
				goNextItemCarrusel();
			else
				break;
			support.sleep(8);
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
