package com.krnl32.jupiter.engine.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {
	public static String readFileContent(Path filePath) throws IOException {
		return new String(Files.readAllBytes(filePath));
	}

	public static byte[] readFileContentBytes(Path filePath) throws IOException {
		return Files.readAllBytes(filePath);
	}

	public static void writeFileContent(Path filePath, String data) throws IOException {
		Files.write(filePath, data.getBytes());
	}

	public static void writeFileContentBytes(Path filePath, byte[] data) throws IOException {
		Files.write(filePath, data);
	}

	public static void deleteFile(Path filePath) throws IOException {
		Files.delete(filePath);
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

	public static Path replaceFileExtension(Path path, String ext) {
		if (ext.startsWith(".")) {
			ext = ext.substring(1);
		}

		String fileName = path.getFileName().toString();
		int dot = fileName.lastIndexOf('.');
		String newFileName = (dot == -1)
			? fileName + "." + ext
			: fileName.substring(0, dot) + "." + ext;

		return path.resolveSibling(newFileName);
	}
}
