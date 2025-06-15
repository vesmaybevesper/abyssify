package vesper.vcc.mixin.effectualEffective;

import com.imeetake.effects.Bubbles.BubbleBreathEffect;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.ladysnake.effective.core.Effective;
import org.ladysnake.effective.core.utils.LinearForcedMotionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;
import vesper.vcc.Config;

@Mixin(BubbleBreathEffect.class)
public class BubbleBreathEffectMixin {
    @Unique
    private static final Random RANDOM = Random.create();
    @Inject(method = "spawnBubbleParticles", at = @At("HEAD"), cancellable = true)
    private static void replaceParticles(MinecraftClient client, PlayerEntity player, CallbackInfo ci){
        if (Config.EffectiveXEffectual && Config.useEffectiveBubbleBreath && FabricLoader.getInstance().isModLoaded("effective") && FabricLoader.getInstance().isModLoaded("effectual")) {
            World world = player.getWorld();
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
                WorldParticleBuilder.create(Effective.BUBBLE).enableForcedSpawn().setScaleData(GenericParticleData.create(0.05f + world.random.nextFloat() * 0.05f).build())
                        .setTransparencyData(GenericParticleData.create(1F).build())
                        .enableNoClip().setLifetime(60 + world.random.nextInt(60))
                        .addTickActor(new LinearForcedMotionImpl(new Vector3f((float) velocityX, (float) velocityY, (float) velocityZ), new Vector3f(0, 0, 0), 10f))
                        .setRenderType(ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT).spawn(world, x, y, z);
                //client.world.addParticle(effect, x, y, z, velocityX, velocityY, velocityZ);
            }
            ci.cancel();
        }
    }
}
