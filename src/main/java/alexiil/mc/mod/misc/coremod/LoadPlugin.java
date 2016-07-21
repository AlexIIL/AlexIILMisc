package alexiil.mc.mod.misc.coremod;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.TransformerExclusions({ "alexiil.mc.mod.misc.coremod" })
@IFMLLoadingPlugin.SortingIndex()
public class LoadPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { ClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;// "alexiil.mods.basicutils.coremod.AlexIILModContainer";
    }

    @Override
    public String getSetupClass() {
        return null;// "alexiil.mods.lib.coremod.AlexIILSetupClass";
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
