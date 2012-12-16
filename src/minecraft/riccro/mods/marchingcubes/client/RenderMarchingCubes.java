package riccro.mods.marchingcubes.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.src.Block;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Tessellator;
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

    /**
     * Checks for an OpenGL error. If there is one, prints the error ID and error string.
     */
    private void checkGLError(String par1Str)
    {
        int var2 = GL11.glGetError();

        if (var2 != 0)
        {
            String var3 = GLU.gluErrorString(var2);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + par1Str);
            System.out.println(var2 + ": " + var3);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
    	//checkGLError("RMC");
    	
    	Tessellator var5 = Tessellator.instance;
        if (var5.isDrawing)
        {
        	// Call draw() to clear out existing block drawing.
        	var5.draw();
        }

        int mode = var5.drawMode;

        var5.startDrawing(GL11.GL_TRIANGLES);
        //var5.setColorOpaque_I(block.getBlockColor());
        var5.setColorOpaque_I(128);
        
        //GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

        int var6 = block.blockIndexInTexture;
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        double var10 = (double)((float)var7 / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

        var5.addVertexWithUV(x+0.5, y+0.5, z+0.5, var10, var14);
        var5.addVertexWithUV(x+1.5, y+0.5, z+1.5, var12, var14);
        var5.addVertexWithUV(x-1.5, y+0.5, z+1.5, var12, var16);
        
        var5.draw();

        GL11.glEnable(GL11.GL_CULL_FACE);
        //GL11.glEnable(GL11.GL_LIGHTING);

        //checkGLError("RMC");

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
