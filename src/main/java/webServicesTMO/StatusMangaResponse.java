package webServicesTMO;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import webTmo.StatusManga;

public class StatusMangaResponse {
	
	private Integer Count;
	private List<StatusManga> Items = null;
	private Integer ScannedCount;
	
	public StatusMangaResponse() {}
	
	public StatusMangaResponse(Integer count, List<StatusManga> items, Integer scannedCount) {
		Count = count;
		Items = items;
		ScannedCount = scannedCount;
	}
	
	public Integer getCount() {
		return Count;
	}
	
	public void setCount(Integer count) {
		Count = count;
	}
	
	public List<StatusManga> getItems() {
		return Items;
	}
	
	public void setItems(List<StatusManga> items) {
		Items = items;
	}
	
	public Integer getScannedCount() {
		return ScannedCount;
	}
	
	public void setScannedCount(Integer scannedCount) {
		ScannedCount = scannedCount;
	}
	
	public static StatusManga parseJSON(String response) {
		Gson gson = new GsonBuilder().create();
		StatusManga mstatus = gson.fromJson(response, StatusManga.class);
		return mstatus;
	}			
}
