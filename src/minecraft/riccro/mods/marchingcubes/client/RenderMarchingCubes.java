package riccro.mods.marchingcubes.client;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;

import riccro.mods.marchingcubes.common.FillerCubeBlock;
import riccro.mods.marchingcubes.common.marchingcubes;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderMarchingCubes implements ISimpleBlockRenderingHandler
{
    public static int renderId;

    public RenderMarchingCubes(int renderId)
    {
        this.renderId = renderId;
    }

    @Override
    public int getRenderId()
    {
        return renderId;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return false;
    }

	public static ChunkCoordinates[] faceBlockOffsets = 
	{
		new ChunkCoordinates( 0,  0,  1),
		new ChunkCoordinates( 0,  0, -1),
		new ChunkCoordinates(-1,  0,  0),
		new ChunkCoordinates( 1,  0,  0),
		new ChunkCoordinates( 0,  1,  0),
		new ChunkCoordinates( 0, -1,  0)
	};
	
	public static int[] faceBlockIds = new int[faceBlockOffsets.length]; 
	
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
    	Tessellator var5 = Tessellator.instance;
        if (var5.isDrawing)
        {
        	// Call draw() to clear out existing block drawing.
        	var5.draw();
        }

        int mode = var5.drawMode;

        var5.startDrawing(GL11.GL_TRIANGLES);
        
        var5.setColorOpaque_I(block.getBlockColor());
        //var5.setColorOpaque_I(128);
        
        //GL11.glDisable(GL11.GL_CULL_FACE);

        int var6 = block.blockIndexInTexture;
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        double var10 = (double)((float)var7 / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

        if (block.blockID == marchingcubes.instance.fillerCubeBlock.blockID)
        {
            for (int var9 = 0; var9 < faceBlockOffsets.length; var9++)
            {
                int id = world.getBlockId(	x + faceBlockOffsets[var9].posX, 
            								y + faceBlockOffsets[var9].posY,
            								z + faceBlockOffsets[var9].posZ);
                
               	faceBlockIds[var9] = id;
            }
	        
            if (faceBlockIds[5] > 0)
            {
	            var5.addVertexWithUV(x+0.5, y+0.5, z+0.5, var10, var14);
		        var5.addVertexWithUV(x,     y    , z+1.0, var12, var14);
		        var5.addVertexWithUV(x+1.0, y    , z+1.0, var12, var16);
            }
        }
        else
        if (block.blockID == marchingcubes.instance.marchingCubeBlock.blockID)
        {
        }
        
        var5.draw();

        //GL11.glEnable(GL11.GL_CULL_FACE);

        // After changing to GL_TRIANGLES, we need 
        // to change back to the previous draw mode.
        var5.startDrawing(mode);
        return true;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

    }
	
}
