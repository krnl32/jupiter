package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ui.layout.LayoutOverflow;
import com.krnl32.jupiter.ui.layout.LayoutType;

public class UILayoutComponent implements Component {
	public LayoutType type;
	public LayoutOverflow overflow;
	public float paddingTop, paddingBottom, paddingLeft, paddingRight;
	public float spacing;
	public boolean expandChildren;

	public UILayoutComponent(LayoutType type, LayoutOverflow overflow, float paddingTop, float paddingBottom, float paddingLeft, float paddingRight, float spacing, boolean expandChildren) {
		this.type = type;
		this.overflow = overflow;
		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.spacing = spacing;
		this.expandChildren = expandChildren;
	}
}
