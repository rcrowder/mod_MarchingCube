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

	public static ChunkCoordinates[][] faceOffsets = 
		{
			// Forward first (back-spin like a foosball player)
			{
				new ChunkCoordinates( 0,  0,  1),	// Forward
				new ChunkCoordinates( 0,  1,  1),
				new ChunkCoordinates( 0,  1,  0),	// Up
				new ChunkCoordinates( 0,  1, -1),
				new ChunkCoordinates( 0,  0, -1),	// Back
				new ChunkCoordinates( 0, -1, -1),
				new ChunkCoordinates( 0, -1,  0),	// Down
				new ChunkCoordinates( 0, -1,  1)
			},
			// Left first (spin clockwise like a Catherine wheel) 
			{
				new ChunkCoordinates(-1,  0,  0),	// Left
				new ChunkCoordinates(-1,  1,  0),
				new ChunkCoordinates( 0,  1,  0),	// Up
				new ChunkCoordinates( 1,  1,  0),
				new ChunkCoordinates( 1,  0,  0),	// Right
				new ChunkCoordinates( 1, -1,  0),
				new ChunkCoordinates( 0, -1,  0),	// Down
				new ChunkCoordinates(-1, -1,  0)
			},
			// Forward first (rotate clockwise on the spot)
			{
				new ChunkCoordinates( 0,  0,  1),	// Forward
				new ChunkCoordinates( 1,  0,  1),
				new ChunkCoordinates( 1,  0,  0),	// Right
				new ChunkCoordinates( 1,  0, -1),
				new ChunkCoordinates( 0,  0, -1),	// Back
				new ChunkCoordinates(-1,  0, -1),
				new ChunkCoordinates(-1,  0,  0),	// Left
				new ChunkCoordinates(-1,  0,  1)
			},
		};

    public static void fillerBlockRenderer(Tessellator var5, int offsetIndex, IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        // TODO: Neighbour colours
        //var5.setColorOpaque_I(block.getBlockColor());
        
        int var6 = block.blockIndexInTexture;

        for (int faceBlockCount = 0; faceBlockCount < faceOffsets[offsetIndex].length; faceBlockCount += 2)
        {
        	boolean connectedEdge = false;
        	
        	int edgeBlockCount = 0;
        	
    		float xOffset = 0.0f;
    		float yOffset = 0.0f;
    		float zOffset = 0.0f;
    		
    		int edgeBlockIndex = 0;
        	for (; edgeBlockIndex >= -2; edgeBlockIndex--)
        	{
        		int index = faceBlockCount + edgeBlockIndex;
        		
        		if (index < 0)
        			index += faceOffsets[offsetIndex].length;
       			
        		xOffset += faceOffsets[offsetIndex][index].posX;
        		yOffset += faceOffsets[offsetIndex][index].posY;
        		zOffset += faceOffsets[offsetIndex][index].posZ;
        		
                int id = world.getBlockId(	x + faceOffsets[offsetIndex][index].posX, 
            								y + faceOffsets[offsetIndex][index].posY,
            								z + faceOffsets[offsetIndex][index].posZ);
               	
               	if (id > 0 && Block.blocksList[id] != null)
               	{
           			edgeBlockCount++;
               	
           			if (id == marchingcubes.instance.fillerCubeBlock.blockID)
           				connectedEdge = true;
               	}
        	}

        	if (edgeBlockCount > 0 && edgeBlockCount < 3)
        	{
        		// Normalise offset vector
        		xOffset /= 4.0;
        		yOffset /= 4.0;
        		zOffset /= 4.0;
                
                int var7 = (var6 & 15) << 4;
                int var8 = var6 & 240;
                double var10 = (double)((float)var7 / 256.0F);
                double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
                double var14 = (double)((float)var8 / 256.0F);
                double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

	            if (!connectedEdge) 
	            {		            
			        // Block centre
		            var5.addVertexWithUV(x + 0.5, y + 0.5, z + 0.5, var10, var14);
		            
		            if (offsetIndex == 0)
		            {
				        var5.addVertexWithUV(x,       y + 0.5 + yOffset, z + 0.5 + zOffset, var12, var14);
				        var5.addVertexWithUV(x + 1.0, y + 0.5 + yOffset, z + 0.5 + zOffset, var12, var16);
		            }
		            else
		            if (offsetIndex == 1)
		            {
				        var5.addVertexWithUV(x + 0.5 + xOffset, y + 0.5 + yOffset, z,       var12, var14);
				        var5.addVertexWithUV(x + 0.5 + xOffset, y + 0.5 + yOffset, z + 1.0, var12, var16);
		            }
		            else
		            {
				        var5.addVertexWithUV(x + 0.5 + xOffset, y,       z + 0.5 + zOffset, var12, var14);
				        var5.addVertexWithUV(x + 0.5 + xOffset, y + 1.0, z + 0.5 + zOffset, var12, var16);
		            }
	            }
	            else
	            {
	            	
	            }
	            
	            // Advance page offset
		        var6++;
        	}
        }
   		
        //getFirstUncoveredBlock
    }

    public static void marchingCubeBlockRenderer(Tessellator var5, int offsetIndex, IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
    	
    }

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
        
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (block.blockID == marchingcubes.instance.fillerCubeBlock.blockID)
        {
        	// YZ Plane
        	fillerBlockRenderer(var5, 0, world, x, y, z, block, modelId, renderer);
        	
        	// XY Plane
        	fillerBlockRenderer(var5, 1, world, x, y, z, block, modelId, renderer);
        	
        	// XZ Plane
        	fillerBlockRenderer(var5, 2, world, x, y, z, block, modelId, renderer);
        }
        else
        if (block.blockID == marchingcubes.instance.marchingCubeBlock.blockID)
        {
        	marchingCubeBlockRenderer(var5, 0, world, x, y, z, block, modelId, renderer);
        }
        
        var5.draw();

        GL11.glEnable(GL11.GL_CULL_FACE);

        // After changing to GL_TRIANGLES, we need 
        // to change back to the previous draw mode
        var5.startDrawing(mode);
        return true;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

    }
	
}
