package webTmo;

public class StatusManga {
	
	private String urlKeyManga;
	private String revision;
	private String tipo;
	private String titulo;	
	private String urlPortada;
	private String urlManga;	
	private String rating;
	private String mas18;
	private String status;
	
	public StatusManga() {}
	
	public StatusManga(String urlKeyManga, String revision, String tipo, String titulo, String urlPortada,
			String urlManga, String rating, String mas18, String status) {
		this.urlKeyManga = urlKeyManga;
		this.revision = revision;
		this.tipo = tipo;
		this.titulo = titulo;
		this.urlPortada = urlPortada;
		this.urlManga = urlManga;
		this.rating = rating;
		this.mas18 = mas18;
		this.status = status;
	}


	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
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
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getUrlKeyManga() {
		return urlKeyManga;
	}
	
	public void setUrlKeyManga(String urlKeyManga) {
		this.urlKeyManga = urlKeyManga;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getMas18() {
		return mas18;
	}

	public void setMas18(String mas18) {
		this.mas18 = mas18;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void printStatusManga() {
		System.out.println("\nkeyManga: "+getUrlKeyManga() + 
				"\nTitulo: "+getTitulo() + 
				"\nurlManga: "+getUrlManga() +
				"\ntipo: "+getTipo() + 
				"\nPortada: "+getUrlPortada() + 
				"\nRating: "+getRating() +
				"\nmas18: "+getMas18() + "\n");
	}
}
