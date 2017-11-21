package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import coenie.technical_assignment.receipt_calculator.dao.Dao;
import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

@Service
public class ItemServiceImpl implements ItemService {
	private final Logger log = LogManager.getLogger(this.getClass());
	
	private Dao dao;
	
	@Autowired
	public ItemServiceImpl (Dao dao) {
		this.dao=dao;
	}

	@Override
	public Item getItem(String itemName) 
			throws ItemNotFoundException {
		log.info("Retreiving item from value: {}...");
		
		if (!StringUtils.hasText(itemName)) {
			String errorMessage = "itemName parameter["+itemName+"] must be populated.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		
		Item result = dao.findItemByName(itemName);
		
		if (result == null) {
			log.error("Could not retreive item named {}", itemName);
			throw new ItemNotFoundException(itemName);
		}
		
		return result;
	}

	@Override
	public List<Item> getItems(List<String> itemNames) 
			throws ItemNotFoundException {
		
		if (CollectionUtils.isEmpty(itemNames)) {
			String errorMessage = "itemNames List parameter["+itemNames+"] must be populated.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);			
		}
		
		return itemNames.stream()
				.map(itemName -> getItem(itemName)) // Will throw Exception from getItem method
				.collect(Collectors.toList());
	}

	@Override
	public Map<Item, Offer> getItemsOnOffer(Set<Item> items) {
		Set<Offer> currentOffers = dao.getOffers();
		
		return currentOffers.stream().collect(Collectors.toMap(Offer::getOfferItem,Function.identity()));
	}

}
