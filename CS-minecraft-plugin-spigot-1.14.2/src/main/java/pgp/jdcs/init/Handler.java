package pgp.jdcs.init;

import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

//Этот класс зарегистрирован как слушатель событий
public class Handler implements Listener {

	private Main plugin;

	public Handler(Main plugin) {
		this.setPlugin(plugin);
	}

	@EventHandler
	public void join(PlayerJoinEvent player) throws InterruptedException {

	}

	@EventHandler
	public void blockBreak(BlockBreakEvent breakPlayer) {
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent placePlayer) {
	}

	@EventHandler
	public void createART(ItemEvent ime) {
		// ime.getEntityType().toString();

		Bukkit.broadcastMessage(ime.getItem().toString());

	}

	@EventHandler
	public void blockInteract(PlayerInteractEvent event) {
		// получаю игрока
		Player player = event.getPlayer();

		@SuppressWarnings("deprecation")
		ItemStack item = player.getItemInHand();// получаею вещь в руке
		if (item.getType() == Material.ARROW) {// сравниваю со стрелой
			Material item2 = Material.GLOWSTONE;
			if (player.getInventory().contains(item2)) {// если есть светокамень создаю летящую стрелу
				Entity arrow = player.launchProjectile(Arrow.class);
				// arrow.setGlowing(true);
				arrow.setVelocity(player.getLocation().getDirection().multiply(1030f));

			}

		} else if (item.getType() == Material.PHANTOM_MEMBRANE) {// если в руках мембрана
			// получаю локацию игрока
			Location location = event.getPlayer().getLocation();
			// получаю все сущьности в радиусе 60 блоков
			Collection<Entity> listEntity = event.getPlayer().getWorld().getNearbyEntities(location, 60, 60, 60);
			int exp = 0;
			int ent = 0;
			int heal = 0;
			for (Iterator<Entity> iterator = listEntity.iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				// если не игрок (кто вызывал)
				if (entity != player) {
					// проверка на живых
					if (entity instanceof LivingEntity) {
						// проверка на то что сущьность не горит
						if (!(entity.getFireTicks() >= 1)) {
							// проверяю чтобы сущьность не была в воде
							Material b = entity.getLocation().getBlock().getType();
							if (b == Material.WATER) {

								/*
								 * ТУТ БЫЛА ОТЛАДКА double x = entity.getLocation().getX(); double y =
								 * entity.getLocation().getY(); double z = entity.getLocation().getZ();
								 * Bukkit.broadcastMessage(":water:" + x + ":" + y + ":" + z);
								 */
							} else {
								ent++;
								// подсвечиваю сущность
								entity.setGlowing(true);
								// поджигаю на мин
								entity.setFireTicks(20 * 60);
								// выдаю опыт
								player.giveExp(1);
								exp++;
								// проверка на колво жизней и выдача
								if (player.getHealth() < 20) {
									player.setHealth(player.getHealth() + 1);
									heal++;
								}
							}

						}

					} else {
						// не живые

						// проверка на лежащие вещи
						if (entity.getType() == EntityType.DROPPED_ITEM) {
							/*
							 * String message1=entity.getType().getEntityClass().;
							 * player.sendMessage("M1:"+message1);
							 */

							Item droppedItem = (Item) entity;
							ItemStack iStack = droppedItem.getItemStack();
							if (iStack.getType() == Material.CLAY_BALL) {
								List<Entity> list = entity.getNearbyEntities(2, 2, 2);

								boolean GLOWSTONE_DUST = false;
								Item droppedItem2 = null;
								for (Iterator<Entity> iterator2 = list.iterator(); iterator2.hasNext();) {
									Entity entity2 = (Entity) iterator2.next();

									droppedItem2 = (Item) entity2;
									if (droppedItem2.getItemStack().getType() == Material.GLOWSTONE_DUST) {
										GLOWSTONE_DUST = true;
									}

								}
								if (GLOWSTONE_DUST == true) {
									if (droppedItem.getItemStack().getAmount() == 1
											&& droppedItem2.getItemStack().getAmount() == 1) {
										droppedItem.remove();
										droppedItem2.remove();
										ItemStack awe = new ItemStack(Material.NETHER_STAR);
										droppedItem.getWorld().dropItem(droppedItem.getLocation(), awe);

									}

								}
							}
						}
						entity.setGlowing(true);
					}
				}

			}
			if (exp + heal + ent >= 1) {
				player.sendMessage(
						"Подожено:" + ent + "\n" + "Восстановлено здоровья:" + heal + "\n" + "Получено опыта:" + exp);

			}
		}

	}

	public Main getPlugin() {
		return plugin;
	}

	public void setPlugin(Main plugin) {
		this.plugin = plugin;
	}
}