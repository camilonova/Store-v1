package com.nova.dat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static DatabaseProxy databaseProxy;
	
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
	 * El constructor intenta crear una conexion con el servidor de la base de datos
	 * utilizando el driver respectivo, que en este caso es de un servidor MySqL.<p>
	 * Primero intenta hacer la conexion remota a la IP registrada en los parametros
	 * globales de la aplicacion, si no es posible, entonces intenta una conexion local.
	 * 
	 */
	private DatabaseProxy() { 
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(ParametrosGlobales.DATABASE_REMOTE_SOURCE, ParametrosGlobales.DATABASE_USER, ParametrosGlobales.DATABASE_PASS);
		} catch (SQLException e) {
			System.out.println("Origen de datos remoto no disponible, intentando el local...");
			try {
				conexion = DriverManager.getConnection(ParametrosGlobales.DATABASE_LOCAL_SOURCE, ParametrosGlobales.DATABASE_USER, ParametrosGlobales.DATABASE_PASS);
			} catch (SQLException e1) {
				System.err.println("Origen de datos no disponible");
				e.printStackTrace();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		leerCantidadColumnasDB();
	}
	
	/**
	 * Metodo que valida si el usuario y el password pasados por parametro
	 * son los correspondientes en la base de datos.
	 * @param user		usuario en la base de datos
	 * @param pass		password en la base de datos
	 * @return			True si el usuario y el password son validos, False de lo contrario
	 */
	public boolean validarUsuario(String user, String pass) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `users` " +
					"WHERE 1 AND `Usuario` = '"+user+"' AND " +
					"`Password` = '"+pass+"' LIMIT 0,1");
			
			if(resultadoOperacion.next()) {
				ParametrosGlobales.USER_ID = resultadoOperacion.getString("ID");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Metodo que inicia la sesion del usuario en la base de datos.
	 * Actualiza el campo de la ultima sesion con el de la fecha actual.
	 *
	 */
	public void iniciarSesion() {
		String FechaHora = DateFormat.getDateInstance(0).format(new Date()) +" "+ DateFormat.getTimeInstance().format(new Date());
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `users` SET " +
					"`UltimaSesion` = '"+FechaHora+"' WHERE `ID` = '"+ParametrosGlobales.USER_ID+"' LIMIT 1 ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que retorna la informacion de inicio de sesion
	 * del usuario validado.
	 * @return		Cadena con la informacion de inicio de sesion.
	 */
	public String getSesionData() {
		String info = "Bienvenido ";
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `users` " +
					"WHERE 1 AND `ID` = '"+ParametrosGlobales.USER_ID+"' LIMIT 0,1");
			if(resultadoOperacion.next()) {
				info += resultadoOperacion.getString("Nombre");
				info += ", su ultimo acceso fue el ";
				info += resultadoOperacion.getString("UltimaSesion");
				// Recuperamos el tipo de acceso del usuario
				ParametrosGlobales.TIPO_ACCESO = resultadoOperacion.getString("TipoAcceso");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return info;
	}

	/**
	 * Este metodo cierra todos los flujos abiertos durante la conexion a la base de 
	 * datos y cierra la conexion liberando toda la memoria utilizada.<p>
	 * Debe ser llamado al cerrar la aplicacion.
	 */
	public void desconectar() {
		try {
			if(resultadoOperacion != null)
				resultadoOperacion.close();
			if(operacion != null)
				operacion.close();
			if(conexion != null)
				conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que retorna la conexion de la base de datos
	 * @return		Conexion a la base de datos
	 */
	public Connection getConexion() {
		return conexion;
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new DatabaseProxy();
		return databaseProxy;
	}
	
	/**
	 * Metodo que lee dinamicamente el tamaño de las tablas
	 * que utiliza la aplicacion.
	 */
	private void leerCantidadColumnasDB() {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `stock`");
			ResultSetMetaData metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_STOCK = metaData.getColumnCount();
			
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `client`");
			metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_CLIENT = metaData.getColumnCount();
			
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `supplier`");
			metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_SUPPLIER = metaData.getColumnCount();
			
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `estimed`");
			metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_ESTIMED = metaData.getColumnCount();
			
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `remision`");
			metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_REMISION = metaData.getColumnCount();

			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar`");
			metaData = resultadoOperacion.getMetaData();
			ParametrosGlobales.CANTIDAD_COLUMNAS_CUENTASxCOBRAR = metaData.getColumnCount();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
