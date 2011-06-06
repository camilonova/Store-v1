package com.nova.cuentasxcobrar.log.command;

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

import com.nova.cuentasxcobrar.dat.CuentasxCobrar_DatabaseProxy;
import com.nova.dat.ParametrosGlobales;

/**
 * Esta clase genera un reporte de las cuentas por cobrar que
 * aun no estan canceladas y las envia a la impresora
 * @author Camilo Nova
 * @version 1.0
 */
public class CuentasxCobrar_Reportes_Pendientes {
	
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
	 * Constructor de la clase. Genera el reporte
	 * y lo envia a la impresora
	 */
	public CuentasxCobrar_Reportes_Pendientes() {
		CuentasxCobrar_DatabaseProxy DB = CuentasxCobrar_DatabaseProxy.getInstancia();
		String[][] datos = DB.getAllData();

		long totalExcluido = 0;
		long totalGravado = 0;
		long totalIva = 0;
		long total = 0;
		
		try {
			printJob = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "STORE_REPORT", null);
			
			if(printJob != null)
				imprimirEncabezado();
			else
				return;

			for(int i=0, espacio=0; i < datos.length; i++) {
				// Filtramos los datos del cliente
				if(datos[i][8].equals("")) {
					impresion.setFont(new Font("Arial", Font.PLAIN, 8));
			
					// Factura
					impresion.drawString(datos[i][0], alinearTextoDerecha(50, datos[i][0]), 150+(13*espacio));
					// Cliente
					if(impresion.getFontMetrics().stringWidth(datos[i][1]) < 150)
						impresion.drawString(datos[i][1], 65, 150+(13*espacio));
					else {
						impresion.drawString(datos[i][1].substring(0, 30), 65, 150+(13*espacio));
						espacio++;
						impresion.drawString(datos[i][1].substring(30), 65, 150+(13*espacio));
					}
					// Fecha Facturacion
					impresion.drawString(datos[i][2], 215, 150+(13*espacio));
					// Valor Excluido
					impresion.drawString(datos[i][3], alinearTextoDerecha(300, datos[i][3]), 150+(13*espacio));
					// Valor Gravado
					impresion.drawString(datos[i][4], alinearTextoDerecha(350, datos[i][4]), 150+(13*espacio));
					// Valor Iva
					impresion.drawString(datos[i][5], alinearTextoDerecha(400, datos[i][5]), 150+(13*espacio));
					// Valor Total
					impresion.drawString(datos[i][6], alinearTextoDerecha(460, datos[i][6]), 150+(13*espacio));
					// Fecha Cancelacion
					impresion.drawString("Sin Cancelar", 485, 150+(13*espacio));
					
					espacio++;
					totalExcluido += Long.parseLong(datos[i][3]);
					totalGravado += Long.parseLong(datos[i][4]);
					totalIva += Long.parseLong(datos[i][5]);
					total += Long.parseLong(datos[i][6]);

					if((i+1) % 40 == 0 && (i+1) != datos.length) {
						// Pasamos a la siguiente hoja
						espacio = 0;
						imprimirEncabezado();

						// Numero de la pagina
						impresion.setFont(new Font("Arial", Font.PLAIN, 8));
						impresion.drawString("Hoja "+String.valueOf((i+1)/40+1), 550, 30);
					}
				}
			}
			/***************************** Imprimimos los resultados *********************/
			impresion.setFont(new Font("Dialog", Font.BOLD, 12));
			// Total Excluido
			impresion.drawString("TOTAL EXCLUIDO", 20, printJob.getPageDimension().height-130);
			impresion.drawString(convertir(String.valueOf(totalExcluido)), 20, printJob.getPageDimension().height-100);
			// Total Gravado
			impresion.drawString("TOTAL GRAVADO", 170, printJob.getPageDimension().height-130);
			impresion.drawString(convertir(String.valueOf(totalGravado)), 170, printJob.getPageDimension().height-100);
			// Total Iva
			impresion.drawString("TOTAL IVA", 320, printJob.getPageDimension().height-130);
			impresion.drawString(convertir(String.valueOf(totalIva)), 320, printJob.getPageDimension().height-100);
			// Total
			impresion.drawString("TOTAL", 470, printJob.getPageDimension().height-130);
			impresion.drawString(convertir(String.valueOf(total)), 470, printJob.getPageDimension().height-100);
			
			printJob.end();
		} catch (HeadlessException e) {
		}
	}
	
	/**
	 * Metodo que imprime el encabezado del formato
	 */
	private void imprimirEncabezado() {
		impresion = printJob.getGraphics();
		
		impresion.setFont(new Font("Dialog", Font.BOLD, 16));
		impresion.setColor(Color.BLACK);
		impresion.drawString("REPORTE POR CLIENTE - CUENTAS POR COBRAR", centrarTexto(printJob.getPageDimension().width/2, "REPORTE DE FECHAS - CUENTAS POR COBRAR"), 50);
		impresion.drawString("FACTURAS PENDIENTES", centrarTexto(printJob.getPageDimension().width/2, "FACTURAS PENDIENTES"), 70);
		impresion.drawString(ParametrosGlobales.OWNER, centrarTexto(printJob.getPageDimension().width/2, ParametrosGlobales.OWNER), 90);
		
		impresion.setFont(new Font("Dialog", Font.BOLD, 8));
		impresion.drawString("Factura", 25, 130);
		impresion.drawString("Cliente", 65, 130);
		impresion.drawString("Fecha", 215, 130);
		impresion.drawString("Excluido", 270, 130);
		impresion.drawString("Gravado", 320, 130);
		impresion.drawString("Iva", 380, 130);
		impresion.drawString("Total", 430, 130);
		impresion.drawString("Fecha Canc.", 480, 130);
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
	 * Metodo que alinea el texto pasado por parametro en la posicion
	 * central de la hoja
	 * @param posicion			Posicion en la cual va el texto
	 * @param texto				Texto a alinear
	 * @return					Posicion alineada del texto
	 */
	private int centrarTexto(int posicion, String texto) {
		return posicion-impresion.getFontMetrics().stringWidth(texto)/2;
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
