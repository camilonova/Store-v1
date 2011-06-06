package com.nova.remision.log.command;

import java.awt.Component;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.nova.gui.Store;
import com.nova.remision.log.builder.Remision_PanelBuilder;

/**
 * Esta clase funciona como puente entre los clientes de los comandos
 * y los comandos.
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_Comando {
	
	/**
	 * Representa la interface de los comandos
	 */
	private Remision_IComando comando;
	
	/**
	 * Representa la instancia unica de la clase
	 */
	private static Remision_Comando instancia;
	
	/**
	 * Constructor de la clase, es privado.
	 *
	 */
	private Remision_Comando() {
	}
	
	/**
	 * Retorna la instancia unica de la clase, para que todos sus
	 * clientes tengan la misma instancia.
	 * @return		Instancia unica de la clase
	 */
	public static Remision_Comando getInstancia() {
		if(instancia == null)
			instancia = new Remision_Comando();
		return instancia;
	}

	/**
	 * Metodo que ejecuta el comando de crear una remision
	 *
	 */
	public void nuevaRemision() {
		comando = new Remision_Nuevo();
		Store.desktop.add((Component) comando);
		try {
			((JInternalFrame) comando).setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		comando.ejecutarComando();
	}

	/**
	 * Metodo que ejecuta el comando de modificar una remision
	 */
	public void modificarRemision() {
		if(Remision_PanelBuilder.getNumeroRemisionSeleccionada().equals("-1")) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una remision!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		comando = new Remision_Modificar();
		Store.desktop.add((Component) comando);
		try {
			((JInternalFrame) comando).setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ejecuta el comando de ver una remision
	 */
	public void verRemision() {
		if(Remision_PanelBuilder.getNumeroRemisionSeleccionada().equals("-1")) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una cotizacion!!!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		comando = new Remision_Ver();
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
		comando = new Remision_Buscar();
		comando.ejecutarComando();
	}
	
	/**
	 * Metodo que ordena mostrar el resultado anterior
	 */
	public void buscarAnterior() {
		Remision_Buscar.anterior();
	}

	/**
	 * Metodo que ordena mostrar el resultado siguiente
	 */
	public void buscarSiguiente() {
		Remision_Buscar.siguiente();
	}
}
