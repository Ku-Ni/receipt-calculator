package coenie.technical_assignment.receipt_calculator.service;

import java.util.List;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Receipt;

public interface CostService {
	Receipt calculateReceipt(List<Item> items);
}
