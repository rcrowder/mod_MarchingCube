package mod_MarchingCube.client;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderMarchingCube implements ISimpleBlockRenderingHandler
{
    public static int renderId;

    public RenderMarchingCube(int renderId)
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

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = block.blockIndexInTexture;

        float var9 = 0.015625F;

        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        double var10 = (double)((float)var7 / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);
        
        long var18 = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
        var18 = var18 * var18 * 42317861L + var18 * 11L;
        int var20 = (int)(var18 >> 16 & 3L);
        var5.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        
        float var21 = (float)x + 0.5F;
        float var22 = (float)z + 0.5F;
        float var23 = (float)(var20 & 1) * 0.5F * (float)(1 - var20 / 2 % 2 * 2);
        float var24 = (float)(var20 + 1 & 1) * 0.5F * (float)(1 - (var20 + 1) / 2 % 2 * 2);
        
        var5.setColorOpaque_I(block.getBlockColor());
        var5.addVertexWithUV((double)(var21 + var23 - var24), (double)((float)y + var9), (double)(var22 + var23 + var24), var10, var14);
        var5.addVertexWithUV((double)(var21 + var23 + var24), (double)((float)y + var9), (double)(var22 - var23 + var24), var12, var14);
        var5.addVertexWithUV((double)(var21 - var23 + var24), (double)((float)y + var9), (double)(var22 - var23 - var24), var12, var16);
        var5.addVertexWithUV((double)(var21 - var23 - var24), (double)((float)y + var9), (double)(var22 + var23 - var24), var10, var16);
        
        var5.setColorOpaque_I((block.getBlockColor() & 16711422) >> 1);
        var5.addVertexWithUV((double)(var21 - var23 - var24), (double)((float)y + var9), (double)(var22 + var23 - var24), var10, var16);
        var5.addVertexWithUV((double)(var21 - var23 + var24), (double)((float)y + var9), (double)(var22 - var23 - var24), var12, var16);
        var5.addVertexWithUV((double)(var21 + var23 + var24), (double)((float)y + var9), (double)(var22 - var23 + var24), var12, var14);
        var5.addVertexWithUV((double)(var21 + var23 - var24), (double)((float)y + var9), (double)(var22 + var23 + var24), var10, var14);
        
        return true;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

    }
	
}
