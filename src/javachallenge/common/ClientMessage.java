package javachallenge.common;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientMessage implements Serializable{
	private static final long serialVersionUID = 9109211941037476652L;
	
	ArrayList<Action> actions ;
	
	public ClientMessage(){
		this.actions = new ArrayList<Action>() ;
	}
	
	public ArrayList<Action> getActions() {
		return actions ;
	}

	public void addAction(Action act, int teamId) {
		actions.add(act) ;
	}

	@Override
	public String toString() {
		return "ClientMessage [actions=" + actions + "]";
	}

	
}
