package amerebagatelle.github.io.fabricskyboxes.developer;

import amerebagatelle.github.io.fabricskyboxes.skyboxes.AbstractSkybox;

public class DeveloperTools {
    public static boolean devMode = false;
    private static AbstractSkybox developSkybox;

    public static void onKeyPress(int key) {
        if (devMode && developSkybox != null) {

        }
    }

    public static void setDevelopSkybox(AbstractSkybox skybox) {
        developSkybox = skybox;
    }

    public static AbstractSkybox getDevelopSkybox() {
        return developSkybox;
    }
}
