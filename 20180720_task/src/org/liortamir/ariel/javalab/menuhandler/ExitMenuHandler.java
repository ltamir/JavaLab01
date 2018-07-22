package org.liortamir.ariel.javalab.menuhandler;



import org.liortamir.ariel.javalab.logic.VectorBuilder;
import org.liortamir.ariel.javalab.persistency.ReadFile;
/**
 * Exits the application with exit status 0
 * This class overrides {@link #handle()} 
 * @author liort
 *
 */
public class ExitMenuHandler extends AbstractMenuHandler {

	public ExitMenuHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Exit";
	}

	@Override
	public void customHandler(VectorBuilder builder, ReadFile file) {

	}

	@Override
	public void handle() {
		System.out.println("Exiting program");
		System.exit(0);
	}
	
	

}
