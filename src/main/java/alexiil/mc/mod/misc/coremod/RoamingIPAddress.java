package alexiil.mc.mod.misc.coremod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.multiplayer.ServerData;

import net.minecraftforge.common.config.Property;

import alexiil.mc.mod.misc.AlexiilMisc;

public class RoamingIPAddress {
    private static Map<String, String> nameToIP = Collections.synchronizedMap(new HashMap<String, String>());

    public static Property roamingIPLoc = null;

    public static void init() {
        roamingIPLoc = AlexiilMisc.CONFIG.get("roamingIP", "location", "null");
        if (AlexiilMisc.PROP_ROAMING_IP.getBoolean()) {
            loadIPAddresses();
        }
        AlexiilMisc.CONFIG.save();
    }

    /** If its in the form roaming-[name]:port then its a roaming server */
    public static ServerData getModifiedRoamingServerData(ServerData data) {
        if (!AlexiilMisc.PROP_ROAMING_IP.getBoolean()) return data;
        if (!data.serverIP.startsWith("roaming-")) return data;
        int indexHyphen = data.serverIP.indexOf("-");
        if (indexHyphen == -1) return data;
        int indexColon = data.serverIP.indexOf(":");
        String port;
        String name;
        if (indexColon == -1) {
            port = "25565";
            name = data.serverIP.substring(indexHyphen + 1);
        } else {
            port = data.serverIP.substring(indexColon + 1);
            name = data.serverIP.substring(indexHyphen + 1, indexColon);
        }
        String ip;
        if (nameToIP.containsKey(name.intern())) ip = nameToIP.get(name.intern());
        else ip = "invalid";
        ip += ":" + port;
        ServerData newData = new ServerData(name, ip, data.func_181041_d());
        newData.gameVersion = data.gameVersion;
        newData.version = data.version;
        return newData;
    }

    public static void loadIPAddresses() {
        try {
            File ipFile = new File(roamingIPLoc.getString());
            if (ipFile.exists()) {
                String line = "";
                try (BufferedReader br = new BufferedReader(new FileReader(ipFile))) {
                    line = br.readLine();
                    int lineNo = -1;
                    while (line != null) {
                        lineNo++;
                        if (line.length() == 0) AlexiilMisc.LOG.warn("Found an empty line at line #" + lineNo);
                        else if (!line.contains("=")) AlexiilMisc.LOG.warn("Did not find an equals sign at line #" + lineNo + ", \"" + line + "\"");
                        else if (line.indexOf("=") == 0) AlexiilMisc.LOG.warn("Found an equals sign at the start of the line #" + lineNo + ", \"" + line + "\"");
                        else if (line.indexOf("=") == line.length() - 1) AlexiilMisc.LOG.warn("Found an equals sign at the start of the line #" + lineNo + ", \"" + line + "\"");
                        else if (line.indexOf("=") != line.lastIndexOf("=")) AlexiilMisc.LOG.warn("Found more than one equals sign in the line #" + lineNo + ", \"" + line + "\"");
                        else {
                            String[] ip = line.split("=");
                            AlexiilMisc.LOG.info("Added (" + ip[0] + ") -> (" + ip[1] + ")");
                            nameToIP.put(ip[0], ip[1]);
                        }
                        line = br.readLine();
                    }
                } catch (Throwable t) {
                    // ErrorHandling.printStackTrace(t, "loading the line " + line);
                    AlexiilMisc.LOG.warn("loading IP addresses failed (" + t.getMessage() + ")");
                    AlexiilMisc.LOG.warn(t.getClass());
                }
            } else AlexiilMisc.LOG.warn("IP file did not exist!");
        } catch (Throwable t) {
            // ErrorHandling.printStackTrace(t, "opening the IP address file");
            AlexiilMisc.LOG.warn("Could not open IP address file (" + t.getMessage() + ")");
            AlexiilMisc.LOG.warn(t.getClass() + " @ " + t.getStackTrace()[0].getLineNumber());
        }
    }
}
