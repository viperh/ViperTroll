package plugins.vipertrollnew.TabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class vtrollCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        List<String> list = new ArrayList<>();

        if(args.length == 1){
            list.add("add");
            list.add("remove");
            list.add("check");
        }
        if(args.length == 2){
            for(Player player : Bukkit.getOnlinePlayers()){
                String name = player.getName();
                list.add(name);
            }
        }
        if(args.length == 3 || args.length == 4){
            list.add("true");
            list.add("false");
        }




        return list;
    }
}
