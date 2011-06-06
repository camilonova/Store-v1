package com.nova.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.log.builder.MenuAccesoComponentes;

/**
 * Esta clase crea el entorno en donde se muestran los diferentes
 * modulos de la aplicacion. Selecciona el Look and Feel de la aplicacion
 * y hace un llamado a la clase <i>ValidarUsuario</i> para validar la entrada.
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class Store extends JFrame {
	
	/**
	 * Representa el tamaño de la pantalla
	 */
	public static Dimension screenSize = new Dimension();
	
	/**
	 * Representa el area donde se muestran las diferentes ventanas
	 */
	public static JDesktopPane desktop = new JDesktopPane();
	
	/**
	 * Representa el menu principal de la aplicacion,
	 * desde donde se acceden a los modulos
	 */
	private MenuAccesoComponentes accesoComponentes;

	/**
	 * Constructor de la clase. Hace la respectiva validacion
	 * del usuario al hacer un llamado a la clase <i>ValidarUsuario</i>
	 * , luego determina las caracteristicas del entorno grafico.
	 *
	 */
	public Store() {
		super("Store v1.0 by Nova - " + ParametrosGlobales.OWNER);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Inicializamos la conexion con la DB
		DatabaseProxy.getInstancia();
		
		// Mostramos la imagen inicial de la aplicacion
		JWindow window = new JWindow();
		window.add(new JLabel(new ImageIcon(ParametrosGlobales.DEVELOPER_LOGO)));
		window.setSize(screenSize.width, screenSize.height);
		window.setVisible(true);
		
		// Mostramos la ventana de validacion
		ValidarUsuario validarUsr = ValidarUsuario.getInstancia();
		validarUsr.setVisible(true);
		
		// Mostramos la informacion de acceso
		JOptionPane.showMessageDialog(this, validarUsr.getSesionInfo());
		
		// Luego de validado liberamos memoria
		validarUsr = null;

		accesoComponentes = new MenuAccesoComponentes(desktop);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DatabaseProxy DB = DatabaseProxy.getInstancia();
				DB.desconectar();
				dispose();
				System.exit(0);
			}
		});

		setContentPane(desktop);
		setJMenuBar(accesoComponentes);
		setSize(screenSize.width, screenSize.height-25);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		// Cerramos la imagen inicial de la aplicacion
		window.dispose();
		window = null;
	}
	
	/**
	 * Metodo principal. Selecciona el Look and Feel y
	 * arranca la aplicacion.
	 * @param args	Sin uso.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(ParametrosGlobales.LOOK_AND_FEEL);
		} catch (Exception e1) {
			System.err.println("No se encuentra el Look & Feel, se continua sin el");
		}
		new Store();
	}
}
