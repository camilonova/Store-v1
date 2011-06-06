package com.nova.supplier.log.command;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nova.supplier.dat.Supplier_DatabaseProxy;
import com.nova.supplier.log.builder.Supplier_PanelBuilder;

/**
 * Esta clase elimina el proveedor seleccionado
 * en la tabla.
 * @author Camilo Nova
 * @version 1.0
 */
public class Supplier_Eliminar extends JDialog implements Supplier_IComando {
	
	/**
	 * Representa el ID del proveedor a eliminar
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
		ID = Supplier_PanelBuilder.getIDProveedorSeleccionado();
		
		if(ID.equals("-1"))
			JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor!!!", "Error", JOptionPane.WARNING_MESSAGE);
		else
			seleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el proveedor con ID "+ID, 
														"Eliminar...", JOptionPane.YES_NO_OPTION);
		
		if(seleccion == JOptionPane.YES_OPTION) {
			Supplier_DatabaseProxy DB = Supplier_DatabaseProxy.getInstancia();
			DB.eliminarProveedor(ID);
		}
	}
}
