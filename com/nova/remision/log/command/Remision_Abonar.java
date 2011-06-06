package com.nova.remision.log.command;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;

/**
 * @author Camilo Nova
 * @version 1.0
 */
public class Remision_Abonar extends JDialog implements Remision_IComando, KeyListener {

	/**
	 * 
	 */
	public Remision_Abonar() {
	}
	
	/**
	 * Metodo que ejecuta el comando
	 */
	public void ejecutarComando() {
		setVisible(true);
	}

	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
/*		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			buscarBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
*/	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}

}
