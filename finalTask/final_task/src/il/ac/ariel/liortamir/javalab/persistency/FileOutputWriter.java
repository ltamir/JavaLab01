package il.ac.ariel.liortamir.javalab.persistency;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes given String data to the given file.<br>
 * If no path is given the file is saved to .\data path.<br>
 * @author liort
 *
 */
public class FileOutputWriter{
	
	private static final String DIR_SEPARATOR = "\\";
	private static final String DATA_DIR = "data";
	
	private String fileName = null;	
	
	public FileOutputWriter(String fileName) {
		this.fileName = fileName;
	}
	
	
	private String buildFilePath() {
		if(fileName.contains("\\"))	//if filename has backslash it was given with full path
			return fileName;
		
		//if file name does not have a path it is in current directory
		final String currDir = System.getProperty("user.dir");
		final String fullPath = currDir + DIR_SEPARATOR + DATA_DIR + DIR_SEPARATOR + fileName;
		return fullPath;
	}
	
	/**
	 * Open the file via try-with-resource and write the data.
	 * 
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException in case of I/O error reading the file
	 */
	public void prepareFile(String data) throws FileNotFoundException, IOException{
		try(FileWriter file = new FileWriter(buildFilePath()))
		{
			file.write(data);
		}	
	}
	

}
