package gg.kar.karsoundsgui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
        implements CommandExecutor {

    private static Main instance;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.BLACK + "相信我,这不是广告,恩");

        instance = this;

    }

    //相信我，这里没有后门QAQ
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sounds")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Warehouse wh = new Warehouse("Sounds");
                for (Sound s : Sound.values()) {
                    wh.addItem(new GUIItem(new ItemStackFactory(Material.NOTE_BLOCK)
                            .setDisplayName(s.name())
                            .toItemStack()) {
                                @Override
                                public void ClickAction(ClickType type, Player u) {
                                    p.playSound(p.getLocation(), s, 1f, 1.0f);
                                }
                            });
                }
                wh.openGUI(p);
                return true;
            }
            sender.sendMessage("后台你想咋样");
            return true;
        }
        return true;
    }

    //获得本类的实例
    public static Main getInstance() {
        return instance;
    }

}
