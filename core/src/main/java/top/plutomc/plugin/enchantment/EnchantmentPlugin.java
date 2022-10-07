package top.plutomc.plugin.enchantment;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

@Plugin(name = "${name}", version = "${version}") // Don't modify this! Please modify the variables in gradle.properties file!
@ApiVersion(ApiVersion.Target.v1_19)
@Authors({
        @Author("PlutoMC"),
        @Author("DeeChael")
})
@DependsOn({
        @Dependency("NBTAPI")
})
@Website("${website}")
@Description("${description}")
public final class EnchantmentPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static EnchantmentPlugin getInstance() {
        return getPlugin(EnchantmentPlugin.class);
    }

}
