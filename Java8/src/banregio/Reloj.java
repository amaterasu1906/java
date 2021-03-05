package banregio;

/* 1.- Teniendo un reloj de manecillas, desarrolle el código necesario con una función
matemática que calcule los grados de diferencia entre las manecillas de hora y minuto
dada la hora 2:15 pm.
Velocidad horario: 360º/12h = 30º/h
Velocidad minutero: 360º/60 min = 6 º/min
2:15 = 2 + (15/60) = 2 + (1/4 ) = 2.25 h
Horario : 2.25 h * 30º/h = 67.5º
Minutero = 15 min * 6 º/min 90º
Entre ellos = 90º - 67.5º = 22.5º
*/

public class Reloj {

	public static void main(String[] args) {

		Integer horaInicio = 2;
		Integer minutosInicio = 15;
		
		float velocidadHora = 360 / 12;
		float velocidadMinutero = 360 / 60;
		
		Float horaAngulo = (horaInicio + ( (float)minutosInicio / 60 )) * velocidadHora;
		Float minutoAngulo = minutosInicio * velocidadMinutero;
		Float anguloDiferencia = minutoAngulo - horaAngulo;
		System.out.println("Diferencia de grados en: ".concat(horaInicio.toString()).
				concat(":").
				concat(minutosInicio.toString()).
				concat(" es ").
				concat(anguloDiferencia.toString()));
	}

}
