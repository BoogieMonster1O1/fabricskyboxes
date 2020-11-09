package io.github.amerebagatelle.fabricskyboxes.skyboxes.textured;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.amerebagatelle.fabricskyboxes.mixin.skybox.WorldRendererAccess;
import io.github.amerebagatelle.fabricskyboxes.skyboxes.AbstractSkybox;
import io.github.amerebagatelle.fabricskyboxes.skyboxes.SkyboxType;
import io.github.amerebagatelle.fabricskyboxes.util.object.Texture;
import io.github.amerebagatelle.fabricskyboxes.util.object.Conditions;
import io.github.amerebagatelle.fabricskyboxes.util.object.Decorations;
import io.github.amerebagatelle.fabricskyboxes.util.object.DefaultProperties;
import io.github.amerebagatelle.fabricskyboxes.util.object.Textures;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix4f;

public class SquareTexturedSkybox extends TexturedSkybox {
    public static Codec<SquareTexturedSkybox> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DefaultProperties.CODEC.fieldOf("properties").forGetter(AbstractSkybox::getDefaultProperties),
            Conditions.CODEC.optionalFieldOf("conditions", Conditions.NO_CONDITIONS).forGetter(AbstractSkybox::getConditions),
            Decorations.CODEC.optionalFieldOf("decorations", Decorations.DEFAULT).forGetter(AbstractSkybox::getDecorations),
            Codec.BOOL.fieldOf("blend").forGetter(TexturedSkybox::isBlend),
            Textures.CODEC.fieldOf("textures").forGetter(SquareTexturedSkybox::getTextures)
    ).apply(instance, SquareTexturedSkybox::new));
    public Textures textures;
    protected final List<Supplier<Texture>> suppliers = Util.make(() -> {
        ImmutableList.Builder<Supplier<Texture>> builder = ImmutableList.builder();
        builder.add(this::getBottomTex);
        builder.add(this::getNorthTex);
        builder.add(this::getSouthTex);
        builder.add(this::getTopTex);
        builder.add(this::getEastTex);
        builder.add(this::getWestTex);
        return builder.build();
    });

    public SquareTexturedSkybox() {
    }

    public SquareTexturedSkybox(DefaultProperties properties, Conditions conditions, Decorations decorations, boolean blend, Textures textures) {
        super(properties, conditions, decorations, blend);
        this.textures = textures;
    }

    @Override
    public SkyboxType<? extends AbstractSkybox> getType() {
        return SkyboxType.SQUARE_TEXTURED_SKYBOX;
    }

    @Override
    public void renderSkybox(WorldRendererAccess worldRendererAccess, MatrixStack matrices, float tickDelta) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        TextureManager textureManager = worldRendererAccess.getTextureManager();

        for (int i = 0; i < 6; ++i) {
            Texture tex = this.suppliers.get(i).get();
            matrices.push();

            // 0 = bottom
            // 1 = north
            // 2 = south
            // 3 = top
            // 4 = east
            // 5 = west

            textureManager.bindTexture(tex.getTextureId());

            if (i == 1) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            } else if (i == 2) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            } else if (i == 3) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
            } else if (i == 4) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
            } else if (i == 5) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
            }

            Matrix4f matrix4f = matrices.peek().getModel();
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).texture(tex.getMinU(), tex.getMinV()).color(1f, 1f, 1f, alpha).next();
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).texture(tex.getMinU(), tex.getMaxV()).color(1f, 1f, 1f, alpha).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).texture(tex.getMaxU(), tex.getMaxV()).color(1f, 1f, 1f, alpha).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).texture(tex.getMaxU(), tex.getMinV()).color(1f, 1f, 1f, alpha).next();
            tessellator.draw();
            matrices.pop();
        }
    }

    public Textures getTextures() {
        return this.textures;
    }

    protected Texture getNorthTex() {
        return new Texture(this.textures.getNorth());
    }

    protected Texture getSouthTex() {
        return new Texture(this.textures.getSouth());
    }

    protected Texture getEastTex() {
        return new Texture(this.textures.getEast());
    }

    protected Texture getWestTex() {
        return new Texture(this.textures.getWest());
    }

    protected Texture getTopTex() {
        return new Texture(this.textures.getTop());
    }

    protected Texture getBottomTex() {
        return new Texture(this.textures.getBottom());
    }
}
