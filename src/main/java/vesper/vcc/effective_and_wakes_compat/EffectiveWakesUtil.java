package vesper.vcc.effective_and_wakes_compat;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.ladysnake.effective.core.Effective;
import org.ladysnake.effective.core.EffectiveConfig;
import org.ladysnake.effective.core.particle.types.SplashParticleType;

import java.util.function.Predicate;

public class EffectiveWakesUtil {
    public static void spawnSplashAccurate(World world, Vec3d pos, double velX, double velY, double velZ){
        SplashParticleType splash = Effective.SPLASH;
        BlockPos blockPos = BlockPos.ofFloored(pos.getX(),pos.getY(),pos.getZ());
        if (EffectiveConfig.glowingPlankton && world.isNight() && world.getBiome(blockPos).matches((Predicate<RegistryKey<Biome>>) BiomeKeys.WARM_OCEAN)) {
            splash = Effective.GLOW_SPLASH;
        }
        world.addParticle(splash, pos.getX(), pos.getY() + 9, pos.getZ(), velX, velY, velZ);
    }
}
