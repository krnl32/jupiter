package com.krnl32.jupiter.editor.build;

public interface BuildStep {
	String getName();
	boolean execute(BuildContext context) throws BuildException;
}
