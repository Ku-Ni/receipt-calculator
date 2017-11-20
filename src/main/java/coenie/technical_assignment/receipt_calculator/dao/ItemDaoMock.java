package coenie.technical_assignment.receipt_calculator.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import coenie.technical_assignment.receipt_calculator.model.Item;

@Repository("ItemDaoMock")
public class ItemDaoMock implements ItemDao {

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

}
