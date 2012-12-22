package riccro.mods.marchingcubes.common;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import riccro.mods.marchingcubes.client.RenderMarchingCubes;
import cpw.mods.fml.common.FMLLog;

public class FillerCubeBlock extends Block
{
	public FillerCubeBlock(int id, int texture)
	{
		super(id, texture, Material.ground);
		this.setCreativeTab(CreativeTabs.tabBlock);	

		neighborChanged = false;
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
		return true;
	}

	public static ChunkCoordinates[] faceOffsets = 
		{
		new ChunkCoordinates(-1,  0,  0),	// Left
		new ChunkCoordinates( 1,  0,  0),	// Right
		new ChunkCoordinates( 0,  1,  0),	// Up
		new ChunkCoordinates( 0, -1,  0),	// Down
		new ChunkCoordinates( 0,  0,  1),	// Forward
		new ChunkCoordinates( 0,  0, -1)	// Back
		};

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int count = 0;
		int thisCount = 0;

		for (int i = faceOffsets.length-1; i >= 0; i--)
		{
			int var5 = par1World.getBlockId(par2+faceOffsets[i].posX, par3+faceOffsets[i].posY, par4+faceOffsets[i].posZ);

			if ((var5 > 0 && !blocksList[var5].blockMaterial.isGroundCover()))
				count++;
			
			if (var5 == blockID)
				thisCount++;
		}

		return count > thisCount;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		neighborChanged = false;

		FMLLog.info("onBlockAdded [%d %d %d]", par2, par3, par4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		neighborChanged = true;

		if (par5 == blockID)
			FMLLog.info("onNeighborBlockChange [%d %d %d] MCB", par2, par3, par4);
		else
			FMLLog.info("onNeighborBlockChange [%d %d %d] %d", par2, par3, par4, par5);
	}

	public boolean neighborChanged;

}
