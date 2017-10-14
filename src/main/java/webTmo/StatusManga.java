package webTmo;

public class StatusManga {
	
	private String keyManga;
	private String revisionManga;
	private String tipoManga;
	private String tituloManga;	
	private String urlPortada;
	private String urlManga;	
	private String ratingManga;
	private String esMas18;
	private String statusManga;
	
	public StatusManga() {}
	
	public StatusManga(String keyManga, String revisionManga, String tipoManga, String tituloManga, String urlPortada,
			String urlManga, String ratingManga, String esMas18, String statusManga) {
		this.keyManga = keyManga;
		this.revisionManga = revisionManga;
		this.tipoManga = tipoManga;
		this.tituloManga = tituloManga;
		this.urlPortada = urlPortada;
		this.urlManga = urlManga;
		this.ratingManga = ratingManga;
		this.esMas18 = esMas18;
		this.statusManga = statusManga;
	}
	
	public String getKeyManga() {
		return keyManga;
	}

	public void setKeyManga(String keyManga) {
		this.keyManga = keyManga;
	}

	public String getRevisionManga() {
		return revisionManga;
	}

	public void setRevisionManga(String revisionManga) {
		this.revisionManga = revisionManga;
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

	public String getUrlPortada() {
		return urlPortada;
	}

	public void setUrlPortada(String urlPortada) {
		this.urlPortada = urlPortada;
	}

	public String getUrlManga() {
		return urlManga;
	}

	public void setUrlManga(String urlManga) {
		this.urlManga = urlManga;
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

	public String getStatusManga() {
		return statusManga;
	}

	public void setStatusManga(String statusManga) {
		this.statusManga = statusManga;
	}
	
	//Si se instancia el objeto con 'null' y despues se invoca este metodo generar error
	public boolean isNull() //Usar este metodo solo cuando la solicitud Request GET fue un exito
	{
		return(	this.keyManga==null &&
				this.revisionManga==null &&
				this.tipoManga==null &&
				this.tituloManga==null &&
				this.urlPortada==null &&
				this.urlManga==null &&
				this.ratingManga==null &&
				this.esMas18==null &&
				this.statusManga==null);
	}
	
	public void printStatusManga() {
		System.out.println("\nkeyManga: "+this.getKeyManga()+ 
				"\nTituloManga: "+ this.getTituloManga()+ 
				"\nurlManga: "+ this.getUrlManga() +
				"\ntipoManga: "+ this.getTipoManga() + 
				"\nPortadaUrl: "+ this.getUrlPortada()+ 
				"\nRatingManga: "+ this.getRatingManga()+
				"\nStatusManga: "+ this.getStatusManga()+
				"\nRevisionManga: "+ this.getRevisionManga()+
				"\nesMas18: "+ this.getEsMas18()+ "\n");
	}
}
