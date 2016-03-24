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
package net.fishdog5000.core.basestuff

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemFood, ItemStack}
import net.minecraft.potion.{Potion, PotionEffect}
import net.minecraft.world.World

class ItemBasicFood(unlocalizedname: String, tab: CreativeTabs, healAmount: Int, saturation: Float, canFeedToWolf: Boolean,
                    effects: Array[PotionEffectGroup], probability: Float, lore: Boolean, MODID: String
                   ) extends ItemFood(healAmount, saturation, canFeedToWolf) with IBaseItem {

    setUnlocalizedName(MODID + "." + unlocalizedname)
    setCreativeTab(tab)

    def hasLore = lore

    def getName = "item." + unlocalizedname

    def reloadTexture(texture: String) =
        multitexture.setCurrentTexture(MODID + ":" + texture)

    def this(unlocalizedname: String, tab: CreativeTabs, healAmount: Int, saturation: Float,
             canFeedToWolf: Boolean, effects: Array[PotionEffectGroup], probability: Float, MODID: String) =
        this(unlocalizedname, tab, healAmount, saturation, canFeedToWolf, effects, probability, false, MODID)

    def this(unlocalizedname: String, tab: CreativeTabs, healAmount: Int, saturation: Float,
             canFeedToWolf: Boolean, effects: Array[PotionEffectGroup], MODID: String) =
        this(unlocalizedname, tab, healAmount, saturation, canFeedToWolf, effects, 0.0F, false, MODID)

    def this(unlocalizedname: String, tab: CreativeTabs, healAmount: Int, saturation: Float, canFeedToWolf: Boolean, lore: Boolean, MODID: String) =
        this(unlocalizedname, tab, healAmount, saturation, canFeedToWolf, null, 0.0F, lore, MODID)

    def this(unlocalizedname: String, tab: CreativeTabs, healAmount: Int, saturation: Float, canFeedToWolf: Boolean, MODID: String) =
        this(unlocalizedname, tab, healAmount, saturation, canFeedToWolf, false, MODID)


    override def onFoodEaten(itemstack: ItemStack, world: World, player: EntityPlayer) {
        if (effects != null)
            for (effect <- effects)
                if (probability == 0.0F) effect.applyEffect(player)
                else effect.applyEffect(player, probability)

        super.onFoodEaten(itemstack, world, player)
    }

}

class PotionEffectGroup(effect: Potion, duration: Int, amplifier: Int, probability: Double) {
    def this(effect: Potion, duration: Int, amplifier: Int) = this(effect, duration, amplifier, 0.0D)

    def applyEffect(player: EntityPlayer): Unit = applyEffect(player, probability)

    def applyEffect(player: EntityPlayer, chance: Double) =
        if (Math.random() * 100 < chance)
            player.addPotionEffect(new PotionEffect(effect, duration, amplifier))
}
