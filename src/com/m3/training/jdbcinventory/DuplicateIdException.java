package com.m3.training.jdbcinventory;

public class DuplicateIdException extends InventoryAddException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateIdException(String message) {
		super(message);
	}
}