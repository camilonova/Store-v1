package com.nova.cuentasxcobrar.log.builder;

import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.cuentasxcobrar.dat.CuentasxCobrar_DatabaseProxy;
import com.nova.cuentasxcobrar.dat.CuentasxCobrar_ModeloTabla;
import com.nova.log.ValidadorFecha;
import com.nova.log.ValidadorNumerico;
import com.nova.log.builder.CuentasxCobrar;

/**
 * Esta clase construye el panel donde se encuentra la tabla de datos.
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar_PanelBuilder extends JPanel implements CuentasxCobrar_AbstractBuilder {

	/**
	 * Representa la tabla de datos
	 */
	private static JTable tabla;
	
	/**
	 * Representa el modelo de la tabla de datos
	 */
	private static CuentasxCobrar_ModeloTabla modelo;

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
	public CuentasxCobrar_PanelBuilder() {
		modelo = new CuentasxCobrar_ModeloTabla();
		tabla = new JTable(modelo);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setColumnSelectionAllowed(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setSurrendersFocusOnKeystroke(true);
		
		setPropiedadesTabla();
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(780,210));

		add(scrollPane);
	}

	/**
	 * Actualiza los datos en la tabla, si el parametro es null
	 * muestra el mensaje por defecto, de los contrario muestra
	 * el mensaje pasado por parametro.
	 * @param message		Mensaje a mostrar
	 */
	public static void actualizarDatos(String message) {
		CuentasxCobrar_DatabaseProxy DB = CuentasxCobrar_DatabaseProxy.getInstancia();
		modelo.setAllData(DB.getAllData());
		
		if(message == null)
			CuentasxCobrar.statusBar.setText("Datos Cargados, Leidas "+DB.getCantidadCuentasxCobrar()+" cuentas por cobrar.");
		else
			CuentasxCobrar.statusBar.setText(message);

		DB = null;
	}
	
	/**
	 * Metodo que retorna el ID de la cuenta por cobrar seleccionada
	 * en la tabla.
	 * @return		ID del producto seleccionado, "-1" si no esta
	 * 				seleccionado ningun campo
	 */
	public static String getIDCuentaxCobrarSeleccionada() {
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

		columnasTabla = tabla.getColumn("Factura");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(50);
		
		columnasTabla = tabla.getColumn("Cliente");
		columnasTabla.setPreferredWidth(200);

		JTextField fechaFld = new JTextField();
		fechaFld.addKeyListener(new ValidadorFecha());
		
		columnasTabla = tabla.getColumn("Fecha Facturacion");
		columnasTabla.setCellEditor(new DefaultCellEditor(fechaFld));
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
		
		JTextField numFld = new JTextField();
		numFld.addKeyListener(new ValidadorNumerico());
		
		columnasTabla= tabla.getColumn("Valor Excluido");
		columnasTabla.setCellEditor(new DefaultCellEditor(numFld));
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Valor Gravado");
		columnasTabla.setCellEditor(new DefaultCellEditor(numFld));
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Valor Iva");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla= tabla.getColumn("Total");
		columnasTabla.setCellRenderer(cellRenderer);
		
		columnasTabla = tabla.getColumn("Fecha Cancelacion");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
		
		columnasTabla = tabla.getColumn("Valor Cancelado");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
	}
}
