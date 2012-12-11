package mod_MarchingCube.common;

import net.minecraft.src.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "mod_MarchingCube", name = "Marching Cube Block", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class mod_MarchingCube
{
	public static Block marchingCubeBlock;
	
	@SidedProxy(clientSide = "mod_MarchingCube.client.ClientProxy", serverSide = "mod_MarchingCube.common.CommonProxy")
    public static CommonProxy proxy;

	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderThings();
		
		// Id, Texture page location
		marchingCubeBlock = new MarchingCubeBlock(250, 0).setBlockName("marchingCubeBlock").setHardness(0.5f);
		
		GameRegistry.registerBlock(marchingCubeBlock);
		LanguageRegistry.addName(marchingCubeBlock, "Marching Cube Block");
	}

}
