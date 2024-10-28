package comboom.sucht.Jumping.Runner;

 public class MyColor extends com.badlogic.gdx.graphics.Color {

    // Constructor for RGBA values
    MyColor(float r, float g, float b, float a) {
        super(normalize(r), normalize(g), normalize(b),normalize(a));
    }

    // Constructor for RGB values, with default alpha
    MyColor(float r, float g, float b) {
        super(r, g, b, 1.0f); // Default alpha to 1.0
    }

    // Default constructor
    public MyColor() {super();}
    public MyColor(MyColor color) {super(color);}

    // Constructor from hex string (e.g., "#87fa56" or "#87fa5680")
    public MyColor(String hex) {
        super(hextoRGBA(hex));
    }

    // Constructor from HSL values
    public MyColor(float h, float s, float l, Float a) {
        super();
        float[] rgb = hslToRgb(h, s, l);
        this.r = normalize(rgb[0]);
        this.g = normalize(rgb[1]);
        this.b = normalize(rgb[2]);
        this.a = (a != null) ? normalize(a) : 1.0f; // Default alpha to 1 if not provided

    }

    // Normalize a float value to be within 0 and 1
    private static float normalize(float value) {
        return Math.max(0.0f, Math.min(value, 1.0f));
    }

    // Convert HSL to RGB
    private float[] hslToRgb(float h, float s, float l) {
        float r, g, b;

        if (s == 0) { // achromatic
            r = g = b = l; // gray
        } else {
            float q = l < 0.5 ? l * (1 + s) : l + s - (l * s);
            float p = 2 * l - q;
            r = hueToRgb(p, q, h + 1 / 3f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1 / 3f);
        }
        return new float[]{r, g, b};
    }

    // Helper function for HSL to RGB conversion
    private float hueToRgb(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1 / 6f) return p + (q - p) * 6 * t;
        if (t < 1 / 2f) return q;
        if (t < 2 / 3f) return p + (q - p) * (2 / 3f - t) * 6;
        return p;
    }

    // Convert hex to RGBA
    private static MyColor hextoRGBA(String hex) {
        MyColor color = new MyColor();
        if (hex.startsWith("#")) {
            hex = hex.substring(1); // Remove #
        }
        if (hex.length() == 6) {
            color.r = normalize(Integer.valueOf(hex.substring(0, 2), 16) / 255f);
            color.g = normalize(Integer.valueOf(hex.substring(2, 4), 16) / 255f);
            color.b = normalize(Integer.valueOf(hex.substring(4, 6), 16) / 255f);
            color.a = 1.0f; // Default alpha to 1
        } else if (hex.length() == 8) {
            color.r = normalize(Integer.valueOf(hex.substring(0, 2), 16) / 255f);
            color.g = normalize(Integer.valueOf(hex.substring(2, 4), 16) / 255f);
            color.b = normalize(Integer.valueOf(hex.substring(4, 6), 16) / 255f);
            color.a = normalize(Integer.valueOf(hex.substring(6, 8), 16) / 255f);
        } else {
            throw new IllegalArgumentException("Invalid hex format");
        }
        return color;
    }

    // Predefined Nord palette colors methods
    private static MyColor black() { return hextoRGBA("#30343F"); }
    private static MyColor red() { return hextoRGBA("#CF2A31"); }
    private static MyColor green() { return hextoRGBA("#7BBAA8"); }
    private static MyColor yellow() { return hextoRGBA("#EFBF6A"); }
    private static MyColor blue() { return hextoRGBA("#63A2CA"); }
    private static MyColor magenta() { return hextoRGBA("#C1AC9E"); }
    private static MyColor cyan() { return hextoRGBA("#F0C9D0"); }
    private static MyColor white() { return hextoRGBA("#EBEFF1"); }

    // Bright (high-intensity) colors
    private static MyColor brightBlack() { return hextoRGBA("#4D4E59"); }
    private static MyColor brightRed() { return hextoRGBA("#CF2A31"); }
    private static MyColor brightGreen() { return hextoRGBA("#7BBAA8"); }
    private static MyColor brightYellow() { return hextoRGBA("#EFBF6A"); }
    private static MyColor brightBlue() { return hextoRGBA("#63A2CA"); }
    private static MyColor brightMagenta() { return hextoRGBA("#BD8A9B"); }
    private static MyColor brightCyan() { return hextoRGBA("#F0C9D0"); }
    private static MyColor brightWhite() { return hextoRGBA("#EBEFF1"); }

    // Returns an array of all predefined colors
    private static MyColor[] getAllColors() {
        return new MyColor[]{
            black(), red(), green(), yellow(), blue(),
            magenta(), cyan(), white(),
            brightBlack(), brightRed(), brightGreen(), brightYellow(),
            brightBlue(), brightMagenta(), brightCyan(), brightWhite()
        };
    }
     // Predefined Nord palette colors
    static final public MyColor BLACK = black();
    static final public MyColor RED = red();
    static final public MyColor GREEN = green();
    static final public MyColor YELLOW = yellow();
    static final public MyColor BLUE = blue();
    static final public MyColor MAGENTA = magenta();
    static final public MyColor CYAN = cyan();
    static final public MyColor WHITE = white();
     // Bright (high-intensity) colors
    static final public MyColor BRIGHT_BLACK = brightBlack();
    static final public MyColor BRIGHT_RED = brightRed();
    static final public MyColor BRIGHT_GREEN = brightGreen();
    static final public MyColor BRIGHT_YELLOW = brightYellow();
    static final public MyColor BRIGHT_BLUE = brightBlue();
    static final public MyColor BRIGHT_MAGENTA = brightMagenta();
    static final public MyColor BRIGHT_CYAN = brightCyan();
    static final public MyColor BRIGHT_WHITE = brightWhite();
     // array of all predefined colors
    static final public MyColor[] ALL_COLORS = getAllColors();
}
