package banregio;

/* 1.- Teniendo un reloj de manecillas, desarrolle el c�digo necesario con una funci�n
matem�tica que calcule los grados de diferencia entre las manecillas de hora y minuto
dada la hora 2:15 pm.
Velocidad horario: 360�/12h = 30�/h
Velocidad minutero: 360�/60 min = 6 �/min
2:15 = 2 + (15/60) = 2 + (1/4 ) = 2.25 h
Horario : 2.25 h * 30�/h = 67.5�
Minutero = 15 min * 6 �/min 90�
Entre ellos = 90� - 67.5� = 22.5�
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
