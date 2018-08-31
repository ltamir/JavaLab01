package il.ac.ariel.liortamir.javalab.infra;

import il.ac.ariel.liortamir.javalab.bl.BusinessLogic;
import il.ac.ariel.liortamir.javalab.model.Account;

public class Bootstrap {

	public static void main(String[] args) {
		AccountStorage db = AccountStorage.getInstance();
		db.add(new Account(1, "aaa", 10.00));
		db.add(new Account(2, "bbb", 10.00));
		db.add(new Account(3, "ccc", 10.00));
		db.add(new Account(4, "ddd", 10.00));
		BusinessLogic bl = new BusinessLogic();
		bl.run();
	}

}
