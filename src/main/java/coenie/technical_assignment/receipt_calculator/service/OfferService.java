package coenie.technical_assignment.receipt_calculator.service;

import java.util.Set;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

public interface OfferService {
	Set<Item> getItemsOnOffer();
	
	Offer getItemOffer(Item item) 
			throws ItemNotOnOfferException;
}
