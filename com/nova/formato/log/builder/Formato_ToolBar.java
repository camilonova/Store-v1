package com.nova.formato.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import com.nova.dat.ParametrosGlobales;
import com.nova.formato.log.strategy.Formato;

/**
 * Esta clase representa los comandos avanzados de operacion
 * de los datos de la tabla, lo ideal seria utilizar el patron
 * command, pero debido a la sencillez de las operaciones no se
 * hace de esa manera.
 * @author Camilo Nova
 * @version 1.1
 */
public class Formato_ToolBar extends JToolBar {
	
	/**
	 * Representa el boton de borrar fila
	 */
	private JButton borrarFilaBtn = new JButton("Borrar Fila", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Remove24.gif"));
	
	/**
	 * Representa el boton de borrar todo
	 */
	private JButton borrarTodoBtn = new JButton("Borrar Todo", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Delete24.gif"));
	
	/**
	 * Representa el boton de imprimir
	 */
	private JButton imprimirBtn = new JButton("Imprimir", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Print24.gif"));
	
	/**
	 * Representa el boton de guardar
	 */
	private JButton guardarBtn = new JButton("Guardar y salir", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/general/Save24.gif"));
	
	/**
	 * Representa el boton de salir
	 */
	private JButton salirBtn = new JButton("Salir sin guardar", new ImageIcon(ParametrosGlobales.RUTA_APLICACION+"resources/media/FastForward24.gif"));
	
	/**
	 * Representa el panel principal del cual depende
	 */
	private Formato formato;
	
	/**
	 * Constructor de la clase, da las propiedades a los botones
	 * y arma la barra de herramientas
	 * @param panelPrincipal		Panel principal del cual depende
	 */
	public Formato_ToolBar(Formato panelPrincipal) {
		formato = panelPrincipal;
		
		borrarFilaBtn.setToolTipText("Borra la fila seleccionada");
		borrarFilaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formato.eliminarFilaSeleccionada();
			}
		});
		borrarFilaBtn.setEnabled(false);
		add(borrarFilaBtn);
		
		borrarTodoBtn.setToolTipText("Borra todos los elementos");
		borrarTodoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formato.eliminarTodasFilas();
			}
		});
		borrarTodoBtn.setEnabled(false);
		add(borrarTodoBtn);

		addSeparator();

		imprimirBtn.setToolTipText("Imprimir");
		imprimirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formato.imprimir();
			}
		});
		add(imprimirBtn);
		
		addSeparator();
		
		guardarBtn.setToolTipText("Guardar y salir");
		guardarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formato.guardar())
					formato.cerrarVentana();
			}
		});
		guardarBtn.setEnabled(false);
		add(guardarBtn);
		
		salirBtn.setToolTipText("Salir sin guardar");
		salirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "Esta seguro de salir sin guardar?", "Salir...", JOptionPane.YES_NO_OPTION);
				
				if(a == JOptionPane.YES_OPTION)
					formato.cerrarVentana();
			}
		});
		add(salirBtn);

		
		// Establecemos permisos
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 1) {
			borrarFilaBtn.setEnabled(true);
			borrarTodoBtn.setEnabled(true);
			guardarBtn.setEnabled(true);
		}

		setFloatable(false);
		setRollover(true);
	}

	/**
	 * Metodo que desabilita los botones de edicion de
	 * los datos de la tabla, para el comando de ver
	 */
	public void deshabilitarBotones() {
		borrarFilaBtn.setEnabled(false);
		borrarTodoBtn.setEnabled(false);
		guardarBtn.setEnabled(false);
	}	
}
