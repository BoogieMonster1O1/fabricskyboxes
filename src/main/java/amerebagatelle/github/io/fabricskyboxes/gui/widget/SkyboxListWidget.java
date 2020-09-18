package amerebagatelle.github.io.fabricskyboxes.gui.widget;

import amerebagatelle.github.io.fabricskyboxes.SkyboxManager;
import amerebagatelle.github.io.fabricskyboxes.developer.DeveloperTools;
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

    public void selectEntry(AbstractSkybox skybox, Entry entry) {
        setSelected(entry);
        ensureVisible(entry);
        DeveloperTools.setDevelopSkybox(skybox);
    }

    public class SkyboxEntry extends SkyboxListWidget.Entry {
        public boolean isTextured;

        public SkyboxEntry(AbstractSkybox skybox) {
            super(skybox);
            isTextured = skybox instanceof TexturedSkybox;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
            if (isTextured) {
                textureManager.bindTexture(((TexturedSkybox) getSkybox()).TEXTURE_TOP);
                RenderUtils.drawTexturedBox(x, y, entryHeight, entryHeight, 0, 0, 1, 1);
            } else {
                MonoColorSkybox monoColorSkybox = (MonoColorSkybox) getSkybox();
                RenderUtils.drawBox(x, y, entryHeight, entryHeight, monoColorSkybox.red, monoColorSkybox.blue, monoColorSkybox.green, 1f);
            }
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            drawStringWithShadow(matrices, textRenderer, getSkybox().name, entryHeight + 20, y + 10, 16777215);
            drawStringWithShadow(matrices, textRenderer, isTextured ? "Textured" : "MonoColor", entryHeight + 20, y + 20, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            SkyboxListWidget.this.selectEntry(getSkybox(), this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<SkyboxListWidget.Entry> {
        private final AbstractSkybox skybox;

        public Entry(AbstractSkybox skybox) {
            this.skybox = skybox;
        }

        public AbstractSkybox getSkybox() {
            return skybox;
        }
    }
}
