package amerebagatelle.github.io.fabricskyboxes.developer;

import amerebagatelle.github.io.fabricskyboxes.skyboxes.AbstractSkybox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class DeveloperTools {
    public static boolean devMode = false;
    private static AbstractSkybox developSkybox;

    public static void onKeyPress(int key) {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        if (isDevMode()) {
            switch (key) {
                case GLFW.GLFW_KEY_P:
                    developSkybox.transitionSpeed += 0.1f;
                    client.player.sendMessage(new LiteralText("Transition Speed: " + String.format("%.1f", developSkybox.transitionSpeed)), true);
                    break;

                case GLFW.GLFW_KEY_O:
                    developSkybox.transitionSpeed -= 0.1f;
                    client.player.sendMessage(new LiteralText("Transition Speed: " + String.format("%.1f", developSkybox.transitionSpeed)), true);
                    break;
            }
        }
    }

    public static void setDevelopSkybox(AbstractSkybox skybox) {
        developSkybox = skybox;
    }

    public static boolean isDevMode() {
        return devMode && developSkybox != null;
    }

    public static AbstractSkybox getDevelopSkybox() {
        return developSkybox;
    }
}
