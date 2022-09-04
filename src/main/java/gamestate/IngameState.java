package gamestate;

import countdowns.GraceCountdown;
import de.niklas.ragemode.Ragemode;
import listener.PlayerDeathListener;
import mapvoting.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import utils.FastBoard;
import utils.ItemBuilder;
import utils.ScoreboardManager;

import java.util.ArrayList;
import java.util.Collections;

public class IngameState extends GameState{

    private Ragemode plugin;
    private Maps maps;
    private ArrayList<Player> players;
    private GraceCountdown graceCountdown;

    public IngameState(Ragemode plugin){
        this.plugin = plugin;
        graceCountdown = new GraceCountdown(plugin);
    }

    @Override
    public void start() {
        Collections.shuffle(plugin.getPlayers());
        players = plugin.getPlayers();

        maps = plugin.getVoting().getWinnerMap();
        maps.load();
        for(int i = 0; i < players.size(); i++) {
            players.get(i).teleport(maps.getLocations()[i]);
            PlayerDeathListener.points.put(players.get(i), 0);
        }

        for(Player current : Bukkit.getOnlinePlayers()){
            current.getInventory().clear();
            current.setLevel(0);
            current.setExp(0);

            FastBoard fastBoard = new FastBoard(current);
            fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
            new ScoreboardManager(plugin).updateBoard(fastBoard);
        }

        for(World worlds : Bukkit.getWorlds()){
            worlds.setGameRule(GameRule.KEEP_INVENTORY, true);
            worlds.setDifficulty(Difficulty.PEACEFUL);
        }

        graceCountdown.start();
    }

    @Override
    public void stop() {

    }
}
