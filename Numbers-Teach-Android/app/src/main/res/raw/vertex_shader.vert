/*coordinates offset*/
uniform float x_offset;
uniform float y_offset;
uniform float z_offset;
/*scale portions*/
uniform float x_scale;
uniform float y_scale;
uniform float z_scale;


uniform mat4 u_mvpMatrix;
attribute vec4 a_position;
attribute vec2 a_tex_coordinate;
varying vec2 v_texCoord;

void main()                  
{                            
   gl_Position = u_mvpMatrix * a_position;
   gl_Position.x += x_offset;
   gl_Position.x *= x_scale;
   gl_Position.y += y_offset;
   gl_Position.y *= y_scale;
   gl_Position.z += z_offset;
   gl_Position.z *= z_scale;

   v_texCoord = a_tex_coordinate;
}