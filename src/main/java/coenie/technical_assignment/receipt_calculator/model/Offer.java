package coenie.technical_assignment.receipt_calculator.model;

public interface Offer {
	OfferType getOfferType();
	Item getItemWithOffer();
	String getOfferName();
}
