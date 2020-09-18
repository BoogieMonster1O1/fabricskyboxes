package amerebagatelle.github.io.fabricskyboxes.gui.widget;

import amerebagatelle.github.io.fabricskyboxes.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FSButtonWidget extends ButtonWidget {
    public FSButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float darkness = this.isHovered() ? 0.3f : 0.1f;
        RenderUtils.drawBox(this.x, this.y, this.width, this.height, darkness, darkness + 0.2f, darkness, 0.3f);
        drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, this.getMessage().asString(), this.x + (this.width / 2), this.y + (this.height / 2) - 5, active ? 16777215 : 10526880);
    }

    public int getBottom() {
        return y + height;
    }
}
