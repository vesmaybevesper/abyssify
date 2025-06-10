package vesper.vcc.mixin.effectualEffective;

import com.imeetake.effects.Bubbles.BubbleBreathEffect;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import org.ladysnake.effective.core.Effective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;
import vesper.vcc.Config;
import vesper.vcc.Util;

@Mixin(BubbleBreathEffect.class)
public class BubbleBreathEffectMixin {
    @Unique
    private static final net.minecraft.util.math.random.Random RANDOM = Random.create();
    @Inject(method = "spawnBubbleParticles", at = @At("HEAD"), cancellable = true)
    private static void replaceParticles(MinecraftClient client, PlayerEntity player, CallbackInfo ci){
        if (Config.EffectiveXEffectual && Config.useEffectiveBubbleBreath && FabricLoader.getInstance().isModLoaded("effective") && FabricLoader.getInstance().isModLoaded("effectual")) {
            double x = player.getX();
            double y = player.getEyeY() - 0.2;
            double z = player.getZ();
            float yaw = player.getYaw() * ((float)Math.PI / 180F);
            double offsetForward = 0.3;
            x += -Math.sin(yaw) * offsetForward;
            z += Math.cos(yaw) * offsetForward;

            for(int i = 0; i < 3; ++i) {
                double velocityX = (RANDOM.nextDouble() - (double)0.5F) * 0.02;
                double velocityY = 0.1 + RANDOM.nextDouble() * 0.05;
                double velocityZ = (RANDOM.nextDouble() - (double)0.5F) * 0.02;
                assert client.world != null;
                ParticleEffect effect = new WorldParticleOptions(Effective.BUBBLE);
                client.world.addParticle(effect, x, y, z, velocityX, velocityY, velocityZ);
            }
            ci.cancel();
        }
    }
}
