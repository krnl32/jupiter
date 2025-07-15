package com.krnl32.jupiter.utility;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GUIUtils {
	public static void renderVector2f(String label, Vector2f vec) {
		ImGui.pushID(label);

		ImGui.columns(2, "vec2columns" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 6);
		ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 4, 4);

		float dragSpeed = 0.1f;
		float buttonSize = 27f;
		float dragWidth = 64f;

		float[] xVal = {vec.x};
		float[] yVal = {vec.y};

		// X Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);
		if (ImGui.button("X", buttonSize, buttonSize)) {
			xVal[0] = 0f;
		}
		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);
		if (ImGui.dragFloat("##X_" + label, xVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.x = xVal[0];
		}
		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Y Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);
		if (ImGui.button("Y", buttonSize, buttonSize)) {
			yVal[0] = 0f;
		}
		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);
		if (ImGui.dragFloat("##Y_" + label, yVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.y = yVal[0];
		}
		ImGui.popItemWidth();

		vec.set(xVal[0], yVal[0]);

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();
	}

	public static void renderVector3f(String label, Vector3f vec) {
		ImGui.pushID(label);

		ImGui.columns(2, "vec3columns" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 6);
		ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 4, 4);

		float dragSpeed = 0.1f;
		float buttonSize = 27f;
		float dragWidth = 64f;

		float[] xVal = {vec.x};
		float[] yVal = {vec.y};
		float[] zVal = {vec.z};

		// X Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);
		if (ImGui.button("X", buttonSize, buttonSize)) {
			xVal[0] = 0f;
		}
		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);
		if (ImGui.dragFloat("##X_" + label, xVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.x = xVal[0];
		}
		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Y Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);
		if (ImGui.button("Y", buttonSize, buttonSize)) {
			yVal[0] = 0f;
		}
		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);
		if (ImGui.dragFloat("##Y_" + label, yVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.y = yVal[0];
		}
		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Z Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.1f, 0.8f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.3f, 1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 0.2f, 1f, 1f);
		if (ImGui.button("Z", buttonSize, buttonSize)) {
			zVal[0] = 0f;
		}
		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);
		if (ImGui.dragFloat("##Z_" + label, zVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.z = zVal[0];
		}
		ImGui.popItemWidth();

		vec.set(xVal[0], yVal[0], zVal[0]);

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();
	}

	public static boolean renderStringInputWithClearButton(String label, ImString value) {
		ImGui.pushID(label);

		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.beginGroup();

		float inputWidth = ImGui.getContentRegionAvailX() - 60;
		ImGui.pushItemWidth(inputWidth);
		boolean changed = ImGui.inputText("##" + label, value);
		ImGui.popItemWidth();

		ImGui.sameLine();
		if (ImGui.button("Clear", 50, 0)) {
			value.set("");
			changed = true;
		}

		ImGui.endGroup();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderIntInput(String label, ImInt value) {
		ImGui.pushID(label);

		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.pushItemWidth(ImGui.getContentRegionAvailX());
		boolean changed = ImGui.inputInt("##" + label, value);
		ImGui.popItemWidth();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderIntInputWithResetButton(String label, ImInt value, int reset) {
		ImGui.pushID(label);

		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.beginGroup();

		float inputWidth = ImGui.getContentRegionAvailX() - 60;
		ImGui.pushItemWidth(inputWidth);

		boolean changed = ImGui.inputInt("##" + label, value);
		ImGui.popItemWidth();

		ImGui.sameLine();

		if (ImGui.button("Reset", 50, 0)) {
			value.set(reset);
			changed = true;
		}

		ImGui.endGroup();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderFloatInput(String label, ImFloat value) {
		ImGui.pushID(label);

		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.pushItemWidth(ImGui.getContentRegionAvailX());
		boolean changed = ImGui.inputFloat("##" + label, value);
		ImGui.popItemWidth();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static void renderFloatReadOnly(String label, float value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();
		ImGui.text(String.format("%.3f", value));
		ImGui.columns(1);
		ImGui.popID();
	}

	public static boolean renderFloatInputWithResetButton(String label, ImFloat value, float reset) {
		ImGui.pushID(label);

		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 110);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.beginGroup();

		float inputWidth = ImGui.getContentRegionAvailX() - 60;
		ImGui.pushItemWidth(inputWidth);

		boolean changed = ImGui.inputFloat("##" + label, value);
		ImGui.popItemWidth();

		ImGui.sameLine();

		if (ImGui.button("Reset", 50, 0)) {
			value.set(reset);
			changed = true;
		}

		ImGui.endGroup();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static void renderBoolReadOnly(String label, boolean value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();
		ImGui.text(value ? "True" : "False");
		ImGui.columns(1);
		ImGui.popID();
	}
}
