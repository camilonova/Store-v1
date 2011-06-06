package com.nova.remision.log.command;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import com.nova.formato.log.strategy.Formato;
import com.nova.formato.log.strategy.Formato_Remision;
import com.nova.gui.Store;

/**
 * @author Camilo Nova
 */
public class Remision_Ver extends JInternalFrame implements Remision_IComando {
	
	/**
	 * Representa el panel principal
	 */
	private Formato formato;
	
	/**
	 * Constructor de la clase, agrega la barra de herramientas
	 * y la interfaz principal.
	 *
	 */
	public Remision_Ver() {
		super("Ver Remision...", false, false, false, false);
		formato = new Formato_Remision(this);
		formato.ver();
		
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