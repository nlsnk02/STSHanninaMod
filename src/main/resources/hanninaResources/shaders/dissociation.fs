//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform vec2 u_screenSize;
uniform vec2 u_size;
uniform vec2 u_pos;
uniform float u_time;


//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;


#define id vec2(0.0,1.0)
#define k 1103515245U
#define PI 3.141592653
#define TAU PI * 2.0


vec3 hash(uvec3 x) {
    x = ((x>>8U)^x.yzx)*k;
    x = ((x>>8U)^x.yzx)*k;
    x = ((x>>8U)^x.yzx)*k;
    return vec3(x)*(1.0/float(0xffffffffU));
}

vec2 trans(vec2 p){
    return (p * u_screenSize - u_pos) / u_size;
}
vec2 snart(vec2 p){
    return (p * u_size + u_pos) / u_screenSize;
}


void main(){
    vec2 screen = v_texCoord * u_screenSize;
    if(screen.x < u_pos.x || screen.x > u_pos.x + u_size.x || screen.y < u_pos.y || screen.y > u_pos.y + u_size.y){
        gl_FragColor = texture2D(u_texture, v_texCoord);
        return;
    }

    bool flag = false;
    bool flag2 = false;

    //picture offset
    float time = 2.0;
    float timeMod = 2.5;
    float repeatTime = 1.25;
    float lineSize = 50.0;
    float offsetMul = 0.01;
    float updateRate2 = 50.0;
    float uvyMul = 100.0;

    vec2 uv = trans(v_texCoord);

    float realSize = lineSize / u_size.y / 2.0;
    float position = mod(u_time, timeMod) / time;
    float position2 = 99.0;
    if (u_time > repeatTime) {
        position2 = mod(u_time - repeatTime, timeMod) / time;
    }
    if (!(uv.y - position > realSize || uv.y - position < -realSize)) {
        uv.x -= hash(uvec3(0.0, uv.y * uvyMul, u_time * updateRate2)).x * offsetMul;
        flag = true;
    } else if (position2 != 99.0) {
        if (!(uv.y - position2 > realSize || uv.y - position2 < -realSize)) {
            uv.x -= hash(uvec3(0.0, uv.y * uvyMul, u_time * updateRate2)).x * offsetMul;
            flag = true;
        }
    }

    vec4 col = texture2D(u_texture, snart(uv));

    float directions = 16.0;
    float quality = 3.0;
    float size = 4.0;

    vec2 radius = size / u_size.xy;
    for(float d = 0.0; d < TAU; d += TAU / directions) {
        for(float i= 1.0 / quality; i <= 1.0; i += 1.0 / quality) {
            col += texture2D(u_texture, snart(uv + vec2(cos(d), sin(d)) * radius * i));
        }
    }
    col /= quality * directions - 14.0;

    //for the black on the left
    if (uv.x < 0.0) {
        col = id.xxxy;
        flag = false;
        flag2 = true;
    }

    //randomized black shit and sploches
    float updateRate4 = 100.0;
    float uvyMul3 = 100.0;
    float cutoff2 = 0.92;
    float valMul2 = 0.007;

    float val2 = hash(uvec3(uv.y * uvyMul3, 0.0, u_time * updateRate4)).x;
    if (val2 > cutoff2) {
        float adjVal2 = (val2 - cutoff2) * valMul2 * (1.0 / (1.0 - cutoff2));
        if (uv.x < adjVal2) {
            col = id.xxxy;
            flag2 = true;
        } else {
            flag = true;
        }
    }

    //static
    if (!flag2) {
        float updateRate = 100.0;
        float mixPercent = 0.05;
        col = mix(col, vec4(hash(uvec3(uv * u_size.xy, u_time * updateRate)).rrr, 1.0), mixPercent);
    }

    //white sploches
    float updateRate3 = 75.0;
    float uvyMul2 = 400.0;
    float uvxMul = 20.0;
    float cutoff = 0.95;
    float valMul = 0.7;
    float falloffMul = 0.7;

    if (flag) {
        float val = hash(uvec3(uv.x * uvxMul, uv.y * uvyMul2, u_time * updateRate3)).x;
        if (val > cutoff) {
            float offset = hash(uvec3(uv.y * uvyMul2, uv.x * uvxMul, u_time * updateRate3)).x;
            float adjVal = (val - cutoff) * valMul * (1.0 / (1.0 - cutoff));
            adjVal -= abs((uv.x * uvxMul - (floor(uv.x * uvxMul) + offset)) * falloffMul);
            adjVal = clamp(adjVal, 0.0, 1.0);
            col = vec4(mix(col.rgb, id.yyy, adjVal), col.a);
        }
    }


    gl_FragColor = col;
}