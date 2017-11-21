package coenie.technical_assignment.receipt_calculator.model;

public interface Offer {
	/**
	 * Adds the item to this offer
	 * 
	 * @param item
	 */
	void addItem(Item item);
	
	/**
	 * Returns the number of times this offer is applied
	 * This is calculated by the number of items added to this
	 * offer and the number required to qualify for this offer.
	 * 
	 * @return number of offers
	 */
	int getNumOffersApplied();
	
	/**
	 * @return This offer type
	 */
	OfferType getOfferType();
	
	/**
	 * @return The item this offer applies to
	 */
	Item getOfferItem();
	
	/**
	 * @return String name for this offer
	 */
	String getOfferName();

	/**
	 * @return Amount received with offer
	 */
	int getOfferAmount();
}
