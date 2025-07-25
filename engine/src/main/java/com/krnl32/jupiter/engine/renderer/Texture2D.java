package com.krnl32.jupiter.engine.renderer;

import com.krnl32.jupiter.engine.core.Logger;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture2D {
	private int textureID;
	private int width, height, channels;
	private int format, internalFormat;

	public Texture2D(int width, int height, int channels) {
		this.width = width;
		this.height = height;
		this.channels = channels;

		if (channels == 4) {
			internalFormat = GL_RGBA8;
			format = GL_RGBA;
		} else if (channels == 3) {
			internalFormat = GL_RGB8;
			format = GL_RGB;
		} else if (channels == 1) {
			internalFormat = GL_R8;
			format = GL_RED;
		} else {
			Logger.error("Texture2D Invalid Channels({})", channels);
			return;
		}

		textureID = glCreateTextures(GL_TEXTURE_2D);
		glTextureStorage2D(textureID, 1, internalFormat, width, height);
		glTextureParameteri(textureID, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTextureParameteri(textureID, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTextureParameteri(textureID, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTextureParameteri(textureID, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}

	public Texture2D(String filepath) {
		ByteBuffer buffer = loadImage(filepath);
		if (buffer == null)
			return;

		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, buffer);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);

		stbi_image_free(buffer);
	}

	public void destroy() {
		glDeleteTextures(textureID);
	}

	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, textureID);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void setBuffer(ByteBuffer data) {
		glTextureSubImage2D(textureID, 0, 0, 0, width, height, format, GL_UNSIGNED_BYTE, data);
	}

	public void setBuffer(int[] data) {
		glTextureSubImage2D(textureID, 0, 0, 0, width, height, format, GL_UNSIGNED_BYTE, data);
	}

	public int getTextureID() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getChannels() {
		return channels;
	}

	public int getFormat() {
		return format;
	}

	public int getInternalFormat() {
		return internalFormat;
	}

	private ByteBuffer loadImage(String filepath) {
		stbi_set_flip_vertically_on_load(true);

		IntBuffer imgWidth = BufferUtils.createIntBuffer(1);
		IntBuffer imgHeight = BufferUtils.createIntBuffer(1);
		IntBuffer imgChannels = BufferUtils.createIntBuffer(1);

		ByteBuffer buffer = stbi_load(filepath, imgWidth, imgHeight, imgChannels, 0);
		if (buffer == null) {
			Logger.error("STBI_Load Failed For({}), Reason: ", filepath, stbi_failure_reason());
			return null;
		}

		width = imgWidth.get(0);
		height = imgHeight.get(0);
		channels = imgChannels.get(0);

		if (channels == 4) {
			internalFormat = GL_RGBA8;
			format = GL_RGBA;
		} else if (channels == 3) {
			internalFormat = GL_RGB8;
			format = GL_RGB;
		} else {
			stbi_image_free(buffer);
			Logger.error("STBI_Load Invalid Channels({}) For({}), Reason: ", channels, filepath, stbi_failure_reason());
			return null;
		}

		return buffer;
	}
}
