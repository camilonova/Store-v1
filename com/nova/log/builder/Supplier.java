package com.nova.log.builder;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JInternalFrame;

import com.nova.supplier.log.builder.Supplier_AbstractBuilder;
import com.nova.supplier.log.builder.Supplier_BarraEstadoBuilder;
import com.nova.supplier.log.builder.Supplier_BarraHerramientasBuilder;
import com.nova.supplier.log.builder.Supplier_PanelBuilder;

/**
 * @author Camilo
 */
public class Supplier extends JInternalFrame implements Componentes {
	
	private Supplier_AbstractBuilder builder;
	
	public static Supplier_BarraEstadoBuilder statusBar;

	public Supplier() {
		super("Proveedores", true, true, true, true);
		
		builder = new Supplier_BarraHerramientasBuilder();
		add((Component) builder, BorderLayout.NORTH);
		
		builder = new Supplier_BarraEstadoBuilder();
		add((Component) builder, BorderLayout.SOUTH);
		
		statusBar = (Supplier_BarraEstadoBuilder) builder;

		builder = new Supplier_PanelBuilder();
		add((Component) builder, BorderLayout.CENTER);
		
		builder = null;
		
		setSize(600,300);
		setAutoscrolls(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
