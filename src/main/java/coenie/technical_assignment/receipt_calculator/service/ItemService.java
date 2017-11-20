package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;

import coenie.technical_assignment.receipt_calculator.model.Item;

public interface ItemService {

	Item getItem(String itemName)
			throws ItemNotFoundException;

	List<Item> getItems(List<String> itemNames)
			throws ItemNotFoundException;

}
