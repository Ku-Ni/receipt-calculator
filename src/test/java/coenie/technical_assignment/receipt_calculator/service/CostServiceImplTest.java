package coenie.technical_assignment.receipt_calculator.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private static Offer offer;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		items = new HashMap<String, Item>();
		items.put("Apples", new Item("APPLES", 25));
		items.put("Oranges", new Item("ORANGES", 30));
		items.put("Garlic", new Item("GARLIC", 15));
		items.put("Papayas", new Item("PAPAYAS", 50)); // three for the price of two
	}
	
	
	@Mock
	OfferService mockOfferService;

	private CostServiceImpl costService;

	
	@Before
	public void setUp() throws ItemNotOnOfferException {
		
		offer = new BuyOneGetOneOffer("Three for the price of two", items.get("Papayas"), 3, 1);
		mockOffersService();

		costService = new CostServiceImpl(mockOfferService);
	}


	private void mockOffersService() throws ItemNotOnOfferException {
		mockOfferService = mock(OfferService.class);
		Map<Item,Offer> mockOfferItems = new HashMap<>();		
		mockOfferItems.put(items.get("Papayas"), offer);
		
		when(mockOfferService.getItemsOnOffer(any())).thenReturn(mockOfferItems);
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
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(offer);

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
		List<Item> expectedItems = new ArrayList<>();
		expectedItems.add(items.get("Apples"));		// 25
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Papayas"));	// 50
		expectedItems.add(items.get("Garlic"));		// 15

		Set<Offer> expectedOffers = new HashSet<>();
		expectedOffers.add(offer);

		int expectedCost = 190;

		Receipt actualReceipt = costService.calculateReceipt(expectedItems);
		System.out.println(actualReceipt.print());

		assertEquals("Receipt items incorrect",expectedItems, actualReceipt.getItems());
		assertEquals("Receipt offers incorrect", expectedOffers, actualReceipt.getAppliedOffers());
		assertEquals("Receipt total incorrect",expectedCost, actualReceipt.getTotalAmount());
	}


	@Test
	public final void testCalculateReceipt_IncludeTwoOffers() {
		System.out.println("testCalculateReceipt_IncludeTwoOffers");
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
		expectedOffers.add(offer);

		int expectedCost = 240;

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
