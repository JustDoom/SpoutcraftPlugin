/*
 * This file is part of SpoutcraftPlugin.
 *
 * Copyright (c) 2011 SpoutcraftDev <http://spoutcraft.org//>
 * SpoutcraftPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutcraftPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutcraftPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.material.Tool;

public class SpoutItemStack extends ItemStack {
	public SpoutItemStack(int typeId, int amount, short data, ItemMeta meta) {
		super(typeId, amount, data);

		if (meta != null) {
			super.setItemMeta(meta);
		}

		Material material = getMaterial();

		// Used to only run if it did not have the UNSTACKABLE enchant already. Runs every time now to keep the enchant
		// level fresh to reduce possible exploiting to make it stackable as counter resets on server restart
		if (material instanceof CustomItem && !((CustomItem) material).isStackable()) {
			addUnsafeEnchantment(SpoutEnchantment.UNSTACKABLE, ((CustomItem) material).getCounter());
		}

		if (material instanceof GenericCustomTool) {
			if (!getEnchantments().containsKey(SpoutEnchantment.MAX_DURABILITY)) {
				addUnsafeEnchantment(SpoutEnchantment.MAX_DURABILITY, ((Tool) material).getMaxDurability());
			}
			if (!getEnchantments().containsKey(SpoutEnchantment.DURABILITY)) {
				addUnsafeEnchantment(SpoutEnchantment.DURABILITY, 0);
			}
		}
	}

	public SpoutItemStack(ItemStack item) {
		this(item.getTypeId(), item.getAmount(), item.getDurability(), item.getItemMeta());
		addUnsafeEnchantments(item.getEnchantments());
	}

	public SpoutItemStack(int typeId) {
		this(typeId, 1, (short) 0, null);
	}

	public SpoutItemStack(int typeId, short data) {
		this(typeId, 1, data, null);
	}

	public SpoutItemStack(CustomItem item) {
		this(item.getRawId(), 1, (short) item.getRawData(), null);
	}

	public SpoutItemStack(CustomItem item, int amount) {
		this(item.getRawId(), amount, (short) item.getRawData(), null);
	}

	public SpoutItemStack(CustomBlock block) {
		this(block.getBlockItem());
	}

	public SpoutItemStack(CustomBlock block, int amount) {
		this(block.getBlockItem(), amount);
	}

	public SpoutItemStack(Material material) {
		this(material.getRawId(), 1, (short) material.getRawData(), null);
	}

	public SpoutItemStack(Material material, int amount) {
		this(material.getRawId(), amount, (short) material.getRawData(), null);
	}

	public Material getMaterial() {
		return MaterialData.getMaterial(this.getTypeId(), this.getDurability());
	}

	public boolean isCustomItem() {
		return getMaterial() instanceof CustomItem;
	}
	
	public boolean isCustomBlock() {
		return getMaterial() instanceof CustomBlock;
	}
}
