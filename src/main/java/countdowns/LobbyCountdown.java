package countdowns;

import de.niklas.ragemode.Ragemode;
import gamestate.GameState;
import gamestate.GameStateUtils;
import gamestate.LobbyState;
import mapvoting.Maps;
import mapvoting.Voting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import utils.FastBoard;
import utils.ScoreboardManager;

import java.util.ArrayList;
import java.util.Collections;

public class LobbyCountdown extends  Countdown{

    private int idleID;
    private int seconds;
    private GameStateUtils gameStateUtils;
    private final byte idle_Time = 10,
                       countdown_Time = 90;
    private boolean isIdling,
                    isRunning;

    public LobbyCountdown(GameStateUtils gameStateUtils){
        this.gameStateUtils = gameStateUtils;
        seconds = countdown_Time;
    }

    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateUtils.getPlugin(), new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 90: case 60: case 30: case 15: case 10: case 5: case 3: case 2:
                        Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§7Die Runde beginnt in §e"+seconds+" §7Sekunden");

                        if(seconds == 5){
                            for(Player current : Bukkit.getOnlinePlayers()){
                                current.getInventory().setItem(0, null);
                                if(current.getOpenInventory() == null)return;
                                if(current.getOpenInventory().getTitle().equals("§7● §eMap Abstimmung"))
                                    current.closeInventory();
                            }
                            Voting voting = gameStateUtils.getPlugin().getVoting();
                            Maps winnerMap;
                            if(voting != null)
                                winnerMap = voting.getWinnerMap();
                            else{
                                ArrayList<Maps> mapsArrayList = gameStateUtils.getPlugin().getMapsArrayList();
                                Collections.shuffle(mapsArrayList);
                                winnerMap = mapsArrayList.get(0);
                            }
                            Bukkit.broadcastMessage("§7[§6RageMode§7] §a"+winnerMap.getName()+" §7hat das §aMapvoting §7gewonnnen");

                            for(Player current : Bukkit.getOnlinePlayers()){

                                FastBoard fastBoard = new FastBoard(current);
                                fastBoard.updateTitle("§6·§e• RageMode §8| §7Stats");
                                new ScoreboardManager(gameStateUtils.getPlugin()).updateBoard(fastBoard);
                                fastBoard.updateLine(3, "§7» §e"+ winnerMap.getName());
                            }
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§7Die Runde beginnt in §e"+seconds+" §7Sekunde");
                        break;
                    case 0:
                        gameStateUtils.setGameState(GameState.Ingame_State);
                        stop();
                        break;

                    default:
                        break;
                }

                for(Player current : Bukkit.getOnlinePlayers()) {
                    if(seconds == -1)return;
                    if(gameStateUtils.getCurrentGameState() instanceof LobbyState) {
                        if(seconds > 0) {
                            current.setLevel(seconds);
                            current.setExp(current.getExp() + (1F / 90));
                        }
                    }else{
                        current.setLevel(0);
                        current.setExp(0);
                    }
                }
                seconds--;
            }
        },0, 20);
    }

    @Override
    public void stop() {
        if(isRunning){
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = countdown_Time;
        }
    }

    public void startIdle(){
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateUtils.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§cEs wird noch auf §e"+(gameStateUtils.getPlugin().getMin_Players()-gameStateUtils.getPlugin().getPlayers().size())+" §eSpieler §cgewartet!");
            }
        }, 0, 20*idle_Time);
    }

    public void stopIdle(){
        if(isIdling){
            Bukkit.getScheduler().cancelTask(idleID);
            isIdling = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
