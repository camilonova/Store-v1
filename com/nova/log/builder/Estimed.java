package com.nova.log.builder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import com.nova.estimed.log.builder.Estimed_AbstractBuilder;
import com.nova.estimed.log.builder.Estimed_BarraEstadoBuilder;
import com.nova.estimed.log.builder.Estimed_BarraHerramientasBuilder;
import com.nova.estimed.log.builder.Estimed_MenuBuilder;
import com.nova.estimed.log.builder.Estimed_PanelBuilder;

/**
 * Esta clase representa el modulo de cotizaciones, que va en
 * el frame principal de la solucion.
 * @author Camilo Nova
 * @version 1.0
 */
public class Estimed extends JInternalFrame implements Componentes {
	
	/**
	 * Representa el constructor de la interface del componente
	 */
	private Estimed_AbstractBuilder builder;
	
	/**
	 * Representa la barra de estado del componente
	 */
	public static Estimed_BarraEstadoBuilder statusBar;

	/**
	 * Constructor de la clase, abre en un frame interno el modulo
	 * de cotizaciones
	 */
	public Estimed() {
		super("Cotizaciones", true, true, true, true);
		
		builder = new Estimed_MenuBuilder(this);
		setJMenuBar((JMenuBar) builder);
		
		builder = new Estimed_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new Estimed_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (Estimed_BarraEstadoBuilder) builder;

		builder = new Estimed_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(600,320);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
