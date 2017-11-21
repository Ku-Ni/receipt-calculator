package coenie.technical_assignment.receipt_calculator.model;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Item {
	private String name;
	private int price;
	
	public Item(String name, int price){
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Item)) {
			return false;
		}
		Item castOther = (Item) other;
		return Objects.equals(name, castOther.name) && Objects.equals(price, castOther.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name).append("price", price)
				.toString();
	}
	
	
}
