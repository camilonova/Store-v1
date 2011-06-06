package com.nova.client.log.command;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nova.client.dat.Client_DatabaseProxy;
import com.nova.client.log.builder.Client_PanelBuilder;

/**
 * Esta clase elimina el cliente seleccionado
 * en la tabla.
 * @author Camilo Nova
 * @version 1.0
 */
public class Client_Borrar extends JDialog implements Client_IComando {
	
	/**
	 * Representa el ID del elemento a eliminar
	 */
	private String ID;
	
	/**
	 * Representa la seleccion hecha por el usuario
	 */
	private int seleccion = 0;

	/**
	 * Metodo que ejecuta el comando de eliminar, eliminando el
	 * cliente seleccionado en la tabla
	 */
	public void ejecutarComando() {
		ID = Client_PanelBuilder.getIDClienteSeleccionado();
		
		if(ID.equals("-1"))
			JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente!!!", "Error", JOptionPane.WARNING_MESSAGE);
		else {
			seleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el cliente con ID "+ID, 
														"Eliminar...", JOptionPane.YES_NO_OPTION);
			if(seleccion == JOptionPane.YES_OPTION) {
				Client_DatabaseProxy DB = Client_DatabaseProxy.getInstancia();
				DB.eliminarCliente(ID);
			}
		}
	}
}
