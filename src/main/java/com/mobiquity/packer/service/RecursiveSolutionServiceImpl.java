package com.mobiquity.packer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Solution;

/**
 * Recursive solution
 * 
 * @author Rahul
 *
 */
public class RecursiveSolutionServiceImpl implements PackerService {

	@Override
	public Solution solve(int capacity, List<Item> items) {

		/*
		 * The following line satisfy the requirements:
		 * "You would prefer to send a package which weighs less in case there is more than one package with the same price."
		 * 
		 */
		items.sort((i1, i2) -> i1.getWeight().compareTo(i2.getWeight()));

		/*
		 * Initialize arrays: weights, values and visited items
		 */
		Double[] wt = items.stream().map(Item::getWeight).toArray(Double[]::new);
		Integer[] val = items.stream().map(Item::getValue).toArray(Integer[]::new);
		Boolean visited[] = new Boolean[items.size()];
		Arrays.fill(visited, Boolean.FALSE);

		/*
		 * Compute the recursion tree, keeping track of visited items
		 */
		Integer maxValue = maximizeValueFor(capacity, wt, val, items.size(), visited);

		List<Item> solutionItems = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			if (visited[i]) {
				solutionItems.add(items.get(i));
			}
		}

		/*
		 * Items included in the solution should be listed following index natural order
		 */
		solutionItems.sort((i1, i2) -> i1.getIndex().compareTo(i2.getIndex()));

		return new Solution(solutionItems, maxValue);
	}

	/**
	 * @param W       the maximum capacity of the package
	 * @param wt      array of items weights
	 * @param val     array of items values
	 * @param N       total number of items
	 * @param visited
	 * @return maximum value for the package
	 */
	private Integer maximizeValueFor(Integer W, Double wt[], Integer val[], Integer N, Boolean visited[]) {

		if (N == 0 || W == 0) {
			return 0; // base case
		}

		if (wt[N - 1] > W) {

			return maximizeValueFor(W, wt, val, N - 1, visited); // overflow capacity, reject item

		} else {

			// pick max between two cases:
			// (1) nth item included
			// (2) nth item not included

			Boolean v1[] = new Boolean[visited.length];
			Boolean v2[] = new Boolean[visited.length];

			System.arraycopy(visited, 0, v1, 0, v1.length);
			System.arraycopy(visited, 0, v2, 0, v2.length);

			v1[N - 1] = true;

			Integer s1 = val[N - 1] + maximizeValueFor(W - wt[N - 1].intValue(), wt, val, N - 1, v1);
			Integer s2 = maximizeValueFor(W, wt, val, N - 1, v2);

			if (s1 > s2) {
				System.arraycopy(v1, 0, visited, 0, v1.length);
				return s1;
			} else {
				System.arraycopy(v2, 0, visited, 0, v2.length);
				return s2;
			}
		}
	}
}
