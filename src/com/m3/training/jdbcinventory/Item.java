package com.m3.training.jdbcinventory;

public class Item {
	
	private static final Integer MAX_LIMIT = 100;
	private String itemName;
	private Integer quantity;
	private Integer idNum;
	private String description;
	private Integer limit;
	
	
	private Item (Integer id, String iName, Integer iQuantity, String iDescription) {
		super();
		
		this.idNum = id;
		this.itemName = iName;
		this.setQuantity(iQuantity);
		this.setDescription(iDescription);
		this.setLimit(MAX_LIMIT);
		
	}

	void setLimit(Integer limitToSet) {
		this.limit = limitToSet;	
	}

	Integer getLimit() {
		return this.limit;
	}
	
	String getItemName() {
		return itemName;
	}
	void setItemName(String itemName) {
		this.itemName = itemName;
	}
	Integer getQuantity() {
		return quantity;
	}
	void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	Integer getidNum() {
		return this.idNum;
	}
	void setidNum(Integer id) {
		this.idNum = id;
	}
	String getDescription() {
		return this.description;
	}
	void setDescription(String description) {
		this.description = description;
	}

	public static Item itemFactory(Integer id, String iName, Integer iQuantity, String iDescription) {
		Item item = new Item(id, iName, iQuantity, iDescription);
		return item;

	}
	
	public String toString() {
		return "id=" + getidNum() + " name=" + getItemName() + " quantity=" + getQuantity() + 
				" description='" + getDescription() + "' max_quantity_limit=" + getLimit() + "\n";
	}
	

	
}
