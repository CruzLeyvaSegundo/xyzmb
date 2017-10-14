package webTmo;

public class Capitulo {
	private Double orderCap;
	private String nroCap;
	private String descripcionCap;

	public Capitulo() {}
	
	public Capitulo(Double orderCap, String nroCap, String descripcionCap) {
		this.descripcionCap = descripcionCap;
		this.nroCap = nroCap;
		this.orderCap = orderCap;
	}
	
	public String getDescripcionCap() {
		return descripcionCap;
	}
	
	public void setDescripcionCap(String descripcionCap) {
		this.descripcionCap = descripcionCap;
	}
	
	public String getNroCap() {
		return nroCap;
	}
	
	public void setNroCap(String nroCap) {
		this.nroCap = nroCap;
	} 
	
	public double getOrderCap() {
		return orderCap;
	}

	public void setOrderCap(Double orderCap) {
		this.orderCap = orderCap;
	}

	public void printCapitulo() {
		System.out.println(this.getStringCapitulo());
	}
	public String getStringCapitulo()
	{
		String r ="";
		r+="\n	NroCap: "+this.getNroCap();
		r+="\n	DescripcionCap: "+this.getDescripcionCap();
		r+="\n	OrderCap: "+this.getOrderCap()+"\n";
		return r;
	}

}
