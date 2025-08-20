package com.krnl32.jupiter.editor.build.steps;

import com.krnl32.jupiter.editor.build.BuildContext;
import com.krnl32.jupiter.editor.build.BuildException;
import com.krnl32.jupiter.editor.build.BuildStep;

public class AssetSerializationStep implements BuildStep {
	@Override
	public String getName() {
		return "AssetSerializationStep";
	}

	@Override
	public boolean execute(BuildContext context) throws BuildException {
		return true;
	}
}
