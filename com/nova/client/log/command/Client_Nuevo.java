package com.nova.client.log.command;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nova.client.dat.Client_DatabaseProxy;
import com.nova.gui.Store;
import com.nova.log.ValidadorNumerico;

/**
 * Esta clase crea una ventana de entrada de datos para agregar un
 * nuevo producto a la base de datos
 * @author Camilo Nova
 * @version 1.0
 */
public class Client_Nuevo extends JDialog implements Client_IComando, KeyListener {

	/**
	 * Representa la etiqueta de nombre
	 */
	private JLabel nombreLbl = new JLabel(" Nombre");
	
	/**
	 * Representa la etiqueta de NIT
	 */
	private JLabel nitLbl = new JLabel(" NIT");
	
	/**
	 * Representa la etiqueta de ciudad
	 */
	private JLabel ciudadLbl = new JLabel(" Ciudad");
	
	/**
	 * Representa la etiqueta de telefono
	 */
	private JLabel telefonoLbl = new JLabel(" Telefono");
	
	/**
	 * Representa la etiqueta de fax
	 */
	private JLabel faxLbl = new JLabel(" Fax");
	
	/**
	 * Representa la etiqueta de movil
	 */
	private JLabel movilLbl = new JLabel(" Movil");
	
	/**
	 * Representa la etiqueta de direccion
	 */
	private JLabel direccionLbl = new JLabel(" Direccion");
	
	/**
	 * Representa la entrada de nombre
	 */
	private JTextField nombreFld = new JTextField();
	
	/**
	 * Representa la entrada de NIT
	 */
	private JTextField nitFld = new JTextField();
	
	/**
	 * Representa la entrada de ciudad
	 */
	private JTextField ciudadFld = new JTextField();
	
	/**
	 * Representa la entrada de telefono
	 */
	private JTextField telefonoFld = new JTextField();
	
	/**
	 * Representa la entrada de fax
	 */
	private JTextField faxFld = new JTextField();
	
	/**
	 * Representa la entrada de movil
	 */
	private JTextField movilFld = new JTextField();
	
	/**
	 * Representa la entrada de direccion
	 */
	private JTextField direccionFld = new JTextField();

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
	 * de datos para agregar un nuevo cliente a la base de datos.
	 *
	 */
	public Client_Nuevo() {
		setLayout(new GridLayout(9,2,2,1));
		
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client_DatabaseProxy DB = Client_DatabaseProxy.getInstancia();
				DB.nuevoCliente(nombreFld.getText(), nitFld.getText(), ciudadFld.getText(), telefonoFld.getText(), 
								faxFld.getText(), movilFld.getText(), direccionFld.getText());
				
				// Revisamos si el usuario quiere continuar agregando
				if(continuarCkb.isSelected()) {
					nombreFld.setText("");
					nitFld.setText("");
					ciudadFld.setText("");
					telefonoFld.setText("");
					faxFld.setText("");
					movilFld.setText("");
					direccionFld.setText("");
					nombreFld.requestFocus();
				}
				else
					dispose();
			}
		});
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		ValidadorNumerico keyList = new ValidadorNumerico(aceptarBtn, cancelarBtn);
		
		nombreFld.addKeyListener(this);
		nitFld.addKeyListener(keyList);
		ciudadFld.addKeyListener(this);
		telefonoFld.addKeyListener(keyList);
		faxFld.addKeyListener(keyList);
		movilFld.addKeyListener(keyList);
		direccionFld.addKeyListener(this);

		add(nombreLbl);
		add(nombreFld);
		add(nitLbl);
		add(nitFld);
		add(ciudadLbl);
		add(ciudadFld);
		add(telefonoLbl);
		add(telefonoFld);
		add(faxLbl);
		add(faxFld);
		add(movilLbl);
		add(movilFld);
		add(direccionLbl);
		add(direccionFld);
		add(continuarCkb);
		add(new JLabel(""));
		add(aceptarBtn);
		add(cancelarBtn);
		
		setSize(250,240);
		setTitle("Nuevo Cliente...");
		setLocation(Store.screenSize.width/2-120, Store.screenSize.height/2-120);
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

	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			aceptarBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}
}