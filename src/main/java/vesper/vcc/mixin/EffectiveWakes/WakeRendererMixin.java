package vesper.vcc.mixin.EffectiveWakes;

import com.goby56.wakes.simulation.Brick;
import com.goby56.wakes.simulation.WakeNode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.BiomeKeys;
import org.ladysnake.effective.core.EffectiveConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vesper.vcc.Config;


@Mixin(value = Brick.class, remap = false)
public abstract class WakeRendererMixin {
    @Unique
    private boolean shouldModifyColor = false;
    @Unique
    private BlockPos currentPos;
    @Unique
    private WakeNode node;

    @Inject(method = "populatePixels", at = @At(value = "HEAD"))
    private void beforePopPixels(CallbackInfo ci) {
        if (Config.EffectiveXWakes && Config.WakeRenderMixin && FabricLoader.getInstance().isModLoaded("wakes") && FabricLoader.getInstance().isModLoaded("effective")) {
            ClientWorld world = MinecraftClient.getInstance().world;
            if (world == null) return;

            Brick self = (Brick) (Object) this;
            currentPos = new BlockPos((int) self.pos.x, (int) self.pos.y, (int) self.pos.z);

            if (EffectiveConfig.glowingPlankton && world.isNight() && world.getBiome(currentPos).matchesKey(BiomeKeys.WARM_OCEAN)) {
                shouldModifyColor = true;
            }
        } else {
            shouldModifyColor = false;
        }
    }


    @ModifyVariable(method = "populatePixels", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private int modifyColor(int original){
        if (shouldModifyColor && node != null){
            ClientWorld world = MinecraftClient.getInstance().world;
            if (world == null) return original;

            Brick self = (Brick) (Object) this;
            WakeNode node = self.get(currentPos.getX(), currentPos.getZ());

            float blockLight = world.getLightLevel(LightType.BLOCK, node.blockPos()) / 15f;
            float age = Math.min(1.0f, node.age / 5f);
            float rgRender = age + blockLight;

            int alpha = (original >> 24) & 0xFF;
            int blue = original & 0xFF;

            int newRed = (int) (rgRender * 255);
            int newGreen = (int) (rgRender * 255);

            return (alpha << 24) | (newRed << 16) | (newGreen << 8) | blue;
        }
        return original;
    }

}


