package mod_MarchingCube.common;

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "mod_MarchingCube", name = "Marching Cube Block", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class mod_MarchingCube
{
	public static Block marchingCubeBlock;
	public static Block fillerCubeBlock;
	
	@SidedProxy(clientSide = "mod_MarchingCube.client.ClientProxy", serverSide = "mod_MarchingCube.common.CommonProxy")
    public static CommonProxy proxy;

	@Instance("mod_MarchingCube")
	public static mod_MarchingCube instance;
	
	private static int[] reservedBlockIds = { 250, 251 };

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			cfg.load();
			reservedBlockIds[0] = cfg.getTerrainBlock(Configuration.CATEGORY_BLOCK, "marchingCubeBlock", 250, "Marching cube block").getInt(250);
			reservedBlockIds[1] = cfg.getTerrainBlock(Configuration.CATEGORY_BLOCK, "fillerCubeBlock", 251, "Filler cube block").getInt(251);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "mod_MarchingCube has a problem loading it's configuration");
		}
		finally
		{
			cfg.save();
		}
	}

	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderThings();
		
		// Constructor args: Block Id, Texture page location
		marchingCubeBlock = new MarchingCubeBlock(reservedBlockIds[0], 0)
								.setBlockName("marchingCubeBlock")
								.setHardness(0.5f);
		
		fillerCubeBlock = new FillerCubeBlock(reservedBlockIds[1], 0)
								.setBlockName("fillerCubeBlock")
								.setHardness(0.5f);
		
		GameRegistry.registerBlock(marchingCubeBlock);
		GameRegistry.registerBlock(fillerCubeBlock);
		
		LanguageRegistry.addName(marchingCubeBlock, "Marching Cube Block");
		LanguageRegistry.addName(fillerCubeBlock, "Filler Cube Block");
	}

}
