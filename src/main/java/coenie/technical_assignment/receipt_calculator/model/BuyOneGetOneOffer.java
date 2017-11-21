package coenie.technical_assignment.receipt_calculator.model;

import java.util.ArrayList;
import java.util.List;

public class BuyOneGetOneOffer implements Offer {

	private final OfferType offerType = OfferType.BOGO;
	
	private final String offerName;
	private final Item offerItem;
	private final int qualifyingAmount;
	private final double amountItemsReceived;
	
	private List<Item> itemsAdded;


	/**
	 * Specifies a Buy One Get One offer, this can be used for a few different instances:
	 * 
	 * <ul>
	 * 	<li>buy item get another item free</li>
	 *  <li>buy 2x items get 3rd free</li>
	 *  <li>buy item get another 1/2 price</li>
	 * </ul>
	 * 
	 * @param offerName String name of the offer
	 * @param offerItem Item which will activate offer
	 * @param qualifyingAmount Integer amount of Items required to apply offer (minimum 2, 1 to buy and 1 to get free)
	 * @param amountItemsReceived Double amount of Items received, e.g can be 0.5 for half off or 1 for free item.
	 */
	public BuyOneGetOneOffer(String offerName, Item offerItem, int qualifyingAmount, double amountItemsReceived ) {
		this.itemsAdded = new ArrayList<>();
		this.offerName = offerName;
		this.offerItem = offerItem;
		this.qualifyingAmount = qualifyingAmount;
		this.amountItemsReceived = amountItemsReceived;
	}


	public OfferType getOfferType() {
		return offerType;
	}

	public String getOfferName() {
		return offerName;
	}

	public Item getOfferItem() {
		return offerItem;
	}

	public int getQualifyingAmount() {
		return qualifyingAmount;
	}

	public double getAmountItemsReceived() {
		return amountItemsReceived;
	}


	@Override
	public void addItem(Item item) {	
		itemsAdded.add(item);		
	}


	@Override
	public int getNumOffersApplied() {
		if (itemsAdded.size()>=qualifyingAmount)
			return itemsAdded.size()/qualifyingAmount;
		
		return 0;
	}


	@Override
	public int getOfferAmount() {
		return Double.valueOf(amountItemsReceived*offerItem.getPrice()).intValue();
	}

}
