package org.liortamir.ariel.javalab.handler;



import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.persistency.ReadFile;
/**
 * Exits the application with exit status 0
 * This class overrides {@link #handle()} 
 * @author liort
 *
 */
public class ExitHandler extends AbstractHandler {

	public ExitHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Exit";
	}

	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) {

	}

	@Override
	public void handle() {
		System.out.println("Exiting program");
		System.exit(0);
	}
	
	

}
