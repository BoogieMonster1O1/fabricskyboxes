package amerebagatelle.github.io.fabricskyboxes.gui;

import amerebagatelle.github.io.fabricskyboxes.gui.widget.SkyboxListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ConfigScreen extends Screen {
    private SkyboxListWidget skyboxListWidget;

    public ConfigScreen() {
        super(new LiteralText("FS Config Screen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        skyboxListWidget = new SkyboxListWidget(client, width / 2 - 20, height - 50, 30, height - 20, 40, 10);
        addChild(skyboxListWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        renderBackground(matrices);
        skyboxListWidget.render(matrices, mouseX, mouseY, delta);
    }

}
