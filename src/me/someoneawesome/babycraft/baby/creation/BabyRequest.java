package me.someoneawesome.babycraft.baby.creation;

import java.util.*;

import me.someoneawesome.babycraft.custom.BcRequest;

public class BabyRequest extends BcRequest {

	public BabyRequest(UUID requester, UUID reciever) {
		super(requester, reciever);
		sendRequest("have a baby with", "baby", "/babycraft accept", "/babycraft deny");
	}

	@Override
	public void commit() {
		BabyCreationAction.startCreation(requester, reciever);
	}
	
}
