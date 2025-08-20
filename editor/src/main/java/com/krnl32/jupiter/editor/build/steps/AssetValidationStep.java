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
		Logger.info("[AssetValidationStep] Validating Assets...");
		if (assetManager.getAssets().isEmpty()) {
			Logger.error("[AssetValidationStep] No Assets Found");
			return false;
		}

		Logger.info("[AssetValidationStep] Validation Successful");
		return true;
	}
}
