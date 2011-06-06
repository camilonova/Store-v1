package com.nova.stock.log.builder;

import java.awt.Dimension;
import java.util.Timer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.Stock;
import com.nova.stock.dat.Stock_ActualizacionCronometrada;
import com.nova.stock.dat.Stock_DatabaseProxy;
import com.nova.stock.dat.Stock_ModeloTabla;
import com.nova.supplier.dat.Supplier_DatabaseProxy;

/**
 * Esta clase construye el panel donde se encuentra la tabla de datos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_PanelBuilder extends JPanel implements Stock_AbstractBuilder {

	/**
	 * Representa la tabla de datos
	 */
	private static JTable tabla;
	
	/**
	 * Representa el modelo de la tabla de datos
	 */
	private static Stock_ModeloTabla modelo;
	
	/**
	 * Representa las columnas de la tabla
	 */
	private static TableColumn columnasTabla;
	
	/**
	 * Representa las propiedades de la celda
	 */
	private static DefaultTableCellRenderer cellRenderer;
	
	/**
	 * Representa la tarea que se debe ejecutar periodicamente
	 */
	private Stock_ActualizacionCronometrada actualizacion;
	
	/**
	 * Representa el demonio que mantiene ejecutando la tarea de actualizacion
	 */
	private Timer tiempo;
	
	/**
	 * Constructor de la clase. Crea el modelo y la tabla con los
	 * datos y las muestra en la interfaz grafica.<p>
	 * Ademas comienza con las actualizaciones periodicas de los
	 * datos de la tabla.
	 */
	public Stock_PanelBuilder() {
		modelo = new Stock_ModeloTabla();
		tabla = new JTable(modelo);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setColumnSelectionAllowed(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);
		
		setPropiedadesTabla();
		
		actualizacion = new Stock_ActualizacionCronometrada(modelo);
		tiempo = new Timer();
		tiempo.schedule(actualizacion, 0, ParametrosGlobales.TIEMPO_ACTUALIZACION_STOCK);
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(770,480));

		add(scrollPane);
	}

	/**
	 * Metodo que actualiza los datos de la tabla, si el parametro es
	 * null imprime el mensaje por defecto
	 * @param message		Mensaje a imprimir, null para mensaje por defecto
	 */
	public static void actualizarDatos(String message) {
		Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
		modelo.setAllData(DB.getAllData());
		
		if(message == null)
			Stock.statusBar.setText("Datos Cargados, Leidos "+DB.getCantidadElementos()+" productos");
		else
			Stock.statusBar.setText(message);

		DB = null;
	}
	
	/**
	 * Metodo que retorna el ID del elemento seleccionado
	 * en la tabla.
	 * @return		ID del producto seleccionado, "-1" si no esta
	 * 				seleccionado ningun campo
	 */
	public static String getIDProductoSeleccionado() {
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
	 * Metodo que da a la tabla el tamaño de las celdas y
	 * el JComboBox para los proveedores.
	 */
	private void setPropiedadesTabla() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("ID");
		columnasTabla.setPreferredWidth(30);
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Cantidad");
		columnasTabla.setPreferredWidth(40);
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Descripcion");
		columnasTabla.setPreferredWidth(300);
		
		columnasTabla = tabla.getColumn("Presentacion");
		columnasTabla.setCellEditor(new DefaultCellEditor(new JComboBox(ParametrosGlobales.LISTA_PRESENTACION)));
		columnasTabla.setPreferredWidth(100);
		
		columnasTabla = tabla.getColumn("Impuesto");
		columnasTabla.setPreferredWidth(40);
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Precio Publico");
		columnasTabla.setPreferredWidth(100);
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Precio Mayorista");
		columnasTabla.setPreferredWidth(100);
		columnasTabla.setCellRenderer(cellRenderer);
		
		Supplier_DatabaseProxy DB = Supplier_DatabaseProxy.getInstancia();
		columnasTabla = tabla.getColumn("Proveedor");
		columnasTabla.setCellEditor(new DefaultCellEditor(new JComboBox(DB.getListaProveedores())));
		columnasTabla.setPreferredWidth(200);
		DB = null;
		
		columnasTabla = tabla.getColumn("Costo");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Observaciones");
		columnasTabla.setPreferredWidth(300);
	}
}
