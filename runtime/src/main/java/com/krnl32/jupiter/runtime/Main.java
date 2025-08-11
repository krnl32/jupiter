package com.krnl32.jupiter.runtime;

import com.krnl32.jupiter.engine.core.EngineSettings;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.core.WindowSettings;
import com.krnl32.jupiter.runtime.runtime.Runtime;

import java.nio.file.Path;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			Logger.info("JupiterRuntime No Project Provided");
			return;
		}

		String projectDirectoryPath = args[0];
		Runtime runtime = new Runtime(new EngineSettings(new WindowSettings("JupiterEditor", 1920, 1080, true)), Path.of(projectDirectoryPath));
		runtime.run();
	}
}
