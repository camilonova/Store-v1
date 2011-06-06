package com.nova.estimed.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.estimed.log.builder.Estimed_PanelBuilder;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 2.0
 */
public class Estimed_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static Estimed_DatabaseProxy databaseProxy;
	
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
	private Estimed_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static Estimed_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new Estimed_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de cotizaciones registradas en la base de datos.
	 */
	public int getCantidadCotizaciones() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed` ");
			
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

		String[][] arrayData = new String[getCantidadCotizaciones()][ParametrosGlobales.CANTIDAD_COLUMNAS_ESTIMED-1];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed` ORDER BY `"+ParametrosGlobales.COLUMNA_ORDEN_DATOS_ESTIMED+"` "+ParametrosGlobales.TIPO_ORDEN_DATOS_ESTIMED);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_ESTIMED-1; j++) {
					if(j+1 < 5)
						arrayData[i][j] = resultadoOperacion.getString(j+1);
					// Nos saltamos la columna de elementos, la cual no queremos mostrar
					else
						arrayData[i][j] = resultadoOperacion.getString(j+2);
				}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayData;
	}
	
	/**
	 * Metodo que retorna el numero de la ultima cotizacion registrada
	 * en la base de datos
	 * @return		Numero de la ultima cotizacion, sumar 1 para obtener el numero siguiente
	 */
	public int getNumeroUltimaCotizacion() {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed` ORDER BY `Cotizacion` DESC ");
			if(resultadoOperacion.next())
				return Integer.parseInt(resultadoOperacion.getString("Cotizacion"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Metodo que guarda una cotizacion en la base de datos, con los parametros recibidos
	 * @param numeroCotizacion			Numero de la cotizacion a guardar (Consecutivo)
	 * @param cliente					Cliente a quien se remite la cotizacion
	 * @param fecha						Fecha de la cotizacion
	 * @param validez					Validez de la cotizacion
	 * @param elementos					Elementos (ver ConvertirDatosTabla)
	 * @param cantidadElementos			Cantidad elementos cotizados
	 * @param valorExcluido				Total Valor Excluido
	 * @param valorGravado				Total Valor Gravado
	 * @param valorIva					Total Valor Iva
	 * @param total						Total Venta
	 */
	public void guardarCotizacion(String numeroCotizacion, String cliente, String fecha, String validez, String elementos,
									String cantidadElementos, String valorExcluido, String valorGravado, String valorIva, 
									String total) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `estimed` ( `Cotizacion` , `Cliente` , `Fecha` , " +
					"`Validez` , `Elementos` , `Cantidad Elementos` , `Valor Excluido` , `Valor Gravado` , " +
					"`Valor Iva` , `Total` ) VALUES ('"+numeroCotizacion+"', '"+cliente+"', '"+fecha+"', '"+
					validez+"', '"+elementos+"', '"+cantidadElementos+"', '"+valorExcluido+"', '"+valorGravado+
					"', '"+valorIva+"', '"+total+"');");
			Estimed_PanelBuilder.actualizarDatos("La cotizacion #"+numeroCotizacion+" ha sido guardada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que actualiza una cotizacion en la base de datos, con los parametros recibidos
	 * @param numeroCotizacion			Numero de la cotizacion a actualizar (Consecutivo)
	 * @param cliente					Cliente a quien se remite la cotizacion
	 * @param fecha						Fecha de la cotizacion
	 * @param validez					Validez de la cotizacion
	 * @param elementos					Elementos (ver ConvertirDatosTabla)
	 * @param cantidadElementos			Cantidad elementos cotizados
	 * @param valorExcluido				Total Valor Excluido
	 * @param valorGravado				Total Valor Gravado
	 * @param valorIva					Total Valor Iva
	 * @param total						Total Venta
	 */
	public void actualizarCotizacion(String numeroCotizacion, String cliente, String fecha, String validez, String elementos,
										String cantidadElementos, String valorExcluido, String valorGravado, String valorIva, 
										String total) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `estimed` SET `Cliente` = '"+cliente+"', " +
					"`Fecha` = '"+fecha+"', `Validez` = '"+validez+"', `Elementos` = '"+elementos+"', `Cantidad Elementos` = '"+cantidadElementos+"', " +
					"`Valor Excluido` = '"+valorExcluido+"', `Valor Gravado` = '"+valorGravado+"', `Valor Iva` = '"+valorIva+"', `Total` = '"+total+
					"' WHERE `Cotizacion` ="+numeroCotizacion+" LIMIT 1 ;");
			Estimed_PanelBuilder.actualizarDatos("La cotizacion #"+numeroCotizacion+" ha sido actualizada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que carga una cotizacion identificada por el parametro
	 * @param numeroCotizacion		Cotizacion a cargar
	 * @return						Datos de la cotizacion, almacenados en la base de datos
	 */
	public String[] cargarCotizacion(String numeroCotizacion) {
		String[] datos = new String[ParametrosGlobales.CANTIDAD_COLUMNAS_ESTIMED-2];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed` WHERE `Cotizacion` = "+numeroCotizacion+"");
			
			if(resultadoOperacion.next())
				// Obtenemos los datos de la tabla menos el primero y el ultimo que ya lo conocemos
				for(int i=1; i <= ParametrosGlobales.CANTIDAD_COLUMNAS_ESTIMED-2; i++)
					datos[i-1] = resultadoOperacion.getString(i+1);
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
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed`");
			ResultSetMetaData metaData = resultadoOperacion.getMetaData();
			datos = new String[metaData.getColumnCount()-1];
			
			for(int j=0; j < datos.length; j++) {
				if(j+1 < 5)
					datos[j] = metaData.getColumnName(j+1);
				// Nos saltamos la columna de elementos, la cual no queremos mostrar
				else
					datos[j] = metaData.getColumnName(j+2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}
}
