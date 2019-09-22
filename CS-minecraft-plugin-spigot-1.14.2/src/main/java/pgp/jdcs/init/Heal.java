package pgp.jdcs.init;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pgp.jdcs.objects.items.Artefact_1;

public class Heal implements CommandExecutor {

        private Main plugin;

        public Heal(Main plugin) {
                this.setPlugin(plugin);
        }

        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                if (!(sender instanceof Player)) {
                        sender.sendMessage("Вы выполняете команду не от игрока!");
                        return true;
                }
                Player p = (Player) sender;

                Artefact_1 artefact_1 = new Artefact_1();
                p.getInventory().addItem(artefact_1);

                Location loc = p.getLocation();
                @SuppressWarnings("unused")
                World world = loc.getWorld();
                return true;
        }

        public Main getPlugin() {
                return plugin;
        }

        public void setPlugin(Main plugin) {
                this.plugin = plugin;
        }
}