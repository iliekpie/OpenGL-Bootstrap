#version 330 core

uniform mat4 MVP;

in vec4 in_VertexPosition;
in vec3 in_Normal;
in vec2 in_UV;

out vec3 pass_Normal;
out vec2 pass_UV;

void main(void) {
    gl_Position = MVP * in_VertexPosition;
    pass_Normal = in_Normal;
    pass_UV = in_UV;
}