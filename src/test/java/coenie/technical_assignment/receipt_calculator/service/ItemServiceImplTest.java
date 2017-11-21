package coenie.technical_assignment.receipt_calculator.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import coenie.technical_assignment.receipt_calculator.dao.Dao;
import coenie.technical_assignment.receipt_calculator.model.BuyOneGetOneOffer;
import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

public class ItemServiceImplTest {

	private static Map<String, Item> items;
	private static Offer mockOffer1, mockOffer2;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		items = new HashMap<String, Item>();
		items.put("Apples", new Item("APPLES", 25));
		items.put("Oranges", new Item("ORANGES", 30));
		items.put("Garlic", new Item("GARLIC", 15));
		items.put("Papayas", new Item("PAPAYAS", 50)); 
		
		mockOffer1 = new BuyOneGetOneOffer("Three for the price of two", items.get("Papayas"), 3, 1);
		mockOffer2 = new BuyOneGetOneOffer("buy one get one free", items.get("Oranges"), 2, 1);
	}
	
	
	private ItemService itemService;
	
	@Mock
	Dao mockDao;
	
	@Before
	public void setUp() {
		setupMockDao();
		itemService = new ItemServiceImpl(mockDao);
	}

	private void setupMockDao() {
		mockDao = mock(Dao.class);
		
		// Use local mock repository
		when(mockDao.findItemByName(anyString())).thenAnswer(i -> items.get(i.getArguments()[0]));		
	}
	
	private void setupMockOffers(Set<Offer> mockOffers) {
		when(mockDao.getOffers()).thenReturn(mockOffers);
	}


	/*
	 *  Tests for GetItem
	 */
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

	
	/*
	 *  Tests for GetItems
	 */
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


	/*
	 *  Tests for GetItemsOnOffer
	 */
	@Test
	public final void testGetItemsOnOffer_RetrieveOffer() {		
		setupMockOffers(new HashSet<>(Arrays.asList(mockOffer1)));
		Map<Item,Offer> expected = new HashMap<>();
		expected.put(mockOffer1.getOfferItem(), mockOffer1);

		Set<Item> testItems = new HashSet<>(items.values());		
		Map<Item,Offer> actual = itemService.getItemsOnOffer(testItems);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public final void testGetItemsOnOffer_NoOffer() {
		setupMockOffers(new HashSet<>());
		Map<Item,Offer> expected = new HashMap<>();
		
		Set<Item> testItems = new HashSet<>(items.values());
		Map<Item,Offer> actual = itemService.getItemsOnOffer(testItems);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public final void testGetItemsOnOffer_TwoOffers() {	
		setupMockOffers(new HashSet<>(Arrays.asList(mockOffer1, mockOffer2)));
		Map<Item,Offer> expected = new HashMap<>();
		expected.put(mockOffer1.getOfferItem(), mockOffer1);
		expected.put(mockOffer2.getOfferItem(), mockOffer2);
		
		
		Set<Item> testItems = new HashSet<>(items.values());
		Map<Item,Offer> actual = itemService.getItemsOnOffer(testItems);
		
		assertEquals(expected,actual);
	}
}
