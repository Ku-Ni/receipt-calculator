package coenie.technical_assignment.receipt_calculator.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuyOneGetOneOffer implements Offer {
	private final String offerName;
	private final Item offerItem;
	private final int qualifyingAmount;
	private final double amountItemsReceived;
	
	private AtomicInteger itemsAdded;


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
		this.itemsAdded = new AtomicInteger();
		this.offerName = offerName;
		this.offerItem = offerItem;
		this.qualifyingAmount = qualifyingAmount;
		this.amountItemsReceived = amountItemsReceived;
	}

	@Override
	public String getOfferName() {
		return offerName;
	}

	@Override
	public Item getOfferItem() {
		return offerItem;
	}

	@Override
	public void addItem() {	
		itemsAdded.incrementAndGet();		
	}

	@Override
	public int getNumOffersApplied() {
		return itemsAdded.get()/qualifyingAmount;
	}

	@Override
	public int getOfferAmount() {
		return Double.valueOf(amountItemsReceived*offerItem.getPrice()).intValue();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof BuyOneGetOneOffer)) {
			return false;
		}
		BuyOneGetOneOffer castOther = (BuyOneGetOneOffer) other;
		return Objects.equals(offerItem, castOther.offerItem);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offerItem);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("offerName", offerName)
				.append("offerItem", offerItem).append("qualifyingAmount", qualifyingAmount)
				.append("amountItemsReceived", amountItemsReceived).append("itemsAdded", itemsAdded).toString();
	}

}
