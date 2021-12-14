package com.mobiquity.packer.service;

import java.util.List;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Solution;

/**
 * @author Rahul
 *
 */
public interface PackerService {

	/**
	 * @param capacity the maximum weight a package can hold
	 * @param items    a list of items, each having index, weight and value
	 * @return
	 */
	public Solution solve(int capacity, List<Item> items);
}
