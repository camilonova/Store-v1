package com.nova.supplier.log.builder;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Esta clase representa la barra de estado de la aplicacion,
 * donde se informa de los cambios o de las acciones tomadas
 * por la aplicacion
 * @author Camilo Nova
 * @version 1.0
 */
public class Supplier_BarraEstadoBuilder extends JLabel implements Supplier_AbstractBuilder {

	/**
	 * Constructor de la clase. Determina el color de fondo
	 * el borde y el texto de bienvenida.
	 */
	public Supplier_BarraEstadoBuilder() {
		setBackground(Color.GRAY);
	}
	
	/**
	 * Metodo sobrecargado para corregir la alineacion del texto
	 */
	public void setText(String text) {
		super.setText("  "+text);
	}

}
