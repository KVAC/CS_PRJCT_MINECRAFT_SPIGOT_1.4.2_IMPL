package pgp.jdcs.init;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Ender implements Listener { // Класс Ender будет у нас слушателем

        @SuppressWarnings("deprecation")
        @EventHandler
        public void use(PlayerInteractEvent e) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
                        return; // Если у нас не
                // нажатие на воздух и не нажатие на правый клик блока, то ретурн:
                if (e.getPlayer().getItemInHand().getType() != Material.ENDER_EYE)
                        return; // Мы ничего делать не будем, если
                // в руке не глаз эндера! А если глаз эндера, проверка проходит.
                Player p = e.getPlayer();// Создаем объект нашего игрока
                ItemStack item = e.getPlayer().getItemInHand();
                // Далее если не указывать if(!item.getItemMeta().hasDisplayName()) return; -
                // выскочит ошибка
                if (!item.getItemMeta().hasDisplayName())
                        return; // Если он не имеет название телепорта, то мы пропускаем этот
                // аналогично и с лоре!
                if (!item.getItemMeta().hasLore())
                        return; // Если не имеет описания, тоже ничего не делаем, пропускаем вариант.
                if (!item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Teleporter"))
                        return; // Если у нас не такое название,
                // то тоже пропускаем!
                if (!item.getItemMeta().getLore().get(0).equals("Ender-teleport"))
                        return;
                // Если игрок прошел все проверки и это дейтствительно оказался наш предмет
                // ендер телепорт и описаниями, с
                // нужными нам названиями и так далее, то мы выдаем возможности на использования
                // этому предмету:
                // Во первых мы отменяем событие, чтобы глаз не выпрыгивал из рук и никаких
                // действий не происходило!
                e.setCancelled(true);
                removeItem(p); // Вызываем метод с удалением 1 предмета из рук!
                teleport(p); // Вызываем метод с телепортацией игрока!

        }

        // todo Кстати, а нам нужно удалить предмет из руки, когда мы кликаем по сундуку
        // в классе Commands - надо бы этот метод
        // todo там использовать =)
        @SuppressWarnings("deprecation")
        private void removeItem(Player p) { // Удаляем предмет из руки, если мы используем 1 раз глаз эндера!
                ItemStack item = p.getItemInHand();
                item.setAmount(item.getAmount() - 1);// Уменьшаем его кол-во на один.
                p.setItemInHand(item);
        }

        private void teleport(Player p) {
                // нам нужно получить направления взгляда игрока.
                Location loc = p.getLocation();
                // Получаем переменную флоат
                float yaw = loc.getYaw(); // направление взгляда слево на права и наоборот.
                float pitch = loc.getPitch(); // направление взгляда сверху вниз.
                if (pitch < -40 || pitch > 40) {// А теперь проверяем!
                        vertical(p); // Если питч меньше -40 или питч больше 40, вызываем метод вертикал(игрок);
                        // Теперь мы сделали вертикальную телепортацию вверх, надо теперь сделать вниз!
                        return; // Пишем ретурн, так как мы уже телепортировались и нам делать ничего не надо!
                }
                float y = toDegree(yaw); // Присвоить игрику метод toDegree
                if (y > 315 || y < 45) {
                        south(p); // То мы его телепортируем на юг.
                        return;
                }

                if (y > 45 && y < 135) {
                        west(p); // на вест его ))
                        return;
                }
                if (y > 135 && y < 225) {
                        north(p); // то на север
                        return;
                }

                if (y > 225 && y < 315) {
                        east(p);
                        return;
                }
        }

        private void vertical(Player p) { // Реализовываем метод телепортации вверх и вниз, который принимает игрока.
                // Теперь делаем проверку, если у нас значение наклона вверх больше 40 или
                // меньше -40 - то нас телепортируем
                // вверх уже или вниз.
                // нам нужно получить направления взгляда игрока.
                Location loc = p.getLocation();
                // Получаем переменную флоат
                float pitch = loc.getPitch(); // направление взгляда сверху вниз.
                if (pitch < -40) { // Если питч меньше 40, то телепортируем его вверх.
                        loc.setY(loc.getY() + 10); // поднимаемся на 10 блоков вверх.
                        if (block(loc))
                                p.teleport(loc); // Если можно телепортировать игрока туда, то телепортируем. Готово.
                        // Вызываем этот метод при каждой телепортации.
                }
                if (pitch > 40) { // Если питч больше 40, то телепортируем его вниз.
                        loc.setY(loc.getY() + -5); // поднимаемся на 5 блоков вниз.
                        if (block(loc))
                                p.teleport(loc); // Если можно телепортировать игрока туда, то телепортируем. Готово.
                        // Вызываем этот метод при каждой телепортации.
                } // На этом завершена полностью вертикальная телепортация
        }

        // Была такая проблема что 90 градусов в бакките определяло как -270, по этому
        // для того, чтобы все было нормально
        // мы создаем метод, где мы проверяем то, куда мы смотрим.
        private float toDegree(float y) {
                if (y <= 360 && y >= 0)
                        return y; // Является ли этот угол нормальным, проверка:
                // но если это не так, мы его выравниваем.
                while (!(y <= 360 || !(y >= 0))) { // Если y меньше или не равен 360 или он не больще или равен нулю, то:
                        if (y > 360) {
                                y = y - 360;
                        }
                        if (y < 0) {
                                y = y + 360;
                        }
                }
                return y;
        }

        private boolean block(Location loc) {// Можно ли игрока телепортировать на то место, проверка на стены.
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                if (loc.getWorld().getBlockAt(x, y, z).getType() != Material.AIR)
                        return false;
                // Получить тип блока по этим координатам. Если не равно воздуху, то нельзя
                // return false;
                if (loc.getWorld().getBlockAt(x, y + 1, z).getType() != Material.AIR)
                        return false; // на 1 блок выше
                return true; // Если у нас эти 2 условия не выполнились, значит все хорошо.
        }

        // Создаем методы север юг запад восток
        private void south(Player p) { // юг принимает игрока
                Location loc = p.getLocation(); // берем локацию
                double z = loc.getZ(); // получаем координату Z
                z = z + 5; // добавляем ему Z + 5 на 5 блока телепортируем
                loc.setZ(z);
                if (block(loc))
                        p.teleport(loc); // если блок на этой локации, если да, то телепортируем туда игрока.
                // Вызываем этот метод при каждой телепортации.
        }

        private void north(Player p) { // север принимает игрока
                Location loc = p.getLocation(); // берем локацию
                double z = loc.getZ(); // получаем координату Z
                z = z - 5; // добавляем ему Z - 5 на 5 блока телепортируем
                loc.setZ(z);
                if (block(loc))
                        p.teleport(loc); // если блок на этой локации, если да, то телепортируем туда игрока.
                // Вызываем этот метод при каждой телепортации.
        }

        private void west(Player p) { // запад принимает игрока
                Location loc = p.getLocation(); // берем локацию
                double x = loc.getX(); // получаем координату X
                x = x - 5; // добавляем ему X - 5 на 5 блока телепортируем
                loc.setX(x);
                if (block(loc))
                        p.teleport(loc); // если блок на этой локации, если да, то телепортируем туда игрока.
                // Вызываем этот метод при каждой телепортации.
        }

        private void east(Player p) { // восток принимает игрока
                Location loc = p.getLocation(); // берем локацию
                double x = loc.getX(); // получаем координату X
                x = x + 5; // добавляем ему X + 5 на 5 блока телепортируем
                loc.setX(x);
                if (block(loc))
                        p.teleport(loc); // если блок на этой локации, если да, то телепортируем туда игрока.
                // Вызываем этот метод при каждой телепортации
        }

}