package org.liortamir.ariel.javalab.handler;


import java.util.Scanner;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.container.ContainerFactory;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

public abstract class AbstractContainerHandler extends AbstractHandler{

	protected ContainerFactory<Integer> containerFactory = new ContainerFactory<>();
	
	public AbstractContainerHandler(String fileName) {
		super(fileName);
	}


	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException {
		
		customFactory();
		while(file.hasNext()) {
			builder.build(file.next(), holder.get());
		}

	}
	
	protected abstract void customFactory();



}
