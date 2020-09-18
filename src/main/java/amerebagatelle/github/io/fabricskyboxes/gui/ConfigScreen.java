package amerebagatelle.github.io.fabricskyboxes.gui;

import amerebagatelle.github.io.fabricskyboxes.developer.DeveloperTools;
import amerebagatelle.github.io.fabricskyboxes.gui.widget.FSButtonWidget;
import amerebagatelle.github.io.fabricskyboxes.gui.widget.SkyboxListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ConfigScreen extends Screen {
    private SkyboxListWidget skyboxListWidget;

    private FSButtonWidget devModeButton;

    public ConfigScreen() {
        super(new LiteralText("FS Config Screen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        skyboxListWidget = new SkyboxListWidget(client, width / 2 - 20, height - 50, 30, height - 20, 40, 10);
        addChild(skyboxListWidget);
        devModeButton = this.addButton(new FSButtonWidget(skyboxListWidget.getRight() + 10, skyboxListWidget.getTop(), 120, 20, new LiteralText(""), press -> {
            this.toggleDevMode();
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtons();
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredString(matrices, textRenderer, "FabricSkyboxes Config", width / 2, 10, 16777215);
        skyboxListWidget.render(matrices, mouseX, mouseY, delta);
    }

    private void toggleDevMode() {
        DeveloperTools.devMode = !DeveloperTools.devMode;
    }

    private void updateButtons() {
        devModeButton.setMessage(new LiteralText("Developer Mode: " + (DeveloperTools.devMode ? "On" : "Off")));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
