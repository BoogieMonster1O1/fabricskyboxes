package io.github.amerebagatelle.fabricskyboxes.mixin.skybox;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccess {
    @Accessor
    TextureManager getTextureManager();

    @Accessor
    VertexFormat getSkyVertexFormat();

    @Accessor
    VertexBuffer getLightSkyBuffer();

    @Accessor
    VertexBuffer getStarsBuffer();

    @Accessor
    VertexBuffer getDarkSkyBuffer();

    @Accessor("SUN")
    static Identifier getSun() {
        throw new AssertionError();
    }

    @Accessor("MOON_PHASES")
    static Identifier getMoonPhases(){
        throw new AssertionError();
    }
}
