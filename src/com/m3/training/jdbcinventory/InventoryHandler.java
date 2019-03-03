package com.m3.training.jdbcinventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryHandler {
	
	final static Logger logger = LogManager.getLogger(InventoryHandler.class);
	
	HashMap<Integer, Item> inventory;
	int nextId;

	// TODO: load all items on constructor;
	private InventoryHandler(List<Item> items, Integer maxId) {
		super();
		
		inventory = new HashMap<Integer, Item>();
		for (Item item : items) {
			inventory.put(item.getidNum(), item);
		}
		
		this.setNextId(maxId);
	}

	
	Boolean addNewItem(String itemName, Integer increment) throws InventoryAddException {
		if (checkStringParam(itemName) || checkNumParam(increment) ) {
			String msg = "The entered value of one or both inputs is null and/or empty";
			logger.warn(msg);
			throw new InventoryAddException(msg);
		}

		Item newItem = Item.itemFactory(this.getNextId(), itemName, increment, null);

		if (inventory.put(newItem.getidNum(), newItem) != null) {
			String msg = "New item added to inventory list: " + newItem.toString();
			logger.info(msg);
			return true;
		}
		return false;

	}

	// method overload for adding item with description
	Boolean addNewItem(String itemName, Integer increment, String description) throws InventoryAddException {
		if (!checkStringParam(itemName) || !checkNumParam(increment) || !checkStringParam(description)) {
			String msg = "The entered value of one or more inputs is null, empty, and/or zero";
			logger.warn(msg);
			throw new InventoryAddException(msg);
		}

		Item newItem = Item.itemFactory(getNextId(), itemName, increment, description);

		if (inventory.put(newItem.getidNum(), newItem) != null) {
			String msg = "New item added to inventory list: " + newItem.toString();
			logger.info(msg);
			return true;
		}
		
		return false;
	}
	
	
	Boolean addNewItem(String itemName) throws InventoryAddException{
		if(!checkStringParam(itemName)) {
			String msg = "Item name is empty or null";
			logger.info(msg);
			throw new InventoryAddException(msg);
		}
		
		Item newItem = Item.itemFactory(this.getNextId(), itemName, 1, null);
		
		if (inventory.put(newItem.getidNum(), newItem) != null) {
			String msg = "New item added to inventory list: " + newItem.toString();
			logger.info(msg);
			return true;
		}
		
		return false;
	}
	
	

	Boolean updateItemName(Item item, String nameUpdate) throws InventoryChangeException {
		if (!checkStringParam(nameUpdate) || !checkItemNotNull(item)) {
			String msg = "The entered value of one or more inputs is null and/or empty";
			logger.warn(msg);
			throw new InventoryChangeException(msg);
		}
		
		String field = "name";
		
		return addToInventory(item, field, nameUpdate);
	}
	

	Boolean updateItemQuantity(Item item, Integer quantityUpdate) throws InventoryChangeException{
		 if(!checkItemNotNull(item)) {
			 String msg = "The input item is null";
			 logger.warn(msg);
			 throw new InventoryChangeException(msg);
		 }
		 checkLimit(quantityUpdate, item);

			String field = "quantity";
			
			return addToInventory(item, field, quantityUpdate.toString());
	 }
	
	Boolean updateItemDescription(Item item, String descriptionUpdate) throws InventoryChangeException {
		if (!checkStringParam(descriptionUpdate) || !checkItemNotNull(item)) {
			String msg = "The entered value of item is null, or the entered description is empty";
			logger.warn(msg);
			throw new InventoryChangeException(msg);
		}
		String field = "description";
		
		return addToInventory(item, field, descriptionUpdate);
		
		
	}
	
	Boolean updateItemID(Item item, Integer idUpdate) throws IllegalArgumentException, InventoryAddException, DuplicateIdException, InventoryDeleteException {
		if (!checkItemNotNull(item)) {
			String msg = "The input item is null";
			logger.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		
		if(!checkForIdConflict(idUpdate)) {
			String msg = "Input id is a duplicate";
			logger.warn(msg);
			throw new DuplicateIdException(msg);
		}
		
		try {
			addNewItem(item.getItemName(), item.getQuantity(), item.getDescription());
			removeFromInventory(item);
			
			String msg = "Updated id of " + item.getItemName() + " to " + item.getidNum();
			logger.info(msg);
	
		} catch (InventoryAddException e) {
			String msg = "Error updating item ID: copy of existing item could not be added to inventory;";
			logger.error(msg);
			throw new InventoryAddException(msg);
		} catch (InventoryDeleteException e) {
			String msg = "Error updating item ID: original copy of item could not be deleted from inventory;";
			logger.error(msg);
			throw new InventoryDeleteException(msg);
		}
		
		return true;
	}
	
	

	Integer getMaxKey(HashMap<Integer, Item> inventory) {
		return inventory.size();
	}
	
	Integer getNextId() {
		return this.nextId++;
	}
	
	HashMap<Integer, Item> getInventory() {
		return inventory;
	}
	
	
	List<Item> getInventoryAsList() {
		List<Item> inventoryAsList = new ArrayList<Item>();
		
		for(Integer key : inventory.keySet()) {
			inventoryAsList.add(inventory.get(key));
		}
		
		return inventoryAsList;
	}


	void setNextId(Integer id) {
		this.nextId = id;
	}

	boolean checkStringParam(String string) {
		return !string.trim().isEmpty() && string != null;
	}

	boolean checkNumParam(Integer num) {
		return num != null && num != 0;
	}
	
	boolean checkItemNotNull(Item itemToCheck) {
		return itemToCheck != null;
	}

	void checkLimit(Integer limitToCheck, Item item) {
		if(Math.abs(limitToCheck)*-1 > item.getLimit() || Math.abs(limitToCheck) < item.getLimit()) {
			item.setLimit(100);
		}
		else {
			item.setLimit(limitToCheck);
		}
	}
	
	boolean checkForIdConflict(Integer idToCheck) {
		for(Integer id : inventory.keySet()) {
			if(id == idToCheck) {
				return false;
			}
		}
		return true;
	}
	
	boolean addToInventory(Item item, String itemField, String fieldValue){
		Item updateItem;
		
		switch(itemField.toLowerCase()) {
		
		case "id":
			Integer idUpdate = Integer.parseInt(fieldValue);
			updateItem = Item.itemFactory(idUpdate, item.getItemName(), item.getQuantity(), item.getDescription());
			break;
			
		case "quantity":
			Integer quantityUpdate = Integer.parseInt(fieldValue);
			updateItem = Item.itemFactory(item.getidNum(), item.getItemName(), quantityUpdate, item.getDescription()); 
			break;
			
			
		case "description":
			updateItem = Item.itemFactory(item.getidNum(), item.getItemName(), item.getQuantity(), fieldValue); 
			break;
			
			
		case "name":
			updateItem = Item.itemFactory(item.getidNum(), fieldValue, item.getQuantity(), item.getDescription()); 
			break;
			
		default:
			return false;
		}
				
		if(inventory.put(updateItem.getidNum(), updateItem) != null) {
			String msg = "Added updated item to inventory: " + updateItem.toString();
			logger.info(msg);
			return true;
		}
		return false;
	}
	
	
	 Boolean removeFromInventory(Item item)  throws IllegalArgumentException, InventoryDeleteException{
		 if(!checkItemNotNull(item)) {
			 String msg = "Input item is null";
			 logger.warn(msg);
			 throw new IllegalArgumentException(msg);
		 }
		 
		 if(!inventory.containsKey(item.getidNum())) {
			 String msg = "Cannot delete item because item does not exit in database";
			 logger.warn(msg);
			 throw new InventoryDeleteException(msg);
		 }
		 
		 if(inventory.remove(item.getidNum()) != null) {
			 String msg = "Removed item to inventory: " + item.toString();
			 logger.info(msg);
			 return true;
		 }
		 
		 return false;
	 }	
	 
	 public static InventoryHandler inventoryHandlerFactory(List<Item> items, Integer maxId) {
		 InventoryHandler iH = new InventoryHandler(items, maxId);
		 return iH;
	 }

}