package com.krnl32.jupiter.engine.events.filesystem;

import com.krnl32.jupiter.engine.event.Event;

import java.nio.file.Path;

public class FileDragDropEvent implements Event {
	private final Path filePath;

	public FileDragDropEvent(Path filePath) {
		this.filePath = filePath;
	}

	public Path getFilePath() {
		return filePath;
	}
}
