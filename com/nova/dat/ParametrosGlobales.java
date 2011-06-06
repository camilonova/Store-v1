package com.nova.dat;

/**
 * Esta clase representa, los datos del dueño de la aplicacion y los datos
 * principales para la ejecucion.<p>
 * Los datos pueden ser accedidos mediante llamados estaticos a la clase
 * incluso si se necesita cambiarlos se pueden instanciar los permitidos.
 * @author Camilo Nova
 * @version 1.5
 */
public abstract class ParametrosGlobales {
	
	/**
	 * Representa la nombre de usuario de la base de datos
	 */
	public static final String DATABASE_USER = "store";
	
	/**
	 * Representa el password del usuario en la base de datos
	 */
	public static final String DATABASE_PASS = "database";
	
	/**
	 * Representa el origen de los datos remoto, o la direccion de la base de datos
	 */
	public static final String DATABASE_REMOTE_SOURCE = "jdbc:mysql://192.168.0.20/store";
	
	/**
	 * Representa el origen de los datos locales, en caso que no este disponible el remoto
	 */
	public static final String DATABASE_LOCAL_SOURCE = "jdbc:mysql://localhost/store";
	
	/**
	 * Representa el nombre del propietario
	 */
	public static final String OWNER = "Papeleria Boyaca";
	
	/**
	 * Representa el numero de intentos maximos de validacion
	 */
	public static final int NUM_INTENTOS_MAXIMOS = 3;

	/**
	 * Representa el tiempo en milisegundos de actualizacion de los datos
	 */
	public static final long TIEMPO_ACTUALIZACION_STOCK = 60000;
	
	/**
	 * Representa el Look and Feel para la interfaz grafica
	 */
	public static final String LOOK_AND_FEEL = "com.birosoft.liquid.LiquidLookAndFeel";
	
	/**
	 * Representa la ruta desde donde se ejecuta la aplicacion
	 */
	public static final String RUTA_APLICACION = System.getProperty("user.dir").replace("\\","/")+"/";
	
	/**
	 * Representa la imagen del logo del propietario
	 */
	public static final String OWNER_LOGO = RUTA_APLICACION+"resources/custom/Logo.gif";

	/**
	 * Representa la imagen del logo del desarrollador
	 */
	public static final String DEVELOPER_LOGO = RUTA_APLICACION+"resources/custom/DesignerLogo.jpg";
	
	/**
	 * Representa la lista de presentaciones de los productos
	 */
	public static final String[] LISTA_PRESENTACION = {"BLOCK", "CAJA", "FRASCO", "KILO", "MADEJA", "METROS", 
														"PAQUETE", "PAR", "PLIEGO", "RESMA", "ROLLO", "UNIDAD"};
	
	/**
	 * Representa la cantidad de columnas de la tabla Stock
	 */
	public static int CANTIDAD_COLUMNAS_STOCK;
	
	/**
	 * Representa la cantidad de columnas de la tabla Client 
	 */
	public static int CANTIDAD_COLUMNAS_CLIENT;
	
	/**
	 * Representa la cantidad de columnas de la tabla Supplier 
	 */
	public static int CANTIDAD_COLUMNAS_SUPPLIER;
	
	/**
	 * Representa la cantidad de columnas de la tabla Estimed
	 */
	public static int CANTIDAD_COLUMNAS_ESTIMED;
	
	/**
	 * Representa la cantidad de columnas de la tabla Remision
	 */
	public static int CANTIDAD_COLUMNAS_REMISION;
	
	/**
	 * Representa la cantidad de columnas de la tabla credit_sales
	 */
	public static int CANTIDAD_COLUMNAS_CUENTASxCOBRAR;
	
	/**
	 * Representa los dias de validez de los formatos
	 */
	public static final int DIAS_VALIDEZ = 30;
	
	/**
	 * Representa el ID del usuario en la base de datos
	 */
	public static String USER_ID = "0";
	
	/**
	 * Representa el tipo de acceso del usuario
	 */
	public static String TIPO_ACCESO = "1";
	
	/**
	 * Representa la columna de orden de los datos de la tabla stock
	 */
	public static String COLUMNA_ORDEN_DATOS_STOCK = "Descripcion";
	
	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla stock
	 */
	public static String TIPO_ORDEN_DATOS_STOCK = "ASC";
	
	/**
	 * Representa la columna de orden de los datos de la tabla client
	 */
	public static String COLUMNA_ORDEN_DATOS_CLIENT = "Nombre";
	
	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla client
	 */
	public static String TIPO_ORDEN_DATOS_CLIENT = "ASC";
	
	/**
	 * Representa la columna de orden de los datos de la tabla supplier
	 */
	public static String COLUMNA_ORDEN_DATOS_SUPPLIER = "Nombre Empresa";

	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla supplier
	 */
	public static String TIPO_ORDEN_DATOS_SUPPLIER = "ASC";
	
	/**
	 * Representa la columnas de orden de los datos de la tabla estimed
	 */
	public static String COLUMNA_ORDEN_DATOS_ESTIMED = "Cotizacion";
	
	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla estimed
	 */
	public static String TIPO_ORDEN_DATOS_ESTIMED = "DESC";
	
	/**
	 * Representa la columnas de orden de los datos de la tabla remision
	 */
	public static String COLUMNA_ORDEN_DATOS_REMISION = "Remision";
	
	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla remision
	 */
	public static String TIPO_ORDEN_DATOS_REMISION = "DESC";
	
	/**
	 * Representa la columnas de orden de los datos de la tabla credit_sales
	 */
	public static String COLUMNA_ORDEN_DATOS_CUENTASxCOBRAR = "Factura";
	
	/**
	 * Representa el orden ascendente o descendente de los datos de la tabla credit_sales
	 */
	public static String TIPO_ORDEN_DATOS_CUENTASxCOBRAR = "DESC";
	
}
