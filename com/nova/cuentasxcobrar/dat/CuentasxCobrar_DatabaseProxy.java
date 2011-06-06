package com.nova.cuentasxcobrar.dat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_PanelBuilder;
import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.CuentasxCobrar;

/**
 * Esta clase representa un proxy entre la aplicacion y la base de datos en la cual
 * se encuentra la informacion de la aplicacion.
 * @author Camilo Nova
 * @version 2.0
 */
public class CuentasxCobrar_DatabaseProxy {
	
	/**
	 * Representa la instancia unica de la clase, se hace con el fin
	 * de que no existan mas instancias de esta clase <i>Singleton Pattern</i>.
	 */
	private static CuentasxCobrar_DatabaseProxy databaseProxy;
	
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
	private CuentasxCobrar_DatabaseProxy() { 
		conexion = DatabaseProxy.getInstancia().getConexion();
	}
	
	/**
	 * Metodo que retorna la instancia de la clase. Se asegura
	 * que exista unicamente una instancia de la clase utilizando
	 * el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase.
	 */
	public static CuentasxCobrar_DatabaseProxy getInstancia() {
		if(databaseProxy == null)
			databaseProxy = new CuentasxCobrar_DatabaseProxy();
		return databaseProxy;
	}
	
	/* **********************		METODOS DE ACCESO A DATOS		******************** */
	
	/**
	 * Retorna la cantidad de cuentas por cobrar registradas en la base de datos.
	 */
	public int getCantidadCuentasxCobrar() {
		int cantidad = 0;
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar` ");
			
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

		String[][] arrayData = new String[getCantidadCuentasxCobrar()][ParametrosGlobales.CANTIDAD_COLUMNAS_CUENTASxCOBRAR];
		
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar` ORDER BY `"+
					ParametrosGlobales.COLUMNA_ORDEN_DATOS_CUENTASxCOBRAR+"` "+
					ParametrosGlobales.TIPO_ORDEN_DATOS_CUENTASxCOBRAR);

			for(int i=0; resultadoOperacion.next(); i++)
				for(int j=0; j < ParametrosGlobales.CANTIDAD_COLUMNAS_CUENTASxCOBRAR; j++)
					arrayData[i][j] = resultadoOperacion.getString(j+1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayData;
	}
	
	/**
	 * Metodo que crea una cuenta por cobrar en la base de datos
	 * @param numeroFacturaCredito			Numero de la factura
	 * @param cliente						Cliente
	 * @param fechaFacturacion				Fecha de facturacion
	 * @param valorExcluido					Total valor excluido
	 * @param valorGravado					Total valor gravado
	 * @param valorIva						Total valor iva
	 * @param total							Total
	 */
	public void nuevaCuentaxCobrar(String numeroFacturaCredito, String cliente, String fechaFacturacion, 
			String valorExcluido, String valorGravado, String valorIva, String total) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("INSERT INTO `cuentasxcobrar` ( `Factura` , `Cliente` , `Fecha Facturacion` , " +
					"`Valor Excluido` , `Valor Gravado` , `Valor Iva` , `Total` , `Fecha Cancelacion` , " +
					"`Valor Cancelado` ) VALUES ('"+numeroFacturaCredito+"', '"+cliente+"', '"+fechaFacturacion+
					"', '"+valorExcluido+"', '"+valorGravado+"', '"+valorIva+"', '"+total+"', '', '');");
			CuentasxCobrar_PanelBuilder.actualizarDatos("La factura #"+numeroFacturaCredito+" ha sido guardada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que actualiza un campo de la factura indentificada
	 * con el parametro. Ademas valida si el dato realmente
	 * fue actualizado en la base de datos.
	 * @param factura	Factura a actualizar
	 * @param columna	Columna del producto a actualizar
	 * @param dato		Dato a actualizar
	 * @return			String con el dato leido de la base de datos
	 */
	public String updateCuentaxCobrar(String factura, int columna, String dato) {
		String datoLeido = "";
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar`");
			
			ResultSetMetaData metadata = resultadoOperacion.getMetaData();
			String nombreColumna = metadata.getColumnName(columna+1);

			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `cuentasxcobrar` SET `"+nombreColumna+
					"` = '"+dato+"' WHERE `Factura` ="+factura+" LIMIT 1;");
			
			calcularTotales(factura);
			
			resultadoOperacion = operacion.executeQuery("SELECT `"+nombreColumna+"` " +
					"FROM `cuentasxcobrar` WHERE `Factura` ="+factura+"");
			resultadoOperacion.next();
			datoLeido = resultadoOperacion.getString(metadata.getColumnName(columna+1)); 
			
			if(datoLeido.equals(dato))
				CuentasxCobrar.statusBar.setText("Celda actualizada con el dato: "+dato);
			else
				CuentasxCobrar.statusBar.setText("Ha ocurrido un error en la actualizacion. Ingrese un dato valido!!!");
		} catch (SQLException e) {
			e.printStackTrace();
			CuentasxCobrar_PanelBuilder.actualizarDatos("Ha ocurrido un error fatal en la actualizacion. Ingrese un dato valido!!!");
		}
		return datoLeido;
	}
	
	/**
	 * Metodo que cancela una cuenta por cobrar en la base de datos, con los parametros recibidos
	 * @param numeroFacturaCredito			Numero de la factura
	 * @param fechaCancelacion				Fecha de cancelacion
	 * @param valorCancelado				Total cancelado
	 */
	public void cancelarCuentaxCobrar(String numeroFacturaCredito, String fechaCancelacion, String valorCancelado) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate("UPDATE `cuentasxcobrar` SET `Fecha Cancelacion` = '"+fechaCancelacion+
					"', `Valor Cancelado` = '"+valorCancelado+"' WHERE `Factura` ="+numeroFacturaCredito+" LIMIT 1 ;");
			CuentasxCobrar_PanelBuilder.actualizarDatos("La factura #"+numeroFacturaCredito+" ha sido cancelada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que recalcula los totales de la factura ingresada
	 * @param factura		Factura a recalcular
	 */
	private void calcularTotales(String factura) {
		try {
			operacion = conexion.createStatement();
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar` WHERE `Factura` ="+factura+"");
			
			if(resultadoOperacion.next()) {
				int excluido = Integer.parseInt(resultadoOperacion.getString("Valor Excluido"));
				int gravado = Integer.parseInt(resultadoOperacion.getString("Valor Gravado"));

				double imp = Double.parseDouble("1.16");
				int result = (int) Math.round(gravado*imp);
				String iva = String.valueOf(Math.abs(gravado-result));
				String total = String.valueOf(excluido + gravado + Math.abs(gravado-result));
				
				operacion.executeUpdate("UPDATE `cuentasxcobrar` SET `Valor Iva` = '"+iva+
					"', `Total` = '"+total+"' WHERE `Factura` ="+factura+" LIMIT 1 ;");
				
			}
			CuentasxCobrar_PanelBuilder.actualizarDatos("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			resultadoOperacion = operacion.executeQuery("SELECT * FROM `cuentasxcobrar`");
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
