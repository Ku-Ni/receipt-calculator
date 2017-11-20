package coenie.technical_assignment.receipt_calculator.dao;

import coenie.technical_assignment.receipt_calculator.model.Item;

public interface ItemDao {
	Item findItemByName(String name);
}
