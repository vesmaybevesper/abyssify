package vesper.vcc.mixin.EffectiveWakes;


import com.goby56.wakes.utils.WakesUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.ladysnake.effective.core.utils.EffectiveUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vesper.vcc.Config;


@Mixin(WakesUtils.class)
public class WakesUtilMixin {
    @Redirect(method = "spawnPaddleSplashCloudParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    private static void effectiveSplash(World world, ParticleEffect particleEffect, double x, double y, double z, double velX, double velY, double velZ, @Local(argsOnly = true)BoatEntity boat){
        if (Config.EffectiveXWakes && Config.oarSplash && FabricLoader.getInstance().isModLoaded("wakes") && FabricLoader.getInstance().isModLoaded("effective")){
            Random random =  world.getRandom();
            int count = random.nextBetween(5, 8);

            for (int i = 0; i < count; i++){
                EffectiveUtils.spawnWaterEffect(world, new Vec3d(x,y,z), random.nextGaussian() / 20f, random.nextFloat() / 4f, random.nextGaussian() / 20f, EffectiveUtils.WaterEffectType.DROPLET);
            }
        }
    }
}
