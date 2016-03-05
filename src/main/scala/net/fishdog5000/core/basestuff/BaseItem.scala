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

import net.fishdog5000.core.{FishdogsCore, MultiTextureModel}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.StatCollector
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BaseItem(unlocalizedname: String, tab: CreativeTabs, lore: Boolean, stacksize: Int, MODID: String) extends Item with IBaseItem {
    setUnlocalizedName(unlocalizedname.replaceFirst("item.", MODID + "."))
    setCreativeTab(tab)
    setMaxStackSize(stacksize)

    @SideOnly(Side.CLIENT)
    def reloadTexture(texture: String) =
        multitexture.setCurrentTexture(MODID + ":" + texture)

    def getName = unlocalizedname

    def hasLore: Boolean = lore

    def this(unlocalizedname: String, tab: CreativeTabs, stacksize: Int, MODID: String) =
        this(unlocalizedname, tab, false, stacksize, MODID)

    def this(unlocalizedname: String, tab: CreativeTabs, lore: Boolean, MODID: String) =
        this(unlocalizedname, tab, lore, 64, MODID)

    def this(unlocalizedname: String, tab: CreativeTabs, MODID: String) =
        this(unlocalizedname, tab, false, MODID)
}

trait IBaseItem extends Item {
    protected var multitexture: MultiTextureModel = _
    protected var cached_lore = ""
    protected var post_cached_lore: java.util.List[String] = _

    def getName: String

    def hasLore: Boolean

    @SideOnly(Side.CLIENT)
    def setMultiTexture(model: MultiTextureModel) =
        multitexture = model

    @SideOnly(Side.CLIENT)
    def reloadTexture(texture: String)

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, _list: java.util.List[String], adv: Boolean) = {
        val list = _list.asInstanceOf[java.util.List[String]]
        if (hasLore) {
            //todo redo
            val listy = FishdogsCore.addInformation(getUnlocalizedName, player, list, adv, cached_lore, post_cached_lore)
            if (listy != null) {
                post_cached_lore = listy
                cached_lore = StatCollector.translateToLocal(getUnlocalizedName + ".lore")
            }
        }
    }
}
