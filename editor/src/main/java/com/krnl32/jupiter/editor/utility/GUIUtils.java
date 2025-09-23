package com.krnl32.jupiter.editor.utility;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.Consumer;

public class GUIUtils {
	public static boolean renderVector2f(String label, Vector2f vec) {
		int uniqueID = System.identityHashCode(vec);
		ImGui.pushID(label + "_" + uniqueID);

		ImGui.columns(2, "vec2columns" + label + "_" + uniqueID, false);
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

		boolean changed = false;

		// X Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);

		if (ImGui.button("X", buttonSize, buttonSize)) {
			xVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##X_" + label + "_" + uniqueID, xVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.x = xVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Y Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);

		if (ImGui.button("Y", buttonSize, buttonSize)) {
			yVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##Y_" + label + "_" + uniqueID, yVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.y = yVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		vec.set(xVal[0], yVal[0]);

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderVector2fClamped(String label, Vector2f vec, float min, float max) {
		int uniqueID = System.identityHashCode(vec);
		ImGui.pushID(label + "_" + uniqueID);

		ImGui.columns(2, "vec2columns" + label + "_" + uniqueID, false);
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

		boolean changed = false;

		// X Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);

		if (ImGui.button("X", buttonSize, buttonSize)) {
			xVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##X_" + label + "_" + uniqueID, xVal, dragSpeed, min, max, "%.3f")) {
			vec.x = xVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Y Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);

		if (ImGui.button("Y", buttonSize, buttonSize)) {
			yVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##Y_" + label + "_" + uniqueID, yVal, dragSpeed, min, max, "%.3f")) {
			vec.y = yVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		vec.set(xVal[0], yVal[0]);

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderVector3f(String label, Vector3f vec) {
		int uniqueID = System.identityHashCode(vec);
		ImGui.pushID(label + "_" + uniqueID);

		ImGui.columns(2, "vec3columns" + label + "_" + uniqueID, false);
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

		boolean changed = false;

		// X Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);

		if (ImGui.button("X", buttonSize, buttonSize)) {
			xVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##X_" + label + "_" + uniqueID, xVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.x = xVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Y Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);

		if (ImGui.button("Y", buttonSize, buttonSize)) {
			yVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##Y_" + label + "_" + uniqueID, yVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.y = yVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.sameLine(0, 8);

		// Z Button + DragFloat
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.1f, 0.8f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.3f, 1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 0.2f, 1f, 1f);

		if (ImGui.button("Z", buttonSize, buttonSize)) {
			zVal[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine(0, 2);

		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##Z_" + label + "_" + uniqueID, zVal, dragSpeed, -Float.MAX_VALUE, Float.MAX_VALUE, "%.3f")) {
			vec.z = zVal[0];
			changed = true;
		}

		ImGui.popItemWidth();

		vec.set(xVal[0], yVal[0], zVal[0]);

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static void renderStringReadOnly(String label, String value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();
		ImGui.text(value);
		ImGui.columns(1);
		ImGui.popID();
	}

	public static void renderStringTextDisabled(String label, String value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.textDisabled(label);
		ImGui.nextColumn();
		ImGui.text(value);
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

	public static void renderIntReadOnly(String label, int value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();
		ImGui.text(String.format("%d", value));
		ImGui.columns(1);
		ImGui.popID();
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

	public static void renderLongReadOnly(String label, long value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();
		ImGui.text(String.format("%d", value));
		ImGui.columns(1);
		ImGui.popID();
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

	public static void renderColorPicker(String label, Vector4f color) {
		int uniqueID = System.identityHashCode(color);
		ImGui.pushID(label + "_" + uniqueID);

		ImGui.columns(2, "Columns_" + label + "_" + uniqueID, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 6);
		ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 2, 4);

		float dragSpeed = 0.01f;
		float buttonSize = 27f;
		float dragWidth = 37f;

		// RGBA
		float[] r = { color.x };
		float[] g = { color.y };
		float[] b = { color.z };
		float[] a = { color.w };

		boolean changed = false;

		float[] rgba = { r[0], g[0], b[0], a[0] };

		// Color Picker
		float currentX = ImGui.getCursorPosX();
		ImGui.setCursorPosX(currentX - 8);
		ImGui.pushItemWidth(25);

		if (ImGui.colorEdit4("##ColorPreview_" + label + "_" + uniqueID, rgba, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.Float)) {
			changed = true;
			r[0] = rgba[0];
			g[0] = rgba[1];
			b[0] = rgba[2];
			a[0] = rgba[3];
		}

		ImGui.popItemWidth();
		ImGui.setCursorPosX(currentX + 28 + 6);
		ImGui.sameLine();

		ImGui.beginGroup();

		// R
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);

		if (ImGui.button("R", buttonSize, buttonSize)) {
			r[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine();
		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##R_" + label + "_" + uniqueID, r, dragSpeed, 0f, 1f, "%.2f")) {
			changed = true;
		}

		ImGui.popItemWidth();
		ImGui.sameLine();

		// G
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.8f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 1f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 1f, 0.2f, 1f);

		if (ImGui.button("G", buttonSize, buttonSize)) {
			g[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine();
		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##G_" + label + "_" + uniqueID, g, dragSpeed, 0f, 1f, "%.2f")) {
			changed = true;
		}

		ImGui.popItemWidth();
		ImGui.sameLine();

		// B
		ImGui.pushStyleColor(ImGuiCol.Button, 0.1f, 0.1f, 0.8f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.3f, 1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 0.2f, 1f, 1f);

		if (ImGui.button("B", buttonSize, buttonSize)) {
			b[0] = 0f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine();
		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##B_" + label + "_" + uniqueID, b, dragSpeed, 0f, 1f, "%.2f")) {
			changed = true;
		}

		ImGui.popItemWidth();
		ImGui.sameLine();

		// A
		ImGui.pushStyleColor(ImGuiCol.Button, 0.6f, 0.6f, 0.6f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.8f, 0.8f, 0.8f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.7f, 0.7f, 0.7f, 1f);

		if (ImGui.button("A", buttonSize, buttonSize)) {
			a[0] = 1f;
			changed = true;
		}

		ImGui.popStyleColor(3);
		ImGui.sameLine();
		ImGui.pushItemWidth(dragWidth);

		if (ImGui.dragFloat("##A_" + label + "_" + uniqueID, a, dragSpeed, 0f, 1f, "%.2f")) {
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.endGroup();

		ImGui.popStyleVar(2);
		ImGui.columns(1);
		ImGui.popID();

		if (changed) {
			color.set(r[0], g[0], b[0], a[0]);
		}
	}

	public static <T extends Enum<T>> boolean renderEnumCombo(String label, T[] values, ImInt selectedIndex) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 110);
		ImGui.text(label);
		ImGui.nextColumn();

		boolean changed = false;
		String[] items = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			items[i] = values[i].name();
		}

		ImGui.pushItemWidth(ImGui.getContentRegionAvailX());

		if (ImGui.combo("##" + label, selectedIndex, items)) {
			changed = true;
		}

		ImGui.popItemWidth();

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderCheckbox(String label, ImBoolean value) {
		ImGui.pushID(label);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 110);
		ImGui.text(label);
		ImGui.nextColumn();

		boolean changed = ImGui.checkbox("##" + label, value);

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}

	public static boolean renderButton(String label) {
		ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);
		boolean clicked = ImGui.button(label);
		ImGui.popStyleColor(3);

		return clicked;
	}

	public static <T extends Asset> boolean renderAssetCombo(List<T> assets, String label, String currentAssetLabel, AssetId currentAssetID, Consumer<AssetId> onSelect) {
		int uniqueID = System.identityHashCode(currentAssetID);
		ImGui.pushID(label + "_" + uniqueID);
		ImGui.columns(2, "Columns_" + label, false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text(label);
		ImGui.nextColumn();

		boolean changed = false;
		ImGui.pushItemWidth(ImGui.getContentRegionAvailX());

		if (ImGui.beginCombo("##" + label, currentAssetLabel)) {
			for (T asset : assets) {
				String selectableLabel = (asset instanceof ScriptAsset) ? ((ScriptAsset) asset).getAssetPath() : asset.getId().toString();

				boolean selected = asset.getId().equals(currentAssetID);

				if (ImGui.selectable(selectableLabel, selected)) {
					onSelect.accept(asset.getId());
					changed = true;
				}

				if (selected) {
					ImGui.setItemDefaultFocus();
				}
			}
			ImGui.endCombo();
		}

		ImGui.columns(1);
		ImGui.popID();

		return changed;
	}
}
