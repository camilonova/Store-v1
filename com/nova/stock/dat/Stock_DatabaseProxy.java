package com.nova.stock.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.Stock;
import com.nova.stock.log.builder.Stock_PanelBuilder;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 2.0
 */
public class Stock_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static Stock_DatabaseProxy databaseProxy;
	
	/**
	 * Representa la conexion con la base de datos.
	 */
	private Connection conexion;
	
	/**
	 * Representa la operacion hecha en la base de datos
	 */
	private Statement operacion;
	
	/**
	 * Representa el resultado de la operacion hecha.
	 */
	private ResultSet resultadoOperacion;
	
	/**
	 * Constructor privado de la clase. Solo puede ser instanciada mediante el
	 * llamado al metodo <i>getInstancia</i> de esta misma clase.
	 *
	 */
	private Stock_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static Stock_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new Stock_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de elementos registrados en la base de datos.
	 */
	public int getCantidadElementos() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` ");
			
			while(resultadoOperacion.next())
				cantidad++;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantidad;
	}
	
	/**
	 * Metodo que obtiene los datos de la base de datos y los convierte
	 * a formato compatible con la tabla para ser mostrados.
	 * @return				Datos para mostrar en la tabla
	 */
	public String[][] getAllData() {
		Vector data = new Vector();
		Vector allData = new Vector();

		String[][] arrayData = new String[getCantidadElementos()][ParametrosGlobales.CANTIDAD_COLUMNAS_STOCK];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` ORDER BY `"+ParametrosGlobales.COLUMNA_ORDEN_DATOS_STOCK+"` "+ParametrosGlobales.TIPO_ORDEN_DATOS_STOCK);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_STOCK; j++)
					arrayData[i][j] = resultadoOperacion.getString(j+1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayData;
	}
	
	/**
	 * Metodo que actualiza un campo del producto indentificado
	 * con el parametro ID. Ademas valida si el dato realmente
	 * fue actualizado en la base de datos.
	 * @param ID		ID del producto a actualizar
	 * @param columna	Columna del producto a actualizar
	 * @param dato		Dato a actualizar
	 * @return			String con el dato leido de la base de datos
	 */
	public String modificarProducto(String ID, int columna, String dato) {
		String datoLeido = "";
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock`");
			
			ResultSetMetaData metadata = resultadoOperacion.getMetaData();
			String nombreColumna = metadata.getColumnName(columna+1);

			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `stock` SET `"+nombreColumna+
					"` = '"+dato+"' WHERE `ID` ="+ID+" LIMIT 1;");
			
			resultadoOperacion = operacion.executeQuery("SELECT `"+nombreColumna+"` " +
					"FROM `stock` WHERE `ID` ="+ID+"");
			if(resultadoOperacion.next())
				datoLeido = resultadoOperacion.getString(metadata.getColumnName(columna+1)); 
			
			if(datoLeido.equals(dato))
				Stock.statusBar.setText("Celda actualizada con el dato: "+dato);
			else
				Stock.statusBar.setText("Ha ocurrido un error en la actualizacion. Ingrese un dato valido!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datoLeido;
	}
	
	/**
	 * Metodo que inserta una nuevo producto en la base de datos.
	 * @param referencia			Referencia del producto
	 * @param cantidad				Cantidad de elementos del producto
	 * @param descripcion			Descripcion del producto
	 * @param marca					Marca del producto
	 * @param presentacion			Presentacion del producto
	 * @param impuesto				Impuesto del producto
	 * @param precioPublico			Precio Publico del producto
	 * @param precioDescuento		Precio Mayorista del producto
	 * @param proveedor				Proveedor del producto
	 * @param costo					Costo del producto
	 * @param observaciones			Observaciones del producto
	 * @param codigo				Codigo del producto (P.E. Codigo de Barras)
	 */
	public void nuevoProducto(String referencia, String cantidad, String descripcion, String marca, 
								String presentacion, String impuesto, String precioPublico, String precioDescuento, 
								String proveedor, String costo, String observaciones, String codigo) {
		if(isProductoRegistrado(descripcion, marca)) {
			// Si el producto ya esta registrado, no lo registra
			JOptionPane.showMessageDialog(null, "El producto ya existe en el inventario!!!", 
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `stock` ( `ID` , `Referencia` , " +
						"`Cantidad` , `Descripcion` , `Marca` , `Presentacion` , " +
						"`Impuesto` , `Precio Publico` , `Precio Mayorista` , " +
						"`Proveedor` , `Costo` , `Observaciones` , `Codigo` ) " +
						"VALUES ('', '"+referencia+"', '"+cantidad+"', '"+descripcion+"', " +
						"'"+marca+"', '"+presentacion+"', '"+impuesto+"', '"+precioPublico+"', " +
						"'"+precioDescuento+"', '"+proveedor+"', '"+costo+"', '"+observaciones+"', '"+codigo+"');");
			Stock_PanelBuilder.actualizarDatos("El producto "+descripcion+" ha sido agregado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que elimina un elemento de la base de datos en base
	 * al parametro recibido.
	 * @param ID		ID del elemento a eliminar
	 */
	public void eliminarProducto(String ID) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("DELETE FROM `stock` WHERE `ID` = "+ID+" LIMIT 1");
			Stock_PanelBuilder.actualizarDatos("El producto # "+ID+" ha sido eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna la lista de los elementos y su marca respectiva
	 * @return		Lista de los elementos
	 */
	public String[] getListaElementos() {
		String[] lista = new String[getCantidadElementos()];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` ORDER BY `Descripcion` ASC");
			
			for(int i=0; i < lista.length; i++)
				if(resultadoOperacion.next())
					lista[i] = resultadoOperacion.getString("Descripcion")+" - "+resultadoOperacion.getString("Marca");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Metodo que retorna el precio publico del producto enviado por
	 * parametro
	 * @param producto		Producto registrado en la base de datos
	 * @param marca			Marca del producto
	 * @return				Precio publico del producto
	 */
	public String getPrecioProducto(String producto, String marca) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` WHERE `Descripcion` = '"+
					producto+"' AND `Marca` = '"+marca+"'");
			
			if(resultadoOperacion.next())
				return resultadoOperacion.getString("Precio Publico");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Metodo que retorna el impuesto del producto enviado por
	 * parametro
	 * @param producto		Producto registrado en la base de datos
	 * @return				Impuesto del producto
	 */
	public String getImpuestoProducto(String producto) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` WHERE `Descripcion` = '"+producto+"'");
			
			if(resultadoOperacion.next())
				return resultadoOperacion.getString("Impuesto");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Metodo que retorna la marca del producto recibido por
	 * parametro
	 * @param string		Producto registrado en la base de datos
	 * @return				Marca del producto
	 */
	public String getMarca(String producto) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` WHERE `Descripcion` = '"+producto+"'");
			
			if(resultadoOperacion.next())
				return resultadoOperacion.getString("Marca");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Metodo que retorna la presentacion del producto recibido por
	 * parametro
	 * @param string		Producto registrado en la base de datos
	 * @return				Presentacion del producto
	 */
	public String getPresentacion(String producto) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` WHERE `Descripcion` = '"+producto+"'");
			
			if(resultadoOperacion.next())
				return resultadoOperacion.getString("Presentacion");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Metodo que valida si el producto y la marca pasados por parametro
	 * estan registrados en la base de datos
	 * @param descripcion			Descripcion del producto
	 * @param marca					Marca del producto
	 * @return						True si esta registrado, False de lo contrario
	 */
	public boolean isProductoRegistrado(String descripcion, String marca) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock` WHERE `Descripcion` LIKE '"+descripcion
														+"' AND `Marca` LIKE '"+marca+"'");
			
			if(resultadoOperacion.next())
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Metodo que retorna el nombre de las columnas de la
	 * tabla
	 * @return		String[] con los nombres de las columnas
	 */
	public String[] getNombresColumnas() {
		String[] datos = null;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock`");
			ResultSetMetaData metaData = resultadoOperacion.getMetaData();
			datos = new String[metaData.getColumnCount()];
			
			for (int i=0; i < datos.length; i++)
				datos[i] = metaData.getColumnName(i+1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}
}
