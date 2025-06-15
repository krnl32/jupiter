package com.github.krnl32.jupiter.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {
	public static String readFileContent(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static void writeFileContent(String filePath, String data) throws IOException {
		Files.write(Paths.get(filePath), data.getBytes());
	}
}
