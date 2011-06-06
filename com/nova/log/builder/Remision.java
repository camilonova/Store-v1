package com.nova.log.builder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import com.nova.remision.log.builder.Remision_AbstractBuilder;
import com.nova.remision.log.builder.Remision_BarraEstadoBuilder;
import com.nova.remision.log.builder.Remision_BarraHerramientasBuilder;
import com.nova.remision.log.builder.Remision_MenuBuilder;
import com.nova.remision.log.builder.Remision_PanelBuilder;

/**
 * Esta clase representa el frame interno de remisiones, perteneciente
 * al conjunto de los componentes de la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision extends JInternalFrame implements Componentes {

	/**
	 * Representa el contructor de la interface del componente
	 */
	private Remision_AbstractBuilder builder;
	
	/**
	 * Representa la barra de estado del componente
	 */
	public static Remision_BarraEstadoBuilder statusBar;
	
	/**
	 * Constructor de la clase, abre el modulo de remisiones
	 * en un frame interno
	 */
	public Remision() {
		super("Remisiones", true, true, true, true);
		
		builder = new Remision_MenuBuilder(this);
		setJMenuBar((JMenuBar) builder);
		
		builder = new Remision_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new Remision_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (Remision_BarraEstadoBuilder) builder;
		
		builder = new Remision_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(600,320);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
