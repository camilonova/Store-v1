package com.nova.cuentasxcobrar.log.command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.nova.gui.Store;

/**
 * Esta clase genera la interfaz de eleccion de reportes del modulo de
 * cuentas por cobrar, permite elegir uno de los reportes y generarlo.
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar_Reportes extends JDialog implements CuentasxCobrar_IComando, KeyListener {

	/**
	 * Representa el boton de reporte por fecha
	 */
	private JRadioButton fechaRBtn = new JRadioButton("Reporte por fecha", true);

	/**
	 * Representa el boton de reporte por cliente
	 */
	private JRadioButton clienteRBtn = new JRadioButton("Reporte por cliente");
	
	/**
	 * Representa el boton de reporte de facturas pendientes (Sin cancelar)
	 */
	private JRadioButton pendientesRBtn = new JRadioButton("Reporte de facturas pendientes");

	/**
	 * Representa el boton de reporte de facturas canceladas
	 */
	private JRadioButton canceladasRBtn = new JRadioButton("Reporte de facturas canceladas");

	/**
	 * Representa el grupo de botones
	 */
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Representa el boton de ver
	 */
	private JButton verBtn = new JButton("Ver");
	
	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn = new JButton("Cancelar");
	
	/**
	 * Constructor de la clase, construye la interfaz de
	 * eleccion de reportes y llama al constructor del
	 * reporte apropiado
	 */
	public CuentasxCobrar_Reportes() {
		verBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

				if(fechaRBtn.isSelected())
					new CuentasxCobrar_Reportes_Fecha();
				else if(clienteRBtn.isSelected())
					new CuentasxCobrar_Reportes_Cliente();
				else if(pendientesRBtn.isSelected())
					new CuentasxCobrar_Reportes_Pendientes();
				else if(canceladasRBtn.isSelected())
					new CuentasxCobrar_Reportes_Canceladas();
			}
		});
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JPanel upperPanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		
		buttonGroup.add(fechaRBtn);
		buttonGroup.add(clienteRBtn);
		buttonGroup.add(pendientesRBtn);
		buttonGroup.add(canceladasRBtn);
		
		fechaRBtn.addKeyListener(this);
		clienteRBtn.addKeyListener(this);
		pendientesRBtn.addKeyListener(this);
		canceladasRBtn.addKeyListener(this);
		
		fechaRBtn.setPreferredSize(new Dimension(180,20));
		clienteRBtn.setPreferredSize(new Dimension(180,20));
		
		upperPanel.setBorder(new TitledBorder(null, "Reportes", TitledBorder.LEFT, TitledBorder.TOP)); 
		upperPanel.add(fechaRBtn);
		upperPanel.add(clienteRBtn);
		upperPanel.add(pendientesRBtn);
		upperPanel.add(canceladasRBtn);
		
		lowerPanel.add(verBtn, BorderLayout.WEST);
		lowerPanel.add(cancelarBtn, BorderLayout.EAST);

		add(upperPanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		
		setSize(200,200);
		setTitle("Generar Reportes...");
		setLocation(Store.screenSize.width/2-100, Store.screenSize.height/2-100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}

	/**
	 * Metodo que ejecuta el comando de generar reportes
	 */
	public void ejecutarComando() {
		setVisible(true);
	}

	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			verBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}
}
