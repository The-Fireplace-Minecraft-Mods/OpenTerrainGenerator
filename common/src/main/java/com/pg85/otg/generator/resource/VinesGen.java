package com.pg85.otg.generator.resource;

import com.pg85.otg.LocalMaterialData;
import com.pg85.otg.LocalWorld;
import com.pg85.otg.OTG;
import com.pg85.otg.configuration.BiomeConfig;
import com.pg85.otg.exception.InvalidConfigException;
import com.pg85.otg.util.minecraftTypes.DefaultMaterial;

import java.util.List;
import java.util.Random;

public class VinesGen extends Resource
{

    public static final int[] d =
    {
        -1, -1, 2, 0, 1, 3
    };
    public static final int[] OPPOSITE_FACING =
    {
        1, 0, 3, 2, 5, 4
    };

    private final int maxAltitude;

    private final int minAltitude;

    public VinesGen(BiomeConfig biomeConfig, List<String> args) throws InvalidConfigException
    {
        super(biomeConfig);
        material = OTG.toLocalMaterialData(DefaultMaterial.VINE, 0);

        assureSize(4, args);
        frequency = readInt(args.get(0), 1, 100);
        rarity = readRarity(args.get(1));
        minAltitude = readInt(args.get(2), OTG.WORLD_DEPTH,
                OTG.WORLD_HEIGHT);
        maxAltitude = readInt(args.get(3), minAltitude,
                OTG.WORLD_HEIGHT);
    }
    public boolean canPlace(LocalWorld world, int x, int y, int z, int paramInt4)
    {
        LocalMaterialData sourceBlock;
        switch (paramInt4)
        {
            default:
                return false;
            case 1:
                sourceBlock = world.getMaterial(x, y + 1, z, false);
                break;
            case 2:
                sourceBlock = world.getMaterial(x, y, z + 1, false);
                break;
            case 3:
                sourceBlock = world.getMaterial(x, y, z - 1, false);
                break;
            case 5:
                sourceBlock = world.getMaterial(x - 1, y, z, false);
                break;
            case 4:
                sourceBlock = world.getMaterial(x + 1, y, z, false);
                break;
        }
        return sourceBlock.isSolid();
    }
    @Override
    public boolean equals(Object other)
    {
        if (!super.equals(other))
            return false;
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (getClass() != other.getClass())
            return false;
        final VinesGen compare = (VinesGen) other;
        return this.maxAltitude == compare.maxAltitude
               && this.minAltitude == compare.minAltitude;
    }

    @Override
    public int getPriority()
    {
        return -50;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + super.hashCode();
        hash = 17 * hash + this.minAltitude;
        hash = 17 * hash + this.maxAltitude;
        return hash;
    }

    @Override
    public String toString()
    {
        return "Vines(" + frequency + "," + rarity + "," + minAltitude + "," + maxAltitude + ")";
    }

    @Override
    public void spawn(LocalWorld world, Random rand, boolean villageInChunk, int x, int z)
    {
        int _x = x;
        int _z = z;
        int y = minAltitude;

        while (y <= maxAltitude)
        {
            if (world.isNullOrAir(_x, y, _z, false))
            {
                for (int direction = 2; direction <= 5; direction++)
                    if (canPlace(world, _x, y, _z, direction))
                    {
                        world.setBlock(_x, y, _z, OTG.toLocalMaterialData(DefaultMaterial.VINE, 1 << d[OPPOSITE_FACING[direction]]), null, false);
                        break;
                    }
            } else
            {
                _x = x + rand.nextInt(4) - rand.nextInt(4);
                _z = z + rand.nextInt(4) - rand.nextInt(4);
            }
            y++;
        }

    }
}
