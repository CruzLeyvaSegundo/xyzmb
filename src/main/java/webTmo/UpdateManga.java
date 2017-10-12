package webTmo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webServicesTMO.StatusMangaRequest;
import webServicesTMO.StatusMangaResponse;
import webServicesTMO.WebServicesTmo;

public class UpdateManga {
	
	private String keyManga;
	private String tipo;
	private String title;
	private String nroCap;
	private String urlManga;
	private String urlLector;
	private String mas18;
	private String urlPortada;
	private String status = "-";
	private String revision = "-";
	
	private Retrofit retrofit;
	private WebServicesTmo webServiceTmo;
	private List<StatusManga> statusMangaResponse;
	private int error;
	
	public UpdateManga(String keyManga, String tipo, String nroCap, String urlManga, String urlLector,
			String mas18) {
		this.keyManga = keyManga;
		this.tipo = tipo;
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
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
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
		if(statusMangaResponse!=null)
			statusMangaResponse.clear();
		Call<StatusMangaResponse> statusMangaCallPost = webServiceTmo.getItemStatusManga(keyManga);
		statusMangaCallPost.enqueue(new Callback<StatusMangaResponse>(){
			public void onFailure(Call<StatusMangaResponse> call, Throwable t) {
				// TODO Auto-generated method stub
				error=1;
				System.out.println("error api:"+t);
			}
			public void onResponse(Call<StatusMangaResponse> call, Response<StatusMangaResponse> response) {
				// TODO Auto-generated method stub
				statusMangaResponse = response.body().getItems();
				
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
			while(true) { //Bucle para depurar respuesta de la api
				if(error==1)//Hubo un error con la api
				{
					System.out.println("ERROR AL CONSUMIR LA API");
					break;
				}
				else if(statusMangaResponse==null)//La respuesta no llega todavia pero no hay error con la api
					sleep(0.2);//Se espera 0.5 segundos
				else //La respuesta fue recibida correctamente
				{
					if(statusMangaResponse.size()==0)//El manga no existe por lo tanto sera añadido
					{
						System.out.println("EL MANGA NO EXISTE, AGREGANDO A LA LISTA DE MANGAS....");
					}
					else //El manga existe y se debe ver si se actualizara la lista de capitulos publicados
					{
						System.out.println("\nTituloOriginal: "+statusMangaResponse.get(0).getTitulo());
						System.out.println("ACTUALIZANDO CAPITULOS....");
					}	
					stop=1;
					break;
				}				
			}
		}

	}
}
