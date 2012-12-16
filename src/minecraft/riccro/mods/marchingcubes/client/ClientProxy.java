package riccro.mods.marchingcubes.client;

import riccro.mods.marchingcubes.common.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	private static RenderMarchingCubes	marchingCubesRenderer;
	
	@Override
    public void registerRenderThings()
    {
		marchingCubesRenderer = new RenderMarchingCubes(RenderingRegistry.getNextAvailableRenderId());
        
        RenderingRegistry.instance().registerBlockHandler(marchingCubesRenderer.renderId, marchingCubesRenderer);
    }
}
