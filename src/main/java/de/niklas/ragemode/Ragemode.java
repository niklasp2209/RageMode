package de.niklas.ragemode;

import commands.SetupCommand;
import commands.StartCommand;
import gamestate.GameState;
import gamestate.GameStateUtils;
import items.AxeListener;
import listener.PlayerChatListener;
import listener.PlayerConnectionListener;
import listener.PlayerDeathListener;
import listener.PlayerListener;
import mapvoting.Maps;
import mapvoting.Voting;
import mapvoting.VotingListener;
import mysql.Mysql;
import mysql.NayzAPI;
import mysql.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ScoreboardManager;
import utils.StatsRanking;

import java.util.ArrayList;

public class Ragemode extends JavaPlugin {

    private GameStateUtils gameStateUtils;
    private Voting voting;
    private PlayerDeathListener playerDeathListener;
    private ArrayList<Player> players;
    private ArrayList<Maps> mapsArrayList;
    private StatsAPI statsAPI;
    private StatsRanking statsRanking;

    private final byte min_Players = 1,
                       max_Players = 8;
    private final String prefix = "§7[§6CookieRage§7] §r",
                         no_Permission = prefix+"§cDu hast leider nicht genügend Rechte.";

    Mysql mysql = new Mysql("localhost", "3306", "System", "admin", "");

    @Override
    public void onEnable(){
        mysql.connect();

        players = new ArrayList<>();
        gameStateUtils = new GameStateUtils(this);
        statsAPI = new StatsAPI(this);
        statsRanking = new StatsRanking(this);

        gameStateUtils.setGameState(GameState.Lobby_State);
        startVoting();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionListener(this), this);
        pluginManager.registerEvents(new AxeListener(this), this);
        pluginManager.registerEvents(new VotingListener(this), this);
        pluginManager.registerEvents(new ScoreboardManager(this), this);
        pluginManager.registerEvents(new PlayerChatListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new PlayerListener(this), this);
        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));

        System.out.println("[CookieRage] Das Plugin wurde gestartet!");

        new ScoreboardManager(this).startHotbar();
        statsRanking.set();
    }

    @Override
    public void onDisable() {
        mysql.disconnect();

        System.out.println("[CookieRage] Das Plugin wurde gestoppt!");
    }

    public void startVoting(){
        mapsArrayList = new ArrayList<>();
        for(String current : getConfig().getConfigurationSection("Maps").getKeys(false)) {
            Maps maps = new Maps(this, current);
            if (maps.isPlayable())
                mapsArrayList.add(maps);
            else
                Bukkit.getConsoleSender().sendMessage("§cDie Map §4" + maps.getName() + " §cist nicht fertig eingerichtet");
        }
        if(mapsArrayList.size() >= 3)
        voting = new Voting(this, mapsArrayList);
        else {
            Bukkit.getConsoleSender().sendMessage("§4Es müssen mindestens 3 Maps eingerichtet sein!");
            voting = null;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public byte getMax_Players() {
        return max_Players;
    }

    public byte getMin_Players() {
        return min_Players;
    }

    public GameStateUtils getGameStateUtils() {
        return gameStateUtils;
    }

    public ArrayList<Maps> getMapsArrayList() {
        return mapsArrayList;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNo_Permission() {
        return no_Permission;
    }

    public Voting getVoting() {
        return voting;
    }

    public PlayerDeathListener getPlayerDeathListener() {
        return playerDeathListener;
    }

    public Mysql getMysql() {
        return mysql;
    }

    public StatsAPI getStatsAPI() {
        return statsAPI;
    }
}
