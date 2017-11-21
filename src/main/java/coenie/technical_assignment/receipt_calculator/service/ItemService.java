package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

public interface ItemService {
	/**
	 * Retrieves Item with supplied name 
	 * 
	 * @param itemName
	 * @return
	 * @throws ItemNotFoundException - When no item matches parameter
	 * @throws IllegalArgumentException - When invalid name is supplied e.g. null or empty String
	 */
	Item getItem(String itemName)
			throws ItemNotFoundException;

	/**
	 * Retrieves List of Item for supplied List of names
	 * 
	 * @param itemNames
	 * @return
	 * @throws ItemNotFoundException - When no item matches a supplied name
	 * @throws IllegalArgumentException - When invalid name is supplied e.g. null or empty String
	 */
	List<Item> getItems(List<String> itemNames)
			throws ItemNotFoundException;
	
	/**
	 * Returns a Map of Offers for any of the supplied items currently
	 * on offer.
	 * 
	 * @param items Set of Item to check 
	 * @return Map with Item as key and Offer for Item as value
	 */
	Map<Item, Offer> getItemsOnOffer(Set<Item> items);

}
