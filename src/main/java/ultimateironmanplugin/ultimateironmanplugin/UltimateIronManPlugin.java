package ultimateironmanplugin.ultimateironmanplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Scanner;

public final class UltimateIronManPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws FileNotFoundException {

        Player p = event.getPlayer();
        String name = p.getName();
        System.out.println(name + " is being processed");
       // BufferedReader br = null;
       try{
            File myObj = new File("C:/Users/eeron/Desktop/papertestserver/plugins/"+ name +".txt");
            if (myObj.createNewFile()){
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/C:/Users//eeron//Desktop//papertestserver//plugins//"+name+".txt", false)));
                    out.print(0);
                    out.close();
                } catch (IOException e) {

                }
                System.out.println("Tiedosto luotu: " + myObj.getName());
            }else {
                System.out.println("Tiedosto on jo olemassa");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       //print out file
        Scanner scanner = new Scanner(new File("C:/Users/eeron/Desktop/papertestserver/plugins/"+ name +".txt"));
        int [] tall = new int [100];
        int deaths = scanner.nextInt();
        System.out.println(name + " on kuollut " + deaths + " kertaa.");
        setMaxHp(deaths, p);

        //check if file exists
        //create file for player if need
        //check stats
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws FileNotFoundException {

        Player p = event.getPlayer();
        String name = p.getName();
        //kattoo monesti kuollu

        Scanner scanner = new Scanner(new File("C:/Users/eeron/Desktop/papertestserver/plugins/"+ name +".txt"));
        int [] tall = new int [100];
        int deaths = scanner.nextInt();
        int newDeaths = deaths + 1;
        //kirjaa uuden kuoleman
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/C:/Users//eeron//Desktop//papertestserver//plugins//"+name+".txt", false)));
            out.print(newDeaths);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setMaxHp(newDeaths, p);

    }

    public void setMaxHp(int deaths, Player player){
        int deathLoose = deaths * 2;
        int deathMod = 20 - deathLoose;
        player.setMaxHealth(deathMod);

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

//command to check global and personal stats