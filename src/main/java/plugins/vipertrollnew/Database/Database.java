package plugins.vipertrollnew.Database;

import plugins.vipertrollnew.Utils.PlayerData;
import plugins.vipertrollnew.Utils.TrollData;
import plugins.vipertrollnew.ViperTrollNew;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class Database {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private final String URL = "jdbc:sqlite:" + ViperTrollNew.instance.getDataFolder().getPath() + "/vtrolls.db";

    public Connection getConnection(){
        if(connection != null){
            return connection;
        }
        try{

            connection = DriverManager.getConnection(URL);
            return connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void initialize(){
        File file = new File(ViperTrollNew.instance.getDataFolder().getPath(), "vtrolls.db");
        if (!file.exists()) {
            try{
                file.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            String CREATE_TROLL_TABLE = "CREATE TABLE IF NOT EXISTS trolls(uuid TEXT, cancel_damage BOOLEAN, instant_kill BOOLEAN)";
            String CREATE_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS player_data(uuid TEXT, username TEXT)";


            try{
                statement = getConnection().createStatement();
                statement.execute(CREATE_TROLL_TABLE);
                statement.execute(CREATE_PLAYER_TABLE);
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }


        }
    }

    public void addTroll(TrollData trollData){

        try{
            preparedStatement = getConnection().prepareStatement("INSERT INTO trolls(uuid, cancel_damage, instant_kill) VALUES (?,?,?)");
            preparedStatement.setString(1, trollData.getUUID());
            preparedStatement.setBoolean(2, trollData.getDamage());
            preparedStatement.setBoolean(2, trollData.getKill());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            updateMap();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int checkTroll(String uuid){
        try{
            preparedStatement = getConnection().prepareStatement("SELECT * FROM trolls WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                resultSet.close();
                preparedStatement.close();
                return 1;
            }
            else{
                resultSet.close();
                preparedStatement.close();
                return 0;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
    public void removeTroll(String uuid){
        try{
            preparedStatement = getConnection().prepareStatement("DELETE FROM trolls WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            updateMap();
        }
        catch(SQLException e){
            e.printStackTrace();
        }


    }

    public void clearDatabase(){
        try{
            statement = getConnection().createStatement();
            statement.execute("DELETE * FROM trolls");
            statement.close();
            updateMap();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateMap(){
        Map<String, TrollData> tempmap = new HashMap<>();

        try{
            preparedStatement = getConnection().prepareStatement("SELECT * FROM trolls");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String uuid = resultSet.getString("uuid");
                Boolean cancel_damage = resultSet.getBoolean("cancel_damage");
                Boolean instant_kill = resultSet.getBoolean("instant_kill");
                TrollData data = new TrollData(uuid, cancel_damage, instant_kill);
                tempmap.put(uuid, data);
            }

            resultSet.close();
            preparedStatement.close();
            ViperTrollNew.instance.map = tempmap;


        }
        catch(SQLException e){
            e.printStackTrace();
        }



    }

    public void addPlayerData(PlayerData data){
        try{
            preparedStatement = getConnection().prepareStatement("INSERT INTO player_data(uuid, username) VALUES (?,?)");
            preparedStatement.setString(1, data.getUUID());
            preparedStatement.setString(2, data.getUsername());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int checkPlayerData(String uuid){
        try{
            preparedStatement = getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                resultSet.close();
                preparedStatement.close();
                return 1;
            }
            else{
                resultSet.close();
                preparedStatement.close();
                return 0;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public PlayerData getPlayerData(String username){
        PlayerData data = null;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT * FROM player_data WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String uuid = resultSet.getString("uuid");
                data = new PlayerData(uuid, username);

            }
            resultSet.close();
            preparedStatement.close();
            return data;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}


