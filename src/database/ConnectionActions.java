package database;

import java.io.IOException;
import java.text.ParseException;

import models.Model;

public interface ConnectionActions<T extends Model, U extends Model> {
	public void Load(Table<T> table1, Table<U> table2, String path) throws IOException, ParseException;
	public void Save(Table<T> table1, Table<U> table2, String path) throws IOException, ParseException;
}
