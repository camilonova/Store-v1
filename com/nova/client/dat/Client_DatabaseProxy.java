package com.nova.client.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.nova.client.log.builder.Client_PanelBuilder;
import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.Client;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 1.5
 */
public class Client_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static Client_DatabaseProxy databaseProxy;
	
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
	private Client_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static Client_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new Client_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de clientes registrados en la base de datos.
	 */
	public int getCantidadClientes() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client` ");
			
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

		String[][] arrayData = new String[getCantidadClientes()][ParametrosGlobales.CANTIDAD_COLUMNAS_CLIENT];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client` ORDER BY `"+ParametrosGlobales.COLUMNA_ORDEN_DATOS_CLIENT+"` "+ParametrosGlobales.TIPO_ORDEN_DATOS_CLIENT);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_CLIENT; j++)
					arrayData[i][j] = resultadoOperacion.getString(j+1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayData;
	}
	
	/**
	 * Metodo que actualiza un campo del cliente indentificado
	 * con el parametro ID. Ademas valida si el dato realmente
	 * fue actualizado en la base de datos.
	 * @param ID		ID del cliente a actualizar
	 * @param columna	Columna del producto a actualizar
	 * @param dato		Dato a actualizar
	 * @return			String con el dato leido de la base de datos
	 */
	public String updateCliente(String ID, int columna, String dato) {
		String datoLeido = "";
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client`");
			
			ResultSetMetaData metadata = resultadoOperacion.getMetaData();
			String nombreColumna = metadata.getColumnName(columna+1);

			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `client` SET `"+nombreColumna+
					"` = '"+dato+"' WHERE `ID` ="+ID+" LIMIT 1;");
			
			resultadoOperacion = operacion.executeQuery("SELECT `"+nombreColumna+"` " +
					"FROM `client` WHERE `ID` ="+ID+"");
			resultadoOperacion.next();
			datoLeido = resultadoOperacion.getString(metadata.getColumnName(columna+1)); 
			
			if(datoLeido.equals(dato))
				Client.statusBar.setText("Celda actualizada con el dato: "+dato);
			else
				Client.statusBar.setText("Ha ocurrido un error en la actualizacion. Ingrese un dato valido!!!");
		} catch (SQLException e) {
			e.printStackTrace();
			Client_PanelBuilder.actualizarDatos("Ha ocurrido un error fatal en la actualizacion. Ingrese un dato valido!!!");
		}
		return datoLeido;
	}
	
	/**
	 * Metodo que agrega un nuevo cliente a la base de datos.
	 * @param nombre		Nombre del cliente
	 * @param nit			NIT del cliente
	 * @param ciudad		Ciudad del cliente
	 * @param telefono		Telefono del cliente
	 * @param fax			Fax del cliente
	 * @param movil			Celular del cliente
	 * @param direccion		Direccion del cliente
	 */
	public void nuevoCliente(String nombre, String nit, String ciudad, String telefono,
								String fax, String movil, String direccion) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `client` ( `ID` , `Nombre` , " +
						"`NIT` , `Ciudad` , `Telefono` , `Fax` , `Movil` , `Direccion` ) " +
						"VALUES ('', '"+nombre+"', '"+nit+"', '"+ciudad+"', " +
						"'"+telefono+"', '"+fax+"', '"+movil+"', '"+direccion+"');");
			Client_PanelBuilder.actualizarDatos("El cliente "+nombre+" ha sido agregado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que elimina un cliente de la base de datos en base
	 * al parametro recibido.
	 * @param ID		ID del cliente a eliminar
	 */
	public void eliminarCliente(String ID) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("DELETE FROM `client` WHERE `ID` = "+ID+" LIMIT 1");
			Client_PanelBuilder.actualizarDatos("El cliente # "+ID+" ha sido eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna la lista de los clientes
	 * @return		Lista de los clientes
	 */
	public String[] getListaClientes() {
		String[] lista = new String[getCantidadClientes()];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client` ORDER BY `Nombre` ASC");
			
			for(int i=0; i < lista.length; i++)
				if(resultadoOperacion.next())
					lista[i] = resultadoOperacion.getString("Nombre");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Metodo que retorna los datos adicionales del cliente.
	 * En este mismo orden direccion, nit, telefono y ciudad.
	 * @param nombreCliente		Nombre del cliente
	 * @return			String[4] con los datos adicionales
	 */
	public String[] getDatosCliente(String nombreCliente) {
		String[] datos = new String[4];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client` WHERE `Nombre` = '"+nombreCliente+"'");
			
			if(resultadoOperacion.next()) {
				datos[0] = resultadoOperacion.getString("Direccion");
				datos[1] = resultadoOperacion.getString("NIT");
				datos[2] = resultadoOperacion.getString("Telefono");
				datos[3] = resultadoOperacion.getString("Ciudad");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
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
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client`");
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
