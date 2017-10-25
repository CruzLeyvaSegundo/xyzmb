package webTmo;

public class LectorCapitulo {
	private String scan;
	private String urlLector;
	
	public LectorCapitulo() {}
	
	public LectorCapitulo(String scan, String urlLector) {
		this.scan = scan;
		this.urlLector = urlLector;
	}
	
	public String getScan() {
		return scan;
	}
	
	public void setScan(String scan) {
		this.scan = scan;
	}
	
	public String getUrlLector() {
		return urlLector;
	}
	
	public void setUrlLector(String urlLector) {
		this.urlLector = urlLector;
	}
}
