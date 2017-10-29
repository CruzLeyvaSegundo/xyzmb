package webTmo;

public class DataManga {

	private String dataManga;
	private String valueManga;
	
	public DataManga(String dataManga, String valueManga) {
		this.dataManga = dataManga;
		this.valueManga = valueManga;
	}
	
	public String getDataManga() {
		return dataManga;
	}
	
	public void setDataManga(String dataManga) {
		this.dataManga = dataManga;
	}
	
	public String getValueManga() {
		return valueManga;
	}
	
	public void setValueManga(String valueManga) {
		this.valueManga = valueManga;
	}
	public boolean isNull() //Usar este metodo solo cuando la solicitud Request GET fue un exito
	{
		return(	this.dataManga==null && this.valueManga==null);
	}
	public int getIntegerValue() {
		return Integer.parseInt(this.valueManga);
	}
	public void masUno() {
		int currentPage = getIntegerValue();
		int newPage = currentPage +1 ;
		setValueManga(String.valueOf(newPage));
	}
	
}
