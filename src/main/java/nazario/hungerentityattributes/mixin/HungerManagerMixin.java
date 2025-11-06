package nazario.hungerentityattributes.mixin;

import nazario.hungerentityattributes.HungerEntityAttribute;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Unique private PlayerEntity player;

    @ModifyConstant(method = "add", constant = @Constant(intValue = 20))
    public int add(int constant) {
        if(player == null) return constant;
        return getMaxHunger(player);
    }

    @ModifyConstant(method = "isNotFull", constant = @Constant(intValue = 20))
    public int isNotFull(int constant) {
        if(player == null) return constant;
        return getMaxHunger(player);
    }

    @Inject(method = "update", at = @At("HEAD"))
    public void update(PlayerEntity player, CallbackInfo ci) {
        this.player = player;
    }


    @Unique
    private int getMaxHunger(PlayerEntity player) {
        var attribute = player.getAttributeInstance(HungerEntityAttribute.MAX_HUNGER);
        return (int)(attribute != null ? attribute.getValue() : HungerEntityAttribute.MAX_HUNGER.getDefaultValue());
    }
}
