package gg.kar.karsoundsgui;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 简易的GUI API，我觉得大部分GUI API的思想大同小异
 * 相信我，这段代码没有抄袭也没有借鉴QAQ
 * 真的，诶，别咬我啊老铁
 * 
 * @author LSeng
 */
public class GUI {

    static Map<Player,GUI> openGUI = new HashMap();
    
    GUIType type;
    String tempName;
    public GUIItem[] items;
    public Inventory inv;

    private GUI() {
    }

    public GUI(GUIType type, String name) {
        this.type = type;
        this.tempName = name;
        switch (type) {
            default:
                this.items = new GUIItem[36];
            case ONEBYNINE:
                this.items = new GUIItem[9];
                break;
            case TWOBYNINE:
                this.items = new GUIItem[18];
                break;
            case THREEBYNINE:
                this.items = new GUIItem[27];
                break;
            case FOURBYNINE:
                this.items = new GUIItem[36];
                break;
            case FIVEBYNINE:
                this.items = new GUIItem[45];
                break;
            case SIXBYNINE:
                this.items = new GUIItem[54];
                break;
        }

        //注册一个新的监听器来执行ClickAction功能
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onInventoryClickEvent(InventoryClickEvent event) {
                if (!(event.getWhoClicked() instanceof Player)) {
                    return;
                }
                Player p = (Player) event.getWhoClicked();
                if (openGUI.containsKey(p) && openGUI.get(p) == GUI.this) {
                    event.setCancelled(true);

                    if (event.getSlot() != -999 && items[event.getSlot()] != null) {
                        items[event.getSlot()].ClickAction(event.getClick(), p);
                    }
                }
            }

            //当玩家关掉GUI时,注销这个监听器
            @EventHandler
            public void onInventoryCloseEvent(InventoryCloseEvent event) {
                if(event.getInventory() == inv){
                    HandlerList.unregisterAll(this);
                    openGUI.remove((Player)event.getPlayer());
                }
            }
            
        }, Main.getInstance());

    }

    /**
     * 批量设置同样的Item(不规则分布分割线？)
     * @param item 
     * @param index 
     */
    public final void setItem(GUIItem item, int... index) {
        for (int i : index) {
            setItem(i, item);
        }
    }

    /**
     * 设置一个Item
     * @param index
     * @param item
     */
    public final void setItem(int index, GUIItem item) {
        if (item == null) {
            this.items[index] = new GUIItem(new ItemStack(0));
        } else {
            this.items[index] = item;
        }
    }

    /**
     * 得到某个角标上的Item
     * @param index
     * @return
     */
    public final GUIItem getItem(int index) {
        return this.items[index];
    }

    /**
     * 打开这个GUI
     * @param p
     */
    public void openGUI(Player p) {
        Inventory inv;
        if (this.type == GUIType.CANCEL) {
            throw new NullPointerException("被取消或不存在的GUI");
        }
        inv = Bukkit.createInventory(null, this.items.length, this.tempName);
        for (int index = 0; index < this.items.length; index++) {
            if (items[index] == null) {
                inv.setItem(index, new ItemStack(Material.AIR));
            } else {
                inv.setItem(index, items[index].display);
            }
        }
        this.inv = inv;
        p.getPlayer().openInventory(inv);
        openGUI.put(p, this);//记录玩家打开的这个GUI(当该GUI被关闭时会移除)

    }

}
