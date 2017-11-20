package coenie.technical_assignment.receipt_calculator.model;

public class BuyOneGetOneOffer implements Offer {

	private final OfferType offerType = OfferType.BOGO;
	private final String offerName;
	private final Item itemWithOffer;
	private final Item itemOffered;
	private final int qualifyingAmount;
	private final double amountItemsReceived;


	/**
	 * Specifies a Buy One Get One offer, this can be used for a few different instances:
	 * <ul>
	 * 	<li>buy item1 get another item1 free</li>
	 *  <li>buy item1 get item2 free</li>
	 *  <li>buy 2x item get 3rd free</li>
	 *  <li>buy item get another 1/2 price</li>
	 * </ul>
	 * 
	 * @param offerName String name of the offer
	 * @param itemWithOffer Item which will activate offer
	 * @param itemOffered Item which is given in offer, e.g. same as Item on offer or get another Item discounted
	 * @param qualifyingAmount Integer amount of Items required to apply offer (minimum 2, 1 to buy and 1 to get free)
	 * @param amountItemsReceived Double amount of Items received, e.g can be 0.5 for half off or 1 for free item.
	 */
	public BuyOneGetOneOffer(String offerName, Item itemWithOffer, Item itemOffered, int qualifyingAmount, double amountItemsReceived ) {
		this.offerName = offerName;
		this.itemWithOffer = itemWithOffer;
		this.itemOffered = itemOffered;
		this.qualifyingAmount = qualifyingAmount;
		this.amountItemsReceived = amountItemsReceived;
	}


	public OfferType getOfferType() {
		return offerType;
	}

	public String getOfferName() {
		return offerName;
	}

	public Item getItemWithOffer() {
		return itemWithOffer;
	}

	public Item getItemOffered() {
		return itemOffered;
	}

	public int getQualifyingAmount() {
		return qualifyingAmount;
	}

	public double getAmountItemsReceived() {
		return amountItemsReceived;
	}

}
