package pt.uma.tpsi.arqd.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pt.uma.tpsi.arqd.Main; // Certifique-se de importar Main corretamente

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Space Invaders");
        config.setWindowedMode(1280, 800);
        config.useVsync(true);
        config.setForegroundFPS(60);
        return config;
    }
}
