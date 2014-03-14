#version 330 core

in vec4 vi_Position;
in vec3 vi_Normal;
in vec2 vi_UV;

out Vertex
{
    vec3 normal;
    vec3 color;
} vertexData;

void main()
{
    gl_Position = vi_Position;
    vertexData.normal = vi_Normal;
    vertexData.color = vec3(1.0, 1.0, 0.0);
}