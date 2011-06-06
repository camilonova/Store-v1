package com.nova.calc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Esta clase muestra una calculadora de impuesto que es muy funcional
 * para la generacion de las cotizaciones y de las remisiones en la
 * aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class Calc_PanelBuilder extends JPanel implements KeyListener {

	/**
	 * Representa la etiqueta de precio
	 */
	private JLabel precioLbl = new JLabel("Precio");
	
	/**
	 * Representa la entrada numerica del precio
	 */
	private JTextField precioFtf = new JTextField();
	
	/**
	 * Representa la etiqueta de impuesto
	 */
	private JLabel impuestoLbl = new JLabel("Impuesto");
	
	/**
	 * Representa la entrada numerica del impuesto
	 */
	private JTextField impuestoFtf = new JTextField();
	
	/**
	 * Representa la seleccion de precio con iva
	 */
	private JRadioButton precioConIvaRdb = new JRadioButton("Precio con IVA", true);
	
	/**
	 * Representa la seleccion de precio sin iva
	 */
	private JRadioButton precioSinIvaRdb = new JRadioButton("Precio sin IVA");
	
	/**
	 * Representa el grupo de botones que permiten una unica eleccion
	 */
	private ButtonGroup grupoBtn = new ButtonGroup();
	
	/**
	 * Representa la etiqueta de total
	 */
	private JLabel totalLbl = new JLabel("Total");
	
	/**
	 * Representa la salida de total
	 */
	private JTextField totalFld = new JTextField();
	
	/**
	 * Representa la etiqueta de impuesto
	 */
	private JLabel impuesto2Lbl = new JLabel("Impuesto");
	
	/**
	 * Representa la salida de impuesto
	 */
	private JTextField impuestoFld = new JTextField();
	
	/**
	 * Constructor de la clase, construye la interfaz
	 * grafica.
	 *
	 */
	public Calc_PanelBuilder() {
		JPanel upperPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		
		precioFtf.setPreferredSize(new Dimension(110,20));
		precioFtf.addKeyListener(this);
		impuestoFtf.setPreferredSize(new Dimension(60,20));
		impuestoFtf.addKeyListener(this);
		impuestoFtf.setText("16");
		
		upperPanel.add(precioLbl);
		upperPanel.add(precioFtf);
		upperPanel.add(impuestoLbl);
		upperPanel.add(impuestoFtf);

		grupoBtn.add(precioConIvaRdb);
		grupoBtn.add(precioSinIvaRdb);
		middlePanel.add(precioConIvaRdb);
		middlePanel.add(precioSinIvaRdb);
		
		totalFld.setPreferredSize(new Dimension(110,20));
		impuestoFld.setPreferredSize(new Dimension(60,20));
		
		totalFld.setEditable(false);
		impuestoFld.setEditable(false);
		lowerPanel.add(totalLbl);
		lowerPanel.add(totalFld);
		lowerPanel.add(impuesto2Lbl);
		lowerPanel.add(impuestoFld);
		
		add(upperPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Metodo que calcula la base y el iva del parametro recibido
	 * @param precio			Precio a calcular impuesto
	 * @param impuesto			Impuesto
	 * @return					String[0] la base, String[1] el iva 
	 */
	public String[] calcularPrecioConIva(String precio, String impuesto) {
		String[] resultado = new String[2];
		
		int valor = Integer.parseInt(precio);
		double imp = Double.parseDouble("1."+impuesto);
		int result = (int) Math.round(valor/imp);
		resultado[0] = String.valueOf(result);
		resultado[1] = String.valueOf(Math.abs(valor-result));
		
		return resultado;
	}
	
	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 * Genera el resultado de la calculadora, redondeado a entero
	 * el resultado mostrado
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			int valor = Integer.parseInt(precioFtf.getText());
			double impuesto = Double.parseDouble("1."+impuestoFtf.getText());
			int resultado = 0;
			
			if(precioConIvaRdb.isSelected())
				resultado = (int) Math.round(valor/impuesto);
			else
				resultado = (int) Math.round(valor*impuesto);
			
			totalFld.setText(String.valueOf(resultado));
			impuestoFld.setText(String.valueOf(Math.abs(valor-resultado)));
		}
	}

	/**
	 * Listener de teclado que restringe la entrada a numeros
	 * solamente
	 */
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)))
			e.consume();
	}

	public void keyPressed(KeyEvent e) {}
}
