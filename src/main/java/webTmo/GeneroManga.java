package webTmo;

public class GeneroManga {
	private String generoKey;
	private String genero;
	private String status;
	
	public GeneroManga() {}
	
	public GeneroManga(String generoKey, String genero, String status) {
		this.generoKey = generoKey;
		this.genero = genero;
		this.status = status;
	}

	public String getGeneroKey() {
		return generoKey;
	}

	public void setGeneroKey(String generoKey) {
		this.generoKey = generoKey;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
