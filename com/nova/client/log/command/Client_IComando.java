package com.nova.client.log.command;

/**
 * Esta interface es la pasarela a los comandos de la aplicacion.
 * Para ser mas preciso, se hace uso del patron <i>Command</i>.
 * @author Camilo Nova
 * @version 1.0
 */
public interface Client_IComando {
	
	/**
	 * Metodo que ejecuta el comando relacionado con
	 * la instanciacion de la interface.
	 */
	public void ejecutarComando();

}
