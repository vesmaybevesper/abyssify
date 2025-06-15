package vesper.vcc.mixin.effectualEffective;

import com.imeetake.EffectualClient;
import com.imeetake.effects.Bubbles.BubblePotsEffect;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;
import org.ladysnake.effective.core.Effective;
import org.ladysnake.effective.core.utils.LinearForcedMotionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;
import vesper.vcc.Config;

@Mixin(BubblePotsEffect.class)
public class PotBubblesMixin {
    @Inject(method = "register", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void overrideRegister(CallbackInfo ci){
        final Random RANDOM = Random.create();
        if (Config.EffectiveXEffectual && Config.useEffectiveBubblePot && FabricLoader.getInstance().isModLoaded("effective") && FabricLoader.getInstance().isModLoaded("effectual")){
            ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(client) -> {
                if (EffectualClient.CONFIG.bubblePots()) {
                    if (client.world != null) {
                        ClientWorld world = client.world;
                        assert client.player != null;
                        BlockPos playerPos = client.player.getBlockPos();
                        int radius = 5;

                        for(BlockPos pos : BlockPos.iterate(playerPos.add(-radius, -radius, -radius), playerPos.add(radius, radius, radius))) {
                            if (world.getBlockState(pos).isOf(Blocks.DECORATED_POT)) {
                                world.getBlockEntity(pos);
                                if (world.getFluidState(pos).isStill() && RANDOM.nextInt(15) == 0) {
                                    WorldParticleBuilder.create(Effective.BUBBLE).enableForcedSpawn().setScaleData(GenericParticleData.create(0.05f + world.random.nextFloat() * 0.05f).build())
                                            .setTransparencyData(GenericParticleData.create(1f).build())
                                            .enableNoClip().setLifetime(60)
                                            .addTickActor(new LinearForcedMotionImpl(new Vector3f(0f, 0.35f, 0f), new Vector3f(0, 0.1f, 0), 5))
                                            .setRenderType(ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT).spawn(world, pos.getX() + 0.5f, pos.getY() + 1.3, pos.getZ() + 0.5f);
                                    // world.addParticle(data, (double)pos.getX() + (double)0.5F, (double)pos.getY() + 1.3, (double)pos.getZ() + (double)0.5F, (double)0.0F, 0.1, (double)0.0F);
                                }
                            }
                        }

                    }
                }
            });
            ci.cancel();
        }
    }
}
