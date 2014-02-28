#version 330 core

in vec2 pass_UV;

out vec4 out_Color;

uniform sampler2D tSampler;

void main() {
    //out_Color = texture(tSampler, pass_UV.st);
    out_Color = vec4(pass_UV.s, pass_UV.t, 0, 1);
}