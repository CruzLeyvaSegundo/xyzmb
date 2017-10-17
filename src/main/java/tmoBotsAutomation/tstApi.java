package tmoBotsAutomation;

import java.lang.reflect.Type;
//import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webServicesTMO.WebServicesTmo;
import webTmo.*;

public class tstApi {

	public static void main(String[] args) {
		
		//Test parsing json simple response
		String jsonText1 = "[{ " +
							  " \"tipoManga\" : \"manga\", "+
							  " \"ratingManga\": \"8.5\", "+
							  " \"statusManga\": \"ok\", "+
							  " \"urlPortada\": \"xdxdxd\", "+
							  " \"urlManga\": \"https://www.tumangaonline.com/biblioteca/mangas/20575/Kami-no-unmei\", "+
							  " \"tituloManga\": \"Kami no unmei\", "+
							  " \"keyManga\": \"2\", "+
							  " \"revisionManga\": \"-\","+
							  " \"esMas18\": \"no\""+
						    "},"+
						    "{"+
							  " \"tipoManga\" : \"manga\", "+
							  " \"ratingManga\": \"8.5\", "+
							  " \"statusManga\": \"ok\", "+
							  " \"urlPortada\": \"xdxdxd\", "+
							  " \"urlManga\": \"https://www.tumangaonline.com/biblioteca/mangas/20575/Kami-no-unmei\", "+
							  " \"tituloManga\": \"pacmon\", "+
							  " \"keyManga\": \"1\", "+
							  " \"revisionManga\": \"-\","+
							  " \"esMas18\": \"no\""+
						    "}]";
				
		//Test parsing json with list of object response
		/*String jsonText2 = "{" + 
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
		*/
		//									TESTING AREA
		

		///argumento 'Type' en lugar de 'class' para fromJson al trabajar con lista de objetos
		Type listStatusManga = new TypeToken<List<StatusManga>>(){}.getType();
		
		Gson gson = new GsonBuilder().create();
		List<StatusManga> tmoMangaStatus = gson.fromJson(jsonText1,listStatusManga);
		for (StatusManga e : tmoMangaStatus) {
			//System.out.println(((LinkedTreeMap<String,String>)e).get("tipoManga"));
			e.printStatusManga();
		}
		//System.out.println("tipo: " +tmoMangaStatus.getTipoManga() + "\ntitulo: " + tmoMangaStatus.getTituloManga());
		
		// JsonText2 test
		/*ContentManga contentManga = gson.fromJson(jsonText2, ContentManga.class);
		System.out.println("\nautor: " +contentManga.getAutor() + "\ncapitulo( nro: " +
				contentManga.getCapitulos().get(0).getNroCap() +" , descripcion: "+
				contentManga.getCapitulos().get(0).getDescripcionCap()+ " )\n"+
							"capitulo( nro: " +contentManga.getCapitulos().get(1).getNroCap() +
							" , descripcion: "+contentManga.getCapitulos().get(1).getDescripcionCap()+ " )");*/
		System.out.println("\n					FIN TESTING MANUAL-------------- EMPIEZA TESTING API\n");
		//------------------------------------------------------------------------------------------------
		//							Consumiendo recursos de una api de aws
		//------------------------------------------------------------------------------------------------
		
		Retrofit retrofit= new Retrofit.Builder()
				.baseUrl("https://c24tvlmm7k.execute-api.us-east-1.amazonaws.com/dev/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		WebServicesTmo webServiceTmo = retrofit.create(WebServicesTmo.class);
		
		
		//Testing metodo GET con retrofit
		//Call<StatusManga> statusMangaCallGET = webServiceTmo.getItemStatusManga("manhwas_31155_THE-STRANGE-STORY-OF-A-GU");
		/*Call<List<StatusManga>> statusMangaCallGET = webServiceTmo.getStatusMangas();
		statusMangaCallGET.enqueue(new Callback<List<StatusManga> >(){
			public void onFailure(Call<List<StatusManga> > call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api: "+ t);
			}
			public void onResponse(Call<List<StatusManga> > call, Response<List<StatusManga> > response) {

				List<StatusManga> mangaResponse = response.body(); 
					//mangaResponse.printStatusManga();
				/*if(statusMangaResponse.size()==0)
					System.out.println("Respuesta vacia devuelta: OBJETO NO ENCONTRADO");
				for (StatusManga e : mangaResponse) {
					e.printStatusManga();
				}
				System.out.println("Objetos devuelto: "+mangaResponse.size());
			}		
		});	*/
		//ContentManga reqStatus = new ContentManga();
		//System.out.println("Texto validado: "+reqStatus.validarTexto("ass\"s"));
		
		System.out.println("POST REQUEST" );
		//Testing metodo POST con retrofit
		//List<String> li = new ArrayList<String>();
		//li.add("terror");li.add("suspenso");
		//List<Capitulo> lc = new ArrayList<Capitulo>();
		//lc.add(new Capitulo(1,"1.00","lolxD"));lc.add(new Capitulo(2,"2.00","pacmon"));
		/*ContentManga reqStatus = new ContentManga(
				"5","tumangaonline","manga","BEARĀ NO MICHI - ベアラーの道","El papu","holi",li,lc,"emision",
				"12-08-2015","mensual","http://xdxd.com","http://xdxd.com","79","8.5","no");
		System.out.println(gson.toJson(reqStatus));	*/
		StatusManga status = new StatusManga("1","no","manga","one piece","xd0","xd","8.5","no","ok","1");;
		Call<StatusManga> statusMangaCallPost = webServiceTmo.setStatusManga(status);
		statusMangaCallPost.enqueue(new Callback<StatusManga>(){
			public void onFailure(Call<StatusManga> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("error al consumir la api" + t );
			}
			public void onResponse(Call<StatusManga> call, Response<StatusManga> response) {
				// TODO Auto-generated method stub
					System.out.println("Testing completo");	
			}
		});	
		System.out.println("FINAL POST REQUEST" );
	}

}
