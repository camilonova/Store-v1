package com.nova.stock.log.command;

import com.nova.stock.log.builder.Stock_PanelBuilder;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 0.5
 */
public class Stock_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private Stock_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static Stock_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 *
	 */
	private Stock_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static Stock_Comando getInstancia() {
		if(instancia == null)
			instancia = new Stock_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de agregar un producto.
	 *
	 */
	public void agregar() {
		comando = new Stock_Agregar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de eliminar un producto
	 */
	public void eliminar() {
		comando = new Stock_Eliminar();
		comando.ejecutarComando();
	}

	/**
	 * Metodo que ejecuta el comando de buscar una cadena
	 */
	public void buscar() {
		comando = new Stock_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		Stock_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		Stock_Buscar.siguiente();
	}
	
	/**
	 * Metodo que actualiza los datos de la tabla
	 */
	public void actualizar() {
		Stock_PanelBuilder.actualizarDatos(null);
	}

}
