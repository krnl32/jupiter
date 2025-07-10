package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.core.Logger;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.*;

public class Framebuffer {
	private int framebufferID;
	private int width, height;
	private List<FrameBufferAttachmentFormat> attachments;
	private List<Integer> colorAttachments;

	public Framebuffer(int width, int height, List<FrameBufferAttachmentFormat> attachments) {
		this.framebufferID = 0;
		this.width = width;
		this.height = height;
		this.attachments = attachments;
		this.colorAttachments = new ArrayList<>();
		reset();
	}

	public void destroy() {
		glDeleteFramebuffers(framebufferID);
		for (int colorAttachment : colorAttachments) {
			glDeleteTextures(colorAttachment);
		}
	}

	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, framebufferID);
		glViewport(0, 0, width, height);
	}

	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public int getFramebufferID() {
		return framebufferID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColorAttachmentID(int attachmentIndex) {
		return colorAttachments.get(attachmentIndex);
	}

	public void resize(int width, int height) {
		if (this.width == width && this.height == height)
			return;
		this.width = width;
		this.height = height;
		reset();
	}

	public int readPixel(int attachmentIndex, int x, int y) {
		IntBuffer pixelData = BufferUtils.createIntBuffer(1);
		glReadBuffer(GL_COLOR_ATTACHMENT0 + attachmentIndex);

		switch (attachments.get(attachmentIndex)) {
			case RED_INTEGER -> {
				glReadPixels(x, y, 1, 1, GL_RED_INTEGER, GL_INT, pixelData);
				return pixelData.get(0);
			}
			case RGBA8 -> {
				ByteBuffer rgba = BufferUtils.createByteBuffer(4);
				glReadPixels(x, y, 1, 1, GL_RGBA, GL_UNSIGNED_BYTE, rgba);
				int r = rgba.get(0) & 0xFF;
				int g = rgba.get(1) & 0xFF;
				int b = rgba.get(2) & 0xFF;
				int a = rgba.get(3) & 0xFF;
				return (r << 24) | (g << 16) | (b << 8) | a;
			}
		}
		return -1;
	}

	public void clearColorAttachment(int attachmentIndex, int data) {
		switch (attachments.get(attachmentIndex)) {
			case RGBA8 -> {
				ByteBuffer clearData = BufferUtils.createByteBuffer(4);
				clearData.put(new byte[]{(byte) data, (byte) data, (byte) data, (byte) data});
				clearData.flip();
				glClearTexImage(colorAttachments.get(attachmentIndex), 0, GL_RGBA, GL_UNSIGNED_BYTE, clearData);
			}
			case RED_INTEGER -> {
				IntBuffer clearData = BufferUtils.createIntBuffer(1);
				clearData.put(data);
				clearData.flip();
				glClearTexImage(colorAttachments.get(attachmentIndex), 0, GL_RED_INTEGER, GL_INT, clearData);
			}
		}
	}

	private void reset() {
		if (framebufferID != 0) {
			glDeleteFramebuffers(framebufferID);
			for (int colorAttachment : colorAttachments) {
				glDeleteTextures(colorAttachment);
			}
			colorAttachments.clear();
		}

		framebufferID = glCreateFramebuffers();

		for (int i = 0; i < attachments.size(); i++) {
			int textureID = glCreateTextures(GL_TEXTURE_2D);

			switch (attachments.get(i)) {
				case RGBA8 -> {
					glTextureStorage2D(textureID, 1, GL_RGBA8, width, height);
					glTextureParameteri(textureID, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
					glTextureParameteri(textureID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
					glNamedFramebufferTexture(framebufferID, GL_COLOR_ATTACHMENT0 + i, textureID, 0);
				}
				case RED_INTEGER -> {
					glTextureStorage2D(textureID, 1, GL_R32I, width, height);
					glTextureParameteri(textureID, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
					glTextureParameteri(textureID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
					glNamedFramebufferTexture(framebufferID, GL_COLOR_ATTACHMENT0 + i, textureID, 0);
				}
			}

			colorAttachments.add(textureID);
		}

		int[] drawBuffers = new int[attachments.size()];
		for (int i = 0; i < attachments.size(); i++) {
			drawBuffers[i] = GL_COLOR_ATTACHMENT0 + i;
		}
		glNamedFramebufferDrawBuffers(framebufferID, drawBuffers);

		int status = glCheckNamedFramebufferStatus(framebufferID, GL_FRAMEBUFFER);
		if (status != GL_FRAMEBUFFER_COMPLETE) {
			Logger.error("Framebuffer({}) Incomplete({})", framebufferID, status);
		}
	}
}
