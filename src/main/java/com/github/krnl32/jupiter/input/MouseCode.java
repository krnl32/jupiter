package com.github.krnl32.jupiter.input;

import com.github.krnl32.jupiter.core.Logger;

public enum MouseCode {
	BUTTON_1(0),
	BUTTON_2(1),
	BUTTON_3(2),
	BUTTON_4(3),
	BUTTON_5(4),
	BUTTON_6(5),
	BUTTON_7(6),
	BUTTON_8(7),

	// Aliases
	BUTTON_LAST(7),
	BUTTON_LEFT(0),
	BUTTON_RIGHT(1),
	BUTTON_MIDDLE(2);

	private final int code;

	MouseCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static MouseCode fromCode(int code) {
		for (MouseCode mouseCode : values()) {
			if (mouseCode.code == code) {
				return mouseCode;
			}
		}
		Logger.critical("Invalid MouseCode({})", code);
		return null;
	}
}
