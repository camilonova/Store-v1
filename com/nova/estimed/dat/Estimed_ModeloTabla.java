package com.nova.estimed.dat;

import javax.swing.table.AbstractTableModel;

/**
 * Esta clase representa el modelo de la tabla, con lo cual se convierte
 * en el patron a seguir de la tabla, si se quisiera modificar alguna
 * caracteristica de la tabla, se debe hacer desde aqui.
 * @author Camilo Nova
 * @version 1.0
 */
public class Estimed_ModeloTabla extends AbstractTableModel {

	/**
	 * Representa los nombres de las columnas de la tabla
	 */
	private String[] encabezados;
	
	/**
	 * Representa los datos de la tabla. Las operaciones de
	 * busqueda y trabajo sobre datos se deben hacer sobre
	 * esta variable.
	 */
	private String[][] datos;
	
	/**
	 * Constructor de la clase. Le da el nombre a las columnas
	 * e inicializa las variables que almacenan los datos.
	 *
	 */
	public Estimed_ModeloTabla() {
		Estimed_DatabaseProxy DB = Estimed_DatabaseProxy.getInstancia();
		encabezados = DB.getNombresColumnas();
		datos = DB.getAllData();
		DB = null;
		
		fireTableStructureChanged();
	}

	/**
	 * Metodo que retorna la cantidad de filas.
	 */
	public int getRowCount() {
		return datos.length;
	}

	/**
	 * Metodo que retorna la cantidad de columnas.
	 */
	public int getColumnCount() {
		return encabezados.length;
	}
	
	/**
	 * Metodo que retorna el nombre de las columnas.
	 */
	public String getColumnName(int column) {
		return (String) encabezados[column];
	}

	/**
	 * Metodo que retorna el objeto situado en los parametros.
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return datos[rowIndex][columnIndex];
	}
	
	/**
	 * Metodo que determina si una celda es editable o no.
	 * Todas las celdas no son editables
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	/**
	 * Metodo que actualiza la informacion de una celda.
	 * Ninguna celda actualiza su informacion
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	/**
	 * Metodo que actualiza la informacion total de la tabla.
	 * @param newData	Datos actualizados a cambiar
	 */
	public void setAllData(String[][] newData) {
		datos = newData;
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que retorna los datos de la tabla
	 * @return		Datos de la tabla
	 */
	public String[][] getAllData() {
		return datos;
	}
}
