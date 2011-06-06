package com.nova.stock.log.command;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nova.stock.dat.Stock_DatabaseProxy;
import com.nova.stock.log.builder.Stock_PanelBuilder;

/**
 * Esta clase elimina el elemento seleccionado
 * en la tabla.
 * @author Camilo Nova
 * @version 1.0
 */
public class Stock_Eliminar extends JDialog implements Stock_IComando {
	
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
	 * elemento seleccionado en la tabla
	 */
	public void ejecutarComando() {
		ID = Stock_PanelBuilder.getIDProductoSeleccionado();
		
		if(ID.equals("-1"))
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento!!!", "Error", JOptionPane.WARNING_MESSAGE);
		else
			seleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el producto con ID "+ID, 
														"Eliminar...", JOptionPane.YES_NO_OPTION);
		
		if(seleccion == JOptionPane.YES_OPTION) {
			Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
			DB.eliminarProducto(ID);
		}
	}
}
