package com.github.krnl32.jupiter.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
	private final List<RenderCommand> commandQueue = new ArrayList<>();
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private Camera camera;
	private Shader shader;

	// temp
	private int vao, vbo, ibo, totalIndices;

	public Renderer() {
		shader = new Shader((System.getProperty("user.dir") + "\\assets\\shaders\\quad_vertex.glsl"), (System.getProperty("user.dir") + "\\assets\\shaders\\quad_fragment.glsl"));
	}

	public void beginFrame() {
		setClearColor(new Vector4f(1, 1, 0, 1));
		shader.bind();
		// shader set camera uniform
		commandQueue.clear();
		spriteBatch.begin();


		float[] vertices = {
			-1.0f, -1.0f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // V0->BOTLEFT (Front)
			1.0f, -1.0f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f, // V1->BOTRIGHT (Front
			1.0f,  1.0f, 0.0f,     1.0f, 0.0f, 1.0f, 1.0f, // V2->TOPRIGHT (Front
			-1.0f,  1.0f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f, // V3->TOPLEFT (Front)
		};

		int[] indices = {
			0, 1, 2, // Front Face
			0, 3, 2,
		};
		totalIndices = indices.length;

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, (7 * Float.BYTES), 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, 4, GL_FLOAT, false, (7 * Float.BYTES), (3 * Float.BYTES));
		glEnableVertexAttribArray(1);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void endFrame() {
		for (var cmd: commandQueue)
			cmd.execute(this);
		spriteBatch.render();

		Matrix4f model = new Matrix4f().identity();
		model.translate(new Vector3f(2.0f, 0.0f, 0.0f)).rotate((45 * (3.14f/180.0f)), new Vector3f(0.0f, 0.0f, 1.0f)).scale(new Vector3f(5.0f, 5.0f, 1.0f));

		Matrix4f view = new Matrix4f().identity();

		Matrix4f projection = new Matrix4f().identity();
		projection.ortho(-10, 10, -10, 10, -1, 1);

		shader.setMat4("u_Model", model);
		shader.setMat4("u_View", view);
		shader.setMat4("u_Projection", projection);

		setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		clear();

		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, totalIndices, GL_UNSIGNED_INT, NULL);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);


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
}
