#version 330 core

uniform mat4 MVP;

in vec4 in_VertexPosition;
in vec3 in_Normal;
in vec4 in_Color;
in vec2 in_UV;

out vec4 pass_Color;

void main(void) {
    gl_Position = MVP * in_VertexPosition;
    pass_Color = in_Color;
}