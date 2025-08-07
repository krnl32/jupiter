package com.krnl32.jupiter.engine.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIO {
	public static String readFileContent(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static String readFileContent(Path filePath) throws IOException {
		return new String(Files.readAllBytes(filePath));
	}

	public static byte[] readFileContentBytes(Path filePath) throws IOException {
		return Files.readAllBytes(filePath);
	}

	public static void writeFileContent(String filePath, String data) throws IOException {
		Files.write(Paths.get(filePath), data.getBytes());
	}

	public static void writeFileContent(Path filePath, String data) throws IOException {
		Files.write(filePath, data.getBytes());
	}

	public static void writeFileContentBytes(Path filePath, byte[] data) throws IOException {
		Files.write(filePath, data);
	}

	public static String readResourceFileContent(String resourcePath) throws IOException {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
			if (inputStream == null) {
				throw new FileNotFoundException("ResourcePath: " + resourcePath);
			}
			return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	public static byte[] readResourceFileContentBytes(String resourcePath) throws IOException {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
			if (inputStream == null) {
				throw new FileNotFoundException("ResourcePath: " + resourcePath);
			}
			return inputStream.readAllBytes();
		}
	}
}
