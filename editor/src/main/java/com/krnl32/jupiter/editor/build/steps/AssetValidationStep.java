package com.krnl32.jupiter.editor.build.steps;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.build.BuildContext;
import com.krnl32.jupiter.editor.build.BuildException;
import com.krnl32.jupiter.editor.build.BuildStep;
import com.krnl32.jupiter.engine.core.Logger;

public class AssetValidationStep implements BuildStep {
	private final EditorAssetManager assetManager;

	public AssetValidationStep(EditorAssetManager assetManager) {
		this.assetManager = assetManager;
	}

	@Override
	public String getName() {
		return "AssetValidationStep";
	}

	@Override
	public boolean execute(BuildContext context) throws BuildException {
		Logger.info("[{}] Validating Assets...", getName());

		if (assetManager.getAssetIds().isEmpty()) {
			Logger.error("[{}] No Assets Found", getName());
			return false;
		}

		Logger.info("[{}] Validation Successful", getName());
		return true;
	}
}
