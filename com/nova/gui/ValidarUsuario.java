package com.nova.gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nova.dat.DatabaseProxy;
import com.nova.dat.ParametrosGlobales;

/**
 * Esta clase muestra la ventana de validacion del usuario, y valida si
 * sus datos son los correctos, si lo son permite continuar con la carga
 * de la aplicacion, en caso contrario sale de la misma.
 * @author Camilo Nova
 * @version 1.0
 */
public class ValidarUsuario extends JDialog implements KeyListener {

	/**
	 * Representa la instancia unica de la clase
	 */
	private static ValidarUsuario validarUsuario;

	/**
	 * Representa el numero de intentos de acceso maximos
	 */
	private int numIntentos = 0;
	
	/**
	 * Representa la cadena de la informacion de inicio de sesion
	 */
	private String SesionInfo;

	/**
	 * Representa el area donde se digita el usuario
	 */
	private JTextField usuarioFld = new JTextField();
	
	/**
	 * Representa el area donde se digita la contraseña
	 */
	private JPasswordField passwordFld = new JPasswordField();

	/**
	 * Representa el boton de entrar
	 */
	private JButton entrarBtn;
	
	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn;

	/**
	 * Constructor de la clase. Para obtener una instancia de la clase
	 * es necesaria la llamada al metodo <i>getInstancia</i>.
	 *
	 */
	private ValidarUsuario() {
		setLayout(new GridLayout(3,2));
		
		add(new JLabel("Usuario:"));
		usuarioFld = new JTextField();
		usuarioFld.addKeyListener(this);
		add(usuarioFld);
		add(new JLabel("Password:"));
		passwordFld = new JPasswordField();
		passwordFld.addKeyListener(this);
		add(passwordFld);

		entrarBtn = new JButton("Entrar");
		entrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numIntentos++;
				// Numero de intentos de acceso maximos
				if(numIntentos == ParametrosGlobales.NUM_INTENTOS_MAXIMOS)
					System.exit(0);
				
				DatabaseProxy DB = DatabaseProxy.getInstancia();
				
				// Validamos si el usuario es valido
				if(DB.validarUsuario(usuarioFld.getText(), String.valueOf(passwordFld.getPassword()))) {
					SesionInfo = DB.getSesionData(); 
					DB.iniciarSesion();
					dispose();
				}
				else {
					usuarioFld.setText("");
					passwordFld.setText("");
					usuarioFld.requestFocus();
				}
			}
		});
		add(entrarBtn);
		
		cancelarBtn = new JButton("Cancelar");
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseProxy DB = DatabaseProxy.getInstancia();
				DB.desconectar();
				dispose();
				System.exit(0);
			}
		});
		add(cancelarBtn);
		
		setSize(200,100);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-100, Toolkit.getDefaultToolkit().getScreenSize().height/2-50);
		setTitle("Valide su Acceso");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelarBtn.doClick();
			}
		});

		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}
	
	/**
	 * Metodo que retorna la informacion de inicio de sesion
	 * @return		Cadena con la informacion
	 */
	public String getSesionInfo() {
		return SesionInfo;
	}
	
	/**
	 * Metodo que retorna una unica instancia de la
	 * clase, aplicando el <i>Singleton Pattern</i>.
	 * @return		Instancia Unica de la clase
	 */
	public static ValidarUsuario getInstancia() {
		if(validarUsuario == null)
			validarUsuario = new ValidarUsuario();
		return validarUsuario;
	}

	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			entrarBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}
}
