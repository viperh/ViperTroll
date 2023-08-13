package plugins.vipertrollnew.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugins.vipertrollnew.Database.Database;
import plugins.vipertrollnew.Utils.PlayerData;
import plugins.vipertrollnew.ViperTrollNew;

public class OnPlayerJoin implements Listener {

    Database database = new Database();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String username = player.getName();
        int playerExists = database.checkPlayerData(uuid);

        if(playerExists == 0) {
            PlayerData data = new PlayerData(uuid, username);
            database.addPlayerData(data);
        }
        if(playerExists == -1){
            ViperTrollNew.instance.getLogger().severe("COULD'T ADD PLAYER TO DATABASE!");
        }

    }
}
