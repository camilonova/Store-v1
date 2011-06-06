package com.nova.supplier.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.dat.ParametrosGlobales;
import com.nova.supplier.log.command.Supplier_Comando;

/**
 * Esta clase representa la barra de herramientas de la aplicacion,
 * muestra los componentes y las funcionalidades mas importantes de
 * la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Supplier_BarraHerramientasBuilder extends JToolBar implements Supplier_AbstractBuilder {

	/**
	 * Representa el boton de nuevo cliente
	 */
	private JButton nuevoBtn = new JButton("Agregar Proveedor");
	
	/**
	 * Representa el boton de borrar cliente
	 */
	private JButton borrarBtn = new JButton("Eliminar proveedor");
	
	/**
	 * Representa el boton de buscar
	 */
	private JButton buscarBtn = new JButton("Buscar");
	
	/**
	 * Representa el boton de anterior en la busqueda
	 */
	private static JButton AntBusquedaBtn = new JButton("Anterior");
	
	/**
	 * Representa el boton de siguiente en la busqueda
	 */
	private static JButton SigBusquedaBtn = new JButton("Siguiente");
	
	/**
	 * Representa el comando de la aplicacion
	 */
	private Supplier_Comando comando;
	
	/**
	 * Constructor de la clase, crea la barra de herramientas para
	 * el modulo de Inventario y le da sus propiedades
	 */
	public Supplier_BarraHerramientasBuilder() {
		nuevoBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Add24.gif"));
		borrarBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Remove24.gif"));
		buscarBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Find24.gif"));
		AntBusquedaBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/navigation/Back24.gif"));
		SigBusquedaBtn.setIcon(new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/navigation/Forward24.gif"));

		comando = Supplier_Comando.getInstancia();
		
		nuevoBtn.setToolTipText("Agregar un nuevo proveedor");
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.agregar();
			}
		});
		add(nuevoBtn);
		
		borrarBtn.setToolTipText("Borrar el proveedor selecionado");
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.eliminar();
			}
		});
		add(borrarBtn);
		
		addSeparator();
		
		buscarBtn.setToolTipText("Buscar proveedores");
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
