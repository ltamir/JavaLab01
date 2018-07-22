package org.liortamir.ariel.javalab;

import org.liortamir.ariel.javalab.logic.BusinessLogic;

public class Bootstrap {

	public static void main(String[] args) {

		BusinessLogic bl = new BusinessLogic();
		bl.menuHandler();
	}
}
