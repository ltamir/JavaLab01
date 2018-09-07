package il.ac.ariel.liortamir.javalab.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.DataException;
import il.ac.ariel.liortamir.javalab.exception.DecodeException;
import il.ac.ariel.liortamir.javalab.exception.EncodeException;
import il.ac.ariel.liortamir.javalab.fsm.Event;

/**
 * Decodes a String representing a request into {@link il.ac.ariel.liortamir.javalab.bl.EventData}<br>
 *  and EventData into a String representing a response.<br>
 * The fields in the string are separated by comma  and expected in the following order:<br>
 * REQUEST_ID,DD/MM/YYYY HH:MM,COMMAND,ACCOUNT,AMOUNT<br>
 * Where:<br>
 * REQUEST_ID 	- unique id for the request (in account level)<br>
 * COMMAND 		- event ID according to the ordinal of each member of {@link il.ac.ariel.liortamir.javalab.fsm.Event}<br>
 * ACCOUNT 		- account id<br>
 * AMOUNT		- (signed) double<br>
 * This class uses  {@link il.ac.ariel.liortamir.javalab.api.API} as the keys for each field.
 * @author liort
 *
 */
public class Codec {

	private boolean isDebug = false;
	private static final String SEPARATOR = ",";
	
	/**
	 * Extract fields from a comma delimited string.
	 * @param line
	 * @return {@link EventData}
	 * @throws DecodeException
	 */
	public EventData decode(String line) throws DecodeException{
		EventData data = new EventData();
		String[] fields = line.split(",");
		
		if(isDebug)
			System.out.println("Codec.decode: " + line);
		
		try {
			data.set(API.REQUEST_ID, validateRequestId(fields[0]));
			data.set(API.DATETIME, validateDateTime(fields[1]));
			data.set(API.EVENT_ID, validateEvent(fields[2]));
			data.set(API.ACCOUNT, validateAccount(fields[3]));
			data.set(API.AMOUNT, validateAmount(fields[4]));	
		}catch (ArrayIndexOutOfBoundsException e) {
			data.getAsString(API.REQUEST_ID, "0");
			throw new DecodeException(e.getMessage());
		}catch(NullPointerException e) {
			data.set(API.REQUEST_ID, "null");
			throw new DecodeException(e.getMessage());
		}
		
		
		return data;
	}
	
	/**
	 * Write fields into a comma delimited String
	 * @param data
	 * @return String
	 * @throws EncodeException
	 */
	public String encode(EventData data) throws EncodeException{
		StringBuilder response = new StringBuilder();
		
		if(isDebug)
			System.out.println("Codec.encode(): before " + data);
		
		try {
			response.append(data.getAsString(API.REQUEST_ID));
			response.append(SEPARATOR);
			response.append(data.getAsString(API.EVENT_ID));
			response.append(SEPARATOR);
			response.append(data.getAsString(API.REQUEST_STATUS));
			response.append(SEPARATOR);
			response.append(data.getAsString(API.ERROR, ""));
		}catch(ClassCastException e) {
			throw new EncodeException(response.toString() + "," + e.getMessage());
		}catch(NullPointerException e) {
			throw new EncodeException(response.toString() + "," + e.getMessage());
		}

		
		if(isDebug)
			System.out.println("Codec.encode(): after " + response.toString());
		
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
			throw new DecodeException("Invalid ID:" + rawId + " " + e.getMessage());
		}
		return result;
	}
	
	private LocalDateTime validateDateTime(String rawDatetime) throws DecodeException{
		LocalDateTime result = null;
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy H:m");
		try {
			result = LocalDateTime.parse(rawDatetime, dateFormatter);
		}catch(DateTimeParseException e) {
			throw new DecodeException("invalid datetime:" + rawDatetime + " " + e.getMessage());
		}
		return result;
	}
	
	private int validateEvent(String rawEvent) throws DecodeException{
		int command;
		try {
			command = Integer.valueOf(rawEvent);	
		}catch(NumberFormatException e) {
			throw new DecodeException("Invalid ID:" + rawEvent + " " + e.getMessage());
		}
		
		if(command < 0 && command >= Event.values().length)
			throw new DecodeException("Invalid event:" + rawEvent + " " + "Invalid Command");
		
		return command;
	}
	
	private int validateAccount(String rawAccount) throws DecodeException{
		int account;
		try{
			account = Integer.valueOf(rawAccount);
		}catch(NumberFormatException e) {
			throw new DecodeException("Invalid account:" + rawAccount + " " + e.getMessage());
		}
		
		return account;
	}
	
	private double validateAmount(String rawCharge) throws DecodeException {
		double charge;
		try{
			charge = Double.valueOf(rawCharge);
		}catch(NumberFormatException e) {
			throw new DecodeException("Invalid amount:" + rawCharge + " " + e.getMessage());
		}
		return charge;
	}
}
