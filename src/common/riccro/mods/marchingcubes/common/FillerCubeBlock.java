package riccro.mods.marchingcubes.common;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import riccro.mods.marchingcubes.client.RenderMarchingCubes;
import cpw.mods.fml.common.FMLLog;

public class FillerCubeBlock extends Block
{
	public FillerCubeBlock(int id, int texture)
	{
		super(id, texture, Material.ground);
		this.setCreativeTab(CreativeTabs.tabBlock);	
	}

    /**
     * The type of render function that is called for this block
     */
	@Override
	public int getRenderType()
    {
        return RenderMarchingCubes.renderId;
    }
    
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
	@Override
    public boolean isCollidable()
    {
        return true;//false;
    }
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
	@Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		FMLLog.info("onBlockAdded [%d %d %d]", par2, par3, par4);
	}

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
		if (par5 == blockID)
			FMLLog.info("onNeighborBlockChange [%d %d %d] MCB", par2, par3, par4);
		else
			FMLLog.info("onNeighborBlockChange [%d %d %d] %d", par2, par3, par4, par5);
    }
}
