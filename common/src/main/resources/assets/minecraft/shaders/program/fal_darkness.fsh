#version 150

uniform sampler2D DiffuseSampler;
uniform float FALDarknessProgress;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 defaultColour = texture2D(DiffuseSampler, texCoord);

    float brightness = (defaultColour.r + defaultColour.g + defaultColour.b) / 3.0;
    vec4 desaturated = vec4(brightness, brightness, brightness, 1.0);

    if (brightness < FALDarknessProgress) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
    } else {
        vec4 col = defaultColour * (1 - FALDarknessProgress) +
                   desaturated * FALDarknessProgress;

        float easeInLength = FALDarknessProgress / 4;

        float f = clamp((brightness - FALDarknessProgress) / easeInLength, 0.0, 1.0);
        col *= f;
        fragColor = vec4(col.r, col.g, col.b, 1.0);
    }
}
