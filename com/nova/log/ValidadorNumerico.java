package com.nova.log;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

/**
 * Esta clase es un listener de teclado que valida que la entrada
 * sea unicamente numerica.
 * @author Camilo Nova
 * @version 1.0
 */
public class ValidadorNumerico implements KeyListener {
	
	/**
	 * Representa el boton aceptar
	 */
	private JButton aceptarBtn;
	
	/**
	 * Representa el boton cancelar
	 */
	private JButton cancelarBtn;
	
	/**
	 * Constructor de la clase, crea dos botones
	 * fantasma para ser compatible con los
	 * demas keyListener.
	 *
	 */
	public ValidadorNumerico() {
		this(new JButton(), new JButton());
	}
	
	/**
	 * Constructor de la clase, recibe como parametros los
	 * botones aceptar y cancelar
	 * @param acep		Boton de aceptar
	 * @param canc		Boton de cancelar
	 */
	public ValidadorNumerico(JButton acep, JButton canc) {
		aceptarBtn = acep;
		cancelarBtn = canc;
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
