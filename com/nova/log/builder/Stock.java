package com.nova.log.builder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import com.nova.stock.log.builder.Stock_AbstractBuilder;
import com.nova.stock.log.builder.Stock_BarraEstadoBuilder;
import com.nova.stock.log.builder.Stock_BarraHerramientasBuilder;
import com.nova.stock.log.builder.Stock_MenuBuilder;
import com.nova.stock.log.builder.Stock_PanelBuilder;

/**
 * Esta clase representa el modulo de inventario en la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock extends JInternalFrame implements Componentes {

	/**
	 * Representa el constructor abstracto de la interfaz grafica
	 */
	private Stock_AbstractBuilder builder;
	
	/**
	 * Representa la barra de estado
	 */
	public static Stock_BarraEstadoBuilder statusBar;

	/**
	 * Constructor de la clase, construye secuencialmente la
	 * interfaz grafica.
	 *
	 */
	public Stock() {
		super("Inventario", true, true, true, true);
		
		builder = new Stock_MenuBuilder(this);
		setJMenuBar((JMenuBar) builder);
		
		builder = new Stock_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new Stock_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (Stock_BarraEstadoBuilder) builder;

		builder = new Stock_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(800,600);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);		
	}
}
