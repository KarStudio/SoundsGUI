package gg.kar.karsoundsgui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * GUI的项目，包含显示的样子和点击事件
 * @author LSeng
 */
public class GUIItem {
    
    ItemStack display;
    
    public GUIItem(ItemStack display){
        this.display = display;
    }
    
    /**
     * 点击事件，通常需要被覆盖重写
     * @param type
     * @param p
     */
    public void ClickAction(ClickType type,Player p){
        
    }
    
    
}
