/**
 * 
 */
package comboom.sucht;

/**
 * 
 */
final class Color {
    private float r, g, b, a = 1.0f; // Default alpha is 1.0

    // Constructor for RGBA values
    Color(float r, float g, float b, float a) {
        this.r = normalize(r);
        this.g = normalize(g);
        this.b = normalize(b);
        this.a = normalize(a);
    }

    // Constructor for RGB values, with default alpha
    Color(float r, float g, float b) {
        this(r, g, b, 1.0f); // Default alpha to 1.0
    }

    // Default constructor
    public Color() {}

    // Constructor from hex string (e.g., "#87fa56" or "#87fa5680")
    public Color(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1); // Remove #
        }
        if (hex.length() == 6) {
            this.r = normalize(Integer.valueOf(hex.substring(0, 2), 16) / 255f);
            this.g = normalize(Integer.valueOf(hex.substring(2, 4), 16) / 255f);
            this.b = normalize(Integer.valueOf(hex.substring(4, 6), 16) / 255f);
            this.a = 1.0f; // Default alpha to 1
        } else if (hex.length() == 8) {
            this.r = normalize(Integer.valueOf(hex.substring(0, 2), 16) / 255f);
            this.g = normalize(Integer.valueOf(hex.substring(2, 4), 16) / 255f);
            this.b = normalize(Integer.valueOf(hex.substring(4, 6), 16) / 255f);
            this.a = normalize(Integer.valueOf(hex.substring(6, 8), 16) / 255f);
        } else {
            throw new IllegalArgumentException("Invalid hex format");
        }
    }

    // Constructor from HSL values
    public Color(float h, float s, float l, Float a) {
        float[] rgb = hslToRgb(h, s, l);
        this.r = normalize(rgb[0]);
        this.g = normalize(rgb[1]);
        this.b = normalize(rgb[2]);
        this.a = (a != null) ? normalize(a) : 1.0f; // Default alpha to 1 if not provided
    }

    // Normalize a float value to be within 0 and 1
    private float normalize(float value) {
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

 // Predefined Nord palette colors of Ascii Colors (defined using hex strings)
    public static Color black() { return new Color("#30343F"); }  // Nord0
    public static Color red() { return new Color("#CF2A31"); }    // Nord11
    public static Color green() { return new Color("#7BBAA8"); }  // Nord14
    public static Color yellow() { return new Color("#EFBF6A"); } // Nord13
    public static Color blue() { return new Color("#63A2CA"); }   // Nord9
    public static Color magenta() { return new Color("#C1AC9E"); } // Nord15
    public static Color cyan() { return new Color("#F0C9D0"); }   // Nord8
    public static Color white() { return new Color("#EBEFF1"); }  // Nord6

    // Bright (high-intensity) colors
    public static Color brightBlack() { return new Color("#4D4E59"); } // Nord1
    public static Color brightRed() { return new Color("#CF2A31"); }   // Nord11 (same as red)
    public static Color brightGreen() { return new Color("#7BBAA8"); } // Nord14 (same as green)
    public static Color brightYellow() { return new Color("#EFBF6A"); } // Nord13 (same as yellow)
    public static Color brightBlue() { return new Color("#63A2CA"); }   // Nord9 (same as blue)
    public static Color brightMagenta() { return new Color("#BD8A9B"); } // Nord15 (slight variation)
    public static Color brightCyan() { return new Color("#F0C9D0"); }   // Nord7
    public static Color brightWhite() { return new Color("#EBEFF1"); }  // Nord6 (same as white)

    // Returns an array of all predefined colors
    public static Color[] getAllColors() {
        return new Color[]{
            black(), red(), green(), yellow(), blue(),
            magenta(), cyan(), white(),
            brightBlack(), brightRed(), brightGreen(), brightYellow(),
            brightBlue(), brightMagenta(), brightCyan(), brightWhite()
        };
    }

	/**
	 * @return the r
	 */
	public synchronized float getR() {
		return this.r;
	}

	/**
	 * @param r the r to set
	 */
	public synchronized void setR(float r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public synchronized float getG() {
		return this.g;
	}

	/**
	 * @param g the g to set
	 */
	public synchronized void setG(float g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public synchronized float getB() {
		return this.b;
	}

	/**
	 * @param b the b to set
	 */
	public synchronized void setB(float b) {
		this.b = b;
	}

	/**
	 * @return the a
	 */
	public synchronized float getA() {
		return this.a;
	}

	/**
	 * @param a the a to set
	 */
	public synchronized void setA(float a) {
		this.a = a;
	}
}