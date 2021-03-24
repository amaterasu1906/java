package com.junit.app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.junit.app.ejemplos.exceptions.DineroInsuficienteException;

//Se maneja solo una instancia, no paralelo
//@TestInstance(Lifecycle.PER_CLASS )
class CuentaTest {
	Cuenta cuenta;
	@BeforeEach
	void initMethodTest() {
		this.cuenta = new Cuenta("Kokomi", new BigDecimal("10.01"));
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("Finalizando");
	}
	
//	Se ejecuta solo 1 vez para inicializar
	@BeforeAll
	static void beforeall() {
		System.out.println("Inicializando.");
	}
//	Se ejecuta solo una vez al final del test
	@AfterAll
	static void afterAll() {
		System.out.println("Finalizando el test");
	}

	@Test
	@DisplayName("Probando creacion de cuenta")
	void test() {
		assertEquals("Kokomi", this.cuenta.getPersona());
		
	}
	
	@Test
	@Disabled
	void testSaldo() {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		assertEquals(10.01, c.getSaldo().doubleValue());
	}
	
	@Test
	void testReferenciaCuenta() {
		Cuenta c = new Cuenta("Teruhashi", new BigDecimal("10.01"));
		Cuenta c2 = new Cuenta("Teruhashi", new BigDecimal("10.01"));
		
//		assertNotEquals(c2, c);
		assertEquals(c2, c);
	}
	
	@Test
	void testDebitoCuenta() {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal("1"));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertEquals(9, c.getSaldo().intValue());
		assertEquals("9.01", c.getSaldo().toPlainString());
	}
//	Colocar nombres a los asserts
	@Test
	void testCreditoCuenta() {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.credito(new BigDecimal("1"));
		assertNotNull(c.getSaldo());
		assertEquals(11, c.getSaldo().intValue());
		assertEquals("11.01", c.getSaldo().toPlainString(), () -> "Saldos diferentes");
	}
	
	@Test
	void testDineroInsuficienteException() {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		Exception exception = assertThrows(DineroInsuficienteException.class, () ->{
			c.debito(new BigDecimal(11));
		});
		assertEquals(exception.getMessage(), "Dinero insuficiente");
	}

	@Test
	void testTransferirDineroCuentas() {
		Cuenta c1 = new Cuenta("Kokomi", new BigDecimal("100.00"));
		Cuenta c2 = new Cuenta("Chisato", new BigDecimal("50.00"));
		
		Banco banco = new Banco();
		banco.setNombre("Banamex");
		banco.transferir(c2, c1, new BigDecimal(10.00));
		
		assertEquals("110.00", c1.getSaldo().toPlainString());
		assertEquals("40.00", c2.getSaldo().toPlainString());
	}
	
	@Test
	void testRelacionBancoCuentas() {
		Cuenta c1 = new Cuenta("Kokomi", new BigDecimal("100.00"));
		Cuenta c2 = new Cuenta("Chisato", new BigDecimal("50.00"));
		
		Banco banco = new Banco();
		banco.setNombre("Banamex");
		banco.addCuenta(c1);
		banco.addCuenta(c2);
		
		banco.transferir(c2, c1, new BigDecimal(10.00));
		
		assertAll(() -> {
			assertEquals("110.00", c1.getSaldo().toPlainString());
		}, () -> {
			assertEquals("40.00", c2.getSaldo().toPlainString());
		}, () -> {
			assertEquals(2, banco.getCuentas().size());
		}, () -> {
			assertEquals("Banamex", c1.getBanco().getNombre());
		}, () -> {
			assertEquals("Kokomi", banco.getCuentas().stream().filter(e -> e.getPersona().equals("Kokomi")).findFirst()
					.get().getPersona());
		}, () -> {
			assertTrue(banco.getCuentas().stream().anyMatch(e -> e.getPersona().equals("Kokomi")));
		});
	}
	@DisplayName("Sistemas Operativos.")
	@Nested
	class SistemaOperativoTest{
//		Habilitar pruebas solo para windows
		@Test
		@EnabledOnOs(OS.WINDOWS)
		void testSoloWindows() {
			
		}
		
//		Deshabilitar prueba para windows
		@Test
		@DisabledOnOs(OS.WINDOWS)
		void testSinWindows() {
			
		}
		@Test
		@EnabledOnOs({OS.LINUX, OS.MAC})
		void testLinuxMac() {
			
		}
		
	}
	@Nested
	class JavaVersionTest{
		@EnabledOnJre(JRE.JAVA_8)
		@Test
		void soloJDK8() {
			
		}
		@DisabledOnJre(JRE.JAVA_8)
		@Test
		void disableJDK() {
			
		}
		@Test
		@Disabled
		void systemProperties() {
			Properties p = System.getProperties();
			p.forEach( (k, v) -> {
				System.out.println(k + " - " + v);
			});
		}
		
		@Test
		@EnabledIfSystemProperty(named = "java.version", matches = "15.0.2")
		void  testJavaVersion() {
			
		}
	}
	@Nested
	class SystemPropertiesTest{
//	solo para desarrollo
		@Test
		@EnabledIfSystemProperty(named = "ENV", matches = "dev")
		void testDev() {		
		}
		
		@Test
		void printVarEnviroment() {
			Map<String, String> getenv = System.getenv();
//			getenv.forEach( (k, v) -> System.out.println(k + " - " + v));
		}
		
		@Test
		@EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-15.0.*")
		void testJavaHome() {
			
		}
		
		@Test
		@EnabledIfEnvironmentVariable( named = "NUMBER_OF_PROCESSORS", matches = "12")
		void testProcesadores() {
			
		}
		
		@Test
		@EnabledIfEnvironmentVariable(named = "ENV", matches = "prod")
		@DisplayName("ENVIROMENT")
		void testEnv() {
			
		}
		
		@Test
		void testDevSaldo() {
			boolean esDev = "prod".equals(System.getProperty("ENV"));
//		Se ejecuta si pasa el primer test, si no se deshabilita
			assumeTrue(esDev);
			assertEquals(10.01, cuenta.getSaldo().doubleValue());
		}
		@Test
		void testDevSaldo2() {
			boolean esDev = "prod".equals(System.getProperty("ENV"));
//		solo se ejecuta lo de adentro si se cumple la premisa
			assumingThat(esDev, () -> {
				assertEquals(10.01, cuenta.getSaldo().doubleValue());			
			});
		}
	}
	

	@DisplayName("Repeticiones")
	@RepeatedTest(value = 3, name = "{displayName} Repeticion #{currentRepetition} de {totalRepetitions}")
	void testDebitoCuentaRepeat(RepetitionInfo info) {
		if( info.getCurrentRepetition() == 3) {
			System.out.println("Repeticion: " + info.getCurrentRepetition());
		}
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal("1"));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertEquals(9, c.getSaldo().intValue());
		assertEquals("9.01", c.getSaldo().toPlainString());
	}
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@ValueSource(strings = {"1", "2", "3", "4", "5", "10.01"})
	void testDebitoCuentaValueSource(String monto) {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal(monto));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertTrue(c.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
	}
	
	@Disabled
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@CsvSource({"1,1", "2, 2.1", "3, 10.00"})
	void testDebitoCuentaCsv(String index, String monto) {
		System.out.println("["+index+"] Monto: "+ monto);
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal(monto));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertTrue(c.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@CsvFileSource(resources = "/data.csv")
	void testDebitoCuentaCsvFileSource(String monto) {
		System.out.println(" Monto: "+ monto);
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal(monto));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertTrue(c.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@MethodSource("montoList")
	void testDebitoCuentaMethodSource(String monto) {
		Cuenta c = new Cuenta("Kokomi", new BigDecimal("10.01"));
		c.debito(new BigDecimal(monto));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertTrue(c.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	static List<String> montoList(){
		return Arrays.asList("1", "2", "3", "4", "5", "10.00");
	}
	
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@CsvSource({"2,1", "3, 2.1", "3, 10.00", "10.01, 10.01"})
	void testDebitoCuentaCsv2(String saldo, String monto) {
		System.out.println("["+saldo+"] Monto: "+ monto);
		Cuenta c = new Cuenta("Kokomi", new BigDecimal(saldo));
		c.debito(new BigDecimal(monto));
		assertNotNull(c.getSaldo(), () -> "El saldo no puede estar nula");
		assertTrue(c.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
}
