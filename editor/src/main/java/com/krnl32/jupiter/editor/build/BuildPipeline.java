package com.krnl32.jupiter.editor.build;

import com.krnl32.jupiter.engine.core.Logger;

import java.util.ArrayList;
import java.util.List;

public class BuildPipeline {
	private final List<BuildStep> steps = new ArrayList<>();

	public void addStep(BuildStep step) {
		steps.add(step);
	}

	public void execute(BuildContext context) {
		Logger.info("[Build] Started...");

		for (BuildStep step : steps) {
			Logger.info("[Build] Running Step({})", step.getName());

			try {
				if (!step.execute(context)) {
					Logger.info("[Build] Aborted by Step({})", step.getName());
					break;
				}
			} catch (BuildException e) {
				Logger.error("[Build] Step({}) Failed: {}", step.getName(), e.getMessage());
				break;
			}
		}

		Logger.info("[Build] Finished");
	}
}
