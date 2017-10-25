package webTmo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Capitulo {
	private Double orderCap;
	private String nroCap;
	private String descripcionCap;
	private List<LectorCapitulo> lectorCap;
	private String isRestaurado;
	
	public Capitulo() {}
	
	public Capitulo(Double orderCap, String nroCap, String descripcionCap,List<LectorCapitulo> lectorCap,String isRestaurado) {
		this.orderCap = orderCap;
		this.nroCap = nroCap;
		this.descripcionCap = descripcionCap;
		this.lectorCap = lectorCap;
		this.isRestaurado = isRestaurado;
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
	
	public List<LectorCapitulo> getLectorCap() {
		return lectorCap;
	}

	public void setLectorCap(List<LectorCapitulo> lectorCap) {
		this.lectorCap = lectorCap;
	}
	
	public void addLectorCap(LectorCapitulo lector) {
		this.lectorCap.add(lector);
	}
	public String getIsRestaurado() {
		return isRestaurado;
	}

	public void setIsRestaurado(String isRestaurado) {
		this.isRestaurado = isRestaurado;
	}
	
	public static LectorCapitulo parseJSON(String response) {
		Gson gson = new GsonBuilder().create();
		LectorCapitulo cap = gson.fromJson(response, LectorCapitulo.class);
		return cap;
	}
	public void printCapitulo() {
		System.out.println(this.getStringCapitulo());
	}
	public String getStringCapitulo()
	{
		String r ="";
		r+="\n	NroCap: "+this.getNroCap();
		r+="\n	DescripcionCap: "+this.getDescripcionCap();
		r+="\n	OrderCap: "+this.getOrderCap();
		r+="\n	isRestaurado: "+this.getIsRestaurado();
		r+="\n	LectorCaps: "+this.getStringLectorCaps()+"\n";
		return r;
	}
	public String getStringLectorCaps()
	{
		String r ="";
		for(LectorCapitulo lc : this.lectorCap)
		{
			r+="\n\t\tScanlation: "+lc.getScan();
			r+="\n\t\tUrlCap: "+lc.getUrlLector()+"\n";
		}
		return r;
	}

}
