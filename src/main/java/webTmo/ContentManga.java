package webTmo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ContentManga {
	//Almacena todo el contenido de un manga
	private String autor;
	@SerializedName("capitulos")
	private List<Capitulo> caps;

	public ContentManga() {}

	public ContentManga(String autor, List<Capitulo> capitulos) {
		this.autor = autor;
		this.caps = capitulos;
	}

	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public List<Capitulo> getCapitulos() {
		return caps;
	}
	
	public void setCapitulos(List<Capitulo> capitulos) {
		this.caps = capitulos;
	}
	
	public static Capitulo parseJSON(String response) {
		Gson gson = new GsonBuilder().create();
		Capitulo cap = gson.fromJson(response, Capitulo.class);
		return cap;
	}
}
