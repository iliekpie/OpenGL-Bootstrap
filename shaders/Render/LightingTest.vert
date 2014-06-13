#version 330 core

in vec4 in_Position;        //model space
in vec3 in_Normal;          //model space
in vec4 in_Color;
in vec2 in_UV;

out vec3 pass_Position;     //camera space
out vec3 pass_Normal;       //camera space
out vec4 pass_Color;
out vec2 pass_UV;

uniform mat4 MVP;           //homogeneous
uniform mat3 normalMatrix;  //model to camera space

void main(void) {
    gl_Position = MVP * in_Position;
    pass_Position = vec3(normalMatrix * in_Position.xyz);
    pass_Normal = vec3(normalMatrix * in_Normal);
    pass_UV = in_UV;
    pass_Color = in_Color;
}