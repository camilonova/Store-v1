package com.nova.remision.log.command;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import com.nova.formato.log.strategy.Formato;
import com.nova.formato.log.strategy.Formato_Remision;
import com.nova.gui.Store;

/**
 * Esta clase muestra la interfaz de cotizacion como una ventana
 * interna del frame de la aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_Nuevo extends JInternalFrame implements Remision_IComando {
	
	/**
	 * Representa el panel principal
	 */
	private Formato formato;
	
	/**
	 * Constructor de la clase, agrega la barra de herramientas
	 * y la interfaz principal.
	 *
	 */
	public Remision_Nuevo() {
		super("Nueva Remision...", false, false, false, false);
		formato = new Formato_Remision(this);
		formato.nuevo();
		
		add(formato.getToolBar(), BorderLayout.NORTH);
		add(formato, BorderLayout.CENTER);
		
		setSize(700,650);
		setLocation(Store.screenSize.width/2-350, Store.screenSize.height/2-350);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Muestra la ventana para agregar una cotizacion.
	 * Se define por implementacion de la interface IComando
	 */
	public void ejecutarComando() {
		setVisible(true);
	}
	
}