package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.event.EventListener;
import com.github.krnl32.jupiter.events.window.WindowCloseEvent;

public class Main {
	public static void main(String[] args) {
		Logger.info("Jupiter Game Engine");

		EventBus eventBus = EventBus.getInstance();
		EventListener<WindowCloseEvent> windowCloseListener = event -> {
			System.out.println("Closing Window");
		};
		eventBus.register(WindowCloseEvent.class, windowCloseListener);
		//eventBus.unregister(WindowCloseEvent.class, windowCloseListener);
		eventBus.emit(new WindowCloseEvent());

		//Game game = new Game("JupiterGame", 640, 480);
		//game.run();
	}
}
