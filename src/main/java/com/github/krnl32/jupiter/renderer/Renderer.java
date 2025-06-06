package com.github.krnl32.jupiter.renderer;

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
	private Camera camera;
	private Shader shader;

	// temp
	RendererCamera rendererCamera;

	public Renderer() {
		shader = new Shader((System.getProperty("user.dir") + "\\assets\\shaders\\quad_vertex.glsl"), (System.getProperty("user.dir") + "\\assets\\shaders\\quad_fragment.glsl"));

		// temp
		setDepthTest(true);
		rendererCamera = new RendererCamera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0, 4, 1);
	}

	public void beginFrame() {
		setClearColor(new Vector4f(1, 1, 0, 1));
		shader.bind();
		// shader set camera uniform
		commandQueue.clear();
		spriteBatch.begin();
	}

	public void endFrame() {
		// tmp
		Matrix4f model = new Matrix4f().identity();
		model.translate(new Vector3f(0.0f, 0.0f, -30.0f)).rotate((45 * (3.14f/180.0f)), new Vector3f(0.0f, 0.0f, 0.0f)).scale(new Vector3f(5.0f, 5.0f, 5.0f));

		rendererCamera.onUpdate(1);

		Matrix4f projection = new Matrix4f().identity();
		//projection.ortho(-10, 10, -10, 10, -1, 1);
		projection.perspective(45.0f, (640/480), 0.1f, 100.0f);

		shader.setMat4("u_Model", model);
		shader.setMat4("u_View", rendererCamera.getViewMatrix());
		shader.setMat4("u_Projection", projection);

		setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		clear();
		//^^^TEMP

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

	public void setCamera(Camera camera) {
		this.camera = camera;
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
