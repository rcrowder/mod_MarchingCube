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
				new ChunkCoordinates(0,  0,  1),	// Forward
				new ChunkCoordinates(0,  1,  1),
				new ChunkCoordinates(0,  1,  0),	// Up
				new ChunkCoordinates(0,  1, -1),
				new ChunkCoordinates(0,  0, -1),	// Back
				new ChunkCoordinates(0, -1, -1),
				new ChunkCoordinates(0, -1,  0),	// Down
				new ChunkCoordinates(0, -1,  1)
			},
			// Left and Right
			{
				new ChunkCoordinates( 1, -1,  0),
				new ChunkCoordinates( 1,  0,  0),	// Left
				new ChunkCoordinates( 1,  1,  0),
				new ChunkCoordinates(-1,  1,  0),
				new ChunkCoordinates(-1,  0,  0),	// Right
				new ChunkCoordinates(-1, -1,  0)
			}
		};
	
	public static int[] faceBlockIds = new int[9]; 
	
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
        
        // TODO: Neighbour colours
        var5.setColorOpaque_I(block.getBlockColor());
        
        GL11.glDisable(GL11.GL_CULL_FACE);

        int var6 = block.blockIndexInTexture;
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        double var10 = (double)((float)var7 / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

        if (block.blockID == marchingcubes.instance.fillerCubeBlock.blockID)
        {
            for (int faceBlockCount = 0; faceBlockCount < faceOffsets[0].length; faceBlockCount += 2)
            {
            	int id = 0;
            	
            	int edgeBlockCount = 0;
            	
            	boolean validEdge = false;
            	
        		int index = 0;
        		
        		float yOffset = 0.0f;
        		float zOffset = 0.0f;
        		
            	for (int edgeBlockIndex = 0; edgeBlockIndex >= -2; edgeBlockIndex--)
            	{
            		index = faceBlockCount + edgeBlockIndex;
            		
            		if (index < 0)
            			index += faceOffsets[0].length;
           			
            		yOffset += faceOffsets[0][index].posY;
            		zOffset += faceOffsets[0][index].posZ;
            		
	                id = world.getBlockId(	x + faceOffsets[0][index].posX, 
	            							y + faceOffsets[0][index].posY,
	            							z + faceOffsets[0][index].posZ);
	               	
	               	if (id >= 0 && Block.blocksList[id] != null)
	               	{
	               		// Jump out early, we have hit a block straight away
	               		//if (id > 0 && edgeBlockIndex == 0)
	               		//	break;
	               		
	               		if (id > 0)
	               		{
	               			edgeBlockCount++;
	               		}
	               		
	               		if (edgeBlockCount == 1 && edgeBlockIndex == -2)
	               			validEdge = true;
	               		
	               		if (edgeBlockCount == 2)
	               			validEdge = true;
	               	}
            	}

            	//faceBlockIds[faceBlockCount / 2] = id;

            	//if (validEdge && edgeBlockCount > 0)
            	{
            		// Normalise offset vector
            		yOffset /= 4;
            		zOffset /= 4;
            		
            		// Block centre
    	            var5.addVertexWithUV(
    	            		x + 0.5, 
    	            		y + 0.5, 
    	            		z + 0.5, 
    	            		var10, var14);
    	            
    		        var5.addVertexWithUV(
    		        		x, 
    		        		y + 0.5 + yOffset, 
    		        		z + 0.5 + zOffset, 
    		        		var12, var14);
    		        
    		        var5.addVertexWithUV(
    		        		x + 1.0, 
    		        		y + 0.5 + yOffset, 
    		        		z + 0.5 + zOffset, 
    		        		var12, var16);

    		        var6++;
    		        var7 = (var6 & 15) << 4;
    		        var8 = var6 & 240;
    		        var10 = (double)((float)var7 / 256.0F);
    		        var12 = (double)(((float)var7 + 15.99F) / 256.0F);
    		        var14 = (double)((float)var8 / 256.0F);
    		        var16 = (double)(((float)var8 + 15.99F) / 256.0F);
            	}
            }
       		
            //getFirstUncoveredBlock
        }
        else
        if (block.blockID == marchingcubes.instance.marchingCubeBlock.blockID)
        {
        }
        
        var5.draw();

        GL11.glEnable(GL11.GL_CULL_FACE);

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
