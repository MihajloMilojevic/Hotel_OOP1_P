package utils;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Model;

public class CustomTableModel<T extends Model> extends AbstractTableModel {
	
	protected static final long serialVersionUID = 503026309281409389L;
	protected ArrayList<T> data;
	protected ArrayList<Pair<String, String>> columns;
	protected T model;
	protected TableDataManiplations<T> dataManipulations;
	
	
	public CustomTableModel(ArrayList<Pair<String, String>> columns, TableDataManiplations<T> dataManipulations, T model) {
		if (columns == null) {
			throw new IllegalArgumentException("Columns cannot be null");
		}
		if (dataManipulations == null) {
			throw new IllegalArgumentException("Data manipulation cannot be null");
		}
		if (model == null) {
			throw new IllegalArgumentException("Model cannot be null");
		}
		this.columns = columns;
		this.dataManipulations = dataManipulations;
		this.model = model;
		refresh();
	}
	
	public T get(int selectedRow) {
		return data.get(selectedRow);
	}
	
	public void edit(T item) throws NoElementException {
		dataManipulations.edit(item);
		refresh();	
	}

	public void remove(int selectedRow) throws NoElementException {
		dataManipulations.remove(data.get(selectedRow));
		refresh();	
	}

	public void add(T item) throws DuplicateIndexException {
		dataManipulations.add(item);
		refresh();	
	}
	
	public void refresh() {
		data = dataManipulations.getData();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns.get(columnIndex).getKey();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object value = model.get(columns.get(columnIndex).getValue());
		if (value == null) {
			System.out.println("Value is null for " + columns.get(columnIndex).getValue() + " in " + model.getClass().getName() + " model");
			return Object.class;
		}
		return value.getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// no cell is editable, it is handled by buttons and dialogs
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columns.get(columnIndex).getValue());
	}
	
	public static interface TableDataManiplations<T extends Model> {
		public ArrayList<T> getData();
		public void edit(T model) throws NoElementException;
		public void remove(T model) throws NoElementException;
		public void add(T model) throws DuplicateIndexException;
	}
}
