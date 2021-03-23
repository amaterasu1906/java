package com.junit.app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.junit.app.ejemplos.exceptions.DineroInsuficienteException;

class CuentaTest {

	@Test
	void test() {
		Cuenta cuenta = new Cuenta("Kokomi", new BigDecimal("10.01"));
		assertEquals("Kokomi", cuenta.getPersona());
		
	}
	
	@Test
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
}
