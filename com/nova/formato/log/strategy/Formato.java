package com.nova.formato.log.strategy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.nova.client.dat.Client_DatabaseProxy;
import com.nova.dat.ParametrosGlobales;
import com.nova.formato.log.builder.Formato_Tabla;
import com.nova.formato.log.builder.Formato_ToolBar;
import com.nova.log.ValidadorNumerico;
import com.nova.stock.dat.Stock_DatabaseProxy;

/**
 * Esta clase representa el formato generalizado de operaciones
 * con los elementos del inventario, tales como la facturacion, la
 * generacion de cotizaciones y las remisiones.
 * @author Camilo Nova
 * @version 2.0
 */
public abstract class Formato extends JPanel {

	/**
	 * Representa el panel donde va el logo
	 */
	private JPanel panelLogo = new JPanel();
	
	/**
	 * Representa el panel donde van los datos de la cotizacion
	 */
	private JPanel panelDatos = new JPanel();
	
	/**
	 * Representa el panel donde se agregan elementos a la cotizacion
	 */
	private JPanel panelAgregar = new JPanel();
	
	/**
	 * Representa el panel donde va la tabla de elementos cotizados
	 */
	private JPanel panelTabla = new JPanel();
	
	/**
	 * Representa el panel donde van los totales de la cotizacion
	 */
	private JPanel panelTotal = new JPanel();
	
	/**
	 * Representa la fuente sobre la cual se muestra el texto
	 */
	private Font font = new Font("Arial", Font.BOLD, 16);
	
	private JLabel clienteLbl = new JLabel("Cliente");
	protected JLabel formatoLbl = new JLabel();
	private JLabel direccionLbl = new JLabel("Direccion");
	private JLabel fechaLbl = new JLabel("Fecha");
	private JLabel nitLbl = new JLabel("NIT");
	private JLabel telefonoLbl = new JLabel("Telefono");
	private JLabel ciudadLbl = new JLabel("Ciudad");
	protected JLabel venceLbl = new JLabel("Valido Hasta");
	
	protected JComboBox clienteCbx;
	protected JTextField formatoFld = new JTextField();
	protected JTextField direccionFld = new JTextField();
	protected JFormattedTextField fechaFld = new JFormattedTextField(new Date());
	protected JTextField nitFld = new JTextField();
	protected JTextField telefonoFld = new JTextField();
	protected JTextField ciudadFld = new JTextField();
	protected JFormattedTextField venceFld = new JFormattedTextField(new Date());
	
	private JLabel cantidadLbl = new JLabel("Cantidad");
	private JLabel descripcionLbl = new JLabel("Descripcion");
	private JLabel valorUnitarioLbl = new JLabel("Valor Unitario");
	
	protected JTextField cantidadFld = new JTextField();
	protected JComboBox descripcionCbx;
	protected JTextField valorUnitarioFld = new JTextField();
	protected JButton agregarBtn = new JButton("Agregar");
	
	private JLabel totalElementosLbl = new JLabel("N° Elementos");
	private JLabel valorSubtotalLbl = new JLabel("Subtotal");
	private JLabel valorExcluidoLbl = new JLabel("Excluido");
	private JLabel valorGravadoLbl = new JLabel("Gravado");
	private JLabel valorIvaLbl = new JLabel("IVA");
	private JLabel valorTotalLbl = new JLabel("Valor Total");
	
	protected JTextField totalElementosFld = new JTextField("0");
	protected JTextField valorSubtotalFld = new JTextField("0");
	protected JTextField valorExcluidoFld = new JTextField("0");
	protected JTextField valorGravadoFld = new JTextField("0");
	protected JTextField valorIvaFld = new JTextField("0");
	protected JTextField valorTotalFld = new JTextField("0");
	
	/**
	 * Representa la tabla donde van los elementos
	 */
	protected Formato_Tabla tabla;

	/**
	 * Representa la barra de herramientas
	 */
	protected Formato_ToolBar barraHerramientas;
	
	/**
	 * Representa el frame principal donde se muestra el formato
	 */
	private JInternalFrame framePrincipal;
	
	/**
	 * Metodo abstracto para guardar el formato
	 * @return		True si se pudo guardar, False de lo contrario
	 */
	public abstract boolean guardar();
	
	/**
	 * Metodo abstracto para mostrar el formato sin datos
	 */
	public abstract void nuevo();
	
	/**
	 * Metodo abstracto para mostrar el formato con datos
	 */
	public abstract void modificar();
	
	/**
	 * Metodo abstracto para mostrar los datos del formato
	 */
	public abstract void ver();
	
	/**
	 * Constructor de la clase, hace una copia de la
	 * referencia del frame que la contiene
	 * @param frame		Frame contenedor
	 */
	public Formato(JInternalFrame frame) {
		framePrincipal = frame;
		barraHerramientas = new Formato_ToolBar(this);
	}
	
	/**
	 * Metodo que construye la interface del formato
	 */
	protected void construir() {
		/************************ Creamos el primer panel ******************************/
		panelLogo.setBackground(Color.WHITE);
		panelLogo.add(new JLabel(new ImageIcon(ParametrosGlobales.OWNER_LOGO)));
		panelLogo.setPreferredSize(new Dimension(700,110));

		/************************ Creamos el segundo panel ******************************/
		Client_DatabaseProxy DBc = Client_DatabaseProxy.getInstancia();
		clienteCbx = new JComboBox(DBc.getListaClientes());
		clienteCbx.insertItemAt("Seleccione uno...", 0);
		clienteCbx.setSelectedIndex(0);
		clienteCbx.requestFocus();
		DBc = null;

		clienteCbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clienteCbx.getSelectedIndex() != 0) {
					Client_DatabaseProxy DB = Client_DatabaseProxy.getInstancia();
					String[] datos = DB.getDatosCliente((String) clienteCbx.getSelectedItem());
					direccionFld.setText(datos[0]);
					nitFld.setText(datos[1]);
					telefonoFld.setText(datos[2]);
					ciudadFld.setText(datos[3]);
				}
				else {
					direccionFld.setText("");
					nitFld.setText("");
					telefonoFld.setText("");
					ciudadFld.setText("");
				}
			}
		});
		
		fechaFld.setHorizontalAlignment(JTextField.CENTER);
		venceFld.setHorizontalAlignment(JTextField.CENTER);

		// Agregamos los dias validos a la fecha y la mostramos
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, ParametrosGlobales.DIAS_VALIDEZ);
		venceFld.setText(DateFormat.getDateInstance().format(cal.getTime()));
		
		clienteLbl.setPreferredSize(new Dimension(80,20));
		clienteCbx.setPreferredSize(new Dimension(350,20));
		formatoLbl.setPreferredSize(new Dimension(80,20));
		formatoFld.setPreferredSize(new Dimension(100,20));
		direccionLbl.setPreferredSize(new Dimension(80,20));
		direccionFld.setPreferredSize(new Dimension(350,20));
		fechaLbl.setPreferredSize(new Dimension(80,20));
		fechaFld.setPreferredSize(new Dimension(100,20));
		nitLbl.setPreferredSize(new Dimension(20,20));
		nitFld.setPreferredSize(new Dimension(125,20));
		telefonoLbl.setPreferredSize(new Dimension(45,20));
		telefonoFld.setPreferredSize(new Dimension(80,20));
		ciudadLbl.setPreferredSize(new Dimension(40,20));
		ciudadFld.setPreferredSize(new Dimension(90,20));
		venceLbl.setPreferredSize(new Dimension(90,20));
		venceFld.setPreferredSize(new Dimension(100,20));
		
		ValidadorNumerico validador = new ValidadorNumerico();
		formatoFld.addKeyListener(validador);
		nitFld.addKeyListener(validador);
		telefonoFld.addKeyListener(validador);
		venceFld.addKeyListener(validador);
		
		direccionFld.setEditable(false);
		nitFld.setEditable(false);
		telefonoFld.setEditable(false);
		ciudadFld.setEditable(false);
		
		formatoFld.setForeground(Color.RED);
		formatoFld.setFont(font);
		formatoFld.setHorizontalAlignment(JTextField.RIGHT);
	
		panelDatos.add(clienteLbl);
		panelDatos.add(clienteCbx);
		panelDatos.add(formatoLbl);
		panelDatos.add(formatoFld);
		panelDatos.add(direccionLbl);
		panelDatos.add(direccionFld);
		panelDatos.add(fechaLbl);
		panelDatos.add(fechaFld);
		panelDatos.add(nitLbl);
		panelDatos.add(nitFld);
		panelDatos.add(telefonoLbl);
		panelDatos.add(telefonoFld);
		panelDatos.add(ciudadLbl);
		panelDatos.add(ciudadFld);
		panelDatos.add(venceLbl);
		panelDatos.add(venceFld);
		panelDatos.setPreferredSize(new Dimension(650,80));

		/************************ Creamos el tercer panel ******************************/
		Stock_DatabaseProxy DBs = Stock_DatabaseProxy.getInstancia();
		descripcionCbx = new JComboBox(DBs.getListaElementos());
		descripcionCbx.insertItemAt("Seleccione uno...", 0);
		descripcionCbx.setSelectedIndex(0);
		DBs = null;
		
		descripcionCbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(descripcionCbx.getSelectedIndex() != 0) {
					Stock_DatabaseProxy DBstk = Stock_DatabaseProxy.getInstancia();
					String[] descripcion = ((String) descripcionCbx.getSelectedItem()).split(" - ");
					valorUnitarioFld.setText(DBstk.getPrecioProducto(descripcion[0], descripcion.length == 2 ? descripcion[1]:""));
					DBstk = null;
				}
				else
					valorUnitarioFld.setText("");
			}
		});
		
		cantidadLbl.setPreferredSize(new Dimension(80,20));
		cantidadFld.setPreferredSize(new Dimension(80,20));
		descripcionLbl.setPreferredSize(new Dimension(350,20));
		descripcionCbx.setPreferredSize(new Dimension(350,20));
		valorUnitarioLbl.setPreferredSize(new Dimension(80,20));
		valorUnitarioFld.setPreferredSize(new Dimension(80,20));
		agregarBtn.setPreferredSize(new Dimension(80,20));
		
		cantidadFld.setHorizontalAlignment(JTextField.RIGHT);
		
		cantidadFld.addKeyListener(new ValidadorNumerico(agregarBtn, new JButton()));
		valorUnitarioFld.addKeyListener(new ValidadorNumerico(agregarBtn, new JButton()));
		
		agregarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stock_DatabaseProxy DBstk = Stock_DatabaseProxy.getInstancia();
				String[] descripcion = ((String) descripcionCbx.getSelectedItem()).split(" - ");
				tabla.agregarProducto(cantidadFld.getText(), (String) descripcionCbx.getSelectedItem(), 
					valorUnitarioFld.getText(), DBstk.getImpuestoProducto(descripcion[0]));
				DBstk = null;
				cantidadFld.setText("");
				descripcionCbx.setSelectedIndex(0);
				valorUnitarioFld.setText("");
				cantidadFld.requestFocus();
			}
		});
	
		panelAgregar.add(cantidadLbl);
		panelAgregar.add(descripcionLbl);
		panelAgregar.add(valorUnitarioLbl);
		panelAgregar.add(Box.createRigidArea(new Dimension(80,20)));
		panelAgregar.add(cantidadFld);
		panelAgregar.add(descripcionCbx);
		panelAgregar.add(valorUnitarioFld);
		panelAgregar.add(agregarBtn);
		panelAgregar.setPreferredSize(new Dimension(650,50));

		/************************ Creamos el cuarto panel ******************************/
		tabla = new Formato_Tabla(this);
		panelTabla.add(tabla);
		panelTabla.setBackground(Color.white);
		panelTabla.setPreferredSize(new Dimension(700,270));

		/************************ Creamos el quinto panel ******************************/
		panelTotal.setLayout(new GridLayout(2,4));
		
		totalElementosFld.setEditable(false);
		valorSubtotalFld.setEditable(false);
		valorExcluidoFld.setEditable(false);
		valorGravadoFld.setEditable(false);
		valorIvaFld.setEditable(false);
		valorTotalFld.setEditable(false);
		
		totalElementosFld.setHorizontalAlignment(JTextField.RIGHT);
		valorSubtotalFld.setHorizontalAlignment(JTextField.RIGHT);
		valorExcluidoFld.setHorizontalAlignment(JTextField.RIGHT);
		valorGravadoFld.setHorizontalAlignment(JTextField.RIGHT);
		valorIvaFld.setHorizontalAlignment(JTextField.RIGHT);
		valorTotalFld.setHorizontalAlignment(JTextField.RIGHT);
		
		valorTotalFld.setFont(font);
		
		panelTotal.add(totalElementosLbl);
		panelTotal.add(valorSubtotalLbl);
		panelTotal.add(valorExcluidoLbl);
		panelTotal.add(valorGravadoLbl);
		panelTotal.add(valorIvaLbl);
		panelTotal.add(valorTotalLbl);
		
		panelTotal.add(totalElementosFld);
		panelTotal.add(valorSubtotalFld);
		panelTotal.add(valorExcluidoFld);
		panelTotal.add(valorGravadoFld);
		panelTotal.add(valorIvaFld);
		panelTotal.add(valorTotalFld);
		panelTotal.setPreferredSize(new Dimension(650,40));
		
		/************************ Terminamos ******************************/
		add(panelLogo);
		add(panelDatos);
		add(panelAgregar);
		add(panelTabla);
		add(panelTotal);
	}
	
	/**
	 * Metodo que totaliza los elementos existentes en el formato
	 * @param cantElementos				Cantidad de elementos en el formato
	 * @param subtotal					Suma de los totales de los elementos
	 * @param valorExcluido				Suma de los productos excluidos
	 * @param valorGravado				Suma de los productos gravados
	 * @param iva						Iva calculado sobre la base del valor gravado
	 */
	public void setTotal(String cantElementos, String subtotal, String valorExcluido, String valorGravado, String iva) {
		totalElementosFld.setText(cantElementos);
		valorSubtotalFld.setText(subtotal);
		valorExcluidoFld.setText(valorExcluido);
		valorGravadoFld.setText(valorGravado);
		valorIvaFld.setText(iva);
		valorTotalFld.setText(String.valueOf(Integer.parseInt(valorExcluido) + Integer.parseInt(valorGravado) + Integer.parseInt(iva)));
	}
	
	/**
	 * Metodo que imprime los datos del formato
	 */
	public void imprimir() {
		new Imprimir_Formato(this);
	}
	
	/**
	 * Metodo que elimina la fila seleccionada en
	 * la tabla de elementos
	 */
	public void eliminarFilaSeleccionada() {
		tabla.eliminarFilaSeleccionada();
	}
	
	/**
	 * Metodo que elimina todas las filas de la tabla
	 * de elementos
	 */
	public void eliminarTodasFilas() {
		tabla.eliminarTodo();
	}
	
	/**
	 * Metodo que cierra la ventana donde se muestra el formato
	 */
	public void cerrarVentana() {
		try {
			framePrincipal.setClosed(true);
			framePrincipal.dispose();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que retorna la instancia de la barra de herramientas
	 * @return	Barra de herramientas del formato
	 */
	public JToolBar getToolBar() {
		return barraHerramientas;
	}
	
	/**
	 * Metodo que desabilita los botones de edicion
	 */
	protected void desabilitarBotones() {
		barraHerramientas.deshabilitarBotones();
	}
}
