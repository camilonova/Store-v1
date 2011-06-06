package com.nova.remision.log.builder;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.log.builder.Remision;
import com.nova.remision.dat.Remision_DatabaseProxy;
import com.nova.remision.dat.Remision_ModeloTabla;

/**
 * Esta clase construye el panel donde se encuentra la tabla de datos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_PanelBuilder extends JPanel implements Remision_AbstractBuilder {

	/**
	 * Representa la tabla de datos
	 */
	private static JTable tabla;
	
	/**
	 * Representa el modelo de la tabla de datos
	 */
	private static Remision_ModeloTabla modelo;
	
	/**
	 * Representa las columnas de la tabla
	 */
	private static TableColumn columnasTabla;
	
	/**
	 * Representa las propiedades de la celda
	 */
	private static DefaultTableCellRenderer cellRenderer;
	
	/**
	 * Constructor de la clase. Crea el modelo y la tabla con los
	 * datos y las muestra en la interfaz grafica.
	 */
	public Remision_PanelBuilder() {
		modelo = new Remision_ModeloTabla();
		tabla = new JTable(modelo);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setColumnSelectionAllowed(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);

		setPropiedadesTabla();
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(580,210));

		add(scrollPane);
	}

	/**
	 * Actualiza los datos de la tabla
	 * @param message
	 */
	public static void actualizarDatos(String message) {
		Remision_DatabaseProxy DB = Remision_DatabaseProxy.getInstancia();
		modelo.setAllData(DB.getAllData());

		if(message == null)
			Remision.statusBar.setText("Datos Cargados, Leidos "+DB.getCantidadRemisiones()+" remisiones.");
		else
			Remision.statusBar.setText(message);

		DB = null;
	}
	
	/**
	 * Metodo que retorna el numero de la remision seleccionada
	 * en la tabla.
	 * @return		ID del producto seleccionado, "-1" si no esta
	 * 				seleccionado ningun campo
	 */
	public static String getNumeroRemisionSeleccionada() {
		if(tabla.getSelectedRow() != -1)
			return (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
		else
			return "-1";
	}
	
	/**
	 * Metodo que retorna los datos de la tabla.
	 * @return		Datos de la tabla
	 */
	public static String[][] getDatosTabla() {
		return modelo.getAllData();
	}
	
	/**
	 * Metodo que selecciona la celda indicada por los parametros
	 * @param row	Fila a seleccionar
	 * @param col	Columna a seleccionar
	 */
	public static void setCeldaSeleccionada(int row, int col) {
		tabla.setColumnSelectionInterval(col,col);
		tabla.setRowSelectionInterval(row,row);
		tabla.scrollRectToVisible(tabla.getCellRect(row,col,true));
	}

	/**
	 * Le da las propiedades a la tabla. Ajusta los espacios entre columnas
	 */
	private void setPropiedadesTabla() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Remision");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(40);
		
		columnasTabla = tabla.getColumn("Cliente");
		columnasTabla.setPreferredWidth(200);
		
		columnasTabla = tabla.getColumn("Fecha Despacho");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Fecha Vencimiento");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
		
		columnasTabla = tabla.getColumn("Cantidad Elementos");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
		
		columnasTabla= tabla.getColumn("Valor Excluido");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Valor Gravado");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Valor Iva");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla= tabla.getColumn("Total");
		columnasTabla.setCellRenderer(cellRenderer);
	}
}
