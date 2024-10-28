package comboom.sucht.Jumping.Runner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.HashMap;
import java.util.Map;

public class Main extends Game {

    public SpriteBatch batch;
    public FitViewport viewport;
    private Map<String, BitmapFont> fonts;

    public void create() {
        batch = new SpriteBatch();

        // Set up the viewport
        viewport = new FitViewport(1920, 1080);

        // Load all fonts
        loadFonts();

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        // Dispose all fonts
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
    }

    private void loadFonts() {
        fonts = new HashMap<>(); // Initialize the map to store fonts

        // Array of font file names
        String[] fontFiles = {
            "CaskaydiaCoveNerdFontPropo-Bold.ttf",
            "CaskaydiaCoveNerdFontPropo-BoldItalic.ttf",
            "CaskaydiaCoveNerdFontPropo-ExtraLight.ttf",
            "CaskaydiaCoveNerdFontPropo-ExtraLightItalic.ttf",
            "CaskaydiaCoveNerdFontPropo-Italic.ttf",
            "CaskaydiaCoveNerdFontPropo-Light.ttf",
            "CaskaydiaCoveNerdFontPropo-LightItalic.ttf",
            "CaskaydiaCoveNerdFontPropo-Regular.ttf",
            "CaskaydiaCoveNerdFontPropo-SemiBold.ttf",
            "CaskaydiaCoveNerdFontPropo-SemiBoldItalic.ttf",
            "CaskaydiaCoveNerdFontPropo-SemiLight.ttf",
            "CaskaydiaCoveNerdFontPropo-SemiLightItalic.ttf"
        };

        // Load each font and store it in the map
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24; // Set the desired font size

        for (String fontFile : fontFiles) {
            generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + fontFile));
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose(); // Dispose of the generator after creating the font
            fonts.put(fontFile, font); // Store the font in the map
        }
    }

    // Method to retrieve a font by name
    public BitmapFont getFont(String fontName) {
        return fonts.get(fontName);
    }
}
