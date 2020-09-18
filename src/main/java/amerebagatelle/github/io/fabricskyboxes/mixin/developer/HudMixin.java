package amerebagatelle.github.io.fabricskyboxes.mixin.developer;

import amerebagatelle.github.io.fabricskyboxes.developer.DeveloperTools;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class HudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void renderDevOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (DeveloperTools.isDevMode())
            MinecraftClient.getInstance().textRenderer.draw(matrices, "Current Skybox: " + DeveloperTools.getDevelopSkybox().name, 10, 10, 16777215);
    }
}
