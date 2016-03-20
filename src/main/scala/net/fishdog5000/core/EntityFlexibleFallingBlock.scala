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
package net.fishdog5000.core

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.{EntityFallingBlock, EntityItem}
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EntityFlexibleFallingBlock(world: World, x: Double, y: Double, z: Double, block: IBlockState) extends EntityFallingBlock(world, x, y, z, block) {
    def this(world: World) =
        this(world, 0, 0, 0, null)

    override def entityDropItem(stack: ItemStack, yoffset: Float): EntityItem = {
        val items = getBlock.getBlock.getDrops(worldObj, new BlockPos(this), getBlock, 0)
        var entiyitem: EntityItem = null
        for (i <- 0 until items.size)
            entiyitem = dropItems(items.get(i), yoffset)
        entiyitem
    }

    private def dropItems(stack: ItemStack, yoffset: Float): EntityItem = {
        if (stack.stackSize != 0 && stack.getItem != null) {
            val entityitem: EntityItem = new EntityItem(worldObj, posX, posY + yoffset.toDouble, posZ, stack)
            entityitem.setDefaultPickupDelay()
            if (captureDrops)
                capturedDrops.add(entityitem)
            else
                worldObj.spawnEntityInWorld(entityitem)
            entityitem
        }
        else {
            null
        }
    }
}
