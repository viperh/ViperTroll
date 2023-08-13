package plugins.vipertrollnew.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import plugins.vipertrollnew.Utils.TrollData;
import plugins.vipertrollnew.ViperTrollNew;


public class OnHit implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){

        Entity victim = event.getEntity();
        Entity damager = event.getDamager();

        if(damager instanceof Player && victim instanceof Player){

            String damagerUUID = damager.getUniqueId().toString();


            if(ViperTrollNew.instance.map.containsKey(damagerUUID)){
                try{
                    TrollData data = ViperTrollNew.instance.map.get(damagerUUID);
                    Boolean damage = data.getDamage();
                    Boolean kill = data.getKill();
                    if(damage){
                        event.setDamage(0);
                    }
                    if(kill){
                        ((Player) damager).setHealth(0);
                    }

                }
                catch(Exception exception){
                    exception.printStackTrace();
                }
            }



        }


    }
}
