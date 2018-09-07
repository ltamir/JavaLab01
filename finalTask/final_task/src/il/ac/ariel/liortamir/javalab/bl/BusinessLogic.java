package il.ac.ariel.liortamir.javalab.bl;

import java.io.IOException;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.api.Codec;
import il.ac.ariel.liortamir.javalab.exception.DataException;
import il.ac.ariel.liortamir.javalab.exception.DecodeException;
import il.ac.ariel.liortamir.javalab.exception.EncodeException;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.fsm.StateMachine;
import il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.DefaultHandler;
import il.ac.ariel.liortamir.javalab.infra.AccountStorage;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;
import il.ac.ariel.liortamir.javalab.persistency.FileInputReader;
import il.ac.ariel.liortamir.javalab.persistency.FileOutputWriter;

/**
 * Implements the logic of this process:<br>
 * 1. Read input via {@link FileInputReader}<br>
 * 2. for each request in the input: <br>
 * 2.1. Convert request to {@link il.ac.ariel.liortamir.javalab.bl.EventData}<br>
 * 2.2. Retrieve the appropriate {@link il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler}<br>
 * 2.3. activate the handler and retrieve response as EventData<br> 
 * 2.4. Convert the response to a String  and append the output buffer<br>
 * 3. Write the output buffer to {@link FileOutputWriter}
 * @author liort
 *
 */
public class BusinessLogic {

	private StateMachine stateMachine = StateMachine.getInstance();
	private Codec codec = new Codec();
	private FileInputReader in = new FileInputReader("input.csv");
	private FileOutputWriter out = new FileOutputWriter("output.csv");
	protected AccountStorage db = AccountStorage.getInstance();
	private DefaultHandler defaultHandler = new DefaultHandler();
	
	private final static Event[] events = Event.values();
	
	// ***** ENTRY POINT ***** //
	
	/**
	 * Prepare an input, iterate over the requests and write results.<br>
	 * Request and response are a String terminated by a {@code '\n'} sign.<br>
	 * This method calls {@link BusinessLogic#handleRequest(String)} to handle each request<br>
	 * and uses {@link java.lang.StringBuilder.StringBuilder} to aggregate the responses.
	 */
	public void run() {
		StringBuilder rawResponse = new StringBuilder();
		try {
			in.prepareFile();
			while(in.hasNext()) {
				
				String output = handleRequest(in.next()); 			
				rawResponse.append(output);
				rawResponse.append("\n");
				
			}
			out.prepareFile(rawResponse.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Decode incoming String record into an EventData.<br>
	 * Call {@link BusinessLogic#processEventData(EventData)} and encode the result into a String
	 * @param record - A String representing the request
	 * @return String representing the result
	 */
	private String handleRequest(String record) {
		String recordOut = null;
		EventData res = null;
		try {
			EventData req = codec.decode(record);
			
			res = processEventData(req);
			
			recordOut = codec.encode(res);
		}catch (DataException e) {
			
			if(e instanceof DecodeException)
				System.out.println("RQUEST REJECTED: " + e.getMessage());

			if(e instanceof EncodeException)
				System.out.println("T4: " + record + "\nERROR: "+ e.getMessage());
			
			return record;
		}
		
		return recordOut;
	}
	
	
	/**
	 * Creates entities from the request and apply the handler 
	 *  given by the {@link il.ac.ariel.liortamir.javalab.fsm.StateMachine} <br>
	 * according to the charge state 
	 * @param req - EventData depicting the request
	 * @return EventData depicting the result of the request
	 */
	private EventData processEventData(EventData req) {
		
		AbstractStateHandler handler;
		State currentState = State.NEW;
		EventData res = null;
		int eventId = -1;
		Account account = null;
		Charge charge = null;
		
		try {
			
			eventId = req.getAsInt(API.EVENT_ID);
			account = db.get(req.getAsInt(API.ACCOUNT));
			charge = account.getCharge(req.getAsInt(API.REQUEST_ID));
			if(charge == null)	//new Charge
				currentState = State.NEW;
			else
				currentState = charge.getState();
			
			handler = stateMachine.getHandler(currentState, eventId);
			
			
			switch(events[eventId]) {
			case RESERVE:
				res = handler.reserve(req, account, charge);
				charge = account.getCharge(req.getAsInt(API.REQUEST_ID));
				break;				
			case COMMIT:
				res = handler.commit(req, account, charge);
				break;
			case REFUND:
				res = handler.refund(req, account, charge);
				break;
			case UNRESERVE:
				res = handler.unreserve(req, account, charge);
				break;
			case CREDIT:
				res = handler.credit(req, account, charge);
				charge = account.getCharge(req.getAsInt(API.REQUEST_ID));
				break;
				default:
					throw new InvalidStateException("Invalit eventId: " + eventId);
			}
			
			handleState(charge, eventId, res.getAsString(API.REQUEST_STATUS));
			
		}catch (InvalidStateException e) {
			System.out.println(e.getMessage());
			res = defaultHandler.consume(req, e.getMessage());
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
			res = defaultHandler.consume(req, e.getMessage());
		} catch (InvalidEntityException e) {
			System.out.println(e.getMessage());
			res = defaultHandler.consume(req, e.getMessage());
		}
		
		return res;
	}
	
	
	/**
	 * Change the state of the charge according to the state machine rules
	 * @param charge
	 * @param eventId
	 * @param status
	 * @throws InvalidEntityException
	 * @throws InvalidStateException
	 */
	private void handleState(Charge charge, int eventId, String status) throws InvalidEntityException, InvalidStateException{
		if(!status.equals(API.OK))
			return;
		
		if(charge == null)
			throw new InvalidEntityException("Cannot change state on a null charge");
		
		
		State currentState = charge.getState();
		charge.setState(stateMachine.getNextState(currentState, eventId));
		
	}

	
}
