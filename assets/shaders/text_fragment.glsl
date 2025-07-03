#version 460 core

out vec4 o_Color;

in vec4 v_Color;
in vec2 v_TextureUV;
in flat float v_TextureID;

uniform sampler2D u_Textures[32];

void main()
{
	vec4 color = v_Color;
	vec4 texSample = vec4(1.0);

	switch(int(v_TextureID))
	{
		case  0: texSample = texture(u_Textures[0], v_TextureUV); break;
		case  1: texSample = texture(u_Textures[1], v_TextureUV); break;
		case  2: texSample = texture(u_Textures[2], v_TextureUV); break;
		case  3: texSample = texture(u_Textures[3], v_TextureUV); break;
		case  4: texSample = texture(u_Textures[4], v_TextureUV); break;
		case  5: texSample = texture(u_Textures[5], v_TextureUV); break;
		case  6: texSample = texture(u_Textures[6], v_TextureUV); break;
		case  7: texSample = texture(u_Textures[7], v_TextureUV); break;
		case  8: texSample = texture(u_Textures[8], v_TextureUV); break;
		case  9: texSample = texture(u_Textures[9], v_TextureUV); break;
		case 10: texSample = texture(u_Textures[10], v_TextureUV); break;
		case 11: texSample = texture(u_Textures[11], v_TextureUV); break;
		case 12: texSample = texture(u_Textures[12], v_TextureUV); break;
		case 13: texSample = texture(u_Textures[13], v_TextureUV); break;
		case 14: texSample = texture(u_Textures[14], v_TextureUV); break;
		case 15: texSample = texture(u_Textures[15], v_TextureUV); break;
		case 16: texSample = texture(u_Textures[16], v_TextureUV); break;
		case 17: texSample = texture(u_Textures[17], v_TextureUV); break;
		case 18: texSample = texture(u_Textures[18], v_TextureUV); break;
		case 19: texSample = texture(u_Textures[19], v_TextureUV); break;
		case 20: texSample = texture(u_Textures[20], v_TextureUV); break;
		case 21: texSample = texture(u_Textures[21], v_TextureUV); break;
		case 22: texSample = texture(u_Textures[22], v_TextureUV); break;
		case 23: texSample = texture(u_Textures[23], v_TextureUV); break;
		case 24: texSample = texture(u_Textures[24], v_TextureUV); break;
		case 25: texSample = texture(u_Textures[25], v_TextureUV); break;
		case 26: texSample = texture(u_Textures[26], v_TextureUV); break;
		case 27: texSample = texture(u_Textures[27], v_TextureUV); break;
		case 28: texSample = texture(u_Textures[28], v_TextureUV); break;
		case 29: texSample = texture(u_Textures[29], v_TextureUV); break;
		case 30: texSample = texture(u_Textures[30], v_TextureUV); break;
		case 31: texSample = texture(u_Textures[31], v_TextureUV); break;
	}

	o_Color = vec4(color.rgb, color.a * texSample.r);
}
