package pgp.jdcs.init;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        Logger log = Logger.getLogger("Minecraft"); // Получили логгер маинкрафта

        @Override
        public void onEnable() {
                Bukkit.getPluginManager().registerEvents(new Ender(), this); // Регистрируем класс ендер
                Bukkit.getPluginManager().registerEvents(new Handler(this), this);

                getLogger().info("Включен");
                getCommand("givart").setExecutor(new Heal(this));
                craft();
        }

        @Override
        public void onDisable() {
                getLogger().info("Выключен");
        }

        private void craft() {
                ItemStack item = new ItemStack(Material.ENDER_EYE); // СОздаем вещь, которую мы хотим скрафтить.
                ItemMeta meta = item.getItemMeta(); // Получаем его метаданную
                meta.setDisplayName(ChatColor.GREEN + "Teleporter");
                List<String> lore = new ArrayList<String>(); // Нам нужно установить описание
                lore.add("Ender-teleport"); // И в наше описание добавляем то, что будет написано.
                meta.setLore(lore); // Устанавливаем наше описание.
                meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true); // Установили данному предмету зачарование на дамаг.
                // 3 уровня.
                item.setItemMeta(meta);// Установить метаданные тому предмету, который мы собираемся крафтить.
                // Теперь вещь готова, надо теперь создать новый рецепт крафта.
// Создаем таблицу крафта
                @SuppressWarnings("deprecation")
                ShapedRecipe s = new ShapedRecipe(item); // В скобках ту вещь, которую мы собираемся крафтить, то есть итем.
                s.shape(new String[] { "A A", " B ", "A A" }); // Создаем массив
                // A - жемчуг эндера "A"
                // B глаз эндера "B"
                // Пробел - пустая строка, пустой слот, в котором ничего не крафтиться. " "
                s.setIngredient('A', Material.ENDER_PEARL); // Установили под букву А - ендер перл.
                s.setIngredient('B', Material.ENDER_EYE); // Установили под букву B - глаз ендера.
                Bukkit.getServer().addRecipe(s); // Регистрируем наш крафт!
        }
}