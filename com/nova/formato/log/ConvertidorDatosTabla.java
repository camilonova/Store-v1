package com.nova.formato.log;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Esta clase, codifica los elementos de la cotizacion para ser almacenados
 * en la base de datos, y tambien los decodifica para que sean leidos por la
 * aplicacion.
 * @author Camilo Nova
 * @version 1.0
 */
public class ConvertidorDatosTabla {
	
	/**
	 * Metodo que codifica los datos para almacenarlos
	 * en la base de datos
	 * @param datos			Datos a codificar
	 * @return				Cadena codificada para almacenar
	 */
	public String compilarDatos(String[][] datos) {
		String cat = "";
		for(int i=0; i < datos.length; i++) {
			for(int j=0; j < datos[i].length; j++) {
				cat += datos[i][j];
				if(j <= datos[i].length-1)
					cat += "~";
			}
			if(i <= datos.length-1)
				cat += "|";
		}
		return cat;
	}
	
	/**
	 * Metodo que de-codifica los datos para cargar
	 * la informacion en la aplicacion
	 * @param datos			Cadena codificada
	 * @return				Datos de-codificados para cargar
	 */
	public String[][] descompilarDatos(String datos) {
		Vector c = new Vector();
		StringTokenizer outside = new StringTokenizer(datos, "|");

		while(outside.hasMoreElements()) {
			c.add(outside.nextToken());
		}
		
		String[][] dat = new String[c.size()][6];
		
		for(int i=0; i < c.size(); i++) {
			StringTokenizer tok = new StringTokenizer((String) c.elementAt(i), "~");
			for(int j=0; tok.hasMoreElements(); j++)
				dat[i][j] = tok.nextToken();
		}
		return dat;
	}
	
}
