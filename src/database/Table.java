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

import models.Model;
import utils.Pair;

public class Table<T extends Model> {
	private File file;
	private HashMap<String, T> rows;
	private HashMap<String, HashMap<String, T>> indecies;
	private T object;
	
	public Table(File file, T object) {
		this.file = file;
		this.object = object;
		this.rows = new HashMap<String, T>();
		this.indecies = new HashMap<String, HashMap<String, T>>();
		if (!file.exists()) {
            try {
            	file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
        }
	}
	public void AddIndex(String indexName) {
		HashMap<String, T> newIndex = new HashMap<String, T>();
		for (T row : this.rows.values()) {
			newIndex.put((String)row.get(indexName), row);
		}
		this.indecies.put(indexName, newIndex);
	}
	public void RemoveIndex(String indexName) {
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
	public void Insert(T row) {
		this.rows.put(row.getId(), row);
        for (String indexName : this.indecies.keySet()) {
            this.indecies.get(indexName).put((String)row.get(indexName), row);
        }
	}
	public void Delete(T row) {
		this.rows.remove(row.getId());
		RegenerateIndecies();
	}
	public void Delete(SelectCondition condition) {
		for (T row : this.rows.values()) {
			if (condition.check(row)) {
				this.rows.remove(row.getId());
			}
		}
		RegenerateIndecies();
	}

	public void DeleteById(String id) {
		this.rows.remove(id);
		RegenerateIndecies();
	}
	public void DeleteByIndex(String indexName, String indexValue) {
		if(!this.indecies.containsKey(indexName)) return;
		T row = this.indecies.get(indexName).get(indexValue);
		if(row == null) return;
		this.rows.remove(row.getId());
		RegenerateIndecies();
	}

	public ArrayList<T> Select(SelectCondition condition) {
		return Select(condition, true);
	}
	
 	@SuppressWarnings("unchecked")
	public ArrayList<T> Select(SelectCondition condition, boolean clone) {
		ArrayList<T> result = new ArrayList<T>();
		for (T row : this.rows.values()) {
            if (condition.check(row)) {
            	try {
					if (clone)
						result.add((T) row.clone());
					else
						result.add(row);
				} catch (CloneNotSupportedException e) {
					System.err.println(e.getMessage());
				}
            }
        }
		return result;
	}
 	
 	public T SelectById(String id) {
	        return SelectById(id, true);
 	}
 	
 	@SuppressWarnings("unchecked")
	public T SelectById(String id, boolean clone) {
		T row = this.rows.get(id);
		if (row == null)
			return null;
		try {
			if (clone) return (T) row.clone();
			return row;
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
			return null;
		}
 	}

	public T SelectByIndex(String indexName, String indexValue) {
		return SelectByIndex(indexName, indexValue, true);
	}
 	
	@SuppressWarnings("unchecked")
	public T SelectByIndex(String indexName, String indexValue, boolean clone) {
		if (!this.indecies.containsKey(indexName)) return null;
		T row = this.indecies.get(indexName).get(indexValue);
		try {
			if(clone) return (T)row.clone();
			return row;
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public void Update(SelectCondition condition, ArrayList<Pair<String, Object>> updates) {
		for (T row : this.rows.values()) {
			if (condition.check(row)) {
				for (Pair<String, Object> update : updates) {
					row.set(update.getKey(), update.getValue());
				}
			}
		}
		RegenerateIndecies();
	}

	public void UpdateByIndex(String indexName, String indexValue, ArrayList<Pair<String, Object>> updates) {
		if (!this.indecies.containsKey(indexName)) return;
		T row = this.indecies.get(indexName).get(indexValue);
		if(row == null) return;
		for (Pair<String, Object> update : updates) {
			row.set(update.getKey(), update.getValue());
		}
		RegenerateIndecies();
	}

	public void Clear() {
		this.rows.clear();
		this.indecies.clear();
	}
	
	private void RegenerateIndecies() {
		for (String indexName : this.indecies.keySet()) {
			this.indecies.remove(indexName);
			this.AddIndex(indexName);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void Load() throws IOException {
		List<String> lines = Files.readAllLines(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
		for (String line : lines) {
			T row;
			try {
				row = (T)((T)this.object.clone()).fromCSV(line);
				this.Insert(row);
			} catch (ParseException | CloneNotSupportedException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public void Save() throws IOException {
		List<String> lines = new ArrayList<String>();
		for (T row : this.rows.values()) {
			lines.add(row.toString());
		}
		Files.write(Path.of(file.getAbsolutePath()), lines, StandardCharsets.UTF_8);
	}
}
