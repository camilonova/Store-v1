package com.nova.client.log.command;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 0.5
 */
public class Client_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private Client_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static Client_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 *
	 */
	private Client_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static Client_Comando getInstancia() {
		if(instancia == null)
			instancia = new Client_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de agregar un producto.
	 *
	 */
	public void agregar() {
		comando = new Client_Nuevo();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de eliminar un producto
	 */
	public void eliminar() {
		comando = new Client_Borrar();
		comando.ejecutarComando();
	}

	/**
	 * Metodo que ejecuta el comando de buscar una cadena
	 */
	public void buscar() {
		comando = new Client_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		Client_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		Client_Buscar.siguiente();
	}
}
