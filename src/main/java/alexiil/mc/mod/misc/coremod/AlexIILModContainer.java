package alexiil.mc.mod.misc.coremod;

import java.util.Collections;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

public class AlexIILModContainer extends DummyModContainer {
    public AlexIILModContainer() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "AlexIILLib.CoreMod";
        meta.name = "AlexIILLib core mod";
        meta.version = "";
        meta.credits = "";
        meta.authorList = Collections.singletonList("AlexIIL");
        meta.description = "";
        meta.screenshots = new String[0];
        meta.logoFile = "";
    }
}
