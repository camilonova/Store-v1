package com.nova.stock.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.dat.ParametrosGlobales;
import com.nova.stock.log.command.Stock_Comando;

/**
 * Esta clase representa la barra de herramientas de la aplicacion,
 * muestra los componentes y las funcionalidades mas importantes de
 * la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_BarraHerramientasBuilder extends JToolBar implements Stock_AbstractBuilder {

	/**
	 * Representa el boton de agregar
	 */
	private JButton agregarBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Add24.gif"));
	
	/**
	 * Representa el boton de eliminar
	 */
	private JButton eliminarBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Remove24.gif"));
	
	/**
	 * Representa el boton de buscar
	 */
	private JButton buscarBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Find24.gif"));
	
	/**
	 * Representa el boton de anterior en la busqueda
	 */
	private static JButton AntBusquedaBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/navigation/Back24.gif"));
	
	/**
	 * Representa el boton de siguiente en la busqueda
	 */
	private static JButton SigBusquedaBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/navigation/Forward24.gif"));
	
	/**
	 * Representa el boton de actualizar datos
	 */
	private static JButton actualizarBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Refresh24.gif"));

	/**
	 * Representa el comando de la aplicacion
	 */
	private Stock_Comando comando;
	
	/**
	 * Constructor de la clase, crea la barra de herramientas para
	 * el modulo de Inventario y le da sus propiedades
	 */
	public Stock_BarraHerramientasBuilder() {
		comando = Stock_Comando.getInstancia();
		
		agregarBtn.setToolTipText("Agregar Elementos");
		agregarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.agregar();
			}
		});
		add(agregarBtn);
		
		eliminarBtn.setToolTipText("Eliminar el elemento selecionado");
		eliminarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.eliminar();
			}
		});
		add(eliminarBtn);
		
		addSeparator();
		
		buscarBtn.setToolTipText("Buscar elementos");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscar();
			}
		});
		add(buscarBtn);
		
		AntBusquedaBtn.setToolTipText("Resultado Anterior");
		AntBusquedaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscarAnterior();
			}
		});
		AntBusquedaBtn.setEnabled(false);
		add(AntBusquedaBtn);
		
		SigBusquedaBtn.setToolTipText("Resultado Siguiente");
		SigBusquedaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscarSiguiente();
			}
		});
		SigBusquedaBtn.setEnabled(false);
		add(SigBusquedaBtn);
		
		addSeparator();
		
		actualizarBtn.setToolTipText("Actualizar los datos");
		actualizarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.actualizar();
			}
		});
		add(actualizarBtn);
		
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
