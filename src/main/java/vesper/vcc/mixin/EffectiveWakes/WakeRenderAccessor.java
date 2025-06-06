package vesper.vcc.mixin.EffectiveWakes;

import com.goby56.wakes.render.WakeRenderer;
import com.goby56.wakes.render.WakeTexture;
import com.goby56.wakes.simulation.Brick;
import net.minecraft.client.render.Camera;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WakeRenderer.class)
public interface WakeRenderAccessor {

    @Invoker("render")
    void invokeRender(Matrix4f matrix, Camera camera, Brick brick, WakeTexture texture);
    @Invoker("initTextures")
    void invokeInitTextures();
}
