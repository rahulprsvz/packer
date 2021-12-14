package com.mobiquity.packer;

import com.mobiquity.exceptions.APIException;

public class Main {

	public static void main(String[] args) {

//		if (args.length > 0) {
//			try {
//				System.out.println(Packer.pack(args[0]));
//			} catch (APIException e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			System.err.println("Please, enter a valid absolute filepath.");
//		}

		try {
			System.out.println(
					Packer.pack("D:\\Interview\\Interview\\packer\\src\\main\\test\\resources\\example_input.txt"));
		} catch (APIException e) {
			e.printStackTrace();
		}

	}

}
