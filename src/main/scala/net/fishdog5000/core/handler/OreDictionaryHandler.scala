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
        OreDictionary.registerOre("listAllmilk", Items.milk_bucket)
        OreDictionary.registerOre("listAllegg", Items.egg)
        OreDictionary.registerOre("listAllwater", Items.water_bucket)
        OreDictionary.registerOre("listAllwater", new ItemStack(Items.potionitem, 1, 0))
        OreDictionary.registerOre("bowlWood", Items.bowl)
        OreDictionary.registerOre("bucketIron", Items.bucket)
        OreDictionary.registerOre("itemSugar", Items.sugar)
        OreDictionary.registerOre("foodSugar", Items.sugar)
        OreDictionary.registerOre("itemLeather", Items.leather)
        OreDictionary.registerOre("foodRawBeef", Items.beef)
        OreDictionary.registerOre("foodBeefRaw", Items.beef)
        OreDictionary.registerOre("foodBread", Items.bread)
    }

    def registerBlockOres() {

    }
}
