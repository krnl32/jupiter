package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.serializer.LevelSerializer;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Level {
	private final String filePath;
	private final LevelSerializer levelSerializer;

	public Level(String filePath) {
		this.filePath = filePath;
		this.levelSerializer = new LevelSerializer();
	}

	public void load(Scene scene) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(filePath)));
			JSONObject data = new JSONObject(content);
			levelSerializer.deserialize(data, scene);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
