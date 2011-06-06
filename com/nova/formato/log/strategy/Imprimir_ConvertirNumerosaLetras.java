package com.nova.formato.log.strategy;

/**
 * Esta clase convierte un numero a su representacion en letras,
 * esta clase inicialmente fue diseñada como una clase funcional
 * y luego optimizada como una clase componente del conjunto de
 * clases que componen el paquete de formato.
 * @author Andrés Guillermo Moreno Pérez, Powered by Camilo Nova
 * @version 2.0
 */
public class Imprimir_ConvertirNumerosaLetras {
	
	/**
	 * Representa en letras los valores numericos
	 */
	private String Numero[] = {"","UN ","DOS ","TRES ","CUATRO ","CINCO ","SEIS ","SIETE ","OCHO ","NUEVE ","DIEZ ",
			"ONCE ","DOCE ","TRECE ","CATORCE ","QUINCE ","DIECISEIS","DIECISIETE","DIECIOCHO","DIECINUEVE","VEINTE"};
	
	/**
	 * Representa en letras los valores de las decenas
	 */
	private String Decenas[] = {"VENTI","TREINTA ","CUARENTA ","CINCUENTA ","SESENTA ","SETENTA ","OCHENTA ","NOVENTA ","CIEN "};
	
	/**
	 * Representa en letras los valores de las centenas
	 */
	private String Centenas[] = {"CIENTO ","DOSCIENTOS ","TRESCIENTOS ","CUATROCIENTOS ","QUINIENTOS ","SEISCIENTOS ",
			"SETECIENTOS ","OCHOCIENTOS ","NOVECIENTOS "};
	
	/**
	 * Representa el numero convertido
	 */
	private String enLetras = new String();
	
	/**
	 * Constructor de la clase, convierte el numero pasado por
	 * parametro a su representacion en letras.
	 * @param numero					Numero a convertir
	 * @throws NumberFormatException
	 */
	public Imprimir_ConvertirNumerosaLetras(String numero) throws NumberFormatException {
		// Validamos que sea un numero legal
		if (Integer.parseInt(numero) > 999999999)
			throw new NumberFormatException("El numero es mayor de 999'999.999, " +
					"no es posible convertirlo");
		
		//Descompone el trio de millones - ¡SGT!
		int k = Integer.parseInt(String.valueOf(Dataposi(numero,8)) + String.valueOf(Dataposi(numero,7)) + String.valueOf(Dataposi(numero,6)));
		if(k == 1)
			enLetras = "UN MILLON ";
		if(k > 1)
			enLetras = Interpretar(String.valueOf(k)) + "MILLONES ";

		//Descompone el trio de miles - ¡SGT!
		int l = Integer.parseInt(String.valueOf(Dataposi(numero,5)) + String.valueOf(Dataposi(numero,4)) + String.valueOf(Dataposi(numero,3)));
		if(l == 1)
			enLetras += "MIL ";
		if(l > 1)
			enLetras += Interpretar(String.valueOf(l))+"MIL ";

		//Descompone el ultimo trio de unidades - ¡SGT!
		int j = Integer.parseInt(String.valueOf(Dataposi(numero,2)) + String.valueOf(Dataposi(numero,1)) + String.valueOf(Dataposi(numero,0)));
		if(j == 1)
			enLetras += "UN";
		
		if(k + l + j == 0)
			enLetras += "CERO";
		if(j > 1)
			enLetras += Interpretar(String.valueOf(j));
		
		enLetras += "PESOS";

	}
	
	/**
	 * Metodo que interpreta los trios de numeros que componen las unidades,
	 * las decenas y las centenas del numero.
	 * @param num			Numero a interpretar, debe ser de longitud menor
	 * 						o igual a 3
	 * @return				Cadena interpretada
	 */
	private String Interpretar(String num) {
		if(num.length() > 3)
			return "";
		String salida="";
		
		if(Dataposi(num,2) != 0)
			salida = Centenas[Dataposi(num,2)-1];
		
		int k = Integer.parseInt(String.valueOf(Dataposi(num,1))+String.valueOf(Dataposi(num,0)));

		if(k <= 20)
			salida += Numero[k];
		else {
			if(k > 30 && Dataposi(num,0) != 0)
				salida += Decenas[Dataposi(num,1)-2] + "Y " + Numero[Dataposi(num,0)];
			else
				salida += Decenas[Dataposi(num,1)-2] + Numero[Dataposi(num,0)];
		}
		//Caso especial con el 100
		if(Dataposi(num,2) == 1 && k == 0)
			salida="CIEN";
		
		return salida;
	}
	
	/**
	 * Metodo que retorna el numero en la posicion de derecha
	 * a izquierda
	 * @param cadena		Cadena a examinar
	 * @param pos			Posicion del numero
	 * @return				Numero en la posicion del parametro
	 */
	private int Dataposi(String cadena,int posicion){
		if(cadena.length() > posicion && posicion >= 0)
			return cadena.charAt(cadena.length()-posicion-1)-48;
		return 0;
	}
	
	/**
	 * Metodo que retorna la representacion en letras del numero
	 * pasado al constructor como parametro
	 */
	public String toString(){
		return enLetras;
	}	
}
