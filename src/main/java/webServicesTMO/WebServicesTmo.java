package webServicesTMO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServicesTmo {
	
	//GET METHODS
	@GET("tmostatus")
	Call<StatusMangaResponse> getStatusMangas();
	
	@GET("tmostatus/{urlKeyManga}")
	Call<StatusMangaResponse> getItemStatusManga(@Path("urlKeyManga") String urlKeyManga);
	
	//POST METHODS
	@POST("tmostatus")
	Call<StatusMangaRequest> setStatusManga(@Body StatusMangaRequest request);
}
