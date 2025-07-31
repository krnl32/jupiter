package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.editor.editor.Editor;
import com.krnl32.jupiter.engine.core.Logger;
import org.apache.commons.cli.*;

public class Main {
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption(null, "help", false, "Prints Help Commands");
		options.addOption(null, "launch", true, "Path to the Project to Launch");

		CommandLine cmd = null;
		try {
			CommandLineParser parser = new DefaultParser();
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return;
		}

		if (cmd.hasOption("launch")) {
			String projectPath = cmd.getOptionValue("launch");
			Logger.info("Jupiter Editor Launching({})...", projectPath);
			Editor editor = new Editor("JupiterEditor", 1920, 1080, projectPath);
			editor.run();
		} else {
			new HelpFormatter().printHelp("editor", options);
		}
	}
}
