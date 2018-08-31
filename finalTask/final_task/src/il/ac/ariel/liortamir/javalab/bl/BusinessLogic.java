package il.ac.ariel.liortamir.javalab.bl;

import java.io.IOException;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.api.Codec;
import il.ac.ariel.liortamir.javalab.exception.DataException;
import il.ac.ariel.liortamir.javalab.exception.DecodeException;
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
	
	private final static Event[] events = Event.values();
	
	public void run() {
		StringBuilder rawResponse = new StringBuilder();
		try {
			in.prepareFile();
			while(in.hasNext()) {
				AbstractStateHandler handler;
				State currentState;
				EventData res = null;
				EventData req = codec.decode(in.next());
				int eventId = req.getAsInt(API.EVENT_ID);
				
				Account account = db.get(req.getAsInt(API.ACCOUNT));
				Charge charge = account.getCharge(req.getAsInt(API.REQUEST_ID));
				if(charge == null)	//new Charge
					charge = AccountHelper.createCharge();
				
				currentState = charge.getState();

				handler = stateMachine.getHandler(currentState, eventId);
				if(handler == null) {
					handler = new DefaultHandler(stateMachine);
				}
				
				switch(events[eventId]) {
				case RESERVE:
					res = reserve(handler, req, account, charge);
					break;				
				case COMMIT:
					res = commit(handler, req, account, charge);
					break;
				case REFUND:
					res = refund(handler, req, account, charge);
					break;
				case UNRESERVE:
					res = unreserve(handler, req, account, charge);
					break;
				case CREDIT:
					break;
					default:
						break;
				}
				
				rawResponse.append(codec.encode(res));
				rawResponse.append("\n");
			}
			out.prepareFile(rawResponse.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DecodeException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	// TODO implement each event as method and call the handler with the event's method
	
	private EventData reserve(AbstractStateHandler handler, EventData req, Account account, Charge charge) {
		EventData res = handler.reserve(req, account, charge);
		
		charge.setState(stateMachine.getNextState(charge.getState(), req.getAsInt(API.EVENT_ID)));
		return res;
	}
	
	private EventData commit(AbstractStateHandler handler, EventData req, Account account, Charge charge) {
		EventData res = handler.consume(req, account, charge);
		return res;
	}
	
	private EventData refund(AbstractStateHandler handler, EventData req, Account account, Charge charge) {
		EventData res = handler.consume(req, account, charge);
		return res;
	}
	
	private EventData unreserve(AbstractStateHandler handler, EventData req, Account account, Charge charge) {
		EventData res = handler.consume(req, account, charge);
		return res;
	}
}
