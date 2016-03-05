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

class ItemDurability(unlocalizedname: String, tab: CreativeTabs, durability: Int, lore: Boolean, MODID: String) extends BaseItem(unlocalizedname, tab, lore, MODID) {
    var enchantability = 0

    setMaxDamage(durability)
    setMaxStackSize(1)

    def this(unlocalizedname: String, tab: CreativeTabs, durability: Int, MODID: String) =
        this(unlocalizedname, tab, durability, false, MODID)

    def setEnchantability(enchant: Int): ItemDurability = {
        enchantability = enchant
        this
    }

    override def getItemEnchantability = enchantability

}
