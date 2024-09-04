/* Decompiler 9ms, total 174ms, lines 10 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import org.bukkit.Material;

public class ServerConfig {
   public static String getServerName() {
      return "&x&B&E&3&4&B&2&lA&x&C&5&3&2&B&8&lf&x&C&C&2&F&B&F&lk&x&D&4&2&D&C&5&lF&x&D&B&2&B&C&B&la&x&E&2&2&8&D&2&lc&x&E&9&2&6&D&8&lt&x&F&1&2&4&D&E&lo&x&F&8&2&1&E&5&lr&x&F&F&1&F&E&B&ly";
   }

   public static Material[] getBlockList() {
      return new Material[]{
              // Natural Blocks
              Material.STONE, Material.GRANITE, Material.POLISHED_GRANITE, Material.DIORITE, Material.POLISHED_DIORITE, Material.ANDESITE, Material.POLISHED_ANDESITE,
              Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.COBBLESTONE, Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
              Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM, Material.OAK_PLANKS, Material.SPRUCE_PLANKS,
              Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS,
              Material.SAND, Material.RED_SAND, Material.GRAVEL, Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE,
              Material.EMERALD_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE, Material.ANCIENT_DEBRIS,
              Material.CLAY, Material.SNOW_BLOCK, Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE, Material.MAGMA_BLOCK, Material.OBSIDIAN,
              Material.DEEPSLATE, Material.POLISHED_DEEPSLATE, Material.COBBLED_DEEPSLATE, Material.DEEPSLATE_TILES, Material.CRACKED_DEEPSLATE_TILES, Material.DEEPSLATE_BRICKS, Material.CRACKED_DEEPSLATE_BRICKS,

              // Built Blocks
              Material.COBBLESTONE_STAIRS, Material.STONE_BRICKS, Material.MOSSY_STONE_BRICKS, Material.CRACKED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS,
              Material.STONE_BRICK_STAIRS, Material.NETHER_BRICKS, Material.RED_NETHER_BRICKS, Material.CHISELED_NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS,
              Material.QUARTZ_BLOCK, Material.CHISELED_QUARTZ_BLOCK, Material.SMOOTH_QUARTZ, Material.QUARTZ_STAIRS, Material.PURPUR_BLOCK, Material.PURPUR_PILLAR,
              Material.PURPUR_STAIRS, Material.PURPUR_SLAB, Material.SMOOTH_SANDSTONE, Material.CUT_SANDSTONE, Material.CHISELED_SANDSTONE, Material.RED_SANDSTONE,
              Material.CUT_RED_SANDSTONE, Material.CHISELED_RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE, Material.BRICKS, Material.SMOOTH_STONE, Material.COBBLESTONE_SLAB,
              Material.STONE_SLAB, Material.STONE_BRICK_SLAB, Material.NETHER_BRICK_SLAB, Material.QUARTZ_SLAB, Material.RED_SANDSTONE_SLAB, Material.SANDSTONE_SLAB,
              Material.MUD_BRICKS, Material.PACKED_MUD, Material.MUD_BRICK_STAIRS, Material.MUD_BRICK_SLAB, Material.POLISHED_BLACKSTONE_STAIRS, Material.POLISHED_BLACKSTONE_SLAB, Material.POLISHED_BLACKSTONE_BRICK_SLAB,

              // Decorative Blocks
              Material.GLASS, Material.WHITE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS,
              Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.PINK_STAINED_GLASS, Material.GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS,
              Material.CYAN_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.GREEN_STAINED_GLASS,
              Material.RED_STAINED_GLASS, Material.BLACK_STAINED_GLASS, Material.GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE,
              Material.MAGENTA_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE,
              Material.PINK_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE,
              Material.PURPLE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE,
              Material.RED_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE, Material.SEA_LANTERN, Material.PRISMARINE, Material.PRISMARINE_BRICKS,
              Material.DARK_PRISMARINE, Material.LANTERN, Material.END_ROD, Material.SHROOMLIGHT, Material.BEACON, Material.JACK_O_LANTERN,

              // Nether Blocks
              Material.NETHERRACK, Material.BASALT, Material.POLISHED_BASALT, Material.SMOOTH_BASALT, Material.BLACKSTONE, Material.POLISHED_BLACKSTONE,
              Material.POLISHED_BLACKSTONE_BRICKS, Material.CHISELED_POLISHED_BLACKSTONE, Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, Material.GILDED_BLACKSTONE,
              Material.NETHER_BRICKS, Material.CHISELED_NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS, Material.SOUL_SAND, Material.SOUL_SOIL,
              Material.GLOWSTONE, Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK, Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM, Material.CRIMSON_FUNGUS,
              Material.WARPED_FUNGUS, Material.CRIMSON_ROOTS, Material.WARPED_ROOTS, Material.SOUL_TORCH, Material.SOUL_LANTERN, Material.SOUL_CAMPFIRE,

              // End Blocks
              Material.END_STONE, Material.END_STONE_BRICKS, Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.PURPUR_STAIRS, Material.PURPUR_SLAB, Material.DRAGON_EGG, Material.SHULKER_BOX,
              Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX,
              Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX, Material.CYAN_SHULKER_BOX,
              Material.PURPLE_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.RED_SHULKER_BOX,
              Material.BLACK_SHULKER_BOX, Material.CHORUS_PLANT, Material.CHORUS_FLOWER, Material.DRAGON_HEAD,

              // Rare Blocks
              Material.EMERALD_BLOCK, Material.DIAMOND_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.NETHERITE_BLOCK, Material.LAPIS_BLOCK,
              Material.REDSTONE_BLOCK, Material.COAL_BLOCK, Material.SLIME_BLOCK, Material.HONEY_BLOCK, Material.SNOW_BLOCK, Material.CLAY,
              Material.HAY_BLOCK, Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA,
              Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
              Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA,
              Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA,
              Material.MAGENTA_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA,
              Material.PINK_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA,
              Material.PURPLE_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA,
              Material.RED_GLAZED_TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA, Material.BLACK_CONCRETE, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE,
              Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE,
              Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE,
              Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.BROWN_CONCRETE_POWDER,
              // Mythic Blocks
              Material.SPAWNER, Material.ENCHANTING_TABLE,    // Mythic Items
      };
   }

   public static String formatAmount(double amount) {
      String[] suffix = new String[]{"", "K", "M", "B", "T"};
      int index;
      for (index = 0; amount >= 1000.0 && index < suffix.length - 1; ++index) {
         amount /= 1000;
      }
      String formattedAmount;
      if (index == 0) {
         formattedAmount = String.format("%.0f", amount); // No decimal places if amount < 1000
      } else {
         formattedAmount = String.format("%.2f", amount) + suffix[index]; // Use decimal places for larger amounts
      }
      return formattedAmount;
   }
}
