package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;

public class TagComponent implements Component {
	public String tag;

	public TagComponent(String tag) {
		this.tag = tag;
	}
}
