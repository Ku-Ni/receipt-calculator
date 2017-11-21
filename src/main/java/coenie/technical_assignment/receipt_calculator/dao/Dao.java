package coenie.technical_assignment.receipt_calculator.dao;

import java.util.Set;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

public interface Dao {
	Item findItemByName(String name);

	Set<Offer> getOffers();
}
