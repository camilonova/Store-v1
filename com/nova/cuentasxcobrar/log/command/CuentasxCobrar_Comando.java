package com.nova.cuentasxcobrar.log.command;

import javax.swing.JOptionPane;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_PanelBuilder;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 2.0
 */
public class CuentasxCobrar_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private CuentasxCobrar_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static CuentasxCobrar_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 *
	 */
	private CuentasxCobrar_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static CuentasxCobrar_Comando getInstancia() {
		if(instancia == null)
			instancia = new CuentasxCobrar_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de agregar una factura.
	 *
	 */
	public void agregar() {
		comando = new CuentasxCobrar_Agregar();
		comando.ejecutarComando();
	}

	/**
	 * Metodo que ejecuta el comando de cancelar una factura
	 */
	public void cancelar() {
		if(!CuentasxCobrar_PanelBuilder.getIDCuentaxCobrarSeleccionada().equals("-1")) {
			comando = new CuentasxCobrar_Cancelar();
			comando.ejecutarComando();
		}
		else
			JOptionPane.showMessageDialog(null, "Debe seleccionar una factura!!!");
	}
	
	/**
	 * Metodo que ejecuta el comando de buscar una cadena
	 */
	public void buscar() {
		comando = new CuentasxCobrar_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		CuentasxCobrar_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		CuentasxCobrar_Buscar.siguiente();
	}

	/**
	 * Metodo que ejecuta el comando de generacion de reportes
	 */
	public void reportes() {
		comando = new CuentasxCobrar_Reportes();
		comando.ejecutarComando();
	}
}
