package database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import app.AppSettings;
import exceptions.DuplicateIndexException;
import models.Model;
import utils.FileChecker;
import utils.Pair;

public class Table<T extends Model> {
	private String settingName;
	private HashMap<String, T> rows;
	private HashMap<String, HashMap<String, T>> indecies;
	private T object;
	private CustomTableParser customParser;
	
	public Table(String settingName, T object) {
		this.settingName = settingName;
		this.object = object;
		this.rows = new HashMap<String, T>();
		this.indecies = new HashMap<String, HashMap<String, T>>();
		
		addIndex("id");
	}

	public Table(String settingName, CustomTableParser customParser) {
		this(settingName, (T)null);
		this.customParser = customParser;
		
	}
	public void addIndex(String indexName) {
		HashMap<String, T> newIndex = new HashMap<String, T>();
		for (T row : this.rows.values()) {
			newIndex.put((String)row.get(indexName), row);
		}
		this.indecies.put(indexName, newIndex);
	}
	public void removeIndex(String indexName) {
		this.indecies.remove(indexName);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> getRows() {
		ArrayList<T> result = new ArrayList<T>();
		for (T row : this.rows.values()) {
        	try {
    			result.add((T)row.clone());
			} catch (CloneNotSupportedException e) {
				System.err.println(e.getMessage());
			}
        }
		return result;
	}
	public void insert(T row) throws DuplicateIndexException {
		if(!isUnique(row)) throw new DuplicateIndexException("Duplicate key");
		this.rows.put(row.getId(), row);
        for (String indexName : this.indecies.keySet()) {
            this.indecies.get(indexName).put(row.get(indexName).toString(), row);
        }
	}
	public void delete(T row) {
		this.rows.remove(row.getId());
		regenerateIndecies();
	}
	public void delete(SelectCondition condition) {
		for (T row : this.rows.values()) {
			if (condition.check(row)) {
				this.rows.remove(row.getId());
			}
		}
		regenerateIndecies();
	}

	public void deleteById(String id) {
		this.rows.remove(id);
		regenerateIndecies();
	}
	public void deleteByIndex(String indexName, String indexValue) {
		if(!this.indecies.containsKey(indexName)) return;
		T row = this.indecies.get(indexName).get(indexValue);
		if(row == null) return;
		this.rows.remove(row.getId());
		regenerateIndecies();
	}

 	@SuppressWarnings("unchecked")
	public ArrayList<T> select(SelectCondition condition) {
		ArrayList<T> result = new ArrayList<T>();
		for (T row : this.rows.values()) {
            if (condition.check(row)) {
            	try {
					result.add((T) row.clone());
				} catch (CloneNotSupportedException e) {
					System.err.println(e.getMessage());
				}
            }
        }
		return result;
	}
 	
 	@SuppressWarnings("unchecked")
	public T selectById(String id) {
		T row = this.rows.get(id);
		if (row == null)
			return null;
		try {
			return (T) row.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
			return null;
		}
 	}


	@SuppressWarnings("unchecked")
	public T selectByIndex(String indexName, String indexValue) {
		if (!this.indecies.containsKey(indexName)) return null;
		T row = this.indecies.get(indexName).get(indexValue);
		try {
			return (T)row.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public void update(SelectCondition condition, ArrayList<Pair<String, Object>> updates) throws DuplicateIndexException {
		for (T row : this.rows.values()) {
			if (condition.check(row)) {
				try {
					@SuppressWarnings("unchecked")
					T copy = (T) row.clone();
					for (Pair<String, Object> update : updates) {
						copy.set(update.getKey(), update.getValue());
					}
					if(!isUnique(copy)) throw new DuplicateIndexException("Duplicate key");
				} catch (CloneNotSupportedException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		for (T row : this.rows.values()) {
			if (condition.check(row)) {
				for (Pair<String, Object> update : updates) {
					row.set(update.getKey(), update.getValue());
				}
			}
		}
		regenerateIndecies();
	}

	public void updateByIndex(String indexName, String indexValue, ArrayList<Pair<String, Object>> updates) throws DuplicateIndexException {
		if (!this.indecies.containsKey(indexName)) return;
		T row = this.indecies.get(indexName).get(indexValue);
		if(row == null) return;
		try {
			@SuppressWarnings("unchecked")
			T copy = (T) row.clone();
			for (Pair<String, Object> update : updates) {
				copy.set(update.getKey(), update.getValue());
			}
			if(!isUnique(copy)) throw new DuplicateIndexException("Duplicate key");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Pair<String, Object> update : updates) {
			row.set(update.getKey(), update.getValue());
		}
		regenerateIndecies();
	}

	public void clear() {
		this.rows.clear();
		this.indecies.clear();
	}
	
	private void regenerateIndecies() {
		Set<String> keys = this.indecies.keySet();
		this.indecies.clear();
		for (String indexName : keys) {
			this.addIndex(indexName);
		}
	}
	
	private boolean isUnique(T model) {
		if(this.rows.containsKey(model.getId())) return false;
		for (String indexName : this.indecies.keySet()) {
			if (this.indecies.get(indexName).containsKey(model.get(indexName).toString()))
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void load() throws IOException {
		File file = FileChecker.getFile(AppSettings.getInstance().getSetting("database", this.settingName, "./data/" + this.settingName + ".csv"));
		List<String> lines = Files.readAllLines(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
		for (String line : lines) {
			T row;
			try {
				if (this.customParser != null) {
					row = (T) this.customParser.parse(line);
				} else {
					row = (T) ((T) this.object.clone()).fromCSV(line);
				}
				this.insert(row);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public void save() throws IOException {
		File file = FileChecker.getFile(AppSettings.getInstance().getSetting("database", this.settingName, "./data/" + this.settingName + ".csv"));
		List<String> lines = new ArrayList<String>();
		for (T row : this.rows.values()) {
			try {
				if (this.customParser != null) {
					lines.add(this.customParser.stringify(row));
				} else {            	
					lines.add(row.toString());
				}
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}
		Files.write(Path.of(file.getAbsolutePath()), lines, StandardCharsets.UTF_8);
	}

	public String getSettingName() {
		return settingName;
	}
}
