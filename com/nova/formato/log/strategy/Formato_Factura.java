package com.nova.formato.log.strategy;

import javax.swing.JInternalFrame;

/**
 * @author Camilo
 */
public class Formato_Factura extends Formato {

	/**
	 * @param frame
	 */
	public Formato_Factura(JInternalFrame framePrincipal) {
		super(framePrincipal);
	}

	public boolean guardar() {
		return false;
	}

	public void nuevo() {
	}

	public void modificar() {
	}

	public void ver() {
	}

}
