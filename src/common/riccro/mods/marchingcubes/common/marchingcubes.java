package riccro.mods.marchingcubes.common;

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

@Mod(modid = "riccro.mods.marchingcubes", name = "Marching Cube Blocks", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class marchingcubes
{
	public static Block marchingCubeBlock;
	public static Block fillerCubeBlock;
	
	@SidedProxy(clientSide = "riccro.mods.marchingcubes.client.ClientProxy", serverSide = "riccro.mods.marchingcubes.common.CommonProxy")
    public static CommonProxy proxy;

	@Instance("marchingcubes")
	public static marchingcubes instance;
	
	private static int[] reservedBlockIds = { 250, 251 };

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			cfg.load();
			reservedBlockIds[0] = cfg.get(Configuration.CATEGORY_BLOCK, "marchingCubeBlockId", 250).getInt(250);
			reservedBlockIds[1] = cfg.get(Configuration.CATEGORY_BLOCK, "fillerCubeBlockId", 251).getInt(251);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "marchingcubes has a problem loading it's configuration");
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
		marchingCubeBlock = new MarchingCubeBlock(reservedBlockIds[0], 2)
								.setBlockName("marchingCubeBlock")
								.setHardness(0.5f);
		
		fillerCubeBlock = new FillerCubeBlock(reservedBlockIds[1], 2)
								.setBlockName("fillerCubeBlock")
								.setHardness(0.5f);
		
		GameRegistry.registerBlock(marchingCubeBlock);
		GameRegistry.registerBlock(fillerCubeBlock);
		
		LanguageRegistry.addName(marchingCubeBlock, "Marching Cube Block");
		LanguageRegistry.addName(fillerCubeBlock, "Filler Cube Block");
	}

}
