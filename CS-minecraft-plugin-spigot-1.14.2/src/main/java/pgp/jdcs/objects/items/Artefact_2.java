package pgp.jdcs.objects.items;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import strings.Strings;

public class Artefact_2 extends ItemStack {
        public Artefact_2() {
                setType(Material.PHANTOM_MEMBRANE);

                setAmount(1);
                ItemMeta meta = getItemMeta();

                ArrayList<String> lore = new ArrayList<>();
                lore.add(0,Strings.Artefact_2_Lore_1);
                lore.add(1,Strings.Artefact_2_Lore_1);
                lore.add(2,Strings.Artefact_2_Lore_1);
                lore.add(3,Strings.Artefact_2_Lore_1);
                lore.add(4,Strings.Artefact_2_Lore_1);
                meta.addEnchant(Enchantment.FIRE_ASPECT, 13, true);

                meta.setDisplayName("ART2");
                meta.setLore(lore);
                setItemMeta(meta);

        }

}