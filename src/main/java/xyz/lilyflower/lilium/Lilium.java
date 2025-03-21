package xyz.lilyflower.lilium;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import xyz.lilyflower.lilium.network.LiliumDirectClickPacket;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;

import org.apache.logging.log4j.Logger;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;
import xyz.lilyflower.lilium.util.registry.ItemRegistry;
import xyz.lilyflower.lilium.util.registry.item.LiliumElytras;

public class Lilium implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Lilium");

	public static final SoundEvent DISCHARGE = SoundEvent.of(Identifier.of("lilium", "discharge"));
	public static final SoundEvent CRATE_OPEN = SoundEvent.of(Identifier.of("lilium", "crate_open"));

	public static final ItemGroup ITEMGROUP_LILIUM_GENERIC = FabricItemGroup.builder()
			.icon(() -> new ItemStack(GenericBlocks.CYAN_ROSE))
			.displayName(Text.translatable("itemGroup.lilium.generic"))
			.entries((displayContext, entries) -> {
				BlockRegistry.BLOCK_ITEMS.forEach((name, item) -> {
					if (!WoodSets.WOODEN_BLOCK_ITEMS.contains(item)) {
						entries.add(item);
					}
				});

				ItemRegistry.ITEMS.forEach((name, item) -> {
					if (!name.contains("elytra_")) {
						entries.add(item);
					}
				});
			})
			.build();

	public static final ItemGroup ITEMGROUP_LILIUM_DENDROLOGY = FabricItemGroup.builder()
			.icon(() -> new ItemStack(WoodSets.DARK_MARSHMALLOW.contents.getFirst()))
			.displayName(Text.translatable("itemGroup.lilium.dendrology"))
			.entries((displayContext, entries) -> {
				for (BlockItem item : WoodSets.WOODEN_BLOCK_ITEMS) {
					entries.add(item);
				}
			})
			.build();

	public static final ItemGroup ITEMGROUP_LILIUM_ELYTRAS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(LiliumElytras.ELYTRA_LESBIAN))
			.displayName(Text.translatable("itemGroup.lilium.elytras"))
			.entries((displayContext, entries) -> {
				ItemRegistry.ITEMS.forEach((name, item) -> {
					if (name.contains("elytra_")) {
						entries.add(item);
					}
				});
			})
			.build();

	public static final RegistryKey<DamageType> RAILGUN_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("lilium", "railgun"));

	@Override
	public void onInitialize() {
		BlockRegistry.init();
		ItemRegistry.init();

		Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_generic")), ITEMGROUP_LILIUM_GENERIC);
		Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_dendrology")), ITEMGROUP_LILIUM_DENDROLOGY);
		Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_elytras")), ITEMGROUP_LILIUM_ELYTRAS);

		Registry.register(Registries.SOUND_EVENT, Identifier.of("lilium", "discharge"), DISCHARGE);
		Registry.register(Registries.SOUND_EVENT, Identifier.of("lilium", "crate_open"), CRATE_OPEN);

		// TODO: Reflections this
		new LiliumDirectClickPacket().register();
	}
}