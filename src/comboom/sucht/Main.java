package comboom.sucht;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Properties;

public class Main {

    // The window handle
    private long window;

    // Color management variables
    private Color COLOR = new Color();
    private Color[] colorPalette = COLOR.getAllColors();
    private int currentColorIndex = 0;

    // FPS tracking variables
    private long lastTime;
    private int frames = 0;

    public void run(String title) {
        System.out.println("LWJGL: " + Version.getVersion());

        // Printing renderer information after OpenGL is initialized
        init(title);

        // Print the OpenGL Renderer details
        System.out.println("Using OpenGL Renderer: " + glGetString(GL_RENDERER));
        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("Vendor: " + glGetString(GL_VENDOR));

        // Printing system information
        printSystemInfo();

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(String title) {
        // Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); 
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(300, 300, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key and mouse callback.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                changeColor();  // Change color on any key press
            }
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                changeColor();  // Change color on mouse click
            }
        });

        // Center the window
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); 
            IntBuffer pHeight = stack.mallocInt(1); 

            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); 

        // Make the window visible
        glfwShowWindow(window);

        // Create OpenGL capabilities to initialize OpenGL context
        GL.createCapabilities();
    }

    private void loop() {
        // Set initial clear color
        setClearColor(colorPalette[currentColorIndex]);

        lastTime = System.currentTimeMillis();

        // Main loop
        while (!glfwWindowShouldClose(window)) {
            tick();
            render();
        }
        
    }

    // Changes the current color by cycling through the palette
    private void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % colorPalette.length;
        setClearColor(colorPalette[currentColorIndex]);
    }

    // Helper function to set the clear color
    private void setClearColor(Color color) {
        glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    // Tick function to manage updates (e.g., input handling)
    private void tick() {
        // FPS tracking
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            System.out.println("FPS: " + frames);
            frames = 0;
            lastTime = currentTime;
        }
    }

    // Render function to handle drawing operations
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the framebuffer

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // Method to print system information
    private void printSystemInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        // OS Name, Version, and Architecture
        System.out.println("OS Name: " + osBean.getName());
        System.out.println("OS Version: " + osBean.getVersion());
        System.out.println("Architecture: " + osBean.getArch());

        // Java system properties
        Properties properties = System.getProperties();
        System.out.println("Java Version: " + properties.getProperty("java.version"));
        System.out.println("Java Vendor: " + properties.getProperty("java.vendor"));

        // If it's a Linux-based system, check for kernel and distro info
        if (osBean.getName().toLowerCase().contains("linux")) {
            try {
                String kernelVersion = System.getProperty("os.version");
                System.out.println("Linux Kernel Version: " + kernelVersion);

                // Get distribution information
                Process proc = Runtime.getRuntime().exec("lsb_release -a");
                java.io.BufferedReader stdInput = new java.io.BufferedReader(new 
                      java.io.InputStreamReader(proc.getInputStream()));
                String s;
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (Exception e) {
                System.out.println("Error retrieving Linux distribution information: " + e.getMessage());
            }
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        new Main().run("Jumping Runner");
    }

    // Getters and Setters for all variables

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public Color[] getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(Color[] colorPalette) {
        this.colorPalette = colorPalette;
    }

    public int getCurrentColorIndex() {
        return currentColorIndex;
    }

    public void setCurrentColorIndex(int currentColorIndex) {
        this.currentColorIndex = currentColorIndex;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public Color getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(Color COLOR) {
        this.COLOR = COLOR;
    }
}