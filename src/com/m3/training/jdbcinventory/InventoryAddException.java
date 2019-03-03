package com.m3.training.jdbcinventory;

public class InventoryAddException extends InventoryChangeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryAddException(String message) {
		super(message);
	}
}
