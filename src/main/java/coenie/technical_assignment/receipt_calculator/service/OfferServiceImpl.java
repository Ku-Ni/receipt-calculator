package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

public class OfferServiceImpl implements OfferService{

	@Override
	public Set<Item> getItemsOnOffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offer getItemOffer(Item item) throws ItemNotOnOfferException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Offer> getItemOffers(List<Item> items) {
		Set<Item> curentOffers = getItemsOnOffer();

		return items.stream()
				.filter(item->curentOffers.contains(item))
				.map(item->getItemOffer(item))
				.collect(Collectors.toSet());
	}

}
