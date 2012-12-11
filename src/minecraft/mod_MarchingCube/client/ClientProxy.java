package mod_MarchingCube.client;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends mod_MarchingCube.common.CommonProxy
{
	private static RenderMarchingCube	marchingCubeRenderer;
	
	@Override
    public void registerRenderThings()
    {
		marchingCubeRenderer = new RenderMarchingCube(RenderingRegistry.getNextAvailableRenderId());
        
        RenderingRegistry.instance().registerBlockHandler(marchingCubeRenderer.renderId, marchingCubeRenderer);
    }
}
