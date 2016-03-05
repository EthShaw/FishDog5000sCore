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
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.fishdog5000.core.basestuff.DurabilityItemCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;*/

public class NEIDurabilityCraftingHandler// extends ShapedRecipeHandler
{
    /*@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		for (Object o : CraftingManager.getInstance().getRecipeList())
		{
			IRecipe irecipe = (IRecipe) o;
			if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
			{
				CachedShapedRecipe recipe = null;
				if (irecipe instanceof DurabilityItemCrafting)
				{
					DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
					if (r.parent instanceof ShapedRecipes)
						recipe = new CachedShapedRecipe((ShapedRecipes) r.parent);
					else if (r.parent instanceof ShapedOreRecipe)
						recipe = forgeShapedRecipe((ShapedOreRecipe) r.parent);
				}

				if (recipe != null)
				{
					recipe.computeVisuals();
					arecipes.add(recipe);
				}
			}
		}
	}

	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("crafting") && getClass() == NEIDurabilityCraftingHandler.class)
		{
			for (Object o : CraftingManager.getInstance().getRecipeList())
			{
				IRecipe irecipe = (IRecipe) o;
				CachedShapedRecipe recipe = null;
				if (irecipe instanceof DurabilityItemCrafting)
				{
					DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
					if (r.parent instanceof ShapedRecipes)
						recipe = new CachedShapedRecipe((ShapedRecipes) r.parent);
					else if (r.parent instanceof ShapedOreRecipe)
						recipe = this.forgeShapedRecipe((ShapedOreRecipe) r.parent);
				}

				if (recipe != null)
				{
					recipe.computeVisuals();
					arecipes.add(recipe);
				}
			}
		}
		else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadUsageRecipes(ItemStack ingredient)
	{
		for (Object o : CraftingManager.getInstance().getRecipeList())
		{
			IRecipe irecipe = (IRecipe) o;
			CachedShapedRecipe recipe = null;
			if (irecipe instanceof DurabilityItemCrafting)
			{
				DurabilityItemCrafting r = (DurabilityItemCrafting) irecipe;
				if (r.parent instanceof ShapedRecipes)
					recipe = new CachedShapedRecipe((ShapedRecipes) r.parent);
				else if (r.parent instanceof ShapedOreRecipe)
					recipe = this.forgeShapedRecipe((ShapedOreRecipe) r.parent);
			}

			if (recipe != null && recipe.contains(recipe.ingredients, ingredient.getItem()))
			{
				recipe.computeVisuals();
				if (recipe.contains(recipe.ingredients, ingredient))
				{
					recipe.setIngredientPermutation(recipe.ingredients, ingredient);
					arecipes.add(recipe);
				}
			}
		}
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("nei.recipe.shaped");
	}*/
}
