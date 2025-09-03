package com.krnl32.jupiter.engine.input.devices;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.input.MouseButtonPressEvent;
import com.krnl32.jupiter.engine.events.input.MouseButtonReleaseEvent;
import com.krnl32.jupiter.engine.events.input.MouseCursorEvent;
import com.krnl32.jupiter.engine.events.input.MouseScrollEvent;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class Mouse {
	private final int MAX_MOUSE_BUTTONS = GLFW.GLFW_MOUSE_BUTTON_LAST;
	private final boolean[] buttonState = new boolean[MAX_MOUSE_BUTTONS];
	private final boolean[] buttonPressed = new boolean[MAX_MOUSE_BUTTONS];
	private final boolean[] buttonReleased = new boolean[MAX_MOUSE_BUTTONS];
	private boolean cursorFirstMove = true;
	private Vector2f cursorPosition = new Vector2f();
	private Vector2f cursorDelta = new Vector2f();
	private Vector2f scrollOffset = new Vector2f();

	public Mouse() {
		EventBus.getInstance().register(MouseButtonPressEvent.class, this::onMouseButtonPressEvent);
		EventBus.getInstance().register(MouseButtonReleaseEvent.class, this::onMouseButtonReleaseEvent);
		EventBus.getInstance().register(MouseCursorEvent.class, this::onMouseCursorEvent);
		EventBus.getInstance().register(MouseScrollEvent.class, this::onMouseScrollEvent);
	}

	public void reset() {
		Arrays.fill(buttonPressed, false);
		Arrays.fill(buttonReleased, false);
		cursorDelta.set(0.0f, 0.0f);
		scrollOffset.set(0.0f, 0.0f);
	}

	public boolean isButtonPressed(MouseCode mouseCode) {
		if (mouseCode.getCode() < 0 || mouseCode.getCode() > MAX_MOUSE_BUTTONS) {
			Logger.error("isButtonPressed({}) mouseCode out of Bounds!", mouseCode.getCode());
		}

		return buttonPressed[mouseCode.getCode()];
	}

	public boolean isButtonReleased(MouseCode mouseCode) {
		if (mouseCode.getCode() < 0 || mouseCode.getCode() > MAX_MOUSE_BUTTONS) {
			Logger.error("isButtonReleased({}) mouseCode out of Bounds!", mouseCode.getCode());
		}

		return buttonReleased[mouseCode.getCode()];
	}

	public boolean isButtonUp(MouseCode mouseCode) {
		if (mouseCode.getCode() < 0 || mouseCode.getCode() > MAX_MOUSE_BUTTONS) {
			Logger.error("isButtonUp({}) mouseCode out of Bounds!", mouseCode.getCode());
		}

		return !buttonState[mouseCode.getCode()];
	}

	public boolean isButtonDown(MouseCode mouseCode) {
		if (mouseCode.getCode() < 0 || mouseCode.getCode() > MAX_MOUSE_BUTTONS) {
			Logger.error("isButtonDown({}) mouseCode out of Bounds!", mouseCode.getCode());
		}

		return buttonState[mouseCode.getCode()];
	}

	public Vector2f getCursorPosition() {
		return cursorPosition;
	}

	public Vector2f getCursorDelta() {
		Vector2f delta = new Vector2f(cursorDelta);
		cursorDelta.set(0.0f, 0.0f);
		return delta;
	}

	public Vector2f getScrollOffset() {
		return scrollOffset;
	}

	public boolean isScrollingUp() {
		return scrollOffset.y > 0;
	}

	public boolean isScrollingDown() {
		return scrollOffset.y < 0;
	}

	private void onMouseButtonPressEvent(MouseButtonPressEvent event) {
		int mouseCode = event.getMouseCode().getCode();

		if (mouseCode < 0 || mouseCode > MAX_MOUSE_BUTTONS) {
			Logger.error("MouseButtonPress mouseCode out of Bounds!", mouseCode);
		}

		if(!buttonState[mouseCode]) {
			buttonPressed[mouseCode] = true;
		}

		buttonState[mouseCode] = true;
	}

	private void onMouseButtonReleaseEvent(MouseButtonReleaseEvent event) {
		int mouseCode = event.getMouseCode().getCode();

		if (mouseCode < 0 || mouseCode > MAX_MOUSE_BUTTONS) {
			Logger.error("MouseButtonRelease mouseCode out of Bounds!", mouseCode);
		}

		buttonReleased[mouseCode] = true;
		buttonState[mouseCode] = false;
	}

	private void onMouseCursorEvent(MouseCursorEvent event) {
		if (cursorFirstMove) {
			cursorPosition.set(event.getPosition());
			cursorFirstMove = false;
			return;
		}

		cursorDelta.set((event.getPosition().x - cursorPosition.x), (cursorPosition.y - event.getPosition().y));
		cursorPosition.set(event.getPosition());
	}

	private void onMouseScrollEvent(MouseScrollEvent event) {
		scrollOffset.set(event.getOffset());
	}
}
