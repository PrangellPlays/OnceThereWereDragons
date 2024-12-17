package dev.prangellplays.otwd;

import dev.prangellplays.otwd.client.screen.OTWDScreenHandlers;
import dev.prangellplays.otwd.command.OTWDCommand;
import dev.prangellplays.otwd.event.DragonCrystalEventHandler;
import dev.prangellplays.otwd.init.*;
import dev.prangellplays.otwd.util.OTWDTags;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OTWD implements ModInitializer {
	public static final String MOD_ID = "otwd";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final List<String> crystalWhitelist = new ArrayList<>();

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, OTWDConfig.class);
		OTWDBlocks.init();
		OTWDItems.init();
		OTWDItemGroups.init();
		OTWDBlockEntities.init();
		OTWDRecipes.init();
		OTWDTags.init();
		OTWDEntities.init();

		DragonCrystalEventHandler.init();
		whitelist();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			OTWDCommand.register(dispatcher);
		});
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static boolean isPouchEmpty(ItemStack stack) {
		NbtList tag = stack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);
		for (NbtElement element : tag) {
			NbtCompound stackTag = (NbtCompound) element;
			ItemStack backpackStack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
			if (!backpackStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public static List<ItemStack> getPouchContents(ItemStack stack) {
		List<ItemStack> stacks = new ArrayList<>();
		NbtList tag = stack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);
		for (NbtElement element : tag) {
			NbtCompound stackTag = (NbtCompound) element;
			ItemStack backpackStack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
			stacks.add(backpackStack);
		}

		return stacks;
	}

	private void whitelist() {
		crystalWhitelist.add("minecraft:wolf");
		crystalWhitelist.add("minecraft:donkey");

		crystalWhitelist.add("otwd:phantom_stalker");
	}
}