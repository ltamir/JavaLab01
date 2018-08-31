package il.ac.ariel.liortamir.javalab.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.DataException;
import il.ac.ariel.liortamir.javalab.exception.DecodeException;
import il.ac.ariel.liortamir.javalab.fsm.Event;

/**
 * Decode a String representing a command into eventData<br>.
 * The fields in the string are separated by comma  and expected in the following order:<br>
 * Reserve: REQUEST_ID,DD/MM/YYYY HH:MM,COMMAND,ACCOUNT,AMOUNT<br>
 * Commit: REQUEST_ID,DD/MM/YYYY HH:MM,COMMAND,ACCOUNT,AMOUNT<br>
 * Refund: REQUEST_ID,DD/MM/YYYY HH:MM,COMMAND,ACCOUNT,AMOUNT<br>
 * 
 * Where:<br>
 * REQUEST_ID 	- unique id for the request (in account level)
 * COMMAND 		- event ID
 * ACCOUNT 		- account id
 * AMOUNT		- double
 * @author liort
 *
 */
public class Codec {

	private boolean isDebug = false;
	private static final String SEPARATOR = ",";
	
	public EventData decode(String line) throws DecodeException {
		EventData data = new EventData();
		String[] fields = line.split(",");
		
		if(isDebug)
			System.out.println("Codec.decode: " + line);
		
		data.set(API.REQUEST_ID, validateRequestId(fields[0]));
		data.set(API.DATETIME, validateDateTime(fields[1]));
		data.set(API.EVENT_ID, validateCommand(fields[2]));
		data.set(API.ACCOUNT, validateAccount(fields[3]));
		data.set(API.AMOUNT, validateAmount(fields[4]));
		
		return data;
	}
	
	public String encode(EventData data) throws DataException{
		StringBuilder response = new StringBuilder();
		
		if(isDebug)
			System.out.println("Codec.encode: " + data);
		
		response.append(data.getAsString(API.REQUEST_ID));
		response.append(SEPARATOR);
		response.append(data.getAsString(API.EVENT_ID));
		response.append(SEPARATOR);
		response.append(data.getAsString(API.REQUEST_STATUS));
		response.append(SEPARATOR);
		response.append(data.getAsString(API.ERROR, ""));
		
		if(isDebug)
			System.out.println("Codec.encode: " + response.toString());
		
		return response.toString();
	}
	

	// ***** validations ***** //

	private int validateRequestId(String rawId) throws DecodeException{
		int result = -1;
		try{
			result = Integer.valueOf(rawId);
			if(result < 0 || result > Event.values().length)
				throw new DecodeException("Unknown Event ID " + result);
		}catch(NumberFormatException e) {
			throw new DecodeException(e.getMessage());
		}
		return result;
	}
	
	private LocalDateTime validateDateTime(String rawDatetime) throws DecodeException{
		LocalDateTime result = null;
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
		try {
			result = LocalDateTime.parse(rawDatetime, dateFormatter);
		}catch(DateTimeParseException e) {
			throw new DecodeException(e.getMessage());
		}
		return result;
	}
	
	private int validateCommand(String roawcommand) throws DecodeException{
		int command;
		try {
			command = Integer.valueOf(roawcommand);	
		}catch(NumberFormatException e) {throw new DecodeException(e.getMessage());}
		
		if(command < 0 && command >= Event.values().length)
			throw new DecodeException("Invalid Command");
		return command;
	}
	
	private int validateAccount(String rawAccount) throws DecodeException{
		int account;
		try{
			account = Integer.valueOf(rawAccount);
		}catch(NumberFormatException e) {
			throw new DecodeException(e.getMessage());
		}
		
		return account;
	}
	
	private double validateAmount(String rawCharge) throws DecodeException {
		double charge;
		try{
			charge = Double.valueOf(rawCharge);
		}catch(NumberFormatException e) {
			throw new DecodeException(e.getMessage());
		}
		return charge;
	}
}
