package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.core.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private final int programID;
	private final Map<String, Integer> uniformLocationCache = new HashMap<>();

	public Shader(String vertexFilePath, String fragmentFilePath) {
		try {
			String vertexSrc = Files.readString(Paths.get(vertexFilePath));
			String fragmentSrc = Files.readString(Paths.get(fragmentFilePath));
			programID = createShader(vertexSrc, fragmentSrc);
			if (programID == 0)
				Logger.error("Failed to Create Shader for({}, {})", vertexFilePath, fragmentFilePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void bind() {
		glUseProgram(programID);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void delete() {
		glDeleteProgram(programID);
	}

	public void setInt(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}

	public void setIntArray(String name, int[] value) {
		glUniform1iv(getUniformLocation(name), value);
	}

	public void setFloat(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}

	public void setFloat2(String name, Vector2f value) {
		glUniform2f(getUniformLocation(name), value.x, value.y);
	}

	public void setFloat3(String name, Vector3f value) {
		glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
	}

	public void setFloat4(String name, Vector4f value) {
		glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
	}

	public void setMat4(String name, Matrix4f value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		glUniformMatrix4fv(getUniformLocation(name), false, buffer);
	}

	private int createShader(String vertexSrc, String fragmentSrc) {
		int program = glCreateProgram();
		int vs = compileShader(GL_VERTEX_SHADER, vertexSrc);
		int fs = compileShader(GL_FRAGMENT_SHADER, fragmentSrc);
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		glLinkProgram(program);
		glValidateProgram(program);
		glDeleteShader(vs);
		glDeleteShader(fs);
		return program;
	}

	private int compileShader(int type, String src) {
		int id = glCreateShader(type);
		glShaderSource(id, src);
		glCompileShader(id);
		validateCompileStatus(id);
		return id;
	}

	private void validateCompileStatus(int id) {
		int res = glGetShaderi(id, GL_COMPILE_STATUS);
		if(res == 0) {
			int len = glGetShaderi(id, GL_INFO_LOG_LENGTH);
			String msg = glGetShaderInfoLog(id, len);
			Logger.error("Failed to Compile Shader({}): {}", id, msg);
			glDeleteShader(id);
		}
	}

	private int getUniformLocation(String name) {
		if (uniformLocationCache.containsKey(name))
			return uniformLocationCache.get(name);

		int location = glGetUniformLocation(programID, name);
		uniformLocationCache.put(name, location);
		return location;
	}
}
