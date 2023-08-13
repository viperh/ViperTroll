package plugins.vipertrollnew.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import plugins.vipertrollnew.Database.Database;
import plugins.vipertrollnew.Utils.PlayerData;
import plugins.vipertrollnew.Utils.TrollData;
import plugins.vipertrollnew.ViperTrollNew;


@SuppressWarnings("all")
public class vtroll implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Database database = new Database();

        if(args.length == 0){
            sender.sendMessage(ViperTrollNew.instance.format("&6Usage: /vtroll add/check/remove"));
            return true;
        }

        if(args[0].equalsIgnoreCase("add")){
            if(!sender.hasPermission("vipertroll.add") && !sender.hasPermission("vipertroll.*")){
                sender.sendMessage(ViperTrollNew.instance.format("&cYou don't have permission to do this!"));
                return true;
            }
            if(args.length != 4){
                sender.sendMessage(ViperTrollNew.instance.format("&6Usage: /vtroll add [player] [cancel_damage] [instant_kill]"));
                return true;
            }



            PlayerData data = database.getPlayerData(args[1]);

            if(data != null){
                String uuid = data.getUUID();
                int checker = database.checkTroll(uuid);
                if(checker == 0){
                    Boolean cancel_damage = Boolean.parseBoolean(args[2]);
                    Boolean instant_kill = Boolean.parseBoolean(args[3]);
                    TrollData tdata = new TrollData(uuid, cancel_damage, instant_kill);
                    database.addTroll(tdata);
                    sender.sendMessage(ViperTrollNew.instance.format("&aPlayer has been added to the database!"));
                    return true;
                }
                if(checker == 1){
                    sender.sendMessage(ViperTrollNew.instance.format("&cPlayer is already in the database!"));
                    return true;
                }
                if(checker == -1){
                    sender.sendMessage(ViperTrollNew.instance.format("&cAn error occured. Please contact an administrator to check the console!"));
                    return true;
                }

            }
            else{
                sender.sendMessage(ViperTrollNew.instance.format("&cPlayer is not in the database. Contact an administrator!"));
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("remove")){
            if(!sender.hasPermission("vipertroll.remove") && !sender.hasPermission("vipertroll.*")){
                sender.sendMessage(ViperTrollNew.instance.format("&cYou don't have permission to do this!"));
                return true;
            }
            if(args.length != 2){
                sender.sendMessage(ViperTrollNew.instance.format("&6Usage: /vtroll remove [player]"));
                return true;
            }

            String username = args[1];

            PlayerData data = database.getPlayerData(username);
            if(data != null){
                String uuid = data.getUUID();
                int checker = database.checkTroll(uuid);
                if(checker == 0){
                    sender.sendMessage(ViperTrollNew.instance.format("&cPlayer is not in the database!"));
                    return true;
                }
                if(checker == 1){
                    database.removeTroll(uuid);
                    sender.sendMessage(ViperTrollNew.instance.format("&aPlayer has been removed from the database!"));
                    return true;
                }
                if(checker == -1){
                    sender.sendMessage(ViperTrollNew.instance.format("&cError occured. Contact an administrator to check the console!"));
                    return true;
                }
            }
            else{
                sender.sendMessage(ViperTrollNew.instance.format("&cPlayer is not in the database. Contact an administrator!"));
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("check")){
            if(!sender.hasPermission("vipertroll.check") && !sender.hasPermission("vipertroll.*")){
                sender.sendMessage(ViperTrollNew.instance.format("&cYou don't have permission to do this!"));
                return true;
            }
            if(args.length != 2){
                sender.sendMessage(ViperTrollNew.instance.format("&6Usage: /vtroll check [player]"));
                return true;
            }

            String username = args[1];


            PlayerData data = database.getPlayerData(username);
            if(data != null){
                String uuid = data.getUUID();
                int checker = database.checkTroll(uuid);
                if(checker == 0){
                    sender.sendMessage(ViperTrollNew.instance.format("&cPlayer is not in the database!"));
                    return true;
                }
                if(checker == 1){
                    sender.sendMessage(ViperTrollNew.instance.format("&aPlayer is in the database!"));
                    return true;
                }
                if(checker == -1){
                    sender.sendMessage(ViperTrollNew.instance.format("&cError occured. Contact an administrator to check the console!"));
                    return true;
                }
            }
            else{
                sender.sendMessage(ViperTrollNew.instance.format("&c Player is not in the database. Contact an administrator to check the console!"));
                return true;
            }


        }


        else{
            sender.sendMessage(ViperTrollNew.instance.format("&6Usage: /vtroll add/check/remove"));
            return true;
        }



        return true;
    }
}
