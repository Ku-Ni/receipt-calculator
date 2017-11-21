package coenie.technical_assignment.receipt_calculator.model;

public interface Offer {
	/**
	 * Increases the amount of items connected to this offer
	 */
	void addItem();
	
	/**
	 * Returns the number of times this offer is applied
	 * This is calculated by the number of items added to this
	 * offer and the number required to qualify for this offer.
	 * 
	 * @return number of offers
	 */
	int getNumOffersApplied();
	
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
