/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 FishDog5000
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.fishdog5000.core.basestuff;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DurabilityItemCrafting implements IRecipe {
    public static final List<DurabilityItemCrafting> recipes = new ArrayList<DurabilityItemCrafting>();
    public final IRecipe parent;
    private final int damage_amount;

    public DurabilityItemCrafting(IRecipe parent_recipe, int damageamount) {
        parent = parent_recipe;
        damage_amount = damageamount;
        recipes.add(this);
    }

    public DurabilityItemCrafting(IRecipe parent_recipe) {
        this(parent_recipe, 1);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if (parent.matches(inv, worldIn)) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack != null && stack.getItemDamage() + damage_amount <= stack.getMaxDamage() + 1)
                    return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return parent.getCraftingResult(inv);
    }

    @Override
    public int getRecipeSize() {
        return parent.getRecipeSize();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return parent.getRecipeOutput();
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] remaining = parent.getRemainingItems(inv);
        for (int i = 0; i < remaining.length; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemDurability) {
                stack.attemptDamageItem(damage_amount, new Random());
                if (stack.getItemDamage() <= stack.getMaxDamage())
                    remaining[i] = stack;
            }
        }
        return remaining;
    }
}
