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
package net.fishdog5000.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFlexibleTNTPrimed extends EntityTNTPrimed {
    public float radius;
    public boolean damage;

    public EntityFlexibleTNTPrimed(World worldIn) {
        super(worldIn);
    }

    public EntityFlexibleTNTPrimed(World world, double x, double y, double z, EntityLivingBase base) {
        super(world, x, y, z, base);
    }

    /*
     * Created to modify private explode method.
     */
    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.03999999910593033D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863D;
        motionY *= 0.9800000190734863D;
        motionZ *= 0.9800000190734863D;

        if (onGround) {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
            motionY *= -0.5D;
        }

        if (/*fuse-- <= 0 todo fix*/ true) {
            setDead();

            if (!worldObj.isRemote) {
                explode();
            }
        } else {
            handleWaterMovement();
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound tags) {
        super.writeEntityToNBT(tags);
        tags.setBoolean("DamageLand", damage);
        tags.setFloat("ExplosionRadius", radius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound tags) {
        super.readEntityFromNBT(tags);
        radius = tags.getFloat("ExplosionRadius");
        damage = tags.getBoolean("DamageLand");
    }

    protected void explode() {
        worldObj.createExplosion(this, posX, posY + height / 2.0F, posZ, radius, damage);
    }
}
