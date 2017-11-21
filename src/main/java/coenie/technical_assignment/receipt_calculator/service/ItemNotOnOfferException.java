package coenie.technical_assignment.receipt_calculator.service;

import coenie.technical_assignment.receipt_calculator.model.Item;

public class ItemNotOnOfferException extends RuntimeException {
	private static final long serialVersionUID = -5319463901887970690L;
	
	public ItemNotOnOfferException(Item item) {
		super(item.getName()+" not currently listed on any offers");
	}
}
