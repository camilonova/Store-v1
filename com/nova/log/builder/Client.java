package com.nova.log.builder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;

import com.nova.client.log.builder.Client_AbstractBuilder;
import com.nova.client.log.builder.Client_BarraEstadoBuilder;
import com.nova.client.log.builder.Client_BarraHerramientasBuilder;
import com.nova.client.log.builder.Client_PanelBuilder;

/**
 * Esta clase representa el modulo de los clientes, este modulo
 * tiene las funcionalidades para el manejo de los clientes
 * en la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Client extends JInternalFrame implements Componentes {
	
	/**
	 * Representa el constructor abstracto del modulo
	 */
	private Client_AbstractBuilder builder;
	
	/**
	 * Representa la barra de estado del modulo
	 */
	public static Client_BarraEstadoBuilder statusBar;

	/**
	 * Constructor de la clase, construye la interfaz
	 * del modulo de los clientes.
	 */
	public Client() {
		super("Clientes", true, true, true, true);
		
		builder = new Client_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new Client_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (Client_BarraEstadoBuilder) builder;

		builder = new Client_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(600,300);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
