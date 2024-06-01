package utils;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import controllers.ControllerActionStatus;
import models.Model;
import views.components.Tab;

public class CustomTableModel<T extends Model> extends AbstractTableModel {
	
	protected static final long serialVersionUID = 503026309281409389L;
	protected ArrayList<T> data;
	protected ArrayList<Pair<String, String>> columns;
	protected T model;
	protected TableDataManiplations<T> dataManipulations;
	protected Tab<?> tab;
	
	
	public CustomTableModel(Tab<?> tab, ArrayList<Pair<String, String>> columns, TableDataManiplations<T> dataManipulations, T model) {
		if (tab == null) {
			throw new IllegalArgumentException("Tab cannot be null");
		}
		if (columns == null) {
			throw new IllegalArgumentException("Columns cannot be null");
		}
		if (dataManipulations == null) {
			throw new IllegalArgumentException("Data manipulation cannot be null");
		}
		if (model == null) {
			throw new IllegalArgumentException("Model cannot be null");
		}
		this.tab = tab;
		this.columns = columns;
		this.dataManipulations = dataManipulations;
		this.model = model;
		refresh();
	}
	
	public T get(int selectedRow) {
		return data.get(selectedRow);
	}
	
	public ControllerActionStatus edit(T item) {
		ControllerActionStatus status = dataManipulations.edit(item);
		refresh();
		tab.notifyTabs();
		return status;
	}

	public ControllerActionStatus remove(int selectedRow)  {
		ControllerActionStatus status = dataManipulations.remove(data.get(selectedRow));
		refresh();
		tab.notifyTabs();
		return status;
	}

	public ControllerActionStatus add(T item) {
		ControllerActionStatus status = dataManipulations.add(item);
		refresh();
		tab.notifyTabs();
		return status;
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
		return columns.get(columnIndex).getFirst();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object value = model.get(columns.get(columnIndex).getSecond());
		if (value == null) {
			// System.out.println("Value is null for " + columns.get(columnIndex).getSecond() + " in " + model.getClass().getName() + " model");
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
		return data.get(rowIndex).get(columns.get(columnIndex).getSecond());
	}
	
	public static interface TableDataManiplations<T extends Model> {
		public ArrayList<T> getData();
		public ControllerActionStatus edit(T model);
		public ControllerActionStatus remove(T model);
		public ControllerActionStatus add(T model);
	}
}
