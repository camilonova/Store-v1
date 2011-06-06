package com.nova.formato.dat;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.nova.calc.gui.Calc_PanelBuilder;
import com.nova.formato.log.builder.Formato_Tabla;
import com.nova.stock.dat.Stock_DatabaseProxy;

/**
 * Esta clase representa el modelo de la tabla, con lo cual se convierte
 * en el patron a seguir de la tabla, si se quisiera modificar alguna
 * caracteristica de la tabla, se debe hacer desde aqui.
 * @author Camilo Nova
 * @version 1.0
 */
public class Formato_ModeloTabla extends AbstractTableModel {

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
	 * Representa la tabla de la cual es modelo
	 */
	private Formato_Tabla tabla;
	
	/**
	 * Representa si la tabla es editable o no
	 */
	private boolean isEditable = true;
	
	/**
	 * Constructor de la clase. Le da el nombre a las columnas
	 * e inicializa las variables que almacenan los datos.
	 *
	 */
	public Formato_ModeloTabla() {
		encabezados = new String[6];
		datos = new String[0][encabezados.length];
		
		encabezados[0] = "Cantidad";
		encabezados[1] = "Descripcion";
		encabezados[2] = "Valor Unitario";
		encabezados[3] = "Gravado";
		encabezados[4] = "Excluido";
		encabezados[5] = "Valor Total";
		fireTableStructureChanged();
	}

	/**
	 * Metodo que obtiene una instancia de la tabla.
	 * @param tablaGUI		Tabla que es dueña del modelo
	 */
	public void setTabla(Formato_Tabla tablaGUI) {
		tabla = tablaGUI;
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
	 * Son editables las columnas de cantidad y precio unitario
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(isEditable && (columnIndex == 0 || columnIndex == 2))
			return true;
		return false;
	}
	
	/**
	 * Metodo que actualiza la informacion de una celda.
	 * Solamente se actualizan las columnas de cantidad y precio unitario
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String[] producto = ((String) datos[rowIndex][1]).split(" - ");

		// Para cuando se modifica la cantidad
		if(columnIndex == 0) {
			Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
			String iva = DB.getImpuestoProducto(producto[0]);

			String base_iva[] = new Calc_PanelBuilder().calcularPrecioConIva(datos[rowIndex][2], iva);

			datos[rowIndex][0] = (String) aValue;
			
			if(!datos[rowIndex][3].equals(" "))
				datos[rowIndex][3] = String.valueOf(base_iva[0]);
			else
				datos[rowIndex][4] = datos[rowIndex][2];
			
			datos[rowIndex][5] = String.valueOf(Integer.parseInt(base_iva[0]) * Integer.parseInt(((String) aValue)));
		}
		// Para cuando se modifica el precio unitario
		else if(columnIndex == 2) {
			Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
			String iva = DB.getImpuestoProducto(producto[0]);

			String base_iva[] = new Calc_PanelBuilder().calcularPrecioConIva((String) aValue, iva);

			datos[rowIndex][2] = (String) aValue;
			
			if(!datos[rowIndex][3].equals(" "))
				datos[rowIndex][3] = base_iva[0];
			else
				datos[rowIndex][4] = datos[rowIndex][2];
			
			datos[rowIndex][5] = String.valueOf(Integer.parseInt(base_iva[0]) * Integer.parseInt((datos[rowIndex][0])));
		}
		tabla.calcularTotalTabla(String.valueOf(datos.length), calcularSubtotal(), calcularTotalExcluido(), calcularTotalGravado(), calcularTotalIva());
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que agrega un nuevo elemento a la lista de elementos de la cotizacion,
	 * ademas calcula el total de la venta neta y de impuestos.
	 * @param cantidad				Cantidad del elemento cotizado
	 * @param descripcion			Descripcion del elemento
	 * @param valorUnitario			Valor Unitario del elemento
	 * @param iva					% de Iva del elemento
	 */
	public void setNewData(String cantidad, String descripcion, String valorUnitario, String iva) {
		if(cantidad.length() == 0 || valorUnitario.length() == 0) {
			JOptionPane.showMessageDialog(null, "Falta ingresar un dato!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String[][] datosNuevos = new String[datos.length+1][encabezados.length];
		
		for(int i=0; i < datosNuevos.length; i++)
			if(i == datosNuevos.length-1) {
				String base_iva[] = new Calc_PanelBuilder().calcularPrecioConIva(valorUnitario, iva);

				datosNuevos[i][0] = cantidad;
				datosNuevos[i][1] = descripcion;
				datosNuevos[i][2] = valorUnitario;
				datosNuevos[i][3] = " ";
				datosNuevos[i][4] = " ";
				
				if(base_iva[1].equals("0"))
					datosNuevos[i][4] = base_iva[0];
				else
					datosNuevos[i][3] = base_iva[0];
				
				datosNuevos[i][5] = String.valueOf(Integer.parseInt(base_iva[0]) * Integer.parseInt(cantidad));
			}
			else
				for(int j=0; j < encabezados.length; j++)
					datosNuevos[i][j] = datos[i][j];
		
		datos = datosNuevos;
		tabla.calcularTotalTabla(String.valueOf(datos.length), calcularSubtotal(), calcularTotalExcluido(), calcularTotalGravado(), calcularTotalIva());
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que elimina la fila pasada por parametro de los datos
	 * @param fila			Fila a eliminar de los datos
	 */
	public void eliminarFila(int fila) {
		if(fila == -1) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String[][] datosNuevos = new String[datos.length-1][encabezados.length];
		
		for(int i=0; i < datosNuevos.length; i++)
			if(fila <= i)
				for(int j=0; j < encabezados.length; j++)
					datosNuevos[i][j] = datos[i+1][j];
			else
				for(int j=0; j < encabezados.length; j++)
					datosNuevos[i][j] = datos[i][j];
		
		datos = datosNuevos;
		tabla.calcularTotalTabla(String.valueOf(datos.length), calcularSubtotal(), calcularTotalExcluido(), calcularTotalGravado(), calcularTotalIva());
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que elimina todos los datos de la tabla.
	 */
	public void eliminarTodo() {
		datos = new String[0][encabezados.length];
		tabla.calcularTotalTabla(String.valueOf(datos.length), calcularSubtotal(), calcularTotalExcluido(), calcularTotalGravado(), calcularTotalIva());
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que calcula el valor del subtotal,
	 * el cual es la suma de los totales en el
	 * formato.
	 * @return		Valor Subtotal
	 */
	private String calcularSubtotal() {
		int valor = 0;
		for(int i=0; i < datos.length; i++)
			valor += Integer.parseInt(datos[i][5]);
		
		return String.valueOf(valor);
	}

	/**
	 * Metodo que calcula el valor del total
	 * de los productos excluidos de impuesto
	 * @return		Valor Total Excluido
	 */
	private String calcularTotalExcluido() {
		int valor = 0;
		for(int i=0; i < datos.length; i++)
			if(!datos[i][4].equals(" "))
				valor += Integer.parseInt(datos[i][5]);
		
		return String.valueOf(valor);
	}

	/**
	 * Metodo que calcula el valor del total
	 * de los productos gravados con impuesto
	 * @return		Valor Total Gravado
	 */
	private String calcularTotalGravado() {
		int valor = 0;
		for(int i=0; i < datos.length; i++)
			if(!datos[i][3].equals(" "))
				valor += Integer.parseInt(datos[i][5]);
		
		return String.valueOf(valor);
	}
	
	/**
	 * Metodo que calcula el valor del iva
	 * el cual se toma como base el valor
	 * total gravado
	 * @return
	 */
	private String calcularTotalIva() {
		// Tomamos por defecto un iva de 16% para todos los productos
		int result = (int) Math.round(Integer.parseInt(calcularTotalGravado())*0.16);

		return String.valueOf(result);
	}

	/**
	 * Metodo que retorna los datos de la tabla
	 * @return		Datos de la tabla
	 */
	public String[][] getAllData() {
		return datos;
	}
	
	/**
	 * Metodo que carga los datos pasados por parametro
	 * a la tabla
	 * @param newData		Datos a cargar
	 */
	public void setAllData(String[][] newData) {
		datos = newData;
	}
	
	/**
	 * Metodo que determina si la tabla es modificable
	 * o no
	 * @param b		True para permitir modificaciones, False de lo contrario
	 */
	public void setEditable(boolean b) {
		isEditable = b;
	}
}
