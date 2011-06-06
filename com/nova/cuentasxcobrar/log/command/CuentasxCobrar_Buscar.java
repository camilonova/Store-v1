package com.nova.cuentasxcobrar.log.command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_BarraHerramientasBuilder;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_PanelBuilder;
import com.nova.gui.Store;
import com.nova.log.builder.CuentasxCobrar;

/**
 * Esta clase muestra una ventana para el ingreso de una cadena a buscar
 * en los datos de la tabla. La cadena a buscar es convertida a minusculas
 * y es buscada en los datos de la tabla tambien convertidos a minusculas.<p>
 * Los datos son almacenados temporalmente en un vector en el cual se puede
 * navegar por los resultados obtenidos.
 * @author Camilo Nova
 * @version 1.2
 */
public class CuentasxCobrar_Buscar extends JDialog implements CuentasxCobrar_IComando, KeyListener {

	/**
	 * Representa la etiqueta de buscar
	 */
	private JLabel buscarLbl = new JLabel("Buscar:");
	
	/**
	 * Representa el area de texto donde se indica que buscar
	 */
	private JTextField buscarFld = new JTextField();
	
	/**
	 * Representa el boton de buscar
	 */
	private JButton buscarBtn = new JButton("Buscar");
	
	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn = new JButton("Cancelar");
	
	/**
	 * Representa el vector donde se almacenan los resultados
	 */
	private static Vector vectorBusqueda = new Vector();
	
	/**
	 * Representa el iterador de los resultados
	 */
	private static ListIterator lista;
	
	/**
	 * Representa los datos de la tabla
	 */
	private String[][] datos;

	/**
	 * Constructor de la clase, construye la interfaz grafica y
	 * ejecuta el algoritmo de busqueda en los datos si el usuario
	 * ha hecho click en el boton de buscar.
	 */
	public CuentasxCobrar_Buscar() {
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datos = CuentasxCobrar_PanelBuilder.getDatosTabla();
				vectorBusqueda.clear();
				
				for(int i=0; i < datos.length; i++)
					for(int j=0; j < datos[i].length; j++)
						if(datos[i][j].toLowerCase().contains(buscarFld.getText().toLowerCase()))
							vectorBusqueda.add(i+"-"+j);

				if(!vectorBusqueda.isEmpty())
					CuentasxCobrar.statusBar.setText("Total Resultados encontrados: "+vectorBusqueda.size());
				else
					CuentasxCobrar.statusBar.setText("No se encontraron resultados");
				
				lista = vectorBusqueda.listIterator();
				siguiente();
				actualizarBotones();
				dispose();
			}
		});
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buscarFld.addKeyListener(this);
		
		JPanel upperPanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		
		buscarFld.setPreferredSize(new Dimension(200,20));
		
		upperPanel.add(buscarLbl, BorderLayout.WEST);
		upperPanel.add(buscarFld, BorderLayout.CENTER);
		lowerPanel.add(buscarBtn, BorderLayout.WEST);
		lowerPanel.add(cancelarBtn, BorderLayout.EAST);
		
		add(upperPanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		
		setSize(280,100);
		setTitle("Buscar...");
		setLocation(Store.screenSize.width/2-140, Store.screenSize.height/2-50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}
	
	/**
	 * Metodo que ejecuta el comando
	 */
	public void ejecutarComando() {
		setVisible(true);
	}

	/**
	 * Metodo que muestra el resultado anterior
	 */
	public static void anterior() {
		if(lista.hasPrevious()) {
			String[] s = ((String) lista.previous()).split("-");
			CuentasxCobrar_PanelBuilder.setCeldaSeleccionada(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}
		actualizarBotones();		
	}

	/**
	 * Metodo que muestra el resultado siguiente
	 */
	public static void siguiente() {
		if(lista.hasNext()) {
			String[] s = ((String) lista.next()).split("-");
			CuentasxCobrar_PanelBuilder.setCeldaSeleccionada(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}
		actualizarBotones();
	}
	
	/**
	 * Metodo que actualiza el estado de los botones de
	 * siguiente y anterior en la barra de herramientas.
	 *
	 */
	private static void actualizarBotones() {
		if(lista.hasPrevious())
			CuentasxCobrar_BarraHerramientasBuilder.setBusquedaAntEnabled(true);
		else
			CuentasxCobrar_BarraHerramientasBuilder.setBusquedaAntEnabled(false);

		if(lista.hasNext())
			CuentasxCobrar_BarraHerramientasBuilder.setBusquedaSigEnabled(true);
		else
			CuentasxCobrar_BarraHerramientasBuilder.setBusquedaSigEnabled(false);
	}

	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			buscarBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}

}
