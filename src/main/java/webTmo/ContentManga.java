package webTmo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import supportClass.SupportMethods;
//import com.google.gson.annotations.SerializedName;

public class ContentManga {

	//Almacena todo el contenido de un manga
	//@SerializedName("keyManga")
	private String keyManga;
	private String webManga;
	private String tipoManga;
	private String tituloManga;//Dentro del titulo del manga no debe haber el caracter especial " sin s respectivo \ o sino acontecera error	
	private String autorManga;
	private String descripcionManga;//Dentro de la descripcion del manga no debe haber el caracter especial " sin s respectivo \ o sino acontecera error														
	private List<String> generos;     // Ejm descripcion:  "Hola mundo lool el : \"texto\" jajajaja"
	//@SerializedName("capitulosManga")
	private List<Capitulo> capitulosManga;
	private String estadoManga;
	private String fechaPublicacion;
	private String peridiocidadManga;
	private String urlManga;
	private String urlPortada;
	private String esMas18;
	private String rakingManga;//Puesto de popularidad del manga con respecto a los demas
	private String ratingManga;//Voloracion en estrellas con puntaje del 1-10
	
	public ContentManga() {}
	
	public ContentManga(String keyManga, String webManga, String tipoManga, String tituloManga, String autor,
			String descripcion, List<String> generos, List<Capitulo> caps, String estado, String fechaPublicacion,
			String peridiocidad, String urlManga, String urlPortada, String rakingManga, String ratingManga,
			String esMas18) {
		this.keyManga = keyManga;
		this.webManga = webManga;
		this.tipoManga = tipoManga;
		this.tituloManga = tituloManga;
		this.autorManga = autor;
		this.descripcionManga = descripcion;
		this.generos = generos;
		this.capitulosManga = caps;
		this.estadoManga = estado;
		this.fechaPublicacion = fechaPublicacion;
		this.peridiocidadManga = peridiocidad;
		this.urlManga = urlManga;
		this.urlPortada = urlPortada;
		this.rakingManga = rakingManga;
		this.ratingManga = ratingManga;
		this.esMas18 = esMas18;
	}

	public String getMangaKey() {
		return keyManga;
	}

	public void setMangaKey(String mangaKey) {
		this.keyManga = mangaKey;
	}

	public String getWebManga() {
		return webManga;
	}

	public void setWebManga(String webManga) {
		this.webManga = webManga;
	}

	public String getTipoManga() {
		return tipoManga;
	}

	public void setTipoManga(String tipoManga) {
		this.tipoManga = tipoManga;
	}

	public String getTituloManga() {
		return tituloManga;
	}

	public void setTituloManga(String tituloManga) {
		this.tituloManga = tituloManga;
	}

	public String getAutorManga() {
		return autorManga;
	}

	public void setAutorManga(String autor) {
		this.autorManga = autor;
	}

	public String getDescripcionManga() {
		return descripcionManga;
	}

	public void setDescripcionManga(String descripcion) {
		this.descripcionManga = descripcion;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public List<Capitulo> getCaps() {
		return capitulosManga;
	}

	public void setCaps(List<Capitulo> caps) {
		this.capitulosManga = caps;
	}

	public String getEstadoManga() {
		return estadoManga;
	}

	public void setEstadoManga(String estado) {
		this.estadoManga = estado;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getPeridiocidad() {
		return peridiocidadManga;
	}

	public void setPeridiocidad(String peridiocidad) {
		this.peridiocidadManga = peridiocidad;
	}

	public String getUrlManga() {
		return urlManga;
	}

	public void setUrlManga(String urlManga) {
		this.urlManga = urlManga;
	}

	public String getUrlPortada() {
		return urlPortada;
	}

	public void setUrlPortada(String urlPortada) {
		this.urlPortada = urlPortada;
	}

	public String getRakingManga() {
		return rakingManga;
	}

	public void setRakingManga(String rakingManga) {
		this.rakingManga = rakingManga;
	}

	public String getRatingManga() {
		return ratingManga;
	}

	public void setRatingManga(String ratingManga) {
		this.ratingManga = ratingManga;
	}

	public String getEsMas18() {
		return esMas18;
	}

	public void setEsMas18(String esMas18) {
		this.esMas18 = esMas18;
	}
	
	public static Capitulo parseJSON(String response) {
		Gson gson = new GsonBuilder().create();
		Capitulo cap = gson.fromJson(response, Capitulo.class);
		return cap;
	}
	public void printGenerosManga() {
		System.out.println(this.getStringGenerosManga());
	}
	public String getStringGenerosManga()
	{
		List<String> g=this.getGeneros();
		String r="";
		r+="\nGeneros:\n ";
		for(String e : g){
			r+=" 	"+e+" ,\n";
		}
		return r;
	}
	public void printCapitulosManga() {
		System.out.println(this.getStringCapitulosManga());
	}
	public String getStringCapitulosManga()
	{
		List<Capitulo> c = this.getCaps();
		String r="";
		r+="\nCapitulosManga: ";
		for(Capitulo e : c){
			r+=e.getStringCapitulo();
		}
		return r;
	}
	public void printContentManga() {
		System.out.println(
				"\nMangaKey: "+this.getMangaKey()+ 
				"\nWebManga: "+this.getWebManga()+
				"\nTipoManga: "+this.getTipoManga()+
				"\nTituloManga: "+this.getTituloManga()+
				"\nAutorManga: "+this.getAutorManga()+
				"\nDescripcionManga: "+this.getDescripcionManga()+
				this.getStringGenerosManga()+
				this.getStringCapitulosManga()+
				"\nEstadoManga: "+this.getEstadoManga()+
				"\nFechaPublicacion: "+this.getFechaPublicacion()+
				"\nPeridiocidad: "+this.getPeridiocidad()+
				"\nUrlManga: "+this.getUrlManga()+
				"\nUrlPortada: "+this.getUrlPortada()+
				"\nRakingManga: "+this.getRakingManga()+
				"\nRatingManga: "+this.getRatingManga()+
				"\nEsMas18: "+this.getEsMas18()+"\n"
				);
	}
	public String validateText(String text) {
		String result="";
		int size = text.length();
		for(int i=0;i<size;i++)
		{
			char c=text.charAt(i);
			if(c=='\"')
				result+="\\\"";
			else
				result+=c;
		}			
		return result;
	}
	public boolean isExistCapitulo(List<Capitulo> capitulos,Capitulo cap) {
		if(capitulos.isEmpty())
			return false;
		for(Capitulo c : capitulos) {
			if(c.getOrderCap()==cap.getOrderCap())
				return true;
		}
		return false;
	}
	public void addCapitulo(Capitulo cap) {
		if(this.capitulosManga==null)
			this.capitulosManga = new ArrayList<Capitulo>();
		if(!isExistCapitulo(this.capitulosManga,cap)) {
			this.capitulosManga.add(cap);
			SupportMethods s = new SupportMethods();
			s.quickSortCaps(this.capitulosManga,0,this.capitulosManga.size()-1);
		}
	}	
}
