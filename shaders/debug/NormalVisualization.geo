#version 330 core

layout (points) in;
layout (line_strip, max_vertices = 2) out;

in Vertex
{
    vec3 normal;
    vec3 color;
} vertex[];

uniform mat4 MVP;
const float normal_scale = 0.1;

out vec3 vertex_color;

void main()
{
    vec3 P = gl_in[0].gl_Position.xyz;
    vec3 N = vertex[0].normal;

    gl_Position = MVP * vec4(P, 1.0);
    vertex_color = vec3(0.0);
    EmitVertex();

    gl_Position = MVP * vec4(P + N * normal_scale, 1.0);
    vertex_color = vec3(1.0);
    EmitVertex();

    EndPrimitive();
}