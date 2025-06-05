package vesper.abyssify.effective_and_wakes_compat;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.effective.core.Effective;
import org.ladysnake.effective.core.EffectiveConfig;
import org.ladysnake.effective.core.particle.contracts.SplashParticleInitialData;
import org.ladysnake.effective.core.particle.types.SplashParticleType;

import java.util.function.Predicate;

public class EffectiveWakesUtil {
    public static void spawnSplashAccurate(World world, Vec3d pos, double velX, double velY, double velZ, @Nullable SplashParticleInitialData data){
        SplashParticleType splash = Effective.SPLASH;
        BlockPos blockPos = BlockPos.ofFloored(pos.getX(),pos.getY(),pos.getZ());
        if (EffectiveConfig.glowingPlankton && isNight(world) && world.getBiome(blockPos).matches((Predicate<RegistryKey<Biome>>) BiomeKeys.WARM_OCEAN)) {
            splash = Effective.GLOW_SPLASH;
        }
        world.addParticle(splash.setData(data), pos.getX(), pos.getY() + 9, pos.getZ(), velX, velY, velZ);
    }

    public static boolean isNight(World world){
        return world.isNight();
    }
}
