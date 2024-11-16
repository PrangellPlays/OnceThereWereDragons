package dev.prangellplays.otwd.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IronMixItem extends Item {
    public IronMixItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.otwd.iron_mix.limestone").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.otwd.iron_mix.sandstone").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.otwd.iron_mix.iron_ore").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.otwd.iron_mix.magma").formatted(Formatting.GRAY));
    }
}
