package com.nova.log.builder;

import javax.swing.JInternalFrame;

import com.nova.calc.gui.Calc_PanelBuilder;
import com.nova.gui.Store;

/**
 * Esta clase muestra una calculadora de impuesto que es muy funcional
 * para la generacion de las cotizaciones y de las remisiones en la
 * aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class TaxCalculator extends JInternalFrame implements Componentes {

	/**
	 * Constructor de la clase, crea la ventana interna y agrega
	 * la interfaz de la calculadora.
	 *
	 */
	public TaxCalculator() {
		super("Calculadora de Impuesto", false, true, false, true);
		
		add(new Calc_PanelBuilder());
		
		setSize(300,140);
		setLocation(Store.screenSize.width-310, Store.screenSize.height-220);
		setAutoscrolls(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
