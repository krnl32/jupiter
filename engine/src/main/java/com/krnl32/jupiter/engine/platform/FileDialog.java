package com.krnl32.jupiter.engine.platform;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileDialog {
	public static String openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a File");
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile().getAbsolutePath() : null;
	}

	public static String openFile(String extensionFilter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a File");
		String ext = extensionFilter.startsWith(".") ? extensionFilter.substring(1) : extensionFilter;
		chooser.setFileFilter(new FileNameExtensionFilter("*" + extensionFilter + " files", ext));
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile().getAbsolutePath() : null;
	}

	public static String openFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile().getAbsolutePath() : null;
	}
}
