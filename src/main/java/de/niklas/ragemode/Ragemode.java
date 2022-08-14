package de.niklas.ragemode;

import commands.SetupCommand;
import commands.StartCommand;
import gamestate.GameState;
import gamestate.GameStateUtils;
import listener.PlayerConnectionListener;
import mapvoting.Maps;
import mapvoting.Voting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Ragemode extends JavaPlugin {

    private GameStateUtils gameStateUtils;
    private Voting voting;
    private ArrayList<Player> players;

    private final byte min_Players = 1,
                       max_Players = 8;
    private final String prefix = "§7[§6RageMode§7] §r",
                         no_Permission = prefix+"§cDu hast leider nicht genügend Rechte.";

    @Override
    public void onEnable(){
        gameStateUtils = new GameStateUtils(this);
        players = new ArrayList<>();

        gameStateUtils.setGameState(GameState.Lobby_State);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionListener(this), this);
        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));

        System.out.println("[RageMode] Das Plugin wurde gstartet!");

        startVoting();
    }

    @Override
    public void onDisable() {
        System.out.println("[RageMode] Das Plugin wurde gestoppt!");
    }

    public void startVoting(){
        ArrayList<Maps> mapsArrayList = new ArrayList<>();
        for(String current : getConfig().getConfigurationSection("Maps").getKeys(false)) {
            Maps maps = new Maps(this, current);
            if (maps.isPlayable())
                mapsArrayList.add(maps);
            else
                Bukkit.getConsoleSender().sendMessage("§cDie Map §4" + maps.getName() + " §cist nicht fertig eingerichtet");
        }
        voting = new Voting(this, mapsArrayList);
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

    public String getPrefix() {
        return prefix;
    }

    public String getNo_Permission() {
        return no_Permission;
    }

    public Voting getVoting() {
        return voting;
    }
}
