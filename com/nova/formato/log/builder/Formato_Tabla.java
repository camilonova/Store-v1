package com.nova.formato.log.builder;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.formato.dat.Formato_ModeloTabla;
import com.nova.formato.log.strategy.Formato;

/**
 * Esta clase construye la tabla y todo lo relacionado con la misma, como el
 * modelo y provee los metodos de acceso a los datos de la tabla
 * @author Camilo Nova
 * @version 1.0
 */
public class Formato_Tabla extends JPanel {

	/**
	 * Representa la tabla de datos
	 */
	private JTable tabla;
	
	/**
	 * Representa el modelo de la tabla de datos
	 */
	private Formato_ModeloTabla modelo;
	
	/**
	 * Representa las columnas de la tabla
	 */
	private TableColumn columnasTabla;
	
	/**
	 * Representa las propiedades de la celda
	 */
	private DefaultTableCellRenderer cellRenderer;
	
	/**
	 * Representa una instancia del panel principal
	 */
	private Formato principal;

	/**
	 * Constructor de la clase, crea la tabla y la agrega al panel
	 * @param principalGUI
	 * 
	 */
	public Formato_Tabla(Formato principalGUI) {
		principal = principalGUI;
		modelo = new Formato_ModeloTabla();
		tabla = new JTable(modelo);
		modelo.setTabla(this);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setColumnSelectionAllowed(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);
		
		setPropiedadesTabla();
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(690,260));
		
		add(scrollPane);
	}
	
	/**
	 * Metodo que elimina la fila seleccionada de la tabla
	 */
	public void eliminarFilaSeleccionada() {
		modelo.eliminarFila(tabla.getSelectedRow());
	}
	
	/**
	 * Metodo que elimina todos los datos de la tabla
	 */
	public void eliminarTodo() {
		modelo.eliminarTodo();
	}
	
	/**
	 * Metodo que retorna los datos de la tabla.
	 * @return		Datos de la tabla
	 */
	public String[][] getDatosTabla() {
		return modelo.getAllData();
	}
	
	/**
	 * Metodo que carga los datos pasados por parametro
	 * en la tabla
	 * @param datos		Datos a cargar
	 */
	public void setDatosTabla(String[][] datos) {
		modelo.setAllData(datos);
	}
	
	/**
	 * Agrega un producto al final de la tabla
	 * @param cantidad				Cantidad de elementos
	 * @param descripcion			Descripcion del elemento
	 * @param valorUnitario			Valor unitario del elemento
	 * @param iva					Iva del elemento
	 */
	public void agregarProducto(String cantidad, String descripcion, String valorUnitario, String iva) {
		modelo.setNewData(cantidad, descripcion, valorUnitario, iva);
		tabla.scrollRectToVisible(tabla.getCellRect(tabla.getRowCount()-1, tabla.getColumnCount()-1, true));
	}
	
	/**
	 * Metodo que determina el total de los elementos del formato
	 * @param cantElementos				Cantidad de elementos cotizados
	 * @param subtotal					Subtotal del formato
	 * @param valorExcluido				Valor Excluido del formato
	 * @param valorGravado				Valor Gravado del formato
	 * @param iva						Iva del formato
	 */
	public void calcularTotalTabla(String cantElementos, String subtotal, String valorExcluido, String valorGravado, String iva) {
		principal.setTotal(cantElementos, subtotal, valorExcluido, valorGravado, iva);
	}
	
	/**
	 * Determina el ancho de las celdas y la orientacion del texto
	 */
	private void setPropiedadesTabla() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla= tabla.getColumn("Cantidad");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(51);
		
		columnasTabla= tabla.getColumn("Descripcion");
		columnasTabla.setPreferredWidth(300);
		
		columnasTabla= tabla.getColumn("Valor Unitario");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(80);
		
		columnasTabla = tabla.getColumn("Gravado");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(70);

		columnasTabla= tabla.getColumn("Excluido");
		columnasTabla.setPreferredWidth(70);
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla= tabla.getColumn("Valor Total");
		columnasTabla.setCellRenderer(cellRenderer);		
		columnasTabla.setPreferredWidth(100);
	}

	/**
	 * Metodo que determina si la tabla se puede
	 * modificar en su totalidad
	 * @param b		True para modificar, False de lo contrario
	 */
	public void setEditable(boolean b) {
		modelo.setEditable(b);
	}
}