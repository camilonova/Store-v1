package com.nova.log.builder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;

import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_AbstractBuilder;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_BarraEstadoBuilder;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_BarraHerramientasBuilder;
import com.nova.cuentasxcobrar.log.builder.CuentasxCobrar_PanelBuilder;
import com.nova.gui.Store;

/**
 * Representa el modulo de ventas a credito en la aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar extends JInternalFrame implements Componentes {
	
	/**
	 * Representa el constructor abstracto del modulo
	 */
	private CuentasxCobrar_AbstractBuilder builder;
	
	/**
	 * Representa la barra de estado del modulo
	 */
	public static CuentasxCobrar_BarraEstadoBuilder statusBar;

	public CuentasxCobrar() {
		super("Cuentas por Cobrar", true, true, true, true);
		
		builder = new CuentasxCobrar_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new CuentasxCobrar_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (CuentasxCobrar_BarraEstadoBuilder) builder;

		builder = new CuentasxCobrar_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(800,300);
		setLocation(Store.screenSize.width/2-400, Store.screenSize.height-400);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
