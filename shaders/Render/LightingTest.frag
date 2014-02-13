#version 330 core

in vec3 pass_Normal;
in vec2 pass_UV;

out vec4 out_Color;

uniform sampler2D tSampler;

void main() {
    out_Color = texture(tSampler, pass_UV.st);
}