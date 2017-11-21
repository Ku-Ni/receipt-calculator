package coenie.technical_assignment.receipt_calculator.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Receipt {
	public static final int TEXT_COLUMN_WIDTH = 30;
	public static final int AMOUNT_COLUMN_WIDTH = 10;
	public static final String LINE = "----------------------------------------";
	public static final String OFFER = "**";
	public static final String NEW_LINE = System.lineSeparator();

	private final List<Item> items;
	private final Set<Offer> appliedOffers;
	private final int totalAmount;

	public Receipt(List<Item> items, int totalAmount, Set<Offer> offers) {
		this.items=Collections.unmodifiableList(items);
		this.appliedOffers=Collections.unmodifiableSet(offers.stream()
				.filter(offer->offer.getNumOffersApplied()>0)
				.collect(Collectors.toSet()));
		this.totalAmount=totalAmount;
	}

	public List<Item> getItems() {
		return items;
	}

	public Set<Offer> getAppliedOffers() {
		return appliedOffers;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public String print() {
		double pence = 100;
		StringBuilder stringBuilder = new StringBuilder(LINE).append(NEW_LINE);
		// Print all items
		items.stream().forEach(
				item -> stringBuilder
				.append(StringUtils.rightPad(item.getName(), TEXT_COLUMN_WIDTH))
				.append(StringUtils.leftPad(String.format("£%.2f", item.getPrice()/pence), AMOUNT_COLUMN_WIDTH))
				.append(NEW_LINE)
				);
		stringBuilder.append(LINE).append(NEW_LINE);

		// Print offers
		if (!appliedOffers.isEmpty()) {
			appliedOffers.stream().forEach(
					offer -> IntStream.range(0,offer.getNumOffersApplied()).forEach(i -> stringBuilder
							.append(StringUtils.rightPad(new StringBuilder(OFFER)
									.append(offer.getOfferItem().getName())
									.append(OFFER).toString(), TEXT_COLUMN_WIDTH))
							.append(NEW_LINE)
							.append(StringUtils.rightPad(new StringBuilder(offer.getOfferName())
									.toString(), TEXT_COLUMN_WIDTH))
							.append(StringUtils.leftPad(
									String.format("-£%.2f", offer.getOfferAmount()/pence), AMOUNT_COLUMN_WIDTH))
							.append(NEW_LINE)
							));
			stringBuilder.append(LINE).append(NEW_LINE);
		}
		
		stringBuilder.append(StringUtils.leftPad(String.format("TOTAL: £%.2f", totalAmount/pence), TEXT_COLUMN_WIDTH+AMOUNT_COLUMN_WIDTH)).append(NEW_LINE);

		return stringBuilder.append(LINE).toString();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Receipt)) {
			return false;
		}
		Receipt castOther = (Receipt) other;
		return Objects.equals(items, castOther.items) && Objects.equals(appliedOffers, castOther.appliedOffers)
				&& Objects.equals(totalAmount, castOther.totalAmount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(items, appliedOffers, totalAmount);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("items", items)
				.append("offers", appliedOffers).append("totalAmount", totalAmount).toString();
	}



}
