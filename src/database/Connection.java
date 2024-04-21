package database;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import models.Model;

public class Connection<T extends Model, U extends Model> {
	private Table<T> table1;
	private Table<U> table2;
	private File file;
	private ConnectionActions<T, U> connectionActions;

	public Connection(Table<T> table1, Table<U> table2, File file, ConnectionActions<T, U> connectionActions) {
		this.table1 = table1;
		this.table2 = table2;
		this.file = file;
		if(!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.connectionActions = connectionActions;
	}

	public void Load() throws IOException, ParseException {
        connectionActions.Load(table1, table2, file.getAbsolutePath());
    }

	public void Save() throws IOException, ParseException {
        connectionActions.Save(table1, table2, file.getAbsolutePath());
    }
}