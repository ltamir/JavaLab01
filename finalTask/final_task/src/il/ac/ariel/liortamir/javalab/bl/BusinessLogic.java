package il.ac.ariel.liortamir.javalab.bl;

import java.io.IOException;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.api.Codec;
import il.ac.ariel.liortamir.javalab.exception.DataException;
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
 * 1. Read input file
 * 2. for each line in the input:
 * 2.1. Convert line to EventData
 * 2.2. Retrieve handler for the input command and Charge state
 * 2.3. activate the handler
 * 2.4. append handler result to a String buffer
 * 3. Write the whole results into the output file.
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
	
	
	public void run() {
		StringBuilder rawResponse = new StringBuilder();
		try {
			in.prepareFile();
			while(in.hasNext()) {
				
				String output = readRequest(in.next()); 			
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
	 * Call {@link BusinessLogic#processData(EventData)} and encode the result into a String
	 * @param record
	 * @return String representing the request result
	 */
	private String readRequest(String record) {
		String recordOut = null;
		EventData res = null;
		try {
			EventData req = codec.decode(record);
			
			res = processData(req);
			
			recordOut = codec.encode(res);
		}catch (DataException e) {
			System.out.println(e.getMessage());
			return record;
		}
		
		return recordOut;
	}
	
	
	/**
	 * Create  entities from the request and apply the handler<br>
	 * according to the charge state 
	 * @param req
	 * @return
	 */
	private EventData processData(EventData req) {
		
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
