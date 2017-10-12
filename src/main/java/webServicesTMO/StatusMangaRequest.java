package webServicesTMO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import webTmo.StatusManga;

public class StatusMangaRequest {
	private String TableName;
	private StatusManga Item;
	
	public StatusMangaRequest() {}
	
	public StatusMangaRequest(String tableName, StatusManga item) {
		TableName = tableName;
		Item = item;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public StatusManga getItem() {
		return Item;
	}

	public void setItem(StatusManga item) {
		Item = item;
	}

	public static StatusManga parseJSON(String request) {
		Gson gson = new GsonBuilder().create();
		StatusManga statusManga = gson.fromJson(request, StatusManga.class);
		return statusManga;
	}
}
