package coenie.technical_assignment.receipt_calculator.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import coenie.technical_assignment.receipt_calculator.dao.ItemDao;
import coenie.technical_assignment.receipt_calculator.model.Item;

public class ItemServiceImplTest {

	private static Map<String, Item> items;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		items = new HashMap<String, Item>();
		items.put("Apples", new Item("APPLES", 25));
		items.put("Oranges", new Item("ORANGES", 30));
		items.put("Garlic", new Item("GARLIC", 15));
		items.put("Papayas", new Item("PAPAYAS", 50)); 
	}
	
	
	private ItemService itemService;
	
	@Mock
	ItemDao mockDao;
	
	@Before
	public void setUp() {
		setupMockDao();
		itemService = new ItemServiceImpl(mockDao);
	}

	private void setupMockDao() {
		mockDao = mock(ItemDao.class);
		
		// Use local items as mock repository
		when(mockDao.findItemByName(anyString())).thenAnswer(i -> items.get(i.getArguments()[0]));
	}

	
	@Test
	public final void testGetItem() {
		String testName = "Apples";
		
		Item actualItem = itemService.getItem(testName);
		
		assertEquals(items.get(testName), actualItem);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public final void testGetItem_ItemNotFound() {
		itemService.getItem("ItemDoesn'tExist");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetItem_IllegalArgument_null() {
		itemService.getItem(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetItem_IllegalArgument_EmptyString() {
		itemService.getItem("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetItem_IllegalArgument_SpaceString() {
		itemService.getItem(" ");
	}

	
	@Test
	public final void testGetItems() {
		List<String> testNames = Arrays.asList("Apples", "Oranges", "Papayas", "Papayas");
		
		List<Item> expectedItems = testNames.stream()
				.map(name->items.get(name)).collect(Collectors.toList());
		
		List<Item> actualItems = itemService.getItems(testNames);
		
		assertEquals(expectedItems, actualItems);
	}

	
	@Test
	public final void testGetItems_OneItem() {
		List<String> testNames = Arrays.asList("Apples");
		
		List<Item> expectedItems = testNames.stream()
				.map(name->items.get(name)).collect(Collectors.toList());
		
		List<Item> actualItems = itemService.getItems(testNames);
		
		assertEquals(expectedItems, actualItems);
	}

	@Test(expected = ItemNotFoundException.class)
	public final void testGetItems_IllegalArgument_ItemNotFound() {
		List<String> testNames = Arrays.asList("Oranges", "Papayas", "InvalidItem", "Papayas");
		
		itemService.getItems(testNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetItems_IllegalArgument_null() {
		itemService.getItems(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetItems_IllegalArgument_EmptyList() {
		itemService.getItems(new ArrayList<>());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetItems_IllegalArgument_ListContainsNull() {
		List<String> testNames = Arrays.asList("Oranges", null, "Papayas", "InvalidItem");
		
		itemService.getItems(testNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetItems_IllegalArgument_ListEmptyString() {
		List<String> testNames = Arrays.asList("Oranges", "Papayas", "", "InvalidItem");
		
		itemService.getItems(testNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetItems_IllegalArgument_ListSpaceString() {
		List<String> testNames = Arrays.asList("Oranges", "Papayas", " ", "InvalidItem");
		
		itemService.getItems(testNames);
	}


}
