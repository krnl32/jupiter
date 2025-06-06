package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.window.WindowResizeEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;

public class Renderer {
	private final List<RenderCommand> commandQueue = new ArrayList<>();
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private Camera activeCamera;
	private final Shader shader;

	public Renderer() {
		setDepthTest(true);
		shader = new Shader((System.getProperty("user.dir") + "\\assets\\shaders\\quad_vertex.glsl"), (System.getProperty("user.dir") + "\\assets\\shaders\\quad_fragment.glsl"));

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			setViewPort(0, 0, event.getWidth(), event.getHeight());
		});
	}

	public void beginFrame() {
		shader.bind();
		commandQueue.clear();
		spriteBatch.begin();
	}

	public void endFrame() {
		shader.setMat4("u_Model", new Matrix4f().identity().translate(new Vector3f(0.0f, 0.0f, -30.0f)).scale(new Vector3f(5.0f, 5.0f, 5.0f)));
		if (activeCamera != null) {
			shader.setMat4("u_View", activeCamera.getViewMatrix());
			shader.setMat4("u_Projection", activeCamera.getProjectionMatrix());
		}

		for (var cmd: commandQueue)
			cmd.execute(this);
		spriteBatch.end();

		shader.unbind();
	}

	public void submit(RenderCommand cmd) {
		commandQueue.add(cmd);
	}

	public void drawSprite(Vector3f position, Sprite sprite) {
		spriteBatch.addSprite(position, sprite);
	}

	public void setActiveCamera(Camera activeCamera) {
		this.activeCamera = activeCamera;
	}

	public void setClearColor(Vector4f color) {
		glClearColor(color.x, color.y, color.z, color.w);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void setViewPort(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}

	public void setDepthTest(boolean state) {
		if (state)
			glEnable(GL_DEPTH_TEST);
		else
			glDisable(GL_DEPTH_TEST);
	}
}
