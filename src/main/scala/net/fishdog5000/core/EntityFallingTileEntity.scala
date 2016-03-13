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

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.block.{Block, BlockFalling, ITileEntityProvider}
import net.minecraft.entity.item.EntityFallingBlock
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.World

class EntityFallingTileEntity(world: World, x: Double, y: Double, z: Double, block: IBlockState) extends EntityFallingBlock(world, x, y, z, block) {
    def this(world: World) =
        this(world, 0, 0, 0, null)

    setHurtEntities(true)

    /**
      * Called to update the entity's position/logic.
      */
    override def onUpdate() {
        val blockstate = getBlock
        var block: Block = null

        if (blockstate == null) {
            setDead()
            return
        }
        else {
            block = blockstate.getBlock
        }

        if (block.getMaterial == Material.air) {
            setDead()
        }
        else {
            prevPosX = posX
            prevPosY = posY
            prevPosZ = posZ
            var pos: BlockPos = null

            if (fallTime == 0) {
                pos = new BlockPos(this)

                if (worldObj.getBlockState(pos).getBlock == block) {
                    worldObj.setBlockToAir(pos)
                }
                else if (!worldObj.isRemote) {
                    setDead()
                    return
                }
            }
            fallTime += 1

            motionY -= 0.03999999910593033D
            moveEntity(motionX, motionY, motionZ)
            motionX *= 0.9800000190734863D
            motionY *= 0.9800000190734863D
            motionZ *= 0.9800000190734863D

            if (!worldObj.isRemote) {
                pos = new BlockPos(this)

                if (onGround) {
                    motionX *= 0.699999988079071D
                    motionZ *= 0.699999988079071D
                    motionY *= -0.5D

                    if (this.worldObj.getBlockState(pos).getBlock != Blocks.piston_extension) {
                        setDead()
                        if (worldObj.canBlockBePlaced(block, pos, true, EnumFacing.UP, null, null) && !BlockFalling.canFallInto(worldObj, pos.down) && worldObj.setBlockState(pos, blockstate, 3)) {
                            block match {
                                case falling: BlockFalling =>
                                    falling.onEndFalling(this.worldObj, pos)
                                case _ =>
                            }
                            if (tileEntityData != null)
                                placeTileEntity(pos, tileEntityData)
                        }
                        else if (shouldDropItem && worldObj.getGameRules.getBoolean("doTileDrops")) {
                            val items = getBlock.getBlock.getDrops(worldObj, pos, getBlock, 0)
                            for (i <- 0 until items.size)
                                entityDropItem(items.get(i), 0.0f)
                            dropTileEntity(pos)
                        }
                    }
                }
                else if (fallTime > 1000000 && !worldObj.isRemote && (pos.getY < 1 || pos.getY > 256) || fallTime > 1000000) {
                    if (shouldDropItem && worldObj.getGameRules.getBoolean("doTileDrops")) {
                        val items = getBlock.getBlock.getDrops(worldObj, pos, getBlock, 0)
                        for (i <- 0 until items.size)
                            entityDropItem(items.get(i), 0.0f)
                        if (!(pos.getY < 1)) {
                            if (pos.getY > 256) {
                                dropTileEntity(new BlockPos(pos.getX, 255, pos.getZ))
                            }
                            else {
                                dropTileEntity(pos)
                            }
                        }
                    }
                    setDead()
                }
            }
        }
    }

    protected def dropTileEntity(blockPos: BlockPos) = {
        //TODO fix farmland drop
        val oldBlockState = worldObj.getBlockState(blockPos)
        var oldnbt: NBTTagCompound = null

        if (oldBlockState.getBlock.isInstanceOf[ITileEntityProvider]) {
            val tileentity = worldObj.getTileEntity(new BlockPos(x, y, z))
            if (tileentity != null) {
                oldnbt = new NBTTagCompound()
                tileentity.writeToNBT(oldnbt)
            }
        }

        worldObj.setBlockState(blockPos, getBlock)
        placeTileEntity(blockPos, tileEntityData)
        val newstate = worldObj.getBlockState(blockPos)
        newstate.getBlock.breakBlock(worldObj, blockPos, newstate)

        worldObj.setBlockToAir(blockPos)
        if (oldBlockState.getBlock != Blocks.air)
            worldObj.setBlockState(blockPos, oldBlockState)
        if (oldnbt != null)
            placeTileEntity(blockPos, oldnbt)
    }

    protected def placeTileEntity(pos: BlockPos, tileEntityTag: NBTTagCompound) {
        getBlock.getBlock.onBlockAdded(worldObj, pos, getBlock)

        if (tileEntityTag != null && getBlock.getBlock.isInstanceOf[ITileEntityProvider]) {
            val tileentity = worldObj.getTileEntity(pos)

            if (tileentity != null) {
                val nbttagcompound = new NBTTagCompound()
                tileentity.writeToNBT(nbttagcompound)
                val iterator = tileEntityTag.getKeySet.iterator

                while (iterator.hasNext) {
                    val s: String = iterator.next()
                    val nbtbase = tileEntityTag.getTag(s)

                    if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                        nbttagcompound.setTag(s, nbtbase.copy())
                }

                tileentity.readFromNBT(nbttagcompound)
                tileentity.markDirty()
            }
        }
    }

    /**
      * Returns true if other Entities should be prevented from moving through this Entity.
      */
    override def canBeCollidedWith = true
}
