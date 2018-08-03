package org.liortamir.ariel.javalab.infra;

import org.liortamir.ariel.javalab.bl.BusinessLogic;

public class Boostrap {

	public static void main(String[] args) {
		String dataFile;
		
		if(args.length > 1) {
			dataFile = args[1];
			System.out.println("Using " + dataFile);
		}else {
			System.out.println("data file should be Data.txt and is expected in current directory");
		}
		BusinessLogic bl = new BusinessLogic();
		bl.menuHandler();
	}
}
