package coenie.technical_assignment.receipt_calculator.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import coenie.technical_assignment.receipt_calculator.model.BuyOneGetOneOffer;
import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Offer;

@Repository("ItemDaoMock")
public class DaoMock implements Dao {

	private static final Map<String, Item> data = new HashMap<String, Item>() {
		private static final long serialVersionUID = 3629215986614030711L;
		{
			put("Apples", new Item("APPLES", 25));
			put("Oranges", new Item("ORANGES", 30));
			put("Garlic", new Item("GARLIC", 15));
			put("Papayas", new Item("PAPAYAS", 50)); 
		}
	};


	@Override
	public Item findItemByName(String name) {
		return data.get(name);
	}
	
	@Override
	public Set<Offer> getOffers(){
		return new HashSet<>(Arrays.asList(new BuyOneGetOneOffer("Three for the price of two", data.get("Papayas"), 3, 1)));
	}

}
