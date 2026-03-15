// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

//#if MC >= 11900
import net.minecraft.core.registries.BuiltInRegistries;
//#endif

public class BuiltInRegistriesUtil {
    //#if MC >= 11900
    public static final DefaultedRegistry<Block> BLOCK = BuiltInRegistries.BLOCK;
    public static final DefaultedRegistry<Item> ITEM = BuiltInRegistries.ITEM;
    public static final DefaultedRegistry<Fluid> FLUID = BuiltInRegistries.FLUID;
    public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = BuiltInRegistries.ENTITY_TYPE;
    public static final Registry<BlockEntityType<?>> BLOCK_ENTITY_TYPE = BuiltInRegistries.BLOCK_ENTITY_TYPE;
    public static final Registry<PoiType> POINT_OF_INTEREST_TYPE = BuiltInRegistries.POINT_OF_INTEREST_TYPE;
    //#else
    //$$ public static final DefaultedRegistry<Block> BLOCK = DefaultedRegistry.BLOCK;
    //$$ public static final DefaultedRegistry<Item> ITEM = DefaultedRegistry.ITEM;
    //$$ public static final DefaultedRegistry<Fluid> FLUID = DefaultedRegistry.FLUID;
    //$$ public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = DefaultedRegistry.ENTITY_TYPE;
    //$$ public static final Registry<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DefaultedRegistry.BLOCK_ENTITY_TYPE;
    //$$ public static final DefaultedRegistry<PoiType> POINT_OF_INTEREST_TYPE = DefaultedRegistry.POINT_OF_INTEREST_TYPE;
    //#endif
}
