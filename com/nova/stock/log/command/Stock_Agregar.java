package com.nova.stock.log.command;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.nova.dat.ParametrosGlobales;
import com.nova.gui.Store;
import com.nova.log.ValidadorNumerico;
import com.nova.stock.dat.Stock_DatabaseProxy;
import com.nova.supplier.dat.Supplier_DatabaseProxy;

/**
 * Esta clase crea una ventana de entrada de datos para agregar un
 * nuevo producto a la base de datos
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_Agregar extends JDialog implements Stock_IComando, KeyListener {

	/**
	 * Representa la entrada de referencia
	 */
	private JTextField referenciaFld = new JTextField();
	
	/**
	 * Representa la entrada de cantidad
	 */
	private JTextField cantidadFld = new JTextField("0");
	
	/**
	 * Representa la entrada de descripcion
	 */
	private JTextField descripcionFld = new JTextField();
	
	/**
	 * Representa la entrada de marca
	 */
	private JTextField marcaFld = new JTextField();
	
	/**
	 * Representa la entrada de la presentacion del producto
	 */
	private JComboBox presentacionCbx;
	
	/**
	 * Representa la entrada de impuesto
	 */
	private JTextField impuestoFld = new JTextField("16");
	
	/**
	 * Representa la entrada de precio publico
	 */
	private JTextField precioPublicoFld = new JTextField();
	
	/**
	 * Representa la entrada de precio mayorista
	 */
	private JTextField precioMayoristaFld = new JTextField();
	
	/**
	 * Representa la entrada de proveedor
	 */
	private JComboBox proveedorCbx;
	
	/**
	 * Representa la entrada de costo
	 */
	private JTextField costoFld = new JTextField();
	
	/**
	 * Representa la entrada de observaciones
	 */
	private JTextField observacionesFld = new JTextField();
	
	/**
	 * Representa la entrada de codigo
	 */
	private JTextField codigoFld = new JTextField();

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
	 * de datos para agregar un nuevo elemento a la base de datos.
	 *
	 */
	public Stock_Agregar() {
		setLayout(new GridLayout(14,2,2,1));
		
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
				
				if(descripcionFld.getText().length() == 0 || precioPublicoFld.getText().length() == 0) {
					// No es posible registrar algun producto sin descripcion ni precio
					JOptionPane.showMessageDialog(null, "Tiene un campo escencial en blanco!!!", 
							"Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				DB.nuevoProducto(referenciaFld.getText(), cantidadFld.getText(), descripcionFld.getText(), 
									marcaFld.getText(), (String) presentacionCbx.getSelectedItem(), 
									impuestoFld.getText(), precioPublicoFld.getText(), precioMayoristaFld.getText(), 
									(String) proveedorCbx.getSelectedItem(), costoFld.getText(), 
									observacionesFld.getText(), codigoFld.getText());
				
				// Revisamos si el usuario quiere continuar agregando
				if(continuarCkb.isSelected()) {
					referenciaFld.setText("");
					cantidadFld.setText("0");
					descripcionFld.setText("");
					marcaFld.setText("");
					impuestoFld.setText("16");
					precioPublicoFld.setText("");
					precioMayoristaFld.setText("");
					costoFld.setText("");
					observacionesFld.setText("");
					codigoFld.setText("");
					referenciaFld.requestFocus();
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
		
		// Valores iniciales
		Supplier_DatabaseProxy DB = Supplier_DatabaseProxy.getInstancia();
		proveedorCbx = new JComboBox(DB.getListaProveedores());
		presentacionCbx = new JComboBox(ParametrosGlobales.LISTA_PRESENTACION);
		DB = null;
		cantidadFld.setEditable(false);
		
		ValidadorNumerico keyList = new ValidadorNumerico(aceptarBtn, cancelarBtn);
		cantidadFld.addKeyListener(keyList);
		impuestoFld.addKeyListener(keyList);
		precioPublicoFld.addKeyListener(keyList);
		precioMayoristaFld.addKeyListener(keyList);
		costoFld.addKeyListener(keyList);

		referenciaFld.addKeyListener(this);
		descripcionFld.addKeyListener(this);
		marcaFld.addKeyListener(this);
		observacionesFld.addKeyListener(this);
		codigoFld.addKeyListener(this);

		add(new JLabel(" Referencia"));
		add(referenciaFld);
		add(new JLabel(" Cantidad"));
		add(cantidadFld);
		add(new JLabel(" Descripcion"));
		add(descripcionFld);
		add(new JLabel(" Marca"));
		add(marcaFld);
		add(new JLabel(" Presentacion"));
		add(presentacionCbx);
		add(new JLabel(" Impuesto"));
		add(impuestoFld);
		add(new JLabel(" Precio Publico"));
		add(precioPublicoFld);
		add(new JLabel(" Precio Mayorista"));
		add(precioMayoristaFld);
		add(new JLabel(" Proveedor"));
		add(proveedorCbx);
		add(new JLabel(" Costo"));
		add(costoFld);
		add(new JLabel(" Observaciones"));
		add(observacionesFld);
		add(new JLabel(" Codigo"));
		add(codigoFld);
		add(continuarCkb);
		add(Box.createRigidArea(new Dimension(2,2)));
		add(aceptarBtn);
		add(cancelarBtn);
		
		setSize(260,360);
		setTitle("Agregar Producto...");
		setLocation(50, Store.screenSize.height/2-180);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
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