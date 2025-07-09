package com.krnl32.jupiter.physics;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.physics.BeginCollisionEvent;
import com.krnl32.jupiter.events.physics.EndCollisionEvent;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactListener implements org.jbox2d.callbacks.ContactListener {
	@Override
	public void beginContact(Contact contact) {
		Entity entityA = (Entity) contact.getFixtureA().getUserData();
		Entity entityB = (Entity) contact.getFixtureB().getUserData();
		EventBus.getInstance().emit(new BeginCollisionEvent(entityA, entityB));
	}

	@Override
	public void endContact(Contact contact) {
		Entity entityA = (Entity) contact.getFixtureA().getUserData();
		Entity entityB = (Entity) contact.getFixtureB().getUserData();
		EventBus.getInstance().emit(new EndCollisionEvent(entityA, entityB));
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
	}
}
