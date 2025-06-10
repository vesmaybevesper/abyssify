package vesper.vcc.mixin.effectualEffective;

import com.imeetake.effects.WaterDrip.WaterDripEffect;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;
import org.ladysnake.effective.core.Effective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vesper.vcc.Config;

@Mixin(WaterDripEffect.class)
public class WaterDripEffectMixin {
    @Unique
    private static final Random RANDOM = Random.create();

    @Inject(method = "spawnWaterDripParticles", at = @At("HEAD"), cancellable = true)
    private static void redirectParticle(ClientWorld world, PlayerEntity player, CallbackInfo ci){
        if (Config.EffectiveXEffectual && Config.useEffectiveDroplet && FabricLoader.getInstance().isModLoaded("effective") && FabricLoader.getInstance().isModLoaded("effectual")) {
            if (RANDOM.nextInt(5) == 0) {
                double offsetX = (double)RANDOM.nextFloat() * 0.4 - (double)0.25F;
                double offsetY = (double)RANDOM.nextFloat() * 0.8 + (double)1.0F;
                double offsetZ = (double)RANDOM.nextFloat() * 0.4 - (double)0.25F;
                world.addParticle(Effective.DROPLET, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 0.0F, -0.02, 0.0F);
            }
            ci.cancel();
        }
    }
}
