package com.nova.formato.log.strategy;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.nova.formato.log.ConvertidorDatosTabla;
import com.nova.remision.dat.Remision_DatabaseProxy;
import com.nova.remision.log.builder.Remision_PanelBuilder;

/**
 * Esta clase hereda de formato para transformar el formato
 * a uno del tipo de remision. Hace parte del sistema de clases
 * que utilizan el patron <i>Strategy</i>.
 * @author Camilo Nova
 * @version 2.0
 */
public class Formato_Remision extends Formato {

	/**
	 * Constructor de la clase, llama a Formato para crear
	 * la interfaz.
	 * @param framePrincipal	Frame desde donde se llama
	 */
	public Formato_Remision(JInternalFrame framePrincipal) {
		super(framePrincipal);
		formatoLbl.setText("Remision N°");
		
		Remision_DatabaseProxy DB = Remision_DatabaseProxy.getInstancia();
		formatoFld.setText(String.valueOf(DB.getNumeroUltimaRemision()+1));
	}

	/**
	 * Dispone el formato para la entrada de una nueva remision
	 */
	public void nuevo() {
		construir();
	}

	/**
	 * Dispone el formato para modificar una remision
	 */
	public void modificar() {
		construir();
		
		// Cargamos los datos en la cotizacion a editar
		String numeroRemision = Remision_PanelBuilder.getNumeroRemisionSeleccionada();
		formatoFld.setText(numeroRemision);
		formatoFld.setEditable(false);
		
		Remision_DatabaseProxy DB = Remision_DatabaseProxy.getInstancia();
		String[] datos = DB.cargarRemision(numeroRemision);
		
		clienteCbx.setSelectedItem(datos[0]);
		fechaFld.setText(datos[1]);
		venceFld.setText(datos[2]);
		tabla.setDatosTabla(new ConvertidorDatosTabla().descompilarDatos(datos[3]));
		
		setTotal(datos[4], String.valueOf(Integer.parseInt(datos[5])+Integer.parseInt(datos[6])), datos[5], datos[6], datos[7]);
	}

	/**
	 * Dispone el formato para ver una cotizacion
	 */
	public void ver() {
		modificar();
		desabilitarBotones();
		
		clienteCbx.setEnabled(false);
		formatoFld.setEditable(false);
		fechaFld.setEditable(false);
		venceFld.setEditable(false);
		cantidadFld.setEnabled(false);
		descripcionCbx.setEnabled(false);
		valorUnitarioFld.setEnabled(false);
		agregarBtn.setEnabled(false);
		tabla.setEditable(false);
	}

	/**
	 * Metodo que guarda la cotizacion en la base de datos.
	 * Hace las validaciones necesarias, si alguna no se cumple
	 * se retorna false.
	 * @return 		True si pudo ser guardado, false de lo contrario
	 */
	public boolean guardar() {
		int a = JOptionPane.showConfirmDialog(null, "Esta seguro de guardar la remision?", "Guardar...", JOptionPane.YES_NO_OPTION);

		if(a == JOptionPane.NO_OPTION)
			return false;
		else if(clienteCbx.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente!!!", "Error", JOptionPane.WARNING_MESSAGE);
			clienteCbx.requestFocus();
			return false;
		}
		else if(formatoFld.getText().length() != 4) {
			JOptionPane.showMessageDialog(null, "El numero de remision debe tener 4 digitos!!!", "Error", JOptionPane.WARNING_MESSAGE);
			formatoFld.requestFocus();
			return false;
		}

		String elementos = new ConvertidorDatosTabla().compilarDatos(tabla.getDatosTabla());
		
		Remision_DatabaseProxy DB = Remision_DatabaseProxy.getInstancia();
		
		// Revisamos si guarda o si actualiza la cotizacion actual
		if(formatoFld.isEditable())
			DB.guardarRemision(formatoFld.getText(), (String) clienteCbx.getSelectedItem(), fechaFld.getText(), 
					venceFld.getText(), elementos, totalElementosFld.getText(), valorExcluidoFld.getText(), 
					valorGravadoFld.getText(), valorIvaFld.getText(), valorTotalFld.getText());
		else
			DB.updateRemision(formatoFld.getText(), (String) clienteCbx.getSelectedItem(), fechaFld.getText(),
					venceFld.getText(), elementos, totalElementosFld.getText(), valorExcluidoFld.getText(), 
					valorGravadoFld.getText(), valorIvaFld.getText(), valorTotalFld.getText());
			
		return true;
	}
}
