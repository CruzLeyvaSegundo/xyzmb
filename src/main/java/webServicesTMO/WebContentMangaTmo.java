package webServicesTMO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
	private List<StatusManga> listStatusMangaResponse;
	private int errorGetListStatusManga;
	public WebContentMangaTmo(){
		// Se instancia la ubicacion de geckodriver
		System.setProperty("webdriver.gecko.driver", "browsers//geckodriver.exe");
		this.driver =  new FirefoxDriver();
		retrofit= new Retrofit.Builder()
					.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		webServiceTmo = retrofit.create(WebServicesTmo.class);
		support = new SupportMethods();
		errorGetListStatusManga=0;
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
		String result="//*[@id=\"page-content\"]/div/div[2]/";
		switch(op)
		{
			case 1: //XpathTitle
				result += "div[1]/div/div/div[2]/h1";
				break;
			case 2: //XpathAutor
				result += " div[2]/div[2]/div[1]/div[1]/div[4]/p[3]/a[1]";
				break;
			case 3: //XpathDescripcion
				result += "div[2]/div[2]/div[1]/div[1]/div[4]/p[8]/span";
				break;
			case 4: //XpathDemografia 
				result += "div[2]/div[2]/div[1]/div[1]/div[4]/p[2]/span[3]";
				break;
			case 5: //XpathEstadoManga
				result += "div[2]/div[1]/div[1]/div/div[1]/div[1]/span";
				break;
			case 6: //XpathFechaPublicacion
				result += "div[2]/div[2]/div[1]/div[1]/div[4]/p[2]/span[1]";
				break;
			case 7: //XpathPeridiocidadManga
				result += "div[2]/div[2]/div[1]/div[1]/div[4]/p[2]/span[2]";
				break;
			case 8: //XpathUrlRakingManga;
				result += "div[2]/div[1]/div[1]/div/div[1]/div[2]/h4";
				break;		
		}
		return result;
	}
	public String createXpathElementGenero(int i) {
		String result="//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[1]/div[1]/div[4]/p[7]/a["+i+"]";
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
	public boolean goFinalCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div[26]/ul/li[10]/a";
		int error=0;
		while(true) //Falta validar que no siempre exisitara el carrusel
		{
			if(existsElement(xPathButton))
			{
				WebElement e= driver.findElement(By.xpath(xPathButton));
				e.click();
				return true;
			}
			else 
			{	
				error++;
				if(error==5)
				{	
					return false;
				}
				support.sleep(2);
			}
		}		
	}
	
	private void getListStatusManga() {
		errorGetListStatusManga=0;
		listStatusMangaResponse=null;
		Call<List<StatusManga> > statusMangaCallPost = webServiceTmo.getStatusMangas();
		statusMangaCallPost.enqueue(new Callback<List<StatusManga> >(){
			public void onFailure(Call<List<StatusManga> > call, Throwable t) {
				// TODO Auto-generated method stub
				errorGetListStatusManga=1;
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<List<StatusManga> > call, Response<List<StatusManga> > response) {
				listStatusMangaResponse=new ArrayList<StatusManga>();
				listStatusMangaResponse = response.body();
				
				System.out.println("CONSULT QUERY CORRECT!!!");	
			}
		});		
	}
	public void getContentsMangas() {
		int stop=0;
		//Se intenta 5 veces la comunicacion con la api si hubiera un error,
		for(int n=0;n<5 && stop==0 ;n++)//Para descartar problemas de red
		{
			getListStatusManga();
			int t=0;
			while(true) { //Bucle para depurar respuesta de la api
				if(errorGetListStatusManga==1)//Hubo un error con la api
				{
					System.out.println("ERROR AL CONSUMIR LA API");
					break;
				}
				else if(listStatusMangaResponse==null)//La respuesta no llega todavia pero no hay error con la api
				{			
					support.sleep(0.2);//Se espera 0.5 segundos
					System.out.println("ESPERANDO RESPUESTA...........   "+(t*0.2)+ "   SEGUNDOS");
					t++;
				}
				else //La respuesta fue recibida correctamente
				{
					boolean firstManga = true;
					for(StatusManga sm : listStatusMangaResponse) {
						String urlManga = sm.getUrlManga();
						driver.navigate().to(urlManga);
						ContentManga contentManga = new ContentManga();  
				        System.out.println("Empieza la recoleccion de generos");    
						int lastG = 30;    
						for(int i = 1;i<=30 ; i++) {	// 30 generos porsiacaso :u	

							String xPathGenero =   createXpathElementGenero(i);
							for(int k=1;k<=5;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
							{
								try {
										WebElement eGenero = driver.findElement(By.xpath(xPathGenero));

										String genero = support.validateText(eGenero.getText());		
										support.sleep(0.4);
										//System.out.println("\nNroCap: "+nroCap+"\nDescripcionCap: "+descripcionCap);	
										if(genero!="" && !genero.contains("genero"))
										{
											contentManga.addGenero(genero);
											System.out.println("\nGenero: "+genero);	
											//contentManga.printCapitulosManga();
											break;
										}
										else if(genero==""&&i>1)
										{
											lastG=i;
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
						//contentManga.printGenerosManga();
						if(contentManga.isExistBanGeneros()) {
							System.out.println("Te cayo el BANKAI (Ban Total)");
							//Actualizar revision de StatusManga a 'si' y status a 'banTotal'
						}
						else if(contentManga.isExistGeneroGenderBender()){
							System.out.println("Te caera el BANKAI, talvez (Ban parcial)");
							//Actualizar revision de StatusManga a 'si' y status a 'banParcial'
						}
						else // Todo salio bien
						{	
							//Dejar revision en 'no' y actualizar  status a 'ok'
							System.out.println("Todo bien joven continue");
							
					        System.out.println("Empieza la recoleccion de informacion primaria");
							int umbralErrMaxText = 0;
	
							//Estos elementos se obtienen de los objetos recuperados StatusManga
							String keyManga = sm.getKeyManga();
							String tipoManga = sm.getTipoManga();
							String urlPortada = sm.getUrlPortada();
							String ratingManga = sm.getRatingManga();
							String mas18 = sm.getEsMas18();		
							
							//Estos elementos se obtienen de la pagina web
							String XpathTitle =  createXpathElement(1);
							String XpathAutor =  createXpathElement(2);
							String XpathDescripcion =  createXpathElement(3);
							String XpathDemografia =  createXpathElement(4);
							String XpathEstadoManga =  createXpathElement(5);
							String XpathFechaPublicacion =  createXpathElement(6);
							String XpathPeridiocidadManga =  createXpathElement(7);
							String XpathRakingManga =  createXpathElement(8);
							for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
							{
								try {
									
										WebElement eTitle =  driver.findElement(By.xpath(XpathTitle));	
										WebElement eAutor =  driver.findElement(By.xpath(XpathAutor));	
										WebElement eDescripcion =  driver.findElement(By.xpath(XpathDescripcion));	
										WebElement eDemografia =  driver.findElement(By.xpath(XpathDemografia));	
										WebElement eEstadoManga =  driver.findElement(By.xpath(XpathEstadoManga));	
										WebElement eFechaPublicacion =  driver.findElement(By.xpath(XpathFechaPublicacion));	
										WebElement ePeridiocidadManga =  driver.findElement(By.xpath(XpathPeridiocidadManga));	
										WebElement eRakingManga =  driver.findElement(By.xpath(XpathRakingManga));	
					
										

										String title = support.validateText(eTitle.getText());	
										String autor = eAutor.getText();	
										String descripcion = support.validateText(eDescripcion.getText());	
										String demografia = eDemografia.getText() ;	
										String estadoManga = eEstadoManga.getAttribute("uib-tooltip");	
										String fechaPublicacion = eFechaPublicacion.getText();	
										String peridiocidadManga =ePeridiocidadManga.getText();	
										String rakingManga = eRakingManga.getText();					

										support.sleep(0.4);
					
										if(title!="" && autor!="" )
										{
											rakingManga = rakingManga.substring(10,rakingManga.length());
											contentManga.setMangaKey(keyManga);
											contentManga.setTipoManga(tipoManga);
											contentManga.setWebManga("tumangaonline");
											contentManga.setTituloManga(title);
											contentManga.setAutorManga(autor);
											contentManga.setDescripcionManga(descripcion);
											contentManga.addGenero(demografia);
											contentManga.setEstadoManga(estadoManga);
											contentManga.setFechaPublicacion(fechaPublicacion);
											contentManga.setPeridiocidad(peridiocidadManga);
											contentManga.setUrlManga(urlManga);
											contentManga.setUrlPortada(urlPortada);
											contentManga.setRakingManga(rakingManga);
											contentManga.setRatingManga(ratingManga);
											contentManga.setEsMas18(mas18);
											/*System.out.println("\nTitulo: "+ title+
																"\nAutor: "+ autor+
																"\nDescripcion: "+ descripcion+
																"\nDemografia: "+ demografia+
																"\nEstadoManga: "+ estadoManga+
																"\nFechaPublicacion: "+ fechaPublicacion+
																"\nPeridiocidadManga: "+ peridiocidadManga+
																"\nRakingManga: "+ rakingManga+
																"\nurlManga: "+ urlManga+"\n");
											contentManga.printGenerosManga();*/
											break;
										}
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
							boolean isExistCarrusel = goFinalCarrusel();
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
												
					
												String nroCap = support.validateText(eNroCap.getText());	
												nroCap = support.matchString("[0-9]+.[0-9][0-9]", nroCap);
												
												String descripcionCap = support.validateText(eDescripcionCap.getText());
					
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
								//contentManga.printCapitulosManga();
								contentManga.printContentManga();
								System.out.println("Fin ListCarrusel");
								if(isEnableNextButtonCarrusel(last) && isExistCarrusel)
									goNextItemCarrusel(last);
								else
									break;
								support.sleep(8);
							}
						}
						if(firstManga)
						{
							//Acepta la cokies de la web
							acceptCookies();
							firstManga = false;
						}
					}
					stop=1;
					break;
				}				
			}
		}
		//closeContentManga();
		//driver.get("https://www.tumangaonline.com/biblioteca/mangas/45/One-Piece");
		//driver.navigate().to("https://www.tumangaonline.com/biblioteca/mangas/45/One-Piece");
		 System.out.println("Acabo todoooooooooo");
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
