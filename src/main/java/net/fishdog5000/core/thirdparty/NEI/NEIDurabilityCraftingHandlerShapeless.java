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
package net.fishdog5000.core.thirdparty.NEI;

/*import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.fishdog5000.core.basestuff.DurabilityItemCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Iterator;
import java.util.List;*/

public class NEIDurabilityCraftingHandlerShapeless// extends ShapelessRecipeHandler
{
    /*@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("nei.recipe.shapeless");
	}

	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("crafting") && getClass() == NEIDurabilityCraftingHandlerShapeless.class)
		{
			List allrecipes = CraftingManager.getInstance().getRecipeList();

			for (Object allrecipe : allrecipes)
			{
				IRecipe irecipe = (IRecipe) allrecipe;
				CachedShapelessRecipe recipe = null;
				if (irecipe instanceof DurabilityItemCrafting)
				{
					DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
					if (r.parent instanceof ShapelessRecipes)
						recipe = shapelessRecipe((ShapelessRecipes) r.parent);
					else if (r.parent instanceof ShapelessOreRecipe)
						recipe = forgeShapelessRecipe((ShapelessOreRecipe) r.parent);
				}

				if (recipe != null)
					arecipes.add(recipe);
			}
		}
		else
		{
			super.loadCraftingRecipes(outputId, results);
		}

	}

	public void loadCraftingRecipes(ItemStack result)
	{
		List allrecipes = CraftingManager.getInstance().getRecipeList();

		for (Object allrecipe : allrecipes)
		{
			IRecipe irecipe = (IRecipe) allrecipe;
			if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
			{
				CachedShapelessRecipe recipe = null;
				if (irecipe instanceof DurabilityItemCrafting)
				{
					DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
					if (r.parent instanceof ShapelessRecipes)
						recipe = this.shapelessRecipe((ShapelessRecipes) r.parent);
					else if (r.parent instanceof ShapelessOreRecipe)
						recipe = this.forgeShapelessRecipe((ShapelessOreRecipe) r.parent);
				}

				if (recipe != null)
					arecipes.add(recipe);
			}
		}

	}

	public void loadUsageRecipes(ItemStack ingredient) {
		List allrecipes = CraftingManager.getInstance().getRecipeList();

		for (Object allrecipe : allrecipes)
		{
			IRecipe irecipe = (IRecipe) allrecipe;
			CachedShapelessRecipe recipe = null;
			if (irecipe instanceof DurabilityItemCrafting)
			{
				DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
				if (r.parent instanceof ShapelessRecipes)
					recipe = this.shapelessRecipe((ShapelessRecipes) r.parent);
				else if (r.parent instanceof ShapelessOreRecipe)
					recipe = this.forgeShapelessRecipe((ShapelessOreRecipe) r.parent);
			}

			if (recipe != null && recipe.contains(recipe.ingredients, ingredient))
			{
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}

	}

	private ShapelessRecipeHandler.CachedShapelessRecipe shapelessRecipe(ShapelessRecipes recipe)
	{
		return recipe.recipeItems == null ? null : new ShapelessRecipeHandler.CachedShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
	}*/
}
