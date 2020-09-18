package amerebagatelle.github.io.fabricskyboxes.gui.widget;

import amerebagatelle.github.io.fabricskyboxes.SkyboxManager;
import amerebagatelle.github.io.fabricskyboxes.skyboxes.AbstractSkybox;
import amerebagatelle.github.io.fabricskyboxes.skyboxes.MonoColorSkybox;
import amerebagatelle.github.io.fabricskyboxes.skyboxes.TexturedSkybox;
import amerebagatelle.github.io.fabricskyboxes.util.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class SkyboxListWidget extends FSListWidget<SkyboxListWidget.Entry> {
    public SkyboxListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        setEntries(SkyboxManager.getInstance().getSkyboxes());
    }

    private void setEntries(List<AbstractSkybox> skyboxes) {
        clearEntries();
        for (AbstractSkybox skybox : skyboxes) {
            addEntry(new SkyboxEntry(skybox));
        }
    }

    @Override
    public int getRowWidth() {
        return width - 10;
    }

    public static class SkyboxEntry extends SkyboxListWidget.Entry {
        public AbstractSkybox skybox;
        public boolean isTextured;

        public SkyboxEntry(AbstractSkybox skybox) {
            this.skybox = skybox;
            isTextured = skybox instanceof TexturedSkybox;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
            if (isTextured) {
                textureManager.bindTexture(((TexturedSkybox) skybox).TEXTURE_TOP);
                RenderUtils.drawTexturedBox(x, y, entryHeight, entryHeight);
            } else {
                MonoColorSkybox monoColorSkybox = (MonoColorSkybox) skybox;
                RenderUtils.drawBox(x, y, entryHeight, entryHeight, monoColorSkybox.red, monoColorSkybox.blue, monoColorSkybox.green, 1f);
            }
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            drawStringWithShadow(matrices, textRenderer, skybox.name, entryHeight + 20, y + 10, 16777215);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<SkyboxListWidget.Entry> {
    }
}
