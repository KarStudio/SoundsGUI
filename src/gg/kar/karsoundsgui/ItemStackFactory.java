package gg.kar.karsoundsgui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 一个简单又实用的快速命名、加注释的工具
 * @author LSeng
 */
public class ItemStackFactory {
    
    ItemStack item;
    
    private ItemStackFactory(){}
    
    public ItemStackFactory(int type){
        this(Material.getMaterial(type),1);
    }
    
    public ItemStackFactory(Material type){
        this(type,1);
    }
    
    public ItemStackFactory(Material type,int amount){
        this(type,amount,(short)0);
    }
    
    public ItemStackFactory(int type,int amount){
        this(Material.getMaterial(type),amount,(short)0);
    }
    
    public ItemStackFactory(Material type,int amount,short data){
        this.item = new ItemStack(type,amount,data);
    }
    
    public ItemStackFactory(int type,int amount,short data){
        this.item = new ItemStack(type,amount,data);
    }
    
    public ItemStackFactory(int type,int amount,int data){
        this(type,amount,(short)data);
    }
    
    public ItemStackFactory(Material type,int amount,int data){
        this(type,amount,(short)data);
    }
    
    /**
     * 由于ItemStackFactory终究不是ItemStack,所以加工完(?)后应转成(?)ItemStack
     * @return
     */
    public ItemStack toItemStack(){
        return this.item;
    }
    
    /**
     * 设置物品的名字
     * @param name
     * @return
     */
    public ItemStackFactory setDisplayName(String name){
        ItemMeta im = this.item.getItemMeta();
        im.setDisplayName(name.replaceAll("&", "§"));
        this.item.setItemMeta(im);
        return this;
    }
    
    /**
     * 设置物品的注释
     * @param lores
     * @return
     */
    public ItemStackFactory setLore(List<String> lores){
        ItemMeta im = this.item.getItemMeta();
        List<String> lores_ = new ArrayList();
        for(String lore : lores){
            lores_.add(lore.replaceAll("&", "§"));
        }
        im.setLore(lores_);
        this.item.setItemMeta(im);
        return this;
    }
    
    /**
     * 添加一条注释
     * @param name
     * @return
     */
    public ItemStackFactory addLore(String name){
        ItemMeta im = this.item.getItemMeta();
        List<String> lores;
        if(im.hasLore()){
            lores = im.getLore();
        }else{
            lores = new ArrayList<>();
        }
        lores.add(name.replaceAll("&", "§"));
        im.setLore(lores);
        this.item.setItemMeta(im);
        return this;
    }
    
}
