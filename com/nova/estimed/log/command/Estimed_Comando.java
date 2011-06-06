package com.nova.estimed.log.command;

import java.awt.Component;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.nova.estimed.log.builder.Estimed_PanelBuilder;
import com.nova.gui.Store;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Estimed_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private Estimed_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static Estimed_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 */
	private Estimed_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static Estimed_Comando getInstancia() {
		if(instancia == null)
			instancia = new Estimed_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de agregar una cotizacion.
	 */
	public void nuevaCotizacion() {
		comando = new Estimed_Nuevo();
		Store.desktop.add((Component) comando);
		try {
			((JInternalFrame) comando).setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		comando.ejecutarComando();
	}

	/**
	 * Metodo que ejecuta el comando de modificar una cotizacion
	 */
	public void modificarCotizacion() {
		if(Estimed_PanelBuilder.getNumeroCotizacionSeleccionada().equals("-1")) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una cotizacion!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		comando = new Estimed_Modificar();
		Store.desktop.add((Component) comando);
		try {
			((JInternalFrame) comando).setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de ver una cotizacion
	 */
	public void verCotizacion() {
		if(Estimed_PanelBuilder.getNumeroCotizacionSeleccionada().equals("-1")) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una cotizacion!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		comando = new Estimed_Ver();
		Store.desktop.add((Component) comando);
		try {
			((JInternalFrame) comando).setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		comando.ejecutarComando();
	}
	

	/**
	 * Metodo que ejecuta el comando de buscar una cadena
	 */
	public void buscar() {
		comando = new Estimed_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		Estimed_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		Estimed_Buscar.siguiente();
	}
}
