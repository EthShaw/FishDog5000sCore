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

import net.fishdog5000.core.basestuff.IBaseBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.pattern.{BlockPattern, BlockStateMatcher, FactoryBlockPattern}
import net.minecraft.block.state.{BlockWorldState, IBlockState}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.monster.EntityIronGolem
import net.minecraft.init.Blocks
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockPowerBlock(material: Material) extends Block(material) with IBaseBlock {
    private var golemBasePattern: BlockPattern = _
    setHardness(2.0F)
    setUnlocalizedName(FishdogsCore.MODID + ":powerblock")
    setCreativeTab(CreativeTabs.tabMaterials)

    def getName = "tile.powerblock"

    /**
      * Called whenever the block is added into the world. Args: world, pos, state
      */
    override def onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
        super.onBlockAdded(world, pos, state)

        if (PowerBlock.PowerBlockAddonerNoStop != null && PowerBlock.PowerBlockAddonerNoStop.nonEmpty)
            for (addon <- PowerBlock.PowerBlockAddonerNoStop)
                addon.triggerBlockAdd(world, pos, state)

        val patternhelper: BlockPattern.PatternHelper = FishdogsJava.blockPatternMatch(getGolemBasePattern, world, pos)
        if (patternhelper != null) {
            for (i <- 0 until getGolemBasePattern.getPalmLength)
                for (k <- 0 until getGolemBasePattern.getThumbLength)
                    world.setBlockState(patternhelper.translateOffset(i, k, 0).getPos, Blocks.air.getDefaultState, 2)

            val blockpos1 = patternhelper.translateOffset(1, 2, 0).getPos
            val irongolem = new EntityIronGolem(world)
            irongolem.setPlayerCreated(false)
            irongolem.setLocationAndAngles(blockpos1.getX + 0.5D, blockpos1.getY + 0.05D, blockpos1.getZ + 0.5D, 0.0F, 0.0F)
            world.spawnEntityInWorld(irongolem)

            for (j <- 0 until 120) {
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, blockpos1.getX + world.rand.nextDouble(), blockpos1.getY + world.rand.nextDouble() * 2.5D, blockpos1.getZ + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D)
            }

            for (j <- 0 until getGolemBasePattern.getPalmLength) {
                for (l <- 0 until getGolemBasePattern.getThumbLength) {
                    val blockworldstate2 = patternhelper.translateOffset(j, l, 0)
                    world.notifyNeighborsRespectDebug(blockworldstate2.getPos, Blocks.air)
                }
            }
        }
        /*var x = pos.getX()
          var y = pos.getY()
          var z = pos.getZ()
        if (world.getBlockState(new BlockPos(x, y, z)) == Blocks.iron_block && world.getBlock(x, y - 2, z) == Blocks.iron_block)
        {
            var var5 = world.getBlock(x - 1, y - 1, z) == Blocks.iron_block && world.getBlock(x + 1, y - 1, z) == Blocks.iron_block
            var var6 = world.getBlock(x, y - 1, z - 1) == Blocks.iron_block && world.getBlock(x, y - 1, z + 1) == Blocks.iron_block

            if (var5 || var6)
            {
                world.setBlock(x, y, z, Blocks.air, 0, 2)
                world.setBlock(x, y - 1, z, Blocks.air, 0, 2)
                world.setBlock(x, y - 2, z, Blocks.air, 0, 2)

                if (var5)
                {
                    world.setBlock(x - 1, y - 1, z, Blocks.air, 0, 2)
                    world.setBlock(x + 1, y - 1, z, Blocks.air, 0, 2)
                }
                else
                {
                    world.setBlock(x, y - 1, z - 1, Blocks.air, 0, 2)
                    world.setBlock(x, y - 1, z + 1, Blocks.air, 0, 2)
                }

                var var7 = new EntityIronGolem(world)
                var7.setPlayerCreated(false)
                var7.setLocationAndAngles(x + 0.5D, y - 1.95D, z + 0.5D, 0.0F, 0.0F)
                world.spawnEntityInWorld(var7)

                for (var8 <- 0 until 120)
                    world.spawnParticle("snowballpoof", x + world.rand.nextDouble(), (y - 2) + world.rand.nextDouble() * 3.9D, z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D)

                world.notifyBlockChange(x, y, z, Blocks.air)
                world.notifyBlockChange(x, y - 1, z, Blocks.air)
                world.notifyBlockChange(x, y - 2, z, Blocks.air)

                if (var5)
                {
                    world.notifyBlockChange(x - 1, y - 1, z, Blocks.air)
                    world.notifyBlockChange(x + 1, y - 1, z, Blocks.air)
                }
                else
                {
                    world.notifyBlockChange(x, y - 1, z - 1, Blocks.air)
                    world.notifyBlockChange(x, y - 1, z + 1, Blocks.air)
                }
            }
        }*/
        else if (PowerBlock.PowerBlockAddoner != null && PowerBlock.PowerBlockAddoner.nonEmpty) {
            for (addon <- PowerBlock.PowerBlockAddoner)
                if (addon.triggerBlockAdd(world, pos, state))
                    return
        }
    }

    protected def getGolemBasePattern: BlockPattern = {
        if (golemBasePattern == null)
            golemBasePattern = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.air))).build()

        golemBasePattern
    }
}

object PowerBlock {
    var PowerBlockAddoner: Vector[PowerBlockAddonTrigger] = Vector.empty

    var PowerBlockAddonerNoStop: Vector[PowerBlockAddonTrigger] = Vector.empty

    def addPowerBlockAddon(block: PowerBlockAddonTrigger) = PowerBlockAddoner = block +: PowerBlockAddoner

    def addPowerBlockAddonNoStop(block: PowerBlockAddonTrigger) = PowerBlockAddonerNoStop = block +: PowerBlockAddonerNoStop
}

trait PowerBlockAddonTrigger {
    def triggerBlockAdd(world: World, pos: BlockPos, state: IBlockState): Boolean
}
