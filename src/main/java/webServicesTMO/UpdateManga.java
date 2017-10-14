package webServicesTMO;

//import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webTmo.StatusManga;

public class UpdateManga {
	
	private String keyManga;
	private String tipoManga;
	private String title;
	private String nroCap;
	private String urlManga;
	private String urlLector;
	private String mas18;
	private String urlPortada;
	private String statusManga = "-";
	private String revisionManga = "-";
	
	private Retrofit retrofit;
	private WebServicesTmo webServiceTmo;
	private StatusManga statusMangaResponse;
	private int error;
	
	public UpdateManga(String keyManga, String tipo, String nroCap, String urlManga, String urlLector,
			String mas18) {
		this.keyManga = keyManga;
		this.tipoManga = tipo;
		this.nroCap = nroCap;
		this.urlManga = urlManga;
		this.urlLector = urlLector;
		this.mas18 = mas18;
		retrofit= new Retrofit.Builder()
				.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		webServiceTmo = retrofit.create(WebServicesTmo.class);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrlManga() {
		return urlManga;
	}
	
	public void setUrlManga(String urlManga) {
		this.urlManga = urlManga;
	}
	
	public String getUrlLector() {
		return urlLector;
	}
	
	public void setUrlLector(String urlLector) {
		this.urlLector = urlLector;
	}
	
	public String getKeyManga() {
		return keyManga;
	}
	
	public void setKeyManga(String keyManga) {
		this.keyManga = keyManga;
	}
	
	public String getNroCap() {
		return nroCap;
	}
	
	public void setNroCap(String nroCap) {
		this.nroCap = nroCap;
	}
	
	public String getTipoManga() {
		return tipoManga;
	}
	
	public void setTipoManga(String tipo) {
		this.tipoManga = tipo;
	}
	
	public String getMas18() {
		return mas18;
	}
	
	public void setMas18(String mas18) {
		this.mas18 = mas18;
	}

	public String getUrlPortada() {
		return urlPortada;
	}

	public void setUrlPortada(String urlPortada) {
		this.urlPortada = urlPortada;
	}

	public String getStatusManga() {
		return statusManga;
	}

	public void setStatusManga(String status) {
		this.statusManga = status;
	}

	public String getRevisionManga() {
		return revisionManga;
	}

	public void setRevisionManga(String revision) {
		this.revisionManga = revision;
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
	
	private void getStatusManga(String keyManga) {
		//Testing metodo POST con retrofit
		error=0;
		statusMangaResponse=null;
		Call<StatusManga> statusMangaCallPost = webServiceTmo.getItemStatusManga(keyManga);
		statusMangaCallPost.enqueue(new Callback<StatusManga>(){
			public void onFailure(Call<StatusManga> call, Throwable t) {
				// TODO Auto-generated method stub
				error=1;
				System.out.println("error api:"+t);
			}
			public void onResponse(Call<StatusManga> call, Response<StatusManga> response) {
				// TODO Auto-generated method stub
				statusMangaResponse=new StatusManga();
				statusMangaResponse = response.body();
				
				System.out.println("CONSULT QUERY CORRECT!!!");	
			}
		});		
	}
	
	public void checkIsExistManga() {
		int stop=0;
		//Se intenta 5 veces la comunicacion con la api si hubiera un error,
		for(int i=0;i<5 && stop==0 ;i++)//Para descartar problemas de red
		{
			getStatusManga(this.keyManga);
			int t=0;
			while(true) { //Bucle para depurar respuesta de la api
				if(error==1)//Hubo un error con la api
				{
					System.out.println("ERROR AL CONSUMIR LA API");
					break;
				}
				else if(statusMangaResponse==null)//La respuesta no llega todavia pero no hay error con la api
				{			
					sleep(0.2);//Se espera 0.5 segundos
					System.out.println("ESPERANDO...........   "+(t*0.2)+ "   SEGUNDOS");
					t++;
				}
				else //La respuesta fue recibida correctamente
				{
					if(statusMangaResponse.isNull())//El manga no existe por lo tanto sera añadido ********
					{
						//Codigo que añade el manga mediante un post.....
						System.out.println("EL MANGA NO EXISTE, AGREGANDO A LA LISTA DE MANGAS....");
					}
					else //El manga existe y se debe ver si se actualizara la lista de capitulos publicados
					{
						//Para actualizar capitulos de un manga crear un metodo en ContentManga
						//Previo se verifica es status del manga si es "ok" o "ban"
						System.out.println("\nTituloOriginal: "+statusMangaResponse.getTituloManga());
						System.out.println("ACTUALIZANDO CAPITULOS....");
					}	
					stop=1;
					break;
				}				
			}
		}

	}
}
