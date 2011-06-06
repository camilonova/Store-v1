package com.nova.supplier.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.Supplier;
import com.nova.supplier.log.builder.Supplier_PanelBuilder;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 1.5
 */
public class Supplier_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static Supplier_DatabaseProxy databaseProxy;
	
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
	private Supplier_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static Supplier_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new Supplier_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de proveedores registrados en la base de datos.
	 */
	public int getCantidadProveedores() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier` ");
			
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

		String[][] arrayData = new String[getCantidadProveedores()][ParametrosGlobales.CANTIDAD_COLUMNAS_SUPPLIER];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier` ORDER BY `"+ParametrosGlobales.COLUMNA_ORDEN_DATOS_SUPPLIER+"` "+ParametrosGlobales.TIPO_ORDEN_DATOS_SUPPLIER);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_SUPPLIER; j++)
					arrayData[i][j] = resultadoOperacion.getString(j+1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayData;
	}
	
	/**
	 * Metodo que actualiza un campo del proveedor indentificado
	 * con el parametro ID. Ademas valida si el dato realmente
	 * fue actualizado en la base de datos.
	 * @param ID		ID del proveedor a actualizar
	 * @param columna	Columna del producto a actualizar
	 * @param dato		Dato a actualizar
	 * @return			String con el dato leido de la base de datos
	 */
	public String updateProveedor(String ID, int columna, String dato) {
		String datoLeido = "";
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier`");
			
			ResultSetMetaData metadata = resultadoOperacion.getMetaData();
			String nombreColumna = metadata.getColumnName(columna+1);

			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `supplier` SET `"+nombreColumna+
					"` = '"+dato+"' WHERE `ID` ="+ID+" LIMIT 1;");
			
			resultadoOperacion = operacion.executeQuery("SELECT `"+nombreColumna+"` " +
					"FROM `supplier` WHERE `ID` ="+ID+"");
			resultadoOperacion.next();
			datoLeido = resultadoOperacion.getString(metadata.getColumnName(columna+1)); 
			
			if(datoLeido.equals(dato))
				Supplier.statusBar.setText("Celda actualizada con el dato: "+dato);
			else
				Supplier.statusBar.setText("Ha ocurrido un error en la actualizacion. Ingrese un dato valido!!!");
		} catch (SQLException e) {
			e.printStackTrace();
			Supplier_PanelBuilder.actualizarDatos("Ha ocurrido un error fatal en la actualizacion. Ingrese un dato valido!!!");
		}
		return datoLeido;
	}
	
	/**
	 * Agrega un nuevo proveedor a la base de datos con los parametros recibidos
	 * @param nombreEmpresa				Nombre de la empresa
	 * @param nit						NIT de la empresa
	 * @param ciudad					Ciudad de origen de la empresa
	 * @param telefono					Telefono de la empresa
	 * @param fax						Fax de la empresa
	 * @param movil						Celular de la empresa
	 * @param direccion					Direccion de la empresa
	 * @param representante				Nombre del representante de la empresa
	 * @param telefonoContacto			Telefono del representante
	 * @param productos					Productos que ofrece la empresa
	 */
	public void nuevoProveedor(String nombreEmpresa, String nit, String ciudad, String telefono,
								String fax, String movil, String direccion, String representante,
								String telefonoContacto, String productos) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `supplier` ( `ID` , `Nombre Empresa` , " +
						"`NIT` , `Ciudad` , `Telefono` , `Fax` , `Movil` , `Direccion` , " +
						"`Representante` , `Telefono Contacto` , `Productos` ) " +
						"VALUES ('', '"+nombreEmpresa+"', '"+nit+"', '"+ciudad+"', " +
						"'"+telefono+"', '"+fax+"', '"+movil+"', '"+direccion+"', " +
						"'"+representante+"', '"+telefonoContacto+"', '"+productos+"');");
			Supplier_PanelBuilder.actualizarDatos("El proveedor "+nombreEmpresa+" ha sido agregado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que elimina un proveedor de la base de datos en base
	 * al parametro recibido.
	 * @param ID		ID del proveedor a eliminar
	 */
	public void eliminarProveedor(String ID) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("DELETE FROM `supplier` WHERE `ID` = "+ID+" LIMIT 1");
			Supplier_PanelBuilder.actualizarDatos("El proveedor # "+ID+" ha sido eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna la lista de los proveedores
	 * @return		Lista de los proveedores
	 */
	public String[] getListaProveedores() {
		String[] lista = new String[getCantidadProveedores()];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier` ORDER BY `Nombre Empresa` ASC");
			
			for(int i=0; i < lista.length; i++)
				if(resultadoOperacion.next())
					lista[i] = resultadoOperacion.getString("Nombre Empresa");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
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
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier`");
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
