package com.nova.cuentasxcobrar.log.command;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nova.cuentasxcobrar.dat.CuentasxCobrar_DatabaseProxy;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_PanelBuilder;
import com.nova.gui.Store;
import com.nova.log.ValidadorFecha;
import com.nova.log.ValidadorNumerico;

/**
 * Esta clase crea una ventana de entrada de datos para cancelar una
 * factura que esta en cuentas por cobrar
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar_Cancelar extends JDialog implements CuentasxCobrar_IComando {

	/**
	 * Representa la etiqueta de fecha de cancelacion
	 */
	private JLabel fechaCancLbl = new JLabel(" Fecha Cancelacion");
	
	/**
	 * Representa la etiqueta de valor cancelado
	 */
	private JLabel valorLbl = new JLabel(" Valor Cancelado");
	
	/**
	 * Representa la entrada de fecha de cancelacion
	 */
	private JFormattedTextField fechaCancFld = new JFormattedTextField(new Date());
	
	/**
	 * Representa la entrada de valor cancelado
	 */
	private JTextField valorFld = new JTextField();
	
	/**
	 * Representa el boton de aceptar
	 */
	private JButton aceptarBtn = new JButton("Aceptar");
	
	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn	= new JButton("Cancelar");
	
	/**
	 * Constructor de la clase, construye la interfaz de entrada
	 * de datos para cancelar una factura existente en la base de datos.
	 */
	public CuentasxCobrar_Cancelar() {
		setLayout(new GridLayout(3,2,2,1));
		
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CuentasxCobrar_DatabaseProxy DB = CuentasxCobrar_DatabaseProxy.getInstancia();
				
				DB.cancelarCuentaxCobrar(CuentasxCobrar_PanelBuilder.getIDCuentaxCobrarSeleccionada(), 
						fechaCancFld.getText(), valorFld.getText().length() == 0 ? "0" : valorFld.getText());
				
				dispose();
			}
				
		});
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		fechaCancFld.addKeyListener(new ValidadorFecha(aceptarBtn, cancelarBtn));
		valorFld.addKeyListener(new ValidadorNumerico(aceptarBtn, cancelarBtn));
		
		add(fechaCancLbl);
		add(fechaCancFld);
		add(valorLbl);
		add(valorFld);
		add(aceptarBtn);
		add(cancelarBtn);
		
		setSize(260,100);
		setTitle("Cancelar cuenta...");
		setLocation(Store.screenSize.width/2-130, Store.screenSize.height/2-50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}
	
	/**
	 * Muestra la ventana para agregar un producto.
	 * Se define por implementacion de la interface IComando
	 */
	public void ejecutarComando() {
		setVisible(true);
	}
}