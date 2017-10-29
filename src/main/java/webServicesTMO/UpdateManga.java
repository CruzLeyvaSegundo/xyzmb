package webServicesTMO;

//import java.util.List;
public class UpdateManga {
	
	private String updateKey;
	private String nroCap;
	private String scanlation;
	private String keyManga;
	private String tipoManga;
	private String urlManga;
	private String urlLector;
	private String revisionCap;
	private String page;
	
	public UpdateManga() {}
	
	public UpdateManga(String updateKey, String nroCap, String scanlation, String keyManga, String tipoManga,
			String urlManga, String urlLector, String status, String page) {
		this.updateKey = updateKey;
		this.nroCap = nroCap;
		this.scanlation = scanlation;
		this.keyManga = keyManga;
		this.tipoManga = tipoManga;
		this.urlManga = urlManga;
		this.urlLector = urlLector;
		this.revisionCap = status;
		this.page = page;
	}
	
	public String getUrlManga() {
		return urlManga;
	}
	
	public void setUrlManga(String urlManga) {
		this.urlManga = urlManga;
	}
	
	public String getUrlLector() {
		return urlLector;
	}
	
	public void setUrlLector(String urlLector) {
		this.urlLector = urlLector;
	}
	
	public String getKeyManga() {
		return keyManga;
	}
	
	public void setKeyManga(String keyManga) {
		this.keyManga = keyManga;
	}
	
	public String getNroCap() {
		return nroCap;
	}
	
	public void setNroCap(String nroCap) {
		this.nroCap = nroCap;
	}
	
	public String getTipoManga() {
		return tipoManga;
	}
	
	public void setTipoManga(String tipo) {
		this.tipoManga = tipo;
	}

	public String getUpdateKey() {
		return updateKey;
	}

	public void setUpdateKey(String updateKey) {
		this.updateKey = updateKey;
	}

	public String getScanlation() {
		return scanlation;
	}

	public void setScanlation(String scanlation) {
		this.scanlation = scanlation;
	}

	public String getStatus() {
		return revisionCap;
	}

	public void setStatus(String status) {
		this.revisionCap = status;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
