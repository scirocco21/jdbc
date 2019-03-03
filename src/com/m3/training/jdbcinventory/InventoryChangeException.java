package com.m3.training.jdbcinventory;

public class InventoryChangeException extends NullPointerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryChangeException(String msg) {
		super(msg);
	}
}