#version 330 core
precision mediump float;    //don't need as high of a precision

in vec3 pass_Position;      //camera space
in vec3 pass_Normal;        //camera space
in vec4 pass_Color;
in vec2 pass_UV;

out vec4 out_Color;

uniform sampler2D tSampler;
uniform vec3 lightPos;      //camera space

void main() {
    //move this out of the fragment shader?
    float distance = length(lightPos - pass_Position);
    vec3 lightDir = normalize(lightPos - pass_Position);
    float diffuse = max(dot(pass_Normal, lightDir), 0.1);

    //Attenuation (distance falloff), Ambient lighting
    //constant attenuation of 1.0 + quadratic of 0.25
    diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));
    diffuse = clamp(diffuse + 0.5, 0, 1);

    //out_Color = vec4(vec3(0.5, 0.5, 0.5)*diffuse, 1);
    out_Color = texture(tSampler, pass_UV.st) * diffuse + pass_Color;
}