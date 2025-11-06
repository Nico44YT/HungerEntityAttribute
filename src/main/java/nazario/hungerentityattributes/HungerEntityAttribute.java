package nazario.hungerentityattributes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

public class HungerEntityAttribute implements ModInitializer {
    public static final String MOD_ID = "hunger-entity-attribute";

    public static EntityAttribute MAX_HUNGER = make("max_hunger", 20, 0, 2048);

    private static EntityAttribute make(final String name, final double base, final double min, final double max) {
        return new ClampedEntityAttribute("attribute.name.generic." + MOD_ID + '.' + name, base, min, max).setTracked(true);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registries.ATTRIBUTE, Identifier.of(MOD_ID, "max_hunger"), MAX_HUNGER);
    }

    @Unique
    public static int getMaxHunger(PlayerEntity player) {
        EntityAttributeInstance attribute = player.getAttributeInstance(HungerEntityAttribute.MAX_HUNGER);
        return (int)(attribute != null ? attribute.getValue() : HungerEntityAttribute.MAX_HUNGER.getDefaultValue());
    }
}
