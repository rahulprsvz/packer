package com.mobiquity.packer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquity.exceptions.APIException;
import com.mobiquity.packer.model.Configuration;
import com.mobiquity.packer.model.Solution;
import com.mobiquity.packer.service.DynamicSolutionServiceImpl;
import com.mobiquity.packer.service.PackerService;
import com.mobiquity.packer.util.ConfigurationParser;

public class Packer {

	private static final PackerService SOLVER = new DynamicSolutionServiceImpl(); 
//	private static final PackerService SOLVER = new RecursiveSolutionServiceImpl();

	/**
	 * @param filePath a valid absolute file path on the local filesystem
	 * @return a String containing the solutions (one line for each test case)
	 * @throws APIException
	 */
	public static String pack(String filePath) throws APIException {

		Path path = Paths.get(filePath);

		try {

			List<String> lines = Files.lines(path).collect(Collectors.toList());

			StringBuffer sb = new StringBuffer();

			for (String l : lines) {
				sb.append(Packer.process(l)).append("\n");
			}

			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param line a single test case in the [capacity] :
	 *             ([index],[weight],[�value])
	 * @return a comma-separated list of items indexes, or '-' if there's no
	 *         solution
	 * @throws APIException
	 */
	public static String process(String line) throws APIException {

		Configuration configuration = ConfigurationParser.parse(line);

		if (configuration == null) {
			throw new APIException("Invalid input file");
		}

		Solution solution = SOLVER.solve(configuration.getCapacity(), configuration.getItems());

		String solutionItemsList;

		if (solution.getItems() != null && !solution.getItems().isEmpty()) {
			solutionItemsList = solution.getItems().stream().map(i -> i.getIndex().toString())
					.collect(Collectors.joining(","));
		} else {
			solutionItemsList = "-";
		}

		return solutionItemsList;
	}
}