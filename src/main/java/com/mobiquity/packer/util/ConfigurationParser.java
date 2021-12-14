package com.mobiquity.packer.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquity.packer.model.Configuration;
import com.mobiquity.packer.model.Item;

/**
 * An utility class to parse raw input data into a Configuration object
 * 
 * @author Rahul
 *
 */
public class ConfigurationParser {

	/**
	 * @param line in the input sample data format
	 * @return a Configuration object, holding input data
	 */
	public static Configuration parse(String line) {

		Configuration configuration = null;

		String[] configurationParts = line.split(" : ");

		try {

			if (configurationParts.length == 2) {

				Integer capacity = Integer.parseInt(configurationParts[0]);

				String[] itemsParts = configurationParts[1].split("\\s+");

				if (itemsParts.length > 0) {

					List<Item> itemsList = new ArrayList<>();

					for (String itemString : itemsParts) {

						String[] itemParts = itemString.replace("(", "").replace(")", "").split(",");
						if (itemParts.length == 3) {

							itemsList.add(new Item(Integer.parseInt(itemParts[0]), Double.parseDouble(itemParts[1]),
									Integer.parseInt(itemParts[2].replace("€", ""))));

						} else {
							itemsList.clear();
							break;
						}
					}

					if (!itemsList.isEmpty()) {
						configuration = new Configuration(capacity, itemsList);
					}

				}

			}
		} catch (NumberFormatException e) {
			configuration = null;
		}

		return configuration;
	}
}
