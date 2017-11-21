package coenie.technical_assignment.receipt_calculator.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;
import coenie.technical_assignment.receipt_calculator.model.Receipt;

@Service
public class CostServiceImpl implements CostService {
	private final Logger log = LogManager.getLogger(this.getClass());

	private OfferService offerService;

	@Autowired
	public CostServiceImpl(OfferService offerService) {
		this.offerService = offerService;
	}


	@Override
	public Receipt calculateReceipt(List<Item> items) {
		log.info("Calculating receipt for {}",items);

		if(CollectionUtils.isEmpty(items)) {
			String errorMessage = "item List parameter ["+items+"] must be populated.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		int totalAmount = items.stream().mapToInt(item->item.getPrice()).sum();

		Map<Item,Offer> itemsOnOffer = offerService.getItemsOnOffer(new HashSet<>(items));

		// Adds items to Offers
		if(!CollectionUtils.isEmpty(itemsOnOffer)) {
			log.info("The following items are on offer: {}", itemsOnOffer.values());

			items.stream()
			.filter(item->itemsOnOffer.containsKey(item))
			.forEach(item->itemsOnOffer.get(item).addItem(item));

			totalAmount = totalAmount-itemsOnOffer.values().stream()
					.mapToInt(offer->offer.getNumOffersApplied()*offer.getOfferAmount())
					.sum();
		}

		return new Receipt(items, totalAmount, new HashSet<>(itemsOnOffer.values()));
	}
}
