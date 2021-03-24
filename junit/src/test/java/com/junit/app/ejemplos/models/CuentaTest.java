package com.junit.app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Timeout;
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
	private TestReporter testReporter;
	private TestInfo testInfo;
	
	@BeforeEach
	void initMethodTest(TestReporter testReporter, TestInfo testInfo) {
		this.testReporter = testReporter;
		this.testInfo = testInfo;
		this.cuenta = new Cuenta("Kokomi", new BigDecimal("10.01"));
		testReporter.publishEntry("Info:" + testInfo.getTags());
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
	
	@Tag("cuenta")
	@Nested
	class CuentaTestClass{
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
			
//			assertNotEquals(c2, c);
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
//		Colocar nombres a los asserts
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
	}

	@Tag("SO")
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
	
	@Tag("java")
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
	
	@Tag("properties")
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
	
	@Tag("repeat")
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
	
	@Tag("param")
	@Nested
	class PruebasParametrizadasTest {
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
		@CsvFileSource(resources = "/data2.csv")
		void testDebitoCuentaCsv2(String saldo, String monto, String esperado, String actual) {
			System.out.println("["+saldo+"] Monto: "+ monto);
			cuenta.setSaldo(new BigDecimal(saldo));
			cuenta.debito(new BigDecimal(monto));
			cuenta.setPersona(actual);
			assertNotNull(cuenta.getSaldo(), () -> "El saldo no puede estar nula");
			assertNotNull(cuenta.getPersona());
			assertEquals(esperado, actual);
			assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
		}
	}
	
	@Tag("param")
	@Tag("archivo")
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
	
	@Tag("timeout")
	@Nested
	class EjemplosTime{
		@Test
		@Timeout(2)
		void testTimeOut() throws InterruptedException {
			TimeUnit.SECONDS.sleep(3);
		}
		@Test
		@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
		void testTimeOut2() throws InterruptedException {
			TimeUnit.MILLISECONDS.sleep(100);
		}
		
		@Test
		void testTimeOutAssertions() {
			assertTimeout(Duration.ofSeconds(2), () -> {
				TimeUnit.MILLISECONDS.sleep(1100);
			});
		}
		
	}
	
}
