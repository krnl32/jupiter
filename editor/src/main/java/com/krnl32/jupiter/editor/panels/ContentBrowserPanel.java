package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.editor.EditorPanel;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.filesystem.FileDragDropEvent;
import com.krnl32.jupiter.engine.project.ProjectContext;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiHoveredFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ContentBrowserPanel implements EditorPanel {
	private static final float THUMBNAIL_SIZE = 80.0f;
	private static final float PADDING = 12.0f;
	private static final float ITEM_WIDTH = THUMBNAIL_SIZE + PADDING;

	private final Path rootPath;
	private Path selectedPath;

	public ContentBrowserPanel() {
		this.rootPath = ProjectContext.getInstance().getAssetDirectory();
		this.selectedPath = ProjectContext.getInstance().getAssetDirectory();

		EventBus.getInstance().register(FileDragDropEvent.class, event -> {
			Path droppedFilePath = event.getFilePath();
			if (!Files.exists(droppedFilePath)) {
				Logger.error("ContentBrowserPanel FileDragDropEvent Invalid File({})", droppedFilePath);
				return;
			}

			Path targetFilePath = selectedPath.resolve(droppedFilePath.getFileName());

			try {
				if (!droppedFilePath.startsWith(rootPath)) {
					Files.copy(droppedFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
				} else {
					targetFilePath = droppedFilePath;
				}

				AssetId importedAssetId = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).importAsset(targetFilePath);
				if (importedAssetId == null) {
					Logger.error("ContentBrowserPanel Failed to Import Asset({})", droppedFilePath);
					return;
				}

				Logger.info("ContentBrowserPanel Imported Asset({}, {})", importedAssetId, targetFilePath);
			} catch (Exception e) {
				Logger.error("ContentBrowserPanel FileDragDropEvent Error File({}): {}", droppedFilePath, e.getMessage());
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.begin("Content Browser");

		ImVec2 panelSize = ImGui.getContentRegionAvail();
		float leftPanelWidth = panelSize.x * 0.25f;

		ImGui.beginChild("LeftPane", leftPanelWidth, panelSize.y, true);
		renderDirectoryTree();
		ImGui.endChild();

		ImGui.sameLine();

		ImGui.beginChild("RightPane", 0, panelSize.y, true);
		renderAssetGrid();
		ImGui.endChild();

		ImGui.end();
	}

	public void renderDirectoryTree() {
		ImGui.pushID("DirectoryTree");

		String rootName = rootPath.getFileName() != null ? rootPath.getFileName().toString() : rootPath.toString();

		int rootFlags = ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.Framed | ImGuiTreeNodeFlags.SpanAvailWidth;
		if (rootPath.equals(selectedPath)) {
			rootFlags |= ImGuiTreeNodeFlags.Selected;
		}

		boolean rootOpen = ImGui.treeNodeEx(rootName, rootFlags);
		if (ImGui.isItemClicked()) {
			selectedPath = rootPath;
		}
		if (rootOpen) {
			renderDirectoryRecursive(rootPath.toFile());
			ImGui.treePop();
		}

		ImGui.popID();
	}

	private void renderDirectoryRecursive(File dir) {
		if (dir == null || !dir.isDirectory()) {
			return;
		}

		File[] subDirs = dir.listFiles(File::isDirectory);
		if (subDirs == null) {
			return;
		}

		for (File sub : subDirs) {
			int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.Framed | ImGuiTreeNodeFlags.SpanAvailWidth;

			boolean selected = sub.toPath().equals(selectedPath);
			if (selected) {
				flags |= ImGuiTreeNodeFlags.Selected;
			}

			ImGui.pushStyleColor(ImGuiCol.Text, ImGui.getColorU32(1.0f, 1.0f, 1.0f, 1.0f));
			ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, new ImVec2(0, 0));

			boolean open = ImGui.treeNodeEx(sub.getName(), flags);

			ImGui.popStyleVar();
			ImGui.popStyleColor();

			if (ImGui.isItemClicked()) {
				selectedPath = sub.toPath();
			}

			if (open) {
				renderDirectoryRecursive(sub);
				ImGui.treePop();
			}
		}
	}

	public void renderAssetGrid() {
		if (selectedPath == null || !selectedPath.toFile().isDirectory()) {
			return;
		}

		File[] files = selectedPath.toFile().listFiles();
		if (files == null) {
			return;
		}

		ImVec2 contentRegion = ImGui.getContentRegionAvail();
		int columnCount = Math.max(1, (int) (contentRegion.x / ITEM_WIDTH));
		ImGui.columns(columnCount, "AssetGrid", false);

		for (File file : files) {
			ImGui.pushID(file.getName());
			ImGui.beginGroup();

			ImVec2 cursorPos = ImGui.getCursorScreenPos();
			ImVec2 itemSize = new ImVec2(THUMBNAIL_SIZE, THUMBNAIL_SIZE + 24);

			ImGui.invisibleButton("##item", itemSize.x, itemSize.y);
			boolean hovered = ImGui.isItemHovered(ImGuiHoveredFlags.RectOnly);
			boolean active = ImGui.isItemActive();

			// Shadow effect behind card
			ImGui.getWindowDrawList().addRectFilled(
				cursorPos.x + 3, cursorPos.y + 3,
				cursorPos.x + itemSize.x + 3, cursorPos.y + itemSize.y + 3,
				ImGui.getColorU32(0, 0, 0, 90), 8
			);

			// Card background
			int bgColor = hovered
				? ImGui.getColorU32(0.22f, 0.38f, 0.65f, 0.85f) // bright accent on hover
				: ImGui.getColorU32(0.15f, 0.15f, 0.18f, 0.85f); // dark card bg
			ImGui.getWindowDrawList().addRectFilled(
				cursorPos.x, cursorPos.y,
				cursorPos.x + itemSize.x, cursorPos.y + itemSize.y,
				bgColor, 8
			);

			// Border
			int borderColor = hovered
				? ImGui.getColorU32(0.4f, 0.65f, 0.95f, 1.0f)
				: ImGui.getColorU32(0.2f, 0.2f, 0.25f, 1.0f);
			ImGui.getWindowDrawList().addRect(
				cursorPos.x, cursorPos.y,
				cursorPos.x + itemSize.x, cursorPos.y + itemSize.y,
				borderColor, 8, 0, 2
			);

			// Folder/File icon placeholder
			int iconColor = file.isDirectory()
				? ImGui.getColorU32(0.3f, 0.5f, 0.9f, 1.0f)
				: ImGui.getColorU32(0.7f, 0.7f, 0.7f, 1.0f);

			ImGui.getWindowDrawList().addRectFilled(
				cursorPos.x + 12, cursorPos.y + 12,
				cursorPos.x + THUMBNAIL_SIZE - 12, cursorPos.y + THUMBNAIL_SIZE - 12,
				iconColor, 6
			);

			String name = file.getName();
			ImGui.setCursorScreenPos(cursorPos.x + 6, cursorPos.y + THUMBNAIL_SIZE + 4);
			ImGui.pushTextWrapPos(cursorPos.x + itemSize.x - 12);
			ImGui.textColored(ImGui.getColorU32(1f, 1f, 1f, 0.9f), name);
			ImGui.popTextWrapPos();

			// Interaction
			if (hovered && ImGui.isMouseDoubleClicked(0)) {
				if (file.isDirectory()) {
					selectedPath = selectedPath.resolve(file.getPath());
				} else {
					// Open file
				}
			}

			if (hovered && ImGui.isMouseClicked(1)) {
				ImGui.openPopup("AssetContextMenu");
			}

			if (ImGui.beginPopup("AssetContextMenu")) {
				if (ImGui.menuItem("Open")) {

				}
				if (ImGui.menuItem("Rename")) {

				}
				if (ImGui.menuItem("Delete")) {
					file.delete();
				}
				ImGui.endPopup();
			}

			ImGui.endGroup();
			ImGui.nextColumn();
			ImGui.popID();
		}

		ImGui.columns(1);
	}
}
