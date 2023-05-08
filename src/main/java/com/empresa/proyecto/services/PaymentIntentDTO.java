package com.empresa.proyecto.services;
//Esta clase es para el cliente, para usarla desde el front

public class PaymentIntentDTO {
	public enum Currency {
		MXN;
	}
	
	private String descripcion;
	private int amount;
	private Currency currency;
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	
}
