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
package net.fishdog5000.core.handler

import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

object OreDictionaryHandler {
    def registerItemOres() {
        OreDictionary.registerOre("listAllmilk", Items.MILK_BUCKET)
        OreDictionary.registerOre("listAllegg", Items.EGG)
        OreDictionary.registerOre("listAllwater", Items.WATER_BUCKET)
        OreDictionary.registerOre("listAllwater", new ItemStack(Items.POTIONITEM, 1, 0))
        OreDictionary.registerOre("bowlWood", Items.BOWL)
        OreDictionary.registerOre("bucketIron", Items.BUCKET)
        OreDictionary.registerOre("itemSugar", Items.SUGAR)
        OreDictionary.registerOre("foodSugar", Items.SUGAR)
        OreDictionary.registerOre("itemLeather", Items.LEATHER)
        OreDictionary.registerOre("foodRawBeef", Items.BEEF)
        OreDictionary.registerOre("foodBeefRaw", Items.BEEF)
        OreDictionary.registerOre("foodBread", Items.BREAD)
    }

    def registerBlockOres() {

    }
}
