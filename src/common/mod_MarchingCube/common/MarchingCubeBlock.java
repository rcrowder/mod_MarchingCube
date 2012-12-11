package mod_MarchingCube.common;

import mod_MarchingCube.client.RenderMarchingCube;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import cpw.mods.fml.common.FMLLog;

public class MarchingCubeBlock extends Block
{
	public MarchingCubeBlock(int id, int texture)
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
        return RenderMarchingCube.renderId;
    }
    
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return true;//false;
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
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
	@Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
		FMLLog.info("onBlockDestroyedByPlayer [%d %d %d] %d", par2, par3, par4, par5);
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

    /**
     * Called upon block activation (right click on the block.)
     */
	@Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int sideClicked, float dx, float dy, float dz)
    {
		FMLLog.info("onBlockActivated [%d %d %d] (%d [%f %f %f?])", par2, par3, par4, sideClicked,dx,dy,dz);
        return false;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
	@Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		FMLLog.info("onBlockClicked [%d %d %d]", par2, par3, par4);
	}
}
