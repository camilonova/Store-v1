package com.nova.supplier.log.command;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Supplier_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private Supplier_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static Supplier_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 */
	private Supplier_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static Supplier_Comando getInstancia() {
		if(instancia == null)
			instancia = new Supplier_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de agregar.
	 *
	 */
	public void agregar() {
		comando = new Supplier_Nuevo();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de eliminar
	 */
	public void eliminar() {
		comando = new Supplier_Eliminar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de buscar una cadena
	 */
	public void buscar() {
		comando = new Supplier_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		Supplier_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		Supplier_Buscar.siguiente();
	}
}
