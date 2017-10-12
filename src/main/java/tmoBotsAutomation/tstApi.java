package tmoBotsAutomation;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webServicesTMO.StatusMangaRequest;
import webServicesTMO.StatusMangaResponse;
import webServicesTMO.WebServicesTmo;
import webTmo.*;

public class tstApi {

	public static void main(String[] args) {
		
		//Test parsing json simple response
		String jsonText1 = "{ " +
							  "\"tipo\" : \"manga\", "+
							  " \"rating\": \"8.5\", "+
							  " \"status\": \"ok\", "+
							  " \"urlPortada\": \"xdxdxd\", "+
							  " \"urlManga\": \"https://www.tumangaonline.com/biblioteca/mangas/20575/Kami-no-unmei\", "+
							  " \"titulo\": \"Kami no unmei\", "+
							  " \"urlKeyManga\": \"2\""+
						    "}";
				
		//Test parsing json with list of object response
		String jsonText2 = "{" + 
				  "\"autor\": \"Tetsuya Imai\", "+ 
				  "\"tstField\": \"xdxdd\", "+ 
				  "\"capitulos\": "+
				  	"[" + 
				  		"{ " + 
				  			"\"descripcionCap\": \"Huye, Reina Roja\", "+ 
				  			"\"nroCap\": \"1.00\" "+ 
				  		"}, "+ 
				  		"{ "+ 
				  			"\"descripcionCap\": \"Los sueños de la habitación triangular\", "+ 
				  			"\"nroCap\": \"2.00\" "+ 
				  		"}"+ 
				  	"] "+
				"}";
		
		//									TESTING AREA
		
		// JsonText1 test
		Gson gson = new GsonBuilder().create();
		StatusManga tmoMangaStatus = gson.fromJson(jsonText1, StatusManga.class);
		System.out.println("tipo: " +tmoMangaStatus.getTipo() + "\ntitulo: " + tmoMangaStatus.getTitulo());
		
		// JsonText2 test
		ContentManga contentManga = gson.fromJson(jsonText2, ContentManga.class);
		System.out.println("\nautor: " +contentManga.getAutor() + "\ncapitulo( nro: " +
				contentManga.getCapitulos().get(0).getNroCap() +" , descripcion: "+
				contentManga.getCapitulos().get(0).getDescripcionCap()+ " )\n"+
							"capitulo( nro: " +contentManga.getCapitulos().get(1).getNroCap() +
							" , descripcion: "+contentManga.getCapitulos().get(1).getDescripcionCap()+ " )");
		
		//------------------------------------------------------------------------------------------------
		//							Consumiendo recursos de una api de aws
		//------------------------------------------------------------------------------------------------
		Retrofit retrofit= new Retrofit.Builder()
				.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		WebServicesTmo webServiceTmo = retrofit.create(WebServicesTmo.class);
		
		
		//Testing metodo GET con retrofit
		Call<StatusMangaResponse> statusMangaCallGET = webServiceTmo.getItemStatusManga("1");
		statusMangaCallGET.enqueue(new Callback<StatusMangaResponse>(){
			public void onFailure(Call<StatusMangaResponse> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<StatusMangaResponse> call, Response<StatusMangaResponse> response) {
				// TODO Auto-generated method stub
				List<StatusManga> statusMangaResponse = response.body().getItems();
				
				if(statusMangaResponse.size()==0)
					System.out.println("Lista vacia devuelto");
				for (StatusManga e : statusMangaResponse) {
					System.out.println("\nurlKeyManga: "+e.getUrlKeyManga() + "\ntipo: "+e.getTipo()+
							"\nTitulo: "+e.getTitulo()+"\nurlManga: "+e.getUrlManga()+"\nurlPortada: "+
							e.getUrlPortada()+"\nRating: "+e.getRating()+"\nRevision: "+e.getRevision()+
							"\nStatus: "+e.getStatus()+"\nmas18: "+e.getMas18());
				}
			}		
		});	
		System.out.println("FIN");
		
		//Testing metodo POST con retrofit
		StatusMangaRequest reqStatus = new StatusMangaRequest("tmoMangaStatus",new StatusManga(
				"4","no","manga","BEARĀ NO MICHI - ベアラーの道","http://xdxd.com","http://xdxd.com","7.9","no","emision"));
		Call<StatusMangaRequest> statusMangaCallPost = webServiceTmo.setStatusManga(reqStatus);
		statusMangaCallPost.enqueue(new Callback<StatusMangaRequest>(){
			public void onFailure(Call<StatusMangaRequest> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api");
			}
			public void onResponse(Call<StatusMangaRequest> call, Response<StatusMangaRequest> response) {
				// TODO Auto-generated method stub
					System.out.println("Testing completo");	
			}
		});	
	}

}
