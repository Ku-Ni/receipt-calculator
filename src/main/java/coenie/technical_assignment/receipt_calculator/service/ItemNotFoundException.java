package coenie.technical_assignment.receipt_calculator.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7802484783675572186L;

	public ItemNotFoundException(String itemName) {
		super("Could not find item named "+itemName);
	}
}
