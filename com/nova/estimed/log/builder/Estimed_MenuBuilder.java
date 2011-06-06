package com.nova.estimed.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nova.dat.ParametrosGlobales;
import com.nova.estimed.log.command.Estimed_Comando;
import com.nova.log.builder.Componentes;

/**
 * Esta clase representa el menu del componente "inventario",
 * muestra las funcionalidades de la aplicacion en su totalidad
 * @author Camilo Nova
 * @version 1.0
 */
public class Estimed_MenuBuilder extends JMenuBar implements Estimed_AbstractBuilder {

	/**
	 * Representa el menu archivo
	 */
	private JMenu archivoMnu = new JMenu("Archivo");
	
	/**
	 * Representa el menu edicion
	 */
	private JMenu edicionMnu = new JMenu("Edicion");
	
	/**
	 * Representa el menu de ayuda
	 */
	private JMenu ayudaMnu = new JMenu("Ayuda");
	
	/**
	 * Representa el item de cerrar
	 */
	private JMenuItem cerrarItm = new JMenuItem("Cerrar");
	
	/**
	 * Representa el item de agregar
	 */
	private JMenuItem nuevoItm = new JMenuItem("Nueva Cotizacion", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/New16.gif"));
		
	/**
	 * Representa el item de eliminar
	 */
	private JMenuItem modificarItm = new JMenuItem("Modificar Cotizacion", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Edit16.gif"));
	
	/**
	 * Representa el item de ver
	 */
	private JMenuItem verItm = new JMenuItem("Ver Cotizacion", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/FindAgain16.gif"));
	
	/**
	 * Representa el item de buscar
	 */
	private JMenuItem buscarItm = new JMenuItem("Buscar", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Find16.gif"));

	/**
	 * Representa los componentes de la aplicacion
	 */
	private Componentes inventario;
	
	/**
	 * Representa los comandos de la aplicacion
	 */
	private Estimed_Comando comando;
	
	/**
	 * Constructor de la clase. Construye el menu
	 * @param comp		Componentes de la aplicacion
	 */
	public Estimed_MenuBuilder(Componentes comp) {
		inventario = comp;
		comando = Estimed_Comando.getInstancia();
		
		archivoMnu.setMnemonic('a');
		edicionMnu.setMnemonic('e');
		ayudaMnu.setMnemonic('y');
		
		archivoMnu.addSeparator();
		
		cerrarItm.setMnemonic('c');
		cerrarItm.setToolTipText("Cierra la ventana");
		cerrarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					((JInternalFrame) inventario).setClosed(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		archivoMnu.add(cerrarItm);
		
		nuevoItm.setMnemonic('n');
		nuevoItm.setToolTipText("Crear una nueva cotizacion");
		nuevoItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.nuevaCotizacion();
			}
		});
		nuevoItm.setEnabled(false);
		edicionMnu.add(nuevoItm);

		modificarItm.setMnemonic('m');
		modificarItm.setToolTipText("Modificar la cotizacion seleccionada");
		modificarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.modificarCotizacion();
			}
		});
		modificarItm.setEnabled(false);
		edicionMnu.add(modificarItm);
		
		verItm.setMnemonic('v');
		verItm.setToolTipText("Ver la cotizacion seleccionada");
		verItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.verCotizacion();
			}
		});
		edicionMnu.add(verItm);
		
		edicionMnu.addSeparator();
		
		buscarItm.setMnemonic('b');
		buscarItm.setToolTipText("Buscar una cadena en la tabla");
		buscarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscar();
			}
		});
		edicionMnu.add(buscarItm);
		
		// Establecemos permisos
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 1) {
			nuevoItm.setEnabled(true);
			modificarItm.setEnabled(true);
		}
		
		add(archivoMnu);
		add(edicionMnu);
		add(ayudaMnu);
	}
}
