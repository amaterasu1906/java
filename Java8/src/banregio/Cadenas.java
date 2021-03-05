package banregio;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 2.- Dada la cadena “itatmtatstttrtititititiingwaitwaaaaaaaaaaaaaatttt “, determine el código
	necesario para crear una función que identifique el caracter con mas repeticiones
	continuas.

 */
public class Cadenas {

	public static void main(String[] args) {
		String cadena = "itatmtatstttrtititititiingwaitwaaaaaaaaaaaaaatttt ";
		
		Map<Character, Integer> lista = new HashMap<>();
		
		for (int i = 0; i < cadena.length(); i++) {
			if(lista.containsKey(cadena.charAt(i))) {
				Integer count = lista.get(cadena.charAt(i));
				lista.put(cadena.charAt(i), count+1);
			}else {
				lista.put(cadena.charAt(i), 1);
			}
		}
		Character masRepetido = null;
		Integer x = 0;
		for ( Entry<Character, Integer> map : lista.entrySet()) {
			if(map.getValue() > x) {
				x = map.getValue();
				masRepetido = map.getKey();
			}
		}
		System.out.println("Mas repetido: " + masRepetido.toString());
	}
}
