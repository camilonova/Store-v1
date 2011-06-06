package com.nova.cuentasxcobrar.log.command;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nova.client.dat.Client_DatabaseProxy;
import com.nova.cuentasxcobrar.dat.CuentasxCobrar_DatabaseProxy;
import com.nova.gui.Store;
import com.nova.log.ValidadorFecha;
import com.nova.log.ValidadorNumerico;

/**
 * Esta clase crea una ventana de entrada de datos para agregar una
 * nueva cuenta por cobrar a la base de datos
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar_Agregar extends JDialog implements CuentasxCobrar_IComando {

	/**
	 * Representa la etiqueta de factura
	 */
	private JLabel facturaLbl = new JLabel(" Factura");
	
	/**
	 * Representa la etiqueta de cliente
	 */
	private JLabel clienteLbl = new JLabel(" Cliente");
	
	/**
	 * Representa la etiqueta de fecha de facturacion
	 */
	private JLabel fechaFactLbl = new JLabel(" Fecha Facturacion");
	
	/**
	 * Representa la etiqueta de valor excluido
	 */
	private JLabel excluidoLbl = new JLabel(" Valor Excluido");
	
	/**
	 * Representa la etiqueta de gravado
	 */
	private JLabel gravadoLbl = new JLabel(" Valor Gravado");
	
	/**
	 * Representa la entrada de factura
	 */
	private JTextField facturaFld = new JTextField();
	
	/**
	 * Representa la entrada de cliente
	 */
	private JComboBox clienteCbx;
	
	/**
	 * Representa la entrada de fecha de facturacion
	 */
	private JFormattedTextField fechaFactFld = new JFormattedTextField(new Date());
	
	/**
	 * Representa la entrada de valor excluido
	 */
	private JTextField excluidoFld = new JTextField();
	
	/**
	 * Representa la entrada de gravado
	 */
	private JTextField gravadoFld = new JTextField();
	
	/**
	 * Representa el checkbox para continuar agregando
	 */
	private JCheckBox continuarCkb = new JCheckBox("Seguir agregando");
	
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
	 * de datos para agregar una nueva factura a la base de datos.
	 */
	public CuentasxCobrar_Agregar() {
		setLayout(new GridLayout(7,2,2,1));
		
		clienteCbx = new JComboBox(Client_DatabaseProxy.getInstancia().getListaClientes());
		clienteCbx.insertItemAt("Seleccione uno...", 0);
		clienteCbx.setSelectedIndex(0);
		
		facturaFld.setHorizontalAlignment(JTextField.RIGHT);
		excluidoFld.setHorizontalAlignment(JTextField.RIGHT);
		gravadoFld.setHorizontalAlignment(JTextField.RIGHT);
		
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clienteCbx.getSelectedIndex() != 0 || facturaFld.getText().length() != 5) {
					CuentasxCobrar_DatabaseProxy DB = CuentasxCobrar_DatabaseProxy.getInstancia();
					
					int valor = Integer.parseInt(gravadoFld.getText());
					double imp = Double.parseDouble("1.16");
					int result = (int) Math.round(valor*imp);
					String iva = String.valueOf(Math.abs(valor-result));
					String total = String.valueOf(Integer.parseInt(excluidoFld.getText()) + 
							Integer.parseInt(gravadoFld.getText()) + Integer.parseInt(iva));
					
					DB.nuevaCuentaxCobrar(facturaFld.getText(), (String) clienteCbx.getSelectedItem(), 
							fechaFactFld.getText(), excluidoFld.getText(), gravadoFld.getText(), iva, total);
					
					// Revisamos si el usuario quiere continuar agregando
					if(continuarCkb.isSelected()) {
						facturaFld.setText("");
						clienteCbx.setSelectedIndex(0);
						fechaFactFld.setText(new Date().toString());
						excluidoFld.setText("");
						gravadoFld.setText("");
						facturaFld.requestFocus();
					}
					else
						dispose();
				}
				else
					clienteCbx.requestFocus();
			}
				
		});
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		ValidadorNumerico keyList = new ValidadorNumerico(aceptarBtn, cancelarBtn);
		
		facturaFld.addKeyListener(keyList);
		fechaFactFld.addKeyListener(new ValidadorFecha(aceptarBtn, cancelarBtn));
		excluidoFld.addKeyListener(keyList);
		gravadoFld.addKeyListener(keyList);
		
		add(facturaLbl);
		add(facturaFld);
		add(clienteLbl);
		add(clienteCbx);
		add(fechaFactLbl);
		add(fechaFactFld);
		add(excluidoLbl);
		add(excluidoFld);
		add(gravadoLbl);
		add(gravadoFld);
		add(continuarCkb);
		add(new JLabel(""));
		add(aceptarBtn);
		add(cancelarBtn);
		
		setSize(280,200);
		setTitle("Nueva Cuenta por Cobrar...");
		setLocation(Store.screenSize.width/2-130, Store.screenSize.height/2-100);
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