package com.m3.training.jdbcinventory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class InventoryHandlerTest {
	List<Item> items = new ArrayList<Item>();
	Item item = Item.itemFactory(1, "Board Game", 25, "Key Forge");
	InventoryHandler objectUnderTest = InventoryHandler.inventoryHandlerFactory(items, 3);

	@Test
	void test_inventoryHandler_getMaxKey() {
		objectUnderTest.inventory.put(1, item);
		assertTrue(objectUnderTest.getMaxKey(objectUnderTest.inventory) == 1);
	}

	@Test
	void test_inventoryHandler_add_item_with_name() {
		objectUnderTest.inventory.put(1, item);

		String emptyName = "";
		String argMsg = "Name can't be an empty string";
		assertThrows(InventoryAddException.class, () -> {
			objectUnderTest.addNewItem(emptyName);
		}, argMsg);
	}

	@Test
	void test_inventoryHandler_add_item_with_name_and_quantity() {
		String newName = "Water bottle";
		Integer nullQty = null;
		String argMsg = "Quantity can't be null";
		assertThrows(InventoryAddException.class, () -> {
			objectUnderTest.addNewItem(newName, nullQty);
		}, argMsg);
	}

	@Test
	void test_inventoryHandler_add_item_with_name_quantity_and_description() {
		String newName = "Water bottle";
		Integer newQty = 5;
		String emptyDesc = "";
		String argMsg = "Description can't be an empty string";
		assertThrows(InventoryAddException.class, () -> {
			objectUnderTest.addNewItem(newName, newQty, emptyDesc);
		}, argMsg);
	}

	@Test
	void test_inventoryHandler_update_item_name() {
		Item nilItem = null;
		String emptyItemMsg = "Item can't be null";
		assertThrows(InventoryChangeException.class, () -> {
			objectUnderTest.updateItemName(nilItem, "Espadillas");
		}, emptyItemMsg);

		objectUnderTest.inventory.put(1, item);
		String emptyName = "";
		String argMsg = "Name can't be an empty string";
		assertThrows(InventoryChangeException.class, () -> {
			objectUnderTest.updateItemName(item, emptyName);
		}, argMsg);
	}

	@Test
	void test_inventoryHandler_update_item_quantity() {
		Item nilItem = null;
		String emptyItemMsg = "Item can't be null";
		assertThrows(InventoryChangeException.class, () -> {
			objectUnderTest.updateItemQuantity(nilItem, 50);
		}, emptyItemMsg);

		objectUnderTest.inventory.put(1, item);
		Integer emptyQty = null;
		String argMsg = "Quantity can't be null";
		assertThrows(NullPointerException.class, () -> {
			objectUnderTest.updateItemQuantity(item, emptyQty);
		}, argMsg);

	}

	@Test
	void test_inventoryHandler_update_item_description() {
		Item nilItem = null;
		String emptyItemMsg = "Item can't be null";
		assertThrows(NullPointerException.class, () -> {
			objectUnderTest.updateItemQuantity(nilItem, 50);
		}, emptyItemMsg);

		objectUnderTest.inventory.put(1, item);
		String emptyDesc = "";
		String argMsg = "Description can't be an empty string";
		assertThrows(InventoryChangeException.class, () -> {
			objectUnderTest.updateItemDescription(item, emptyDesc);
		}, argMsg);
	}

	@Test
	void test_inventoryHandler_remove_item() {
		String msg = "Cannot delete item that is not in database";
		assertThrows(InventoryDeleteException.class, () -> {
			objectUnderTest.removeFromInventory(item);
		}, msg);

		String emptyItemMsg = "Cannot remove null item";
		assertThrows(IllegalArgumentException.class, () -> {
			objectUnderTest.removeFromInventory(null);
		}, emptyItemMsg);

	}
}