package com.mobiquity.packer.service;

import java.util.ArrayList;
import java.util.List;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Solution;

/**
 * Dynamic Programming solution
 * 
 * @author Rahul
 *
 */

public class DynamicSolutionServiceImpl implements PackerService {

	@Override
	public Solution solve(int capacity, List<Item> items) {

		/*
		 * The following lines are necessary in case there's at least one item in items
		 * list having non-integer weight. Each item's Weight should not have more than
		 * 2 fractional digits.
		 * 
		 */
		if (!onlyIntegerWeigths(items)) {
			capacity *= 100;
			items.stream().forEach(i -> i.setWeight(i.getWeight() * 100));
		}

		/*
		 * The following line satisfy the requirements:
		 * "You would prefer to send a package which weighs less in case there is more than one package with the same price."
		 * 
		 */
		items.sort((i1, i2) -> i1.getWeight().compareTo(i2.getWeight()));

		Item[] itemsArray = new Item[items.size()];
		itemsArray = items.toArray(itemsArray);

		/*
		 * Create a matrix having N+1 rows and capacity+1 columns
		 */
		int N = items.size();
		int[][] matrix = new int[N + 1][capacity + 1];

		/*
		 * Initialize the first zero-column
		 */
		for (int i = 0; i <= capacity; i++) {
			matrix[0][i] = 0;
		}

		/*
		 * Build the solutions matrix
		 */
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= capacity; j++) {
				if (itemsArray[i - 1].getWeight() > j) {
					matrix[i][j] = matrix[i - 1][j];
				} else {
					matrix[i][j] = Math.max(matrix[i - 1][j],
							matrix[i - 1][j - itemsArray[i - 1].getWeight().intValue()] + itemsArray[i - 1].getValue());
				}
			}
		}

		/*
		 * Max value is in the lower right corner of the matrix
		 */
		int res = matrix[N][capacity];
		int w = capacity;
		List<Item> solutionItems = new ArrayList<>();

		/*
		 * Iterate the matrix in reverse order, looking for items to include in solution
		 */
		for (int i = N; i > 0 && res > 0; i--) {
			if (res != matrix[i - 1][w]) {
				solutionItems.add(itemsArray[i - 1]);
				res -= itemsArray[i - 1].getValue();
				w -= itemsArray[i - 1].getWeight();
			}
		}

		/*
		 * Items included in the solution should be listed following index natural order
		 */
		solutionItems.sort((i1, i2) -> i1.getIndex().compareTo(i2.getIndex()));

		return new Solution(solutionItems, matrix[N][capacity]);
	}

	/**
	 * @param items
	 * @return false if there's at least one items' element having non-integer
	 *         weight
	 */
	public boolean onlyIntegerWeigths(List<Item> items) {

		boolean allIntegers = true;
		for (Item i : items) {
			if (i.getWeight() != Math.floor(i.getWeight())) {
				allIntegers = false;
				break;
			}
		}

		return allIntegers;
	}
}
