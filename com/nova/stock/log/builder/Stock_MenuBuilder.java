package com.nova.stock.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.Componentes;
import com.nova.stock.log.command.Stock_Comando;

/**
 * Esta clase representa el menu del componente "inventario",
 * muestra las funcionalidades de la aplicacion en su totalidad
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_MenuBuilder extends JMenuBar implements Stock_AbstractBuilder {

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
	private JMenuItem agregarItm = new JMenuItem("Agregar", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Add16.gif"));
	
	/**
	 * Representa el item de eliminar
	 */
	private JMenuItem eliminarItm = new JMenuItem("Eliminar", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Remove16.gif"));
		
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
	private Stock_Comando comando;
	
	/**
	 * Constructor de la clase. Construye el menu
	 * @param comp		Componentes de la aplicacion
	 */
	public Stock_MenuBuilder(Componentes comp) {
		inventario = comp;
		comando = Stock_Comando.getInstancia();
		
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
		
		agregarItm.setMnemonic('a');
		agregarItm.setToolTipText("Agregar un producto");
		agregarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.agregar();
			}
		});
		edicionMnu.add(agregarItm);
		
		eliminarItm.setMnemonic('e');
		eliminarItm.setToolTipText("Eliminar el producto seleccionado");
		eliminarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.eliminar();
			}
		});
		edicionMnu.add(eliminarItm);
		
		buscarItm.setMnemonic('b');
		buscarItm.setToolTipText("Buscar una cadena en la tabla");
		buscarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.buscar();
			}
		});
		edicionMnu.add(buscarItm);
		
		edicionMnu.addSeparator();
		
		add(archivoMnu);
		add(edicionMnu);
		add(ayudaMnu);
	}
}
