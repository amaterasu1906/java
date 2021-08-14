package com.junit.app.ejemplos.models;

import java.math.BigDecimal;

import com.junit.app.ejemplos.exceptions.DineroInsuficienteException;

public class Cuenta {

	private String persona;
	private BigDecimal saldo;
	private Banco banco;
	
	public Cuenta(String persona, BigDecimal saldo) {
		this.persona = persona;
		this.saldo = saldo;
	} 
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	
	public void debito(BigDecimal monto) {
		BigDecimal nuevo = this.saldo.subtract(monto);
		if( nuevo.compareTo(BigDecimal.ZERO) < 0)
			throw new DineroInsuficienteException("Dinero insuficiente");
		this.saldo = nuevo;
	}
	public void credito(BigDecimal monto) {
		this.saldo = this.saldo.add(monto);
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	@Override
	public boolean equals(Object obj) {
		if( obj == null || !(obj instanceof Cuenta))
			return false;
		Cuenta c = (Cuenta) obj;
		if( this.persona == null || this.saldo == null)
			return false;
		
		return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
}
