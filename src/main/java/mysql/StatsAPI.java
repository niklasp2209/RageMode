package mysql;

import de.niklas.ragemode.Ragemode;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsAPI {

    private Ragemode plugin;

    public StatsAPI(Ragemode plugin){
       this.plugin = plugin;
    }


    public int getKills(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM CookieRage WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getInt("stats_Kills");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public String playerUUID(String displayname) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM Spieler WHERE Displayname='"+displayname+"'");
        try {
            if(rs.next()) {
                return rs.getString("UUID");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return "null";
    }
    public String getUUID(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM Spieler WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getString("DisplayName");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return "null";
    }

    public void addKill(String uuid, int kills) {
        int current = getKills(uuid);
        int newkills = current+kills;
        plugin.getMysql().update("UPDATE CookieRage SET stats_Kills = '"+newkills+"'  WHERE UUID='"+uuid+"'");
    }


    public int getDeaths(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM CookieRage WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getInt("stats_Deaths");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void addDeaths(String uuid, int deaths) {
        int current = getDeaths(uuid);
        int newDeaths = current+deaths;
        plugin.getMysql().update("UPDATE CookieRage SET stats_Deaths = '"+newDeaths+"'  WHERE UUID='"+uuid+"'");
    }


    public int getPlayed(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM CookieRage WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getInt("stats_Played");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void addPlayed(String uuid, int played) {
        int current = getPlayed(uuid);
        int newPlayed = current+played;
        plugin.getMysql().update("UPDATE CookieRage SET stats_Played = '"+newPlayed+"'  WHERE UUID='"+uuid+"'");
    }


    public int getWins(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM CookieRage WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getInt("stats_Wins");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void addWins(String uuid, int wins) {
        int current = getWins(uuid);
        int newWins = current+wins;
        plugin.getMysql().update("UPDATE CookieRage SET stats_Wins = '"+newWins+"'  WHERE UUID='"+uuid+"'");
    }


    public int getPoints(String uuid) {
        ResultSet rs = plugin.getMysql().getResult("SELECT * FROM CookieRage WHERE UUID='"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getInt("stats_Points");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void addPoints(String uuid, int buffs) {
        int current = getPoints(uuid);
        int newBuffs = current+buffs;
        plugin.getMysql().update("UPDATE CookieRage SET stats_Points = '"+newBuffs+"'  WHERE UUID='"+uuid+"'");
    }

    public void setDefault(String uuid) {
        plugin.getMysql().update("INSERT INTO CookieRage (UUID,stats_Kills,stats_Deaths,stats_Played,stats_Wins,stats_Points) VALUES ('"+uuid+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"')");
    }
}
