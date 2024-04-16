package database;

import java.util.ArrayList;
import java.util.HashMap;

import models.Model;

public abstract class Table {
	private String tableName;
	private String primaryKey;
	private String filePath;
	private ArrayList<Model> rows;
	private HashMap<String, HashMap<String, Model>> indecies;

	public Table(String tableName, String primaryKey, String filePath) {
		this.tableName = tableName;
		this.primaryKey = primaryKey;
		this.filePath = filePath;
		this.rows = new ArrayList<Model>();
		this.indecies = new HashMap<String, HashMap<String, Model>>();
		this.AddIndex(primaryKey);
	}

	public void AddIndex(String indexName) {
		HashMap<String, Model> newIndex = new HashMap<String, Model>();
		for (Model row : this.rows) {
			newIndex.put((String)row.get(indexName), row);
		}
		this.indecies.put(indexName, newIndex);
	}
}
