package webServicesTMO;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import webTmo.ContentManga;
import webTmo.DataManga;
import webTmo.GeneroManga;
import webTmo.StatusManga;

public interface WebServicesTmo {
	
	//GET METHODS  -- Todos los metodos GET han sido testeados
	@GET("tmostatus")
	Call<List<StatusManga>> getStatusMangas();

	@GET("mangas")
	Call<List<ContentManga>> getContentsMangas();
	
	@GET("tmostatus/{urlKeyManga}")
	Call<StatusManga> getItemStatusManga(@Path("urlKeyManga") String urlKeyManga);
	
	@GET("mangas/{urlKeyManga}")
	Call<ContentManga> getItemContentManga(@Path("urlKeyManga") String urlKeyManga);
	
	@GET("tmostatus/pageStatus/{page}")
	Call<List<StatusManga>> getPageStatusManga(@Path("page") String page);
	
	//POST METHODS  ---- Todos los metodos POST han sido testeados
	@POST("tmostatus")
	Call<StatusManga> setStatusManga(@Body StatusManga request);
	
	@POST("mangas")
	Call<String> setContentManga(@Body ContentManga request);
	
	@POST("dataManga")
	Call<DataManga> setDataValue(@Body DataManga request);
	
	@POST("generoManga")
	Call<String> setGeneroManga(@Body GeneroManga request);
}
