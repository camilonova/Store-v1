package com.nova.formato.log.strategy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.nova.stock.dat.Stock_DatabaseProxy;

/**
 * Esta clase, imprime en el formato predeterminado por el cliente
 * los datos obtenidos del formato pasado por parametro al constructor
 * Fue una clase hecha "a puro pulso" :)
 * @author Camilo Nova
 * @version 1.5
 */
public class Imprimir_Formato {
	
	/**
	 * Representa el tipo de conversion que se debe utilizar para los numeros
	 */
	private NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.US);
	
	/**
	 * Representa el trabajo de impresion manejado por el OS
	 */
	private PrintJob printJob;
	
	/**
	 * Representa los graficos en pixeles a imprimir en el trabajo de impresion
	 */
	private Graphics impresion;
	
	/**
	 * Representa la clase que convierte un numero a su representacion en letras
	 */
	private Imprimir_ConvertirNumerosaLetras convertir;

	/**
	 * Constructor de la clase, imprime en papel el formato
	 * recibido por parametro
	 * @param formato		Formato a imprimir
	 */
	protected Imprimir_Formato(Formato formato) {
		numberFormat.setMaximumFractionDigits(0);

		try {
			printJob = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "STORE_FORMAT", null);
			
			if(printJob != null)
				imprimirEncabezado(formato);
			else
				return;
			
			/**************************	Imprimimos el cuerpo ***************************/
			
			String[][] datos = formato.tabla.getDatosTabla();
			Stock_DatabaseProxy DB = Stock_DatabaseProxy.getInstancia();
			impresion.setFont(new Font("Arial", Font.PLAIN, 8));
			
			for(int i=0,espacio=0; i < datos.length; i++,espacio++) {
				// Item
				impresion.drawString(String.valueOf(i+1), alinearTextoDerecha(50, String.valueOf(i+1)), 290+(13*espacio));
				// Elemento
				if(impresion.getFontMetrics().stringWidth(datos[i][1]) < 250)
					impresion.drawString(datos[i][1], 65, 290+(13*espacio));
				else {
					impresion.drawString(datos[i][1].substring(0, 60), 65, 290+(13*espacio));
					espacio++;
					impresion.drawString(datos[i][1].substring(60), 65, 290+(13*espacio));
				}
				// Presentacion
				impresion.drawString(DB.getPresentacion(datos[i][1].split(" - ")[0]), 315, 290+(13*espacio));
				// Cantidad
				impresion.drawString(datos[i][0], alinearTextoDerecha(380, datos[i][0]), 290+(13*espacio));
				
				if(datos[i][3].equals(" "))// Valor Unitario Excento
					impresion.drawString(convertir(datos[i][4]), alinearTextoDerecha(510, convertir(datos[i][4])), 290+(13*espacio));
				else// Valor Unitario Gravado
					impresion.drawString(convertir(datos[i][3]), alinearTextoDerecha(450, convertir(datos[i][3])), 290+(13*espacio));
				
				// Valor Total
				impresion.drawString(convertir(datos[i][5]), alinearTextoDerecha(570, convertir(datos[i][5])), 290+(13*espacio));
				
				if((i+1) % 25 == 0 && (i+1) != datos.length) {
					impresion.setFont(new Font("Dialog", Font.PLAIN, 12));
					impresion.drawString("Continua...", 500, 734);
					
					// Pasamos a la siguiente hoja
					espacio = 0;
					imprimirEncabezado(formato);

					// Numero de la pagina
					impresion.setFont(new Font("Arial", Font.PLAIN, 8));
					impresion.drawString("Hoja "+String.valueOf((i+1)/25+1), 520, 160);
				}
			}
			
			/**************************	Imprimimos el Pie ***************************/

			// Total en letras
			impresion.setFont(new Font("Arial", Font.PLAIN, 6));
			impresion.drawString(new Imprimir_ConvertirNumerosaLetras(formato.valorTotalFld.getText()).toString(), 80, 655);
			
			impresion.setFont(new Font("Dialog", Font.PLAIN, 12));
			// Subtotal
			impresion.drawString(convertir(formato.valorSubtotalFld.getText()), alinearTextoDerecha(570, convertir(formato.valorSubtotalFld.getText())), 636);
			// Valor Excluido
			impresion.drawString(convertir(formato.valorExcluidoFld.getText()), alinearTextoDerecha(570, convertir(formato.valorExcluidoFld.getText())), 660);
			// Valor Gravado
			impresion.drawString(convertir(formato.valorGravadoFld.getText()), alinearTextoDerecha(570, convertir(formato.valorGravadoFld.getText())), 682);
			// Valor I.V.A.
			impresion.drawString(convertir(formato.valorIvaFld.getText()), alinearTextoDerecha(570, convertir(formato.valorIvaFld.getText())), 705);
			
			impresion.setFont(new Font("Dialog", Font.BOLD, 14));
			// Gran total
			impresion.drawString(convertir(formato.valorTotalFld.getText()), alinearTextoDerecha(570, convertir(formato.valorTotalFld.getText())), 734);

			printJob.end();
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que imprime el encabezado en una nueva pagina
	 * @param formato		Formato del contructor de la clase
	 */
	private void imprimirEncabezado(Formato formato) {
		impresion = printJob.getGraphics();
		impresion.setFont(new Font("Dialog", Font.BOLD, 16));
		impresion.setColor(Color.BLACK);
		
		/*************************	Imprimimos el encabezado ************************/
		
		impresion.drawString(formato.formatoFld.getText(), 500, 180);
		impresion.setFont(new Font("Dialog", Font.PLAIN, 12));
		impresion.drawString(formato.fechaFld.getText(), 100, 180);
		impresion.drawString(formato.venceFld.getText(), alinearTextoDerecha(370, formato.venceFld.getText()), 180);
		impresion.drawString((String) formato.clienteCbx.getSelectedItem(), 100, 215);
		impresion.drawString(formato.nitFld.getText(), 450, 215);
		impresion.drawString(formato.ciudadFld.getText(), 100, 245);
		impresion.drawString(formato.direccionFld.getText(), 240, 245);
		impresion.drawString(formato.telefonoFld.getText(), 470, 245);
	}

	/**
	 * Metodo que alinea el texto pasado por parametro en la posicion
	 * a la derecha (Utilizado para los numeros)
	 * @param posicion			Posicion en la cual va el texto
	 * @param texto				Texto a alinear
	 * @return					Posicion alineada del texto
	 */
	private int alinearTextoDerecha(int posicion, String texto) {
		return posicion-impresion.getFontMetrics().stringWidth(texto);
	}

	/**
	 * Metodo que convierte el numero pasado a formato para
	 * imprimir
	 * @param numero	Numero a convertir
	 * @return			Numero convertido
	 */
	private String convertir(String numero) {
		int moneda = Integer.parseInt(numero);
		return numberFormat.format(moneda).replaceAll(",",".");	
	}	
}
