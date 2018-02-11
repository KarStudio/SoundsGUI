package gg.kar.karsoundsgui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * 这个是之前在一个生存服看到全球市场功能时触发的灵感，
 * 将所有的商品展示在一个大箱子的GUI上，并可以翻页
 * 你可以用它来做邮箱、大型生存服的小镇传送的GUI等
 * 
 * @author LSeng
 */
public class Warehouse {
    
    GUI gui;
    List<GUIItem> items = new ArrayList();
    String tempName;
    int pageChoose = 0;
    
    private Warehouse() {
    }
    
    public Warehouse(String name) {
        this.gui = new GUI(GUIType.SIXBYNINE, name);
        
        //胡乱排版中...
        for (int i = 36; i < 45; i++) {
            this.gui.setItem(i, new GUIItem(new ItemStackFactory(Material.STAINED_GLASS_PANE)
                    .setDisplayName(" ")
                    .toItemStack()));
        }
        
        this.gui.setItem(new GUIItem(new ItemStackFactory(Material.STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .toItemStack()), 48,49, 50);
        
        this.gui.setItem(46, new GUIItem(new ItemStackFactory(Material.REDSTONE)
                .setDisplayName("§c关闭")
                .toItemStack()) {
                    @Override
                    public void ClickAction(ClickType type, Player u) {
                        u.closeInventory();
                    }
                });
        
    }
    
    /**
     * 得到该GUI某个角标的Item
     * @param index 角标
     * @return 该GUI某个角标的Item
     */
    public final GUIItem getItem(int index) {
        return this.items.get(index);
    }
    
    /**
     * 移除该GUI某个角标的Item
     * @param index 角标
     */
    public final void removeItem(int index) {
        this.items.remove(index);
    }
    
    /**
     * 移除该GUI的一个Item
     * @param item 该Item的指针(壮哉我大C++？)
     */
    public final void removeItem(GUIItem item) {
        this.items.remove(item);
    }
    
    /**
     * 添加一个Item
     * @param item
     */
    public final void addItem(GUIItem item) {
        this.items.add(item);
    }
    
    /**
     * 批量添加Item
     * @param items
     */
    public final void addItem(List<GUIItem> items) {
        for (GUIItem item : items) {
            addItem(item);
        }
    }
    
    /**
     * 批量添加Item
     * @param items
     */
    public final void addItem(GUIItem... items) {
        for (GUIItem item : items) {
            addItem(item);
        }
    }
    
    /**
     * 获得所有的Item
     * @return 所有的Item
     */
    public final List<GUIItem> getItems() {
        return this.items;
    }
    
    /**
     * 获得该GUI
     * @return
     */
    public GUI getGUI() {
        return getGUI(this.items);
    }
    
    /**
     * 在一个List<GUIItem>中提取Items，并自动排版(实际上是对这堆烂摊子进行整理)
     * @return 排版后的GUI
     */
    private GUI getGUI(List<GUIItem> items) {
        int page = items.size() / 36 + 1; //总页数
        //清空原来在GUI显示的所有Item
        for (int i = 0; i < 36; i++) {
            gui.setItem(i, new GUIItem(new ItemStack(0)));
        }
        
        //注释无从下手...   我相信你们能看懂(就偷个懒还不行么)。
        int index = 0;
        if (pageChoose == page - 1) {//如果选择的是最后一页
            if (pageChoose == 0) {
                for (int i = 0; i < items.size() % 36; i++) {
                    gui.setItem(index, items.get(i));
                    index++;
                }
            } else {
                for (int i = this.pageChoose * 36; i < pageChoose * 36 + items.size() % 36; i++) {
                    gui.setItem(index, items.get(i));
                    index++;
                }
            }
        } else if (pageChoose == 0) {//如果选择的是第一页
            for (int i = 0; i < 36; i++) {
                gui.setItem(index, items.get(i));
                index++;
            }
        } else {//其他情况
            for (int i = this.pageChoose * 36; i < this.pageChoose * 36 + 36; i++) {
                gui.setItem(index, items.get(i));
                index++;
            }
        }
        
        //对该GUI最后一行做点小修改
        if (this.pageChoose == 0) {
            gui.setItem(51, new GUIItem(new ItemStackFactory(192)
                    .setDisplayName("&c这已经是首页了哦")
                    .toItemStack()));
        } else {
            gui.setItem(51, new GUIItem(new ItemStackFactory(Material.FENCE)
                    .setDisplayName("&e上一页")
                    .toItemStack()) {
                        @Override
                        public void ClickAction(ClickType type, Player u) {
                            Warehouse.this.pageChoose--;
                            openGUI(u);
                        }
                    });
        }
        
        gui.setItem(52, new GUIItem(new ItemStackFactory(Material.PAPER, pageChoose + 1)
                .setDisplayName("&7第 &f&l"+(pageChoose + 1)+" &7页")
                .toItemStack()));
        
        if (this.pageChoose < page - 1) {
            gui.setItem(53, new GUIItem(new ItemStackFactory(Material.FENCE)
                    .setDisplayName("&e下一页")
                    .toItemStack()) {
                        @Override
                        public void ClickAction(ClickType type, Player u) {
                            Warehouse.this.pageChoose++;
                            openGUI(u);
                        }
                    });
        } else {
            gui.setItem(53, new GUIItem(new ItemStackFactory(192)
                    .setDisplayName("&c这已经是最后一页了哦")
                    .toItemStack()));
        }
        
        return this.gui;
    }
    
    /**
     * 打开该GUI
     * @param p 打开该GUI的玩家
     */
    public void openGUI(Player p) {
        getGUI().openGUI(p);
    }
    
}

