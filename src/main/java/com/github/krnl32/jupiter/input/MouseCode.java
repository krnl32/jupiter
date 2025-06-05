package com.github.krnl32.jupiter.input;

import com.github.krnl32.jupiter.core.Logger;

public enum MouseCode {
	Button1(0),
	Button2(1),
	Button3(2),
	Button4(3),
	Button5(4),
	Button6(5),
	Button7(6),
	Button8(7),

	// Aliases (custom logic to resolve them)
	ButtonLast(7),
	ButtonLeft(0),
	ButtonRight(1),
	ButtonMiddle(2);

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
