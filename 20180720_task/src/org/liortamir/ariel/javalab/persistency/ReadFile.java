package org.liortamir.ariel.javalab.persistency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Reads given data file from .\data path and expose an Iterator <br>
 * to read each line in the file.
 * only non-empty lines are read. 
 * @author liort
 *
 */
public class ReadFile implements Iterator<String>{
	
	private static final String DATA_DIR = "data";
	private static final String DIR_SEPARATOR = "\\";
	
	private String fileName = null;
	private int pos = 0;
	private List<String> lineList = new ArrayList<>();
	
	
	public ReadFile(String fileName) {
		this.fileName = fileName;
	}
	
	
	private String buildFilePath() {
		final String currDir = System.getProperty("user.dir");
		final String fullPath = currDir + DIR_SEPARATOR + DATA_DIR + DIR_SEPARATOR + fileName; 
		return fullPath;
	}
	
	/**
	 * Open the file via try-with-resource <br>
	 * and add each non empty line to the ArrayList {@link #lineList}
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException in case of I/O error reading the file
	 */
	public void prepareFile() throws FileNotFoundException, IOException{
		try(FileReader file = new FileReader(buildFilePath()))
		{
			BufferedReader bf = new BufferedReader(file);
			String row;
			while((row = bf.readLine()) != null) {
				if(row.length() > 0)
					lineList.add(row);
			}
		}	
	}
	
	
	/**
	 * Implementation of {@link java.util.Iterator#next()}.<br>
	 * If current position is beyond number of lines return {@code null}<br>
	 * else return the line in current position and increment the position 
	 */
	@Override
	public String next(){
		String line = null;
		if(pos >= lineList.size())
			return line;
		line = lineList.get(pos);
		pos++;
		return line;
	}
	

	/**
	 * Implementation of {@link java.util.Iterator#hasNext()}.<br>
	 * If position is lesser than the size of {@link #lineList} return {@code true}<br>
	 * else return {@code false}
	 */
	@Override
	public boolean hasNext() {
		return pos < lineList.size();
	}
}
