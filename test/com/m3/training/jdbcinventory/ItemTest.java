package com.m3.training.jdbcinventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class ItemTest {
	
	int id = 1;
	String name = "Golf ball";
	int quantity = 30;
	String descr = "Balls for playing golf";
	int limit = 100;
	

	@Test
	void test_Item_ItemFactory() {
		Item item = Item.itemFactory(id, name, quantity, descr);
		assertNotNull(item);
	}
	
	@Test
	void test_Item_toString() {
		Item item = Item.itemFactory(id, name, quantity, descr);
		String expected = "id=" + id + " name=" + name + " quantity=" 
				+ quantity + " description='" + descr + "' max_quantity_limit=" + limit + "\n";
		assertEquals(item.toString(), expected);
	}
	

}
