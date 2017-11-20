package coenie.technical_assignment.receipt_calculator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import coenie.technical_assignment.receipt_calculator.model.Item;
import coenie.technical_assignment.receipt_calculator.model.Receipt;
import coenie.technical_assignment.receipt_calculator.service.CostService;
import coenie.technical_assignment.receipt_calculator.service.ItemService;

@RestController
@RequestMapping("/basket")
public class BasketController {
	
	private CostService costService;
	private ItemService itemService;
	
	@Autowired
	public BasketController(CostService costService, ItemService itemService) {
		this.costService = costService;
		this.itemService = itemService;
	}
	
	@RequestMapping(value="/receipt", method = RequestMethod.POST)
	public Receipt calculateReceipt(@RequestBody List<String> itemNames) {
		List<Item> items = itemService.getItems(itemNames);
		return costService.calculateReceipt(items);
	}
	
	@RequestMapping(value="/cost/{item}", method = RequestMethod.GET)
	public Item getItemCost(@PathVariable String item){
		return itemService.getItem(item);
	}
}
