//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;

uniform float u_time;
uniform vec2 u_screenSize;

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;



void main() {
    vec2 uv = v_texCoord;
    uv.x = uv.x + 0.01 * u_time * u_time * sign(sin(uv.y * 150.0));
    uv.x = clamp(uv.x, 0.0, 1.0);
    gl_FragColor = texture2D(u_texture, uv);
}
