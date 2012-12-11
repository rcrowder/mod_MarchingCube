package mod_MarchingCube.common;

import mod_MarchingCube.client.RenderMarchingCube;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

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
}
