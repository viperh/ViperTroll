package plugins.vipertrollnew.Utils;

public class TrollData {

    private String uuid;
    private Boolean cancel_damage;
    private Boolean instant_kill;

    public TrollData(String UUID, Boolean cdamage, Boolean ikill){
        uuid = UUID;
        cancel_damage = cdamage;
        instant_kill = ikill;
    }

    public String getUUID(){
        return uuid;
    }
    public Boolean getDamage(){
        return cancel_damage;
    }
    public Boolean getKill(){
        return instant_kill;
    }





}
