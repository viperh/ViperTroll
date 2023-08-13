package plugins.vipertrollnew.Utils;

public class PlayerData {
    private String uuid;
    private String username;
    public PlayerData(String UUID, String name){
        uuid = UUID;
        username = name;
    }
    public String getUsername(){
        return username;
    }
    public String getUUID(){
        return uuid;
    }
}
