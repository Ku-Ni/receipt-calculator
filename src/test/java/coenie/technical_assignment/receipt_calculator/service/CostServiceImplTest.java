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
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import coenie.technical_assignment.receipt_calculator.model.BuyOneGetOneOffer;
import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;
import coenie.technical_assignment.receipt_calculator.model.Receipt;

public class CostServiceImplTest {

	private static Map<String, Item> items;
	private Offer mockOffer1, mockOffer2;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		items = new HashMap<String, Item>();
		items.put("Apples", new Item("APPLES", 25));
		items.put("Oranges", new Item("ORANGES", 30));
		items.put("Garlic", new Item("GARLIC", 15));
		items.put("Papayas", new Item("PAPAYAS", 50)); // three for the price of two
	}
	
	
	@Mock
	ItemService mockItemService;

	private CostServiceImpl costService;

	
	@Before
	public void setUp() {
		// Do not declare offers in @BeforeClass! Should be created for each run to mimic live.
		mockOffer1 = new BuyOneGetOneOffer("Three for the price of two", items.get("Papayas"), 3, 1);
		mockOffer2 = new BuyOneGetOneOffer("buy one get one free", items.get("Oranges"), 2, 1);
		
		mockItemService = mock(ItemService.class);		

		costService = new CostServiceImpl(mockItemService);
	}


	private void mockOffersService(Set<Offer> offerItems) {
		Map<Item,Offer> mockResult = offerItems.stream().collect(Collectors.toMap(Offer::getOfferItem,Function.identity()));
		System.out.println("Mocking offers:"+mockResult);
		
		when(mockItemService.getItemsOnOffer(any())).thenReturn(mockResult);
	}


	@Test
	public final void testCalculateReceipt() {
		System.out.println("testCalculateReceipt");
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15
		expectedItems.add(items.get("Oranges"));	// 30

		Set<Offer> expectedOffers = new HashSet<>();

		int expectedCost = 170;


		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test
	public final void testCalculateReceipt_IncludeOffer() {
		System.out.println("testCalculateReceipt_IncludeOffer");
		mockOffersService(new HashSet<>(Arrays.asList(mockOffer1)));
		
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(mockOffer1);

		int expectedCost = 140;

		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test
	public final void testCalculateReceipt_IncludeOneOfferMoreItems() {
		System.out.println("testCalculateReceipt_IncludeOneOfferMoreItems");
		mockOffersService(new HashSet<>(Arrays.asList(mockOffer1)));
		
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(mockOffer1);

		int expectedCost = 190;

		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test
	public final void testCalculateReceipt_IncludeSameOfferTwice() {
		System.out.println("testCalculateReceipt_IncludeTwoOffers");
		mockOffersService(new HashSet<>(Arrays.asList(mockOffer1)));
		
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(mockOffer1);

		int expectedCost = 240;

		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test
	public final void testCalculateReceipt_IncludedifferentOffers() {
		System.out.println("testCalculateReceipt_IncludeTwoOffers");
		mockOffersService(new HashSet<>(Arrays.asList(mockOffer1, mockOffer2)));
		
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Oranges"));	// 30
		expectedItems.add(items.get("Oranges"));	// 30
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(mockOffer1);
		expectedOffers.add(mockOffer2);

		int expectedCost = 170;

		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test(expected = IllegalArgumentException.class)
	public final void testCalculateReceipt_IllegalArgument_EmptyList() {
		List<Item> testList = new ArrayList<>();

		costService.calculateReceipt(testList);
	}


	@Test(expected = IllegalArgumentException.class)
	public final void testCalculateReceipt_IllegalArgument_nullParameter() {
		costService.calculateReceipt(null);
	}

}
