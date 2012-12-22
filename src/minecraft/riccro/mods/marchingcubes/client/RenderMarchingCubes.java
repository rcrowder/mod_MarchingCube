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

	public static void fillerBlockRenderer(Tessellator var5, int planeIndex, IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		// TODO: Neighbour colours
		//var5.setColorOpaque_I(block.getBlockColor());

		int var6 = block.blockIndexInTexture;

		int var7 = (var6 & 15) << 4;
		int var8 = var6 & 240;

		double var10 = (double)((float)var7 / 256.0F);
		double var11 = (double)(((float)var7 + 7.99F) / 256.0F);
		double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
		double var14 = (double)((float)var8 / 256.0F);
		double var15 = (double)(((float)var8 + 7.99F) / 256.0F);
		double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

		// Loop through blocks adjacent to this block (i.e. adjacent face planes matching this blocks face planes)
		for (int faceBlockCount = 0; faceBlockCount < faceOffsets[planeIndex].length; faceBlockCount += 2)
		{
			boolean fillerCubeAdjacent = false;

			int edgeBlockCount = 0;

			// Direction vector to the common edge (from centre of our block)
			double xOffset = 0.0;
			double yOffset = 0.0;
			double zOffset = 0.0;

			// Loop around the common edge
			for (int edgeBlockIndex = 0; edgeBlockIndex >= -2; edgeBlockIndex--)
			{
				int offsetIndex = faceBlockCount + edgeBlockIndex;

				if (offsetIndex < 0)
					offsetIndex += faceOffsets[planeIndex].length;

				if (!fillerCubeAdjacent)
				{
					xOffset += faceOffsets[planeIndex][offsetIndex].posX;
					yOffset += faceOffsets[planeIndex][offsetIndex].posY;
					zOffset += faceOffsets[planeIndex][offsetIndex].posZ;
				}
				
				int id = world.getBlockId(
						x + faceOffsets[planeIndex][offsetIndex].posX, 
						y + faceOffsets[planeIndex][offsetIndex].posY,
						z + faceOffsets[planeIndex][offsetIndex].posZ);

				if (id > 0 && Block.blocksList[id] != null)
				{
					if (Block.opaqueCubeLookup[id])
						edgeBlockCount++;

					// Filler blocks are opaque, so could them here
					if (id == marchingcubes.instance.fillerCubeBlock.blockID)
					{
						edgeBlockCount++;
						fillerCubeAdjacent = true;
					}
				}
			}

			// At least one block around our common edge?
			if (edgeBlockCount > 0)
			{
				if (edgeBlockCount < 3 && !fillerCubeAdjacent) 
				{		            
					// Normalise offset vector
					xOffset /= 4.0;
					yOffset /= 4.0;
					zOffset /= 4.0;
					
					// Block centre
					var5.addVertexWithUV(x + 0.5, y + 0.5, z + 0.5, var11, var15);

					if (planeIndex == 0)
					{
						// YZ Plane
						var5.addVertexWithUV(x,       y + 0.5 + yOffset, z + 0.5 + zOffset, 
								var10, var15 - ((yOffset * 16.0f) / 256.0f));
						
						var5.addVertexWithUV(x + 1.0, y + 0.5 + yOffset, z + 0.5 + zOffset, 
								var12, var15 - ((yOffset * 16.0f) / 256.0f));
					}
					else
						if (planeIndex == 1)
						{
							// XY Plane
							var5.addVertexWithUV(x + 0.5 + xOffset, y + 0.5 + yOffset, z,       
									var12, var15 - ((yOffset * 16.0f) / 256.0f));
							
							var5.addVertexWithUV(x + 0.5 + xOffset, y + 0.5 + yOffset, z + 1.0, 
									var10, var15 - ((yOffset * 16.0f) / 256.0f));
						}
						else
						{
							// XZ Plane
							var5.addVertexWithUV(x + 0.5 + xOffset, y,       z + 0.5 + zOffset, 
									var11 + ((xOffset * 16.0f) / 256.0f), var16);
							
							var5.addVertexWithUV(x + 0.5 + xOffset, y + 1.0, z + 0.5 + zOffset, 
									var11 + ((xOffset * 16.0f) / 256.0f), var14);
						}
				}
				else
					if (edgeBlockCount < 3 && fillerCubeAdjacent)
					{
						// Block centre
						//var5.addVertexWithUV(x + 0.5, y + 0.5, z + 0.5, var11, var15);

					}
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
			// Call draw() to clear out any existing block drawing.
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
	public boolean shouldRender3DInInventory()
	{
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

	}

}
