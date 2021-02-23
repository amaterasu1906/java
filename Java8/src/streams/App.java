package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {

	private static List<User> listaUsuarios;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		setUpUsers();
		listaUsuarios.stream().forEach(u->u.setNombre(u.getNombre().concat(" Apellido")));
		print();
		
		List<String> nombres = listaUsuarios.stream().map(n->n.getNombre()).collect(Collectors.toList());
		nombres.stream().forEach(e-> System.out.println(e));
		
		System.out.println("--------filter---------");
		setUpUsers();
		List<User> userFilter = listaUsuarios.stream()
				.filter(e-> e.getId() < 4)
				.filter(e-> !e.getNombre().equalsIgnoreCase("miwa"))
				.collect(Collectors.toList());
		userFilter.stream().forEach(e->System.out.println(e.getId().toString().concat(" - ").concat(e.getNombre())));
		System.out.println("--------Find first---------");
		setUpUsers();
		User userFind = listaUsuarios.stream()
				.filter(e-> e.getNombre().equalsIgnoreCase("kokomi"))
				.findFirst()
				.orElse(null);
		System.out.println(userFind.getId().toString().concat(" - ").concat(userFind.getNombre()));
		
		System.out.println("--------FlatMap---------");
		List<List<User>> listaMultiple = new ArrayList<>();
		listaMultiple.add(Arrays.asList(
				new User(1,"Kokomi"),
				new User(2,"Aura"),
				new User(3,"Chisato")
				));
		listaMultiple.add(Arrays.asList(
				new User(1,"Kokomi"),
				new User(2,"Nami"),
				new User(3,"Robin")
				));
		List<User> listaUnica = listaMultiple.stream()
				.flatMap(e-> e.stream())
				.collect(Collectors.toList());
		listaUnica.stream().forEach(e-> System.out.println(e.getId().toString().concat(" - ").concat(e.getNombre())));
		
		System.out.println("--------Peek---------");
		setUpUsers();
		List<User> peekUsers = listaUsuarios.stream().peek( e-> e.setNombre(e.getNombre().concat(" Saiki")))
				.filter(e-> e.getNombre().equalsIgnoreCase("Kokomi saiki"))
				.collect(Collectors.toList());
		peekUsers.stream().forEach(e-> System.out.println(e.getNombre()));
		System.out.println("--------Count---------");
		setUpUsers();
		Long count = listaUsuarios.stream().filter(e-> e.getNombre().equalsIgnoreCase("kokomi"))
				.count();
		System.out.println("Count: ".concat(count.toString()));
		
		System.out.println("--------Sorted---------");
		listaUsuarios = listaUsuarios.stream().sorted(Comparator.comparing(User::getNombre))
		.collect(Collectors.toList());
		print();
		
		System.out.println("--------Min Max---------");
		setUpUsers();
		User min = listaUsuarios.stream().min(Comparator.comparing(User::getId)).orElse(null);
		User max = listaUsuarios.stream().max(Comparator.comparing(User::getId)).orElse(null);
		
		System.out.println(min.getId());
		System.out.println(max.getId());
		
		System.out.println("--------Distinct---------solo funciona con listas sin objetos");
		setUpUsers();
//		List<User> userDisctinct = listaUsuarios.stream().distinct().collect(Collectors.toList());
//		userDisctinct.stream().forEach(e-> System.out.println(e.getNombre()));
		List<User> listDistinct = listaUsuarios.stream().filter(distinctByKey(e-> e.getNombre()))
				.collect(Collectors.toList());
		listDistinct.stream().forEach(e-> System.out.println(e.getNombre()));
		
		System.out.println("--------allMatch anyMatch noneMatch---------");
		List<Integer> numeros = Arrays.asList(100,200,300,1000,3000,5000);
		boolean allMatch = numeros.stream().allMatch(e-> e>301);
		System.out.println(allMatch);
		boolean anyMatch = numeros.stream().anyMatch(e-> e>301);
		System.out.println(anyMatch);
		boolean noneMatch = numeros.stream().noneMatch(e-> e>5000);
		System.out.println(noneMatch);
		
		System.out.println("--------Sum Average Range---------");
		Double average = listaUsuarios.stream().mapToInt(User::getId)
				.average()
				.orElse(0);
		System.out.println("Promedio: ".concat(average.toString()));
		
		Integer suma = listaUsuarios.stream().mapToInt(User::getId)
				.sum();
		System.out.println("Suma: ".concat(suma.toString()));
		System.out.println("Range: "+IntStream.range(0, 100).sum());
		
		System.out.println("--------Reduce---------");
		Integer reduce = listaUsuarios.stream()
				.map(User::getId)
				.reduce(1, Integer::sum);
		System.out.println("Suma+Reduce: ".concat(reduce.toString()));
		
		System.out.println("--------Reduce---------");
		String names = listaUsuarios.stream()
				.map(User::getNombre)
				.collect(Collectors.joining(" -> "))
				.toString();
		System.out.println("Los nombres son: ".concat(names));
		
		System.out.println("--------toSet---------");
		Set<String> setUsers = listaUsuarios.stream()
				.map(User::getNombre)
				.collect(Collectors.toSet());
		System.out.println("Usuarios set(): ".concat(setUsers.toString()));
		
		System.out.println("--------SummarizingDouble---------");
		setUpUsers();
		DoubleSummaryStatistics estadisticas = listaUsuarios.stream()
				.collect(Collectors.summarizingDouble(User::getId));
		System.out.println("Promedio: " + estadisticas.getAverage()
						+	"Suma: " + estadisticas.getSum()
						+	"Maximo: " + estadisticas.getMax()
						+	"Minimo: " + estadisticas.getMin()
						+	"Elementos: " + estadisticas.getCount());
		DoubleSummaryStatistics estadisticas2 = listaUsuarios.stream()
				.mapToDouble(User::getId).summaryStatistics();
		System.out.println("Promedio: " + estadisticas2.getAverage()
						+	"Suma: " + estadisticas2.getSum()
						+	"Maximo: " + estadisticas2.getMax()
						+	"Minimo: " + estadisticas2.getMin()
						+	"Elementos: " + estadisticas2.getCount());
		
		System.out.println("--------PartitioningBy---------");
		List<Integer> nums = Arrays.asList(1,2,3,4,5,6,10,100,22,300,40);
		Map<Boolean, List<Integer>> partition = nums.stream()
				.collect(Collectors.partitioningBy(e-> e>9));
		System.out.println("Numero mayores al predicado: ");
		partition.get(true).stream().forEach(e->System.out.println(e));
		System.out.println("Numero menores al predicado: ");
		partition.get(false).stream().forEach(e->System.out.println(e));
		
		System.out.println("--------GroupingBy---------");
		Map<Character, List<User>> userAlpha = listaUsuarios.stream()
				.collect(Collectors.groupingBy(e-> new Character(e.getNombre().charAt(0))));
		for (int i = 0; i<26; i++) {
			char letra = (char) ('A'+i);
			try {
				userAlpha.get(letra).stream().forEach(e-> System.out.println(e.getNombre()));							
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
//		userAlpha.get('k').stream().forEach(e-> System.out.println(e.getNombre()));
		System.out.println("--------Mapping---------");
		List<String> personas = listaUsuarios.stream()
				.collect(Collectors.mapping(User::getNombre, Collectors.toList()));
		personas.stream().forEach(e->System.out.println(e));
		
		System.out.println("--------StreamParalelo---------");
		long t1 = System.currentTimeMillis();
		listaUsuarios.stream().forEach(e->convertirAMayusculas(e.getNombre()));
		long t2 = System.currentTimeMillis();
		System.out.println("Tiempo Normal: " + (t2-t1));
		t1 = System.currentTimeMillis();
		listaUsuarios.parallelStream().forEach(e->convertirAMayusculas(e.getNombre()));
		t2 = System.currentTimeMillis();
		System.out.println("Tiempo Paralelo: " + (t2-t1));
	}
	public static String convertirAMayusculas(String nombre) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return nombre.toUpperCase();
	}
	
	public static <T> Predicate<T> distinctByKey(
		Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
	}
	
	private static void setUpUsers() {
		listaUsuarios = Arrays.asList(
				new User(1,"Chisato"),
				new User(2,"Kokomi"),
				new User(3,"Miwa"),
				new User(4,"Chiyou"),
				new User(5,"Aura"),
				new User(7,"Kokomi"),
				new User(6,"Nemu")
				);
	}

	private static void print() {
		listaUsuarios.stream().forEach(
				u-> System.out.println(u.getId().toString().concat(" ").concat(u.getNombre())));
	}
}