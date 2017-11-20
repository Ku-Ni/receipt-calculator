package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Receipt;

public class CostServiceImpl implements CostService {
	
	private ItemService itemService; 
	private OfferService offerService;

	@Autowired
	public CostServiceImpl(ItemService itemService, OfferService offerService) {
		this.itemService = itemService;
		this.offerService = offerService;
	}

	@Override
	public Receipt calculateReceipt(List<Item> items) {
		// TODO Auto-generated method stub
		return null;
	}

}
