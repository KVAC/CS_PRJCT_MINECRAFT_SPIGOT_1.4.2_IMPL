package pgp.jdcs.objects.items;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import strings.Strings;

public class Artefact_1 extends ItemStack {
        private ItemMeta artMeta;
        private ArrayList<String> lore;
        public Artefact_1() {
                setType(Material.CLAY_BALL);
                setAmount(1);

                artMeta = getItemMeta();

                lore = new ArrayList<>();

                lore.add(0,Strings.Artefact_1_Lore_1);
                lore.add(1,Strings.Artefact_1_Lore_1);
                lore.add(2,Strings.Artefact_1_Lore_1);
                lore.add(3,Strings.Artefact_1_Lore_1);
                lore.add(4,Strings.Artefact_1_Lore_1);
                artMeta.addEnchant(Enchantment.FIRE_ASPECT, 13, true);

                artMeta.setDisplayName("ART1");
                artMeta.setLore(lore);
                setItemMeta(artMeta);
                setArtMeta(artMeta);

        }
        public ItemMeta getArtMeta() {
                return artMeta;
        }
        public void setArtMeta(ItemMeta artMeta) {
                this.artMeta = artMeta;
        }
        public ArrayList<String> getLore() {
                return lore;
        }
        public void setLore(ArrayList<String> lore) {
                this.lore = lore;
        }

}