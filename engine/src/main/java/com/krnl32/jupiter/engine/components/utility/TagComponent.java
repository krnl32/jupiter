package com.krnl32.jupiter.engine.components.utility;

import com.krnl32.jupiter.engine.ecs.Component;

public class TagComponent implements Component {
	public String tag;

	public TagComponent(String tag) {
		this.tag = tag;
	}
}
