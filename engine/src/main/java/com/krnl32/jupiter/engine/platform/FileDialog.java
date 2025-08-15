package com.krnl32.jupiter.engine.platform;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;

public class FileDialog {
	public static Path openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a File");
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			? chooser.getSelectedFile().toPath()
			: null;
	}

	public static Path openFile(String extensionFilter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a File");
		String ext = extensionFilter.startsWith(".") ? extensionFilter.substring(1) : extensionFilter;
		chooser.setFileFilter(new FileNameExtensionFilter("*" + extensionFilter + " files", ext));
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			? chooser.getSelectedFile().toPath()
			: null;
	}

	public static Path openFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			? chooser.getSelectedFile().toPath()
			: null;
	}
}
