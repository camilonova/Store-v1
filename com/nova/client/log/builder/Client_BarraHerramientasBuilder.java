package com.nova.client.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.client.log.command.Client_Comando;
import com.nova.dat.ParametrosGlobales;

/**
 * Esta clase representa la barra de herramientas de la aplicacion,
 * muestra los componentes y las funcionalidades mas importantes de
 * la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Client_BarraHerramientasBuilder extends JToolBar implements Client_AbstractBuilder {

	/**
	 * Representa el boton de nuevo cliente
	 */
	private JButton nuevoBtn = new JButton(Messages.getString("Client_BarraHerramientasBuilder.0")); //$NON-NLS-1$
	
	/**
	 * Representa el boton de borrar cliente
	 */
	private JButton borrarBtn = new JButton(Messages.getString("Client_BarraHerramientasBuilder.1")); //$NON-NLS-1$
	
	/**
	 * Representa el boton de buscar
	 */
	private JButton buscarBtn = new JButton(Messages.getString("Client_BarraHerramientasBuilder.2")); //$NON-NLS-1$
	
	/**
	 * Representa el boton de anterior en la busqueda
	 */
	private static JButton AntBusquedaBtn = new JButton(Messages.getString("Client_BarraHerramientasBuilder.3")); //$NON-NLS-1$
	
	/**
	 * Representa el boton de siguiente en la busqueda
	 */
	private static JButton SigBusquedaBtn = new JButton(Messages.getString("Client_BarraHerramientasBuilder.4")); //$NON-NLS-1$
	
	/**
	 * Representa el comando de la aplicacion
	 */
	private Client_Comando comando;
	
	/**
	 * Constructor de la clase, crea la barra de herramientas para
	 * el modulo de Inventario y le da sus propiedades
	 */
	public Client_BarraHerramientasBuilder() {
		nuevoBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+Messages.getString("Client_BarraHerramientasBuilder.5"))); //$NON-NLS-1$
		borrarBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+Messages.getString("Client_BarraHerramientasBuilder.6"))); //$NON-NLS-1$
		buscarBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+Messages.getString("Client_BarraHerramientasBuilder.7"))); //$NON-NLS-1$
		AntBusquedaBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+Messages.getString("Client_BarraHerramientasBuilder.8"))); //$NON-NLS-1$
		SigBusquedaBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+Messages.getString("Client_BarraHerramientasBuilder.9"))); //$NON-NLS-1$

		comando = Client_Comando.getInstancia();
		
		nuevoBtn.setToolTipText(Messages.getString("Client_BarraHerramientasBuilder.10")); //$NON-NLS-1$
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.agregar();
			}
		});
		add(nuevoBtn);
		
		borrarBtn.setToolTipText(Messages.getString("Client_BarraHerramientasBuilder.11")); //$NON-NLS-1$
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.eliminar();
			}
		});
		add(borrarBtn);
		
		addSeparator();
		
		buscarBtn.setToolTipText(Messages.getString("Client_BarraHerramientasBuilder.12")); //$NON-NLS-1$
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscar();
			}
		});
		add(buscarBtn);
		
		AntBusquedaBtn.setToolTipText(Messages.getString("Client_BarraHerramientasBuilder.13")); //$NON-NLS-1$
		AntBusquedaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscarAnterior();
			}
		});
		AntBusquedaBtn.setEnabled(false);
		add(AntBusquedaBtn);
		
		SigBusquedaBtn.setToolTipText(Messages.getString("Client_BarraHerramientasBuilder.14")); //$NON-NLS-1$
		SigBusquedaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscarSiguiente();
			}
		});
		SigBusquedaBtn.setEnabled(false);
		add(SigBusquedaBtn);
		
		setFloatable(false);
		setRollover(true);
	}
	
	/**
	 * Metodo que selecciona si el boton de busqueda anterior 
	 * debe estar activo o no
	 * @param estado		True si se quiere activar, False de lo contrario
	 */
	public static void setBusquedaAntEnabled(boolean estado) {
		AntBusquedaBtn.setEnabled(estado);
	}

	/**
	 * Metodo que selecciona si el boton de busqueda siguiente 
	 * debe estar activo o no
	 * @param estado		True si se quiere activar, False de lo contrario
	 */
	public static void setBusquedaSigEnabled(boolean estado) {
		SigBusquedaBtn.setEnabled(estado);
	}
}
