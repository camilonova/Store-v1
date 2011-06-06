package com.nova.remision.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.remision.log.builder.Remision_PanelBuilder;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static Remision_DatabaseProxy databaseProxy;
	
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
	private Remision_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static Remision_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new Remision_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de remisiones registradas en la base de datos.
	 */
	public int getCantidadRemisiones() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision` ");
			
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

		String[][] arrayData = new String[getCantidadRemisiones()][ParametrosGlobales.CANTIDAD_COLUMNAS_REMISION-1];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision` ORDER BY `"+
					ParametrosGlobales.COLUMNA_ORDEN_DATOS_REMISION+"` "+ParametrosGlobales.TIPO_ORDEN_DATOS_REMISION);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_REMISION-1; j++) {
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
	 * Metodo que retorna el numero de la ultima remision registrada
	 * en la base de datos
	 * @return		Numero de la ultima remision
	 */
	public int getNumeroUltimaRemision() {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision` ORDER BY `Remision` DESC ");
			if(resultadoOperacion.next())
				return Integer.parseInt(resultadoOperacion.getString("Remision"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Metodo que guarda una remision en la base de datos, con los parametros recibidos
	 * @param numeroRemision			Numero de la remision a guardar (Consecutivo)
	 * @param cliente					Cliente a quien se remiten los elementos
	 * @param fechaDespacho				Fecha del despacho
	 * @param fechaVencimiento			Fecha de cobro
	 * @param elementos					Elementos (ver ConvertirDatosTabla)
	 * @param cantidadElementos			Cantidad elementos en remision
	 * @param valorExcluido				Total Valor Excluido
	 * @param valorGravado				Total Valor Gravado
	 * @param valorIva					Total Valor Iva
	 * @param total						Total Venta
	 */
	public void guardarRemision(String numeroRemision, String cliente, String fechaDespacho, 
			String fechaVencimiento, String elementos, String cantidadElementos, String valorExcluido, 
			String valorGravado, String valorIva, String total) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `remision` ( `Remision` , `Cliente` , `Fecha Despacho` , " +
					"`Fecha Vencimiento` , `Elementos` , `Cantidad Elementos` , `Valor Excluido` , `Valor Gravado` , " +
					"`Valor Iva` , `Total` ) VALUES ('"+numeroRemision+"', '"+cliente+"', '"+fechaDespacho+"', '"+
					fechaVencimiento+"', '"+elementos+"', '"+cantidadElementos+"', '"+valorExcluido+"', '"+
					valorGravado+"', '"+valorIva+"', '"+total+"');");
			Remision_PanelBuilder.actualizarDatos("La remision #"+numeroRemision+" ha sido guardada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que actualiza una remision en la base de datos, con los parametros recibidos
	 * @param numeroRemision			Numero de la remision a guardar (Consecutivo)
	 * @param cliente					Cliente a quien se remiten los elementos
	 * @param fechaDespacho				Fecha del despacho
	 * @param fechaVencimiento			Fecha de cobro
	 * @param elementos					Elementos (ver ConvertirDatosTabla)
	 * @param cantidadElementos			Cantidad elementos en remision
	 * @param valorExcluido				Total Valor Excluido
	 * @param valorGravado				Total Valor Gravado
	 * @param valorIva					Total Valor Iva
	 * @param total						Total Venta
	 */
	public void updateRemision(String numeroRemision, String cliente, String fechaDespacho, 
			String fechaVencimiento, String elementos, String cantidadElementos, String valorExcluido, 
			String valorGravado, String valorIva, String total) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `remision` SET `Cliente` = '"+cliente+"', " +
					"`Fecha Despacho` = '"+fechaDespacho+"', `Fecha Vencimiento` = '"+fechaVencimiento+
					"', `Elementos` = '"+elementos+"', `Cantidad Elementos` = '"+cantidadElementos+"', "+
					"`Valor Excluido` = '"+valorExcluido+"', `Valor Gravado` = '"+valorGravado+"', `Valor Iva` = '"+
					valorIva+"', `Total` = '"+total+"' WHERE `Remision` ="+numeroRemision+" LIMIT 1 ;");
			Remision_PanelBuilder.actualizarDatos("La remision #"+numeroRemision+" ha sido actualizada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que carga una remision identificada por el parametro
	 * @param numeroRemision		Remision a cargar
	 * @return						Datos de la remision, almacenados en la base de datos
	 */
	public String[] cargarRemision(String numeroRemision) {
		String[] datos = new String[ParametrosGlobales.CANTIDAD_COLUMNAS_REMISION-2];
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision` WHERE `Remision` = "+numeroRemision+"");
			
			if(resultadoOperacion.next())
				// Obtenemos los datos de la tabla menos el primero y el ultimo que ya lo conocemos
				for(int i=1; i <= ParametrosGlobales.CANTIDAD_COLUMNAS_REMISION-2; i++)
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
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision`");
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
