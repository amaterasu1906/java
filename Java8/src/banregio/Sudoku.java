package banregio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 3.- Dado un SUDOKU y sus reglas:
	El Sudoku es una cuadrícula de 9x9, dividida en regiones de 3x3, en la cual inicialmente se
	llenan con valores algunas casillas en posiciones aleatorias x,y. El objetivo es rellenar
	todas las casillas con números del 1-9 sin que estos se repitan en una misma fila, columna
	ni región.

 */
public class Sudoku {

	static int y = 3;
	static Integer[][] sudoku1 =  {
			{1,2,3,4,5,6,7,9,8},
			{4,5,6,7,8,9,1,2,3},
			{7,8,9,2,3,1,4,5,6},
			{2,1,4,5,6,8,9,3,7},
			{3,6,5,9,2,7,8,1,4},
			{8,9,7,1,4,3,2,6,5},
			{5,7,2,3,9,4,6,8,1},
			{9,4,8,6,1,5,3,7,2},
			{6,3,1,8,7,2,5,4,9}};
	static Integer sudoku[][] = {
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9}};

	public static void main(String[] args) {
		boolean valido = true;
//		Horizontal
		for (int i = 0; i < 9; i++) {
			boolean v = repetido(Arrays.asList(sudoku[i]));
			if(!v) {
				valido = false;			
			}
		}
//		Vertical
		for (int i = 0; i < 9; i++) {
			List<Integer> vertical = new ArrayList<>();
			for (int j = 0; j < 9; j++) {
				vertical.add(sudoku[j][i]);
			}
			boolean v = repetido(vertical);
			if(!v) {
				valido = false;			
			}
			
		}
//		Por area
		int a = 0, b = 0, c=0;
		for (int t = 0; t < 9; t++) {
			List<Integer> area = new ArrayList<>();
			for (int i = a; i < 9; i++) {
				for (int j = b; j < 9; j++) {
					if( j != y) {
						area.add(sudoku[b+c][a]);
						b++; 
					}else { break;}
				}
				b=0;
				a++;
				if(a%y == 0) break;
			}
			if(a==9) {
				a=0;
				c+=y;
			}
			boolean v = repetido(area);
			if(!v) {
				valido = false;			
			}
		}
		System.out.println("Sudoku: " + (valido ? "Correcto" : "Incorrecto"));
	}
	
	public static boolean repetido(List<Integer> l) {
		Double suma = l.stream().mapToDouble(e-> Double.parseDouble(e.toString())).sum();
		return suma.equals(45.0);
	}
	

}
