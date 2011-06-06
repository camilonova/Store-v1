package com.nova.remision.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.dat.ParametrosGlobales;
import com.nova.remision.log.command.Remision_Comando;


/**
 * Esta clase representa la barra de herramientas de la aplicacion,
 * muestra los componentes y las funcionalidades mas importantes de
 * la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_BarraHerramientasBuilder extends JToolBar implements Remision_AbstractBuilder {

	/**
	 * Representa el boton de nueva cotizacion
	 */
	private JButton nuevoBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/New24.gif"));
	
	/**
	 * Representa el boton de modificar
	 */
	private JButton modificarBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Edit24.gif"));
	
	/**
	 * Representa el boton de ver una cotizacion
	 */
	private JButton verBtn = new JButton(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/FindAgain24.gif"));
	
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
	 * Representa el comando de la aplicacion
	 */
	private Remision_Comando comando;
	
	/**
	 * Constructor de la clase, crea la barra de herramientas para
	 * el modulo de Inventario y le da sus propiedades
	 */
	public Remision_BarraHerramientasBuilder() {
		comando = Remision_Comando.getInstancia();
		
		nuevoBtn.setToolTipText("Crear una nueva remision");
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.nuevaRemision();
			}
		});
		nuevoBtn.setEnabled(false);
		add(nuevoBtn);
		
		modificarBtn.setToolTipText("Modifica la remision seleccionada");
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.modificarRemision();
			}
		});
		modificarBtn.setEnabled(false);
		add(modificarBtn);
		
		verBtn.setToolTipText("Ver la remision seleccionada");
		verBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.verRemision();
			}
		});
		modificarBtn.setEnabled(false);
		add(verBtn);
		
		addSeparator();
		
		buscarBtn.setToolTipText("Buscar remisiones");
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
		
		// Establecemos permisos
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 1) {
			nuevoBtn.setEnabled(true);
			modificarBtn.setEnabled(true);
		}

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
