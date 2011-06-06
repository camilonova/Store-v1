package com.nova.log.builder;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;

/**
 * Esta clase representa la barra de herramientas en donde estan
 * disponibles los modulos de la solucion informatica.
 * @author Camilo Nova
 * @version 1.0
 */
public class MenuAccesoComponentes extends JMenuBar {

	private JMenu modulosMnu = new JMenu("Modulos");
	
	private JMenu herramientasMnu = new JMenu("Herramientas");
	
	private JMenu ayudaMnu = new JMenu("Ayuda");


	private JMenuItem remisionItm = new JMenuItem("Remisiones");
	
	private JMenuItem cotizacionItm = new JMenuItem("Cotizaciones");
	
	private JMenuItem inventarioItm = new JMenuItem("Inventario");
	
	private JMenuItem salirItm = new JMenuItem("Salir");
	
	private JMenuItem clientesItm = new JMenuItem("Clientes");
	
	private JMenuItem proveedoresItm = new JMenuItem("Proveedores");

	private JMenuItem cuentasxCobrarItm = new JMenuItem("Cuentas por Cobrar");

	private JMenuItem calculadoraItm = new JMenuItem("Calculadora");

	private JMenuItem ayudaItm = new JMenuItem("Ayuda");
	
	private JMenuItem acercaDeItm = new JMenuItem("Acerca de...");
	
	private Componentes componentes;
	
	private JDesktopPane desktopPane;

	public MenuAccesoComponentes(JDesktopPane desktop) {
		desktopPane = desktop;
		
		// -- Menu Modulos -- //
		
		remisionItm.setEnabled(false);
		remisionItm.setMnemonic('r');
		remisionItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		remisionItm.setToolTipText("Abre el modulo de Remisiones");
		remisionItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new Remision();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		cotizacionItm.setEnabled(true);
		cotizacionItm.setMnemonic('c');
		cotizacionItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		cotizacionItm.setToolTipText("Abre el modulo de Cotizaciones");
		cotizacionItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new Estimed();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		inventarioItm.setEnabled(false);
		inventarioItm.setMnemonic('i');
		inventarioItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		inventarioItm.setToolTipText("Abre el modulo de Inventario");
		inventarioItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new Stock();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		salirItm.setMnemonic('s');
		salirItm.setToolTipText("Salir de la aplicacion");
		salirItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseProxy DB = DatabaseProxy.getInstancia();
				DB.desconectar();
				System.exit(0);
			}
		});
		
		// -- Menu Herramientas -- //
		
		clientesItm.setEnabled(false);
		clientesItm.setMnemonic('c');
		clientesItm.setToolTipText("Muestra los clientes");
		clientesItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_MASK));
		clientesItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new Client();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		proveedoresItm.setEnabled(false);
		proveedoresItm.setMnemonic('p');
		proveedoresItm.setToolTipText("Muestra los proveedores");
		proveedoresItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		proveedoresItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new Supplier();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		cuentasxCobrarItm.setEnabled(false);
		cuentasxCobrarItm.setMnemonic('o');
		cuentasxCobrarItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		cuentasxCobrarItm.setToolTipText("Abre el modulo de Cuentas por Cobrar");
		cuentasxCobrarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new CuentasxCobrar();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		calculadoraItm.setMnemonic('a');
		calculadoraItm.setToolTipText("Abre la calculadora de impuestos");
		calculadoraItm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		calculadoraItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				componentes = new TaxCalculator();
				desktopPane.add((Component) componentes);
				try {
					((JInternalFrame) componentes).setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// -- Menu Ayuda -- //
		
		ayudaItm.setMnemonic('a');
		ayudaItm.setToolTipText("Abre la ayuda de la aplicacion");
		ayudaItm.setIcon(new ImageIcon("ico/general/Help16.gif"));
		ayudaItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		acercaDeItm.setMnemonic('c');
		acercaDeItm.setToolTipText("Muestra la informacion sobre la aplicacion");
		acercaDeItm.setIcon(new ImageIcon("ico/general/About16.gif"));
		acercaDeItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Desarrollado por " +
						"Camilo Hernando Nova \n\tNova Inc. 2005");
			}
		});
		
		modulosMnu.setMnemonic('m');
		modulosMnu.add(remisionItm);
		modulosMnu.add(cotizacionItm);
		modulosMnu.add(inventarioItm);
		modulosMnu.addSeparator();
		modulosMnu.add(salirItm);
		
		herramientasMnu.setMnemonic('h');
		herramientasMnu.add(clientesItm);
		herramientasMnu.add(proveedoresItm);
		herramientasMnu.add(cuentasxCobrarItm);
		herramientasMnu.addSeparator();
		herramientasMnu.add(calculadoraItm);
		
		ayudaMnu.setMnemonic('y');
		ayudaMnu.add(ayudaItm);
		ayudaMnu.addSeparator();
		ayudaMnu.add(acercaDeItm);
		
		// Damos permisos
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 3) {
			inventarioItm.setEnabled(true);
			remisionItm.setEnabled(true);
		}
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 4) {
			clientesItm.setEnabled(true);
			proveedoresItm.setEnabled(true);
		}
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) > 5)
			cuentasxCobrarItm.setEnabled(true);
		if(Integer.parseInt(ParametrosGlobales.TIPO_ACCESO) == 7)
			System.out.println("Welcome my LORD...");
		
		add(modulosMnu);
		add(herramientasMnu);
		add(ayudaMnu);
	}
}
