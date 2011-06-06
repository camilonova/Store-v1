package com.nova.client.log.builder;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.client.dat.Client_DatabaseProxy;
import com.nova.client.dat.Client_ModeloTabla;
import com.nova.log.builder.Client;

/**
 * Esta clase construye el panel donde se encuentra la tabla de datos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Client_PanelBuilder extends JPanel implements Client_AbstractBuilder {

	/**
	 * Representa la tabla de datos
	 */
	private static JTable tabla;
	
	/**
	 * Representa el modelo de la tabla de datos
	 */
	private static Client_ModeloTabla modelo;
	
	/**
	 * Representa el modelo de las celdas
	 */
	private DefaultTableCellRenderer cellRenderer;

	/**
	 * Representa las columnas de la tabla
	 */
	private TableColumn columnasTabla;
	
	/**
	 * Constructor de la clase. Crea el modelo y la tabla con los
	 * datos y las muestra en la interfaz grafica.
	 *
	 */
	public Client_PanelBuilder() {
		modelo = new Client_ModeloTabla();
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
	 * Actualiza los datos de la tabla y muestra la cadena recibida por
	 * parametro
	 * @param message	Mensaje a mostrar en la barra de estado
	 */
	public static void actualizarDatos(String message) {
		Client_DatabaseProxy DB = Client_DatabaseProxy.getInstancia();
		modelo.setAllData(DB.getAllData());

		if(message == null)
			Client.statusBar.setText("Datos Cargados, Leidos "+DB.getCantidadClientes()+" proveedores.");
		else
			Client.statusBar.setText(message);

		DB = null;
	}
	
	/**
	 * Metodo que retorna el ID del cliente seleccionado
	 * en la tabla.
	 * @return		ID del producto seleccionado, "-1" si no esta
	 * 				seleccionado ningun campo
	 */
	public static String getIDClienteSeleccionado() {
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
		
		columnasTabla = tabla.getColumn("Nombre");
		columnasTabla.setPreferredWidth(250);

		columnasTabla = tabla.getColumn("NIT");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Telefono");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Fax");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Movil");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Direccion");
		columnasTabla.setPreferredWidth(250);

	}
}
