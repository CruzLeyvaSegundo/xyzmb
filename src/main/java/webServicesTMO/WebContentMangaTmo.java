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
import webTmo.DataManga;
import webTmo.GeneroManga;
import webTmo.LectorCapitulo;
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
			case 9: //XpathTitleAux;
				result += "div[2]/div[2]/div[1]/div[1]/div[4]/p[1]/spam[1]";
				break;		
		}
		return result;
	}
	
	//Si el carrusel llego a su fin entonces class= "disabled" en el boton next '>'
	public boolean isEnableNextButtonCarrusel() {
		String xPathButton =  "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div[last()]/ul/li[2]";
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
	public boolean isMangaValidado() {
		String xPathValidar = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[1]";
		if(driver.findElement(By.xpath(xPathValidar)).getAttribute("ng-if").contains("1"))
			return true;
		else
			return false;
	}
	public boolean isMangaBloqueado() {
		String xPathValidar = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[1]/div/div[1]";
		if(driver.findElement(By.xpath(xPathValidar)).getAttribute("class").contains("page-section"))
			return true;
		else
			return false;
	}

	//Se mueven al siguiente item del carrusel
	public void goNextItemCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div[last()]/ul/li[2]/a";
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				WebElement e= driver.findElement(By.xpath(xPathButton));
				e.click();
				waitLoadCaps();
				break;
			}
			support.sleep(2);
		}
	}
	
	public void acceptCookies(){		
		driver.findElement(By.xpath("//*[@id=\"cookie1\"]/button")).click();
	}
	public boolean waitContentManga()
	{
		String xPathButton = "/html/body/div[3]/div/div[1]";
		int t=1;
		while(true)
		{
			
			if(existsElement(xPathButton))
			{
				if(!driver.findElement(By.xpath(xPathButton)).getAttribute("class").contains("ng-hide"))
				{
					support.sleep(0.25);
					System.out.println("WAITING TO LOADING CONTENT MANGA");
					t++;
					if(t>20)
					{
						System.out.println("Internet leeento");
						return false;
					}
				}
				else 
					return true;
			}
		}
	}
	public void waitLoadCaps()
	{
		String xPathButton = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[2]";
		int t=1;
		while(true)
		{
			if(!driver.findElement(By.xpath(xPathButton)).getAttribute("class").contains("ng-hide"))
			{
				support.sleep(0.25);
				System.out.println("WAITING TO LOADING CAPS");
				t++;
				if(t>50)
				{
					System.out.println("Internet leeento");
					break;
				}
			}
			else
				break;
		}
	}
	//Ordena la lista de mangas desde los recien publicados hasta a las entradas mas antiguas
	public boolean goFinalCarrusel() {
		String xPathButton = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div[last()]/ul/li[last()]";
		int error=0;
		while(true) 
		{
			if(existsElement(xPathButton))
			{
				WebElement eCarrusel = driver.findElement(By.xpath(xPathButton));
				if(!eCarrusel.getAttribute("class").contains("disabled"))
				{
					System.out.println("Existe Carrusel");
					eCarrusel.findElement(By.xpath("a")).click();
					waitLoadCaps();
					return true;
				}	
				System.out.println("No existe Carrusel");
				return false;
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
	private void setStatusManga(StatusManga st) {
		//Testing metodo POST con retrofit
		Call<StatusManga> statusMangaCallPost = webServiceTmo.setStatusManga(st);
		try {
			statusMangaCallPost.execute();
			System.out.println("Insert statusManga correct");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean setContentManga(ContentManga ct) {
		//Testing metodo POST con retrofit
		Call<String> contentMangaCallPost = webServiceTmo.setContentManga(ct);
		try {
			String status = (contentMangaCallPost.execute()).body();
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
	private void setGeneroManga(GeneroManga gm){
		//Testing metodo POST con retrofit
		Call<String> contentMangaCallPost = webServiceTmo.setGeneroManga(gm);
		try {
			String status = (contentMangaCallPost.execute()).body();
			//if(status.contains("200"))
				//return true;
			//else 
				//return false;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return false;	
		}
	}
	
	private void insertDataValue(DataManga reqStatus) {
		//Testing metodo POST con retrofit
		Call<DataManga> statusMangaCallPost = webServiceTmo.setDataValue(reqStatus);
		statusMangaCallPost.enqueue(new Callback<DataManga>(){
			public void onFailure(Call<DataManga> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<DataManga> call, Response<DataManga> response) {
				// TODO Auto-generated method stub
					System.out.println("INSERT CORRECT!!");	
			}
		});		
	}
	
	private List<StatusManga> getPageStatusManga(int page) {
		//Testing metodo POST con retrofit
		Call<List<StatusManga>> statusMangaCallGet = webServiceTmo.getPageStatusManga(String.valueOf(page));
		List<StatusManga> st =null;
		try {
			st = (statusMangaCallGet.execute()).body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;	
	}
	public void getContentsMangas() 
	{
		int page=40,pagesError=0,error1=0,insertCorrect=0,error2=0,mangasNoValidados=0,mangasBloquedado = 0,mangasValidados=0,mangasBanTotal=0,mangasBanParcial=0,mangasOk=0;
		boolean firstManga = true;
		List<StatusManga>  listStatusMangaResponse = new ArrayList<StatusManga>();
		while(true)
		{
			System.out.println("Page : "+page); 
			listStatusMangaResponse = getPageStatusManga(page);	
			page++;
			//System.out.println("Page : "+page);   
			if(listStatusMangaResponse.size() == 0)
				break;
			if(listStatusMangaResponse.size()<90)
				pagesError++;
			for(StatusManga sm : listStatusMangaResponse) 
			{
				if(sm.getRevisionManga().contains("no"))
				{
					String urlManga = sm.getUrlManga();
					//driver.navigate().to("https://www.tumangaonline.com/biblioteca/mangas/8717/Dungeon-ni-Deai-o-Motomeru-no-wa-Machigatte-Iru-Darou-ka-");
					driver.navigate().to(urlManga);
					
					if(firstManga)
					{
						//Acepta la cokies de la web
						acceptCookies();
						firstManga = false;
					}
					
					/*if(!waitContentManga()) //error al cargar contenido del manga
						continue;*/
					
					sm.setRevisionManga("yes");
					if(isMangaValidado())//mangaValidado
					{			
						ContentManga contentManga = new ContentManga();
						
						System.out.println("Empieza la recoleccion de generos");    
						String xPathGeneros = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[1]/div[1]/div[4]/p[7]/a";
						List<WebElement> eListGeneros = driver.findElements(By.xpath(xPathGeneros));
						System.out.println("Inicio ListCarrusel con #Elements: "+eListGeneros.size());
						int sizeG = eListGeneros.size() -1;
						if(eListGeneros.size()==1)
							sizeG = 1;
						for(int eg = 0 ; eg<sizeG;eg++) 
						{
							String genero = support.validateText(eListGeneros.get(eg).getText());	
							contentManga.addGenero(genero);
							setGeneroManga(new GeneroManga((support.validateText(genero.toLowerCase())),
										genero,"ok"));
								//System.out.println("Error Insert genero ");	
						}
						
						System.out.println("Validando generos");
						//contentManga.printGenerosManga();
						if(contentManga.isExistBanGeneros()) 
						{
							mangasBanTotal++;
							System.out.println("Te cayo el BANKAI (Ban Total)");
							sm.setStatusManga("banTotal");
							sm.setTextSearch("-");
							sm.setTituloAuxiliar("-");
							setStatusManga(sm);
							//Actualizar revision de StatusManga a 'si' y status a 'banTotal'
						}
						else if(contentManga.isExistGeneroGenderBender())
						{
							mangasBanParcial++;
							System.out.println("Te caera el BANKAI, talvez (Ban parcial)");
							sm.setStatusManga("banParcial");
							sm.setTextSearch("-");
							sm.setTituloAuxiliar("-");
							setStatusManga(sm);
							//Actualizar revision de StatusManga a 'si' y status a 'banParcial'
						}
						else // Todo salio bien
						{	
							mangasOk++;
							sm.setStatusManga("ok");
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
							String XpathTitleAux =  createXpathElement(9);
							
							for(int k=1;k<=10;k++)//Si el contenido solicitado no carga entonces lo vuelve a intentar hasta 'n' veces (n=10)
							{
								try {
											
									WebElement eTitle =  driver.findElement(By.xpath(XpathTitle));	
									WebElement eTitleAux =  driver.findElement(By.xpath(XpathTitleAux));	
									WebElement eAutor =  driver.findElement(By.xpath(XpathAutor));	
									WebElement eDescripcion =  driver.findElement(By.xpath(XpathDescripcion));	
									WebElement eDemografia =  driver.findElement(By.xpath(XpathDemografia));	
									WebElement eEstadoManga =  driver.findElement(By.xpath(XpathEstadoManga));	
									WebElement eFechaPublicacion =  driver.findElement(By.xpath(XpathFechaPublicacion));	
									WebElement ePeridiocidadManga =  driver.findElement(By.xpath(XpathPeridiocidadManga));	
									WebElement eRakingManga =  driver.findElement(By.xpath(XpathRakingManga));											
		
									String title = support.validateText(eTitle.getText());	
									String titleAux = support.validateText(eTitleAux.getText());	
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
										sm.setTituloAuxiliar(titleAux.toLowerCase());
										sm.setTextSearch(support.generateTextSearch(title, titleAux));
										rakingManga = rakingManga.substring(10,rakingManga.length());
										contentManga.setMangaKey(keyManga);
										contentManga.setTipoManga(tipoManga);
										contentManga.setWebManga("tumangaonline");
										contentManga.setTituloManga(title);
										contentManga.setTituloAuxiliar(titleAux);
										contentManga.setAutorManga(autor);
										contentManga.setDescripcionManga(descripcion);
										if(demografia.length()!=0)
										{
											contentManga.addGenero(demografia);
											//if(
											setGeneroManga(new GeneroManga((support.validateText(demografia.toLowerCase())),
													demografia,"ok"));
												//System.out.println("Error Insert genero ");	
										}
										else 
											demografia = "-";
										contentManga.setEstadoManga(estadoManga);
										contentManga.setFechaPublicacion(fechaPublicacion);
										contentManga.setPeridiocidad(peridiocidadManga);
										contentManga.setUrlManga(urlManga);
										contentManga.setUrlPortada(urlPortada);
										contentManga.setRakingManga(rakingManga);
										contentManga.setRatingManga(ratingManga);
										contentManga.setEsMas18(mas18);
										/*System.out.println("\nTitulo: "+ title+
															"\nTituloAuxiliar: "+ titleAux+
																		"\nAutor: "+ autor+
																		"\nDescripcion: "+ descripcion+
																		"\nDemografia: "+ demografia+
																		"\nEstadoManga: "+ estadoManga+
																		"\nFechaPublicacion: "+ fechaPublicacion+
																		"\nPeridiocidadManga: "+ peridiocidadManga+
																		"\nRakingManga: "+ rakingManga+
																		"\nurlManga: "+ urlManga+
																		"\ntextSearch: "+ sm.getTextSearch()+"\n");
										contentManga.printGenerosManga();*/
										break;
									}
									else
									{
										System.out.println("\n         REPITIENDO LECTURA...");
										support.sleep(2);	
									}
								}
								catch(NoSuchElementException e) 
								{
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
							System.out.println("Informacion manga obtenida");
							
							System.out.println("Empieza el bucle para recorrer el carrusel de capitulos");		
							//Nos posicionamos al final del carrusel							
							boolean isExistCarrusel = goFinalCarrusel();
							contentManga.setCaps(new ArrayList<Capitulo>());
							while(true)
							{
								//Code
								String xPathCarrusel = "//*[@id=\"page-content\"]/div/div[2]/div[2]/div[2]/div[3]/div[3]/div[4]/div";
								List<WebElement> eListCarrusel = driver.findElements(By.xpath(xPathCarrusel));
								System.out.println("Inicio ListCarrusel con #Elements: "+eListCarrusel.size());
								for(int ec = 0 ; ec<eListCarrusel.size()-1;ec++) 
								{				
									//Titulo y urlManga
									WebElement eNroCap = eListCarrusel.get(ec).findElement(By.xpath("div[1]/h3"));
									WebElement eDescripcionCap = eListCarrusel.get(ec).findElement(By.xpath("div[1]/h3/small"));	
									
									String nroCap = support.validateText(eNroCap.getText());	
									nroCap = support.matchString("[0-9]+.[0-9][0-9]", nroCap);
									String descripcionCap = support.validateText(eDescripcionCap.getText());
									Double orderCap = Double.parseDouble(nroCap);
									//System.out.println("\nNroCap: "+orderCap+"\nDescripcionCap: "+descripcionCap.length());	
									//List urlLector
									List<LectorCapitulo> lectorC = new ArrayList<LectorCapitulo>();
									List<WebElement> eUrlLector = eListCarrusel.get(ec).findElements(By.xpath("div[2]/div/div/table/tbody/tr"));
									for(WebElement ul : eUrlLector) 
									{
										String urlLector = ul.findElement(By.xpath("td[last()]/a")).getAttribute("href");
										String scanlation = ul.findElement(By.xpath("td[1]/div[1]/a")).getAttribute("innerText");
										//String urlScanlation = ul.findElement(By.xpath("td[1]/div[1]/a")).getAttribute("href");
										lectorC.add(new LectorCapitulo(scanlation,urlLector));
										/*System.out.println("Scanlation: "+scanlation+
															"\nurlLectorManga: " + urlLector);*/
									}
										
									contentManga.addCapitulo(new Capitulo(orderCap,nroCap,descripcionCap,lectorC,"no"));
									//System.out.println("\nNroCap: "+orderCap+"\nDescripcionCap: "+descripcionCap);	
								}		
								//contentManga.printCapitulosManga();		
								System.out.println("Fin ListCarrusel");
								if(isEnableNextButtonCarrusel() && isExistCarrusel)
									goNextItemCarrusel();
								else
									break;
							}
							//contentManga.printCapitulosManga();
							String xpathNroCaps = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div[3]/div[1]/div/h1/small";
							String nroCapsStr =  driver.findElement(By.xpath(xpathNroCaps)).getText();
							int nroCaps = Integer.valueOf(support.matchString("[0-9]+",nroCapsStr));
							contentManga.setNroCaps(nroCaps);
							if(isMangaBloqueado())
							{
								mangasBloquedado++;
								sm.setStatusManga("okBloqueado");
								//System.out.println("Manga bloquedao");
								System.out.println("Manga bloqueado  , nroCaps: "+nroCaps+"x");
								if(nroCaps!=0) 
								{
									System.out.println("Restaurar manga");
									contentManga.restaurarCapsManga();
								}
								//contentManga.restaurarCapsManga();
								//contentManga.printCapitulosManga();
							}			
							contentManga.printContentManga();
							if(contentManga.validarContentManga())
								if(setContentManga(contentManga))
								{
									insertCorrect++;
									setStatusManga(sm);
								}
								else
									error1++;
							else
								error2++;
						}
						System.out.println("Current page: 	"+sm.getPage());
						//setStatusManga(sm);
						//Gson gson = new GsonBuilder().create();
						//System.out.println(gson.toJson(contentManga));	
					}		
					else 
					{
						mangasNoValidados++;
						sm.setTextSearch("-");
						sm.setTituloAuxiliar("-");
						sm.setStatusManga("noValidado");
						sm.printStatusManga();
						System.out.println("Manga no validado");
						setStatusManga(sm);
					}
				}
			}	  
		}
		insertDataValue(new DataManga("ErroresDesconocidos",String.valueOf(error1)));
		support.sleep(4);
		insertDataValue(new DataManga("erroresValidarDatos",String.valueOf(error2)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasValidados",String.valueOf(mangasValidados)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasNoValidados",String.valueOf(mangasNoValidados)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasBloqueados",String.valueOf(mangasBloquedado)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasOK",String.valueOf(mangasOk)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasBanTotal",String.valueOf(mangasBanTotal)));
		support.sleep(4);
		insertDataValue(new DataManga("MangasBanParcial",String.valueOf(mangasBanParcial)));
		support.sleep(4);
		insertDataValue(new DataManga("insertCorrect",String.valueOf(insertCorrect)));
		support.sleep(4);
		insertDataValue(new DataManga("pagesError",String.valueOf(pagesError)));
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
