package com.nova.stock.dat;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.nova.log.builder.Stock;

/**
 * Esta clase define la tarea que debe ser ejecutada periodicamente
 * en esta aplicacion, esta tarea es la de actualizar los datos
 * periodicamente en la tabla.
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_ActualizacionCronometrada extends TimerTask {
	
	/**
	 * Representa el modelo de la tabla a actualizar
	 */
	private Stock_ModeloTabla modelo;
	
	/**
	 * Constructor de la clase, recibe como parametro el
	 * modelo de la tabla a actualizar
	 * @param modelo	Modelo de la tabla a actualizar
	 */
	public Stock_ActualizacionCronometrada(Stock_ModeloTabla modelo) {
		this.modelo = modelo;
	}
	
	/**
	 * Metodo que se ejecuta periodicamente y actualiza los
	 * datos en la tabla.
	 */
	public void run() {
		Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
		modelo.setAllData(DB.getAllData());
		Stock.statusBar.setText("Datos Actualizados a las "+DateFormat.getTimeInstance().format(new Date())+
								" - "+DB.getAllData().length+" Elementos Leidos ");
		DB = null;		
	}

}
