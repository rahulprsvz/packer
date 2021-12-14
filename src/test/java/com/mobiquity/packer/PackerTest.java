package com.mobiquity.packer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mobiquity.exceptions.APIException;


/**
 * Unit test for Packer
 */
public class PackerTest {
	private List<String> inputLines;
	private List<String> outputLines;
	
	@BeforeEach
    public void setup() {
		inputLines = readLinesFromResource("example_input.txt");
		outputLines = readLinesFromResource("example_output.txt");
    }
	
	@AfterEach
	public void teardown() {
		inputLines = null;
		outputLines = null;
	}

	@Test
    public void testResources() {
		assertNotNull(inputLines);
		assertNotNull(outputLines);
		assertEquals(inputLines.size(), outputLines.size());
	}
	
    @Test
    public void testPacker() {
    	
    	for ( int i = 0; i < inputLines.size(); i++ ) {
    		try {
    			
				assertEquals( outputLines.get(i), Packer.process(inputLines.get(i)) );
				
			} catch (APIException e) {
				fail(e.getMessage());
			}
    	}
    }
    
    private List<String> readLinesFromResource(String filename) {
    	ClassLoader  loader = PackerTest.class.getClassLoader();
    	List<String> lines;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(loader.getResourceAsStream(filename), "UTF-8"
			));
			
			
			lines = br.lines().parallel().map(Object::toString).collect(Collectors.toList()); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			lines = null;
		}	
		return lines;
    }
}
