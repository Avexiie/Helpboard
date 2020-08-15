package com.github.avexiie;

import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class Session {

    // Dependencies
    // PlaceholderAPI = dependency ID 0
    public static String[] dependencies = {"PlaceholderAPI"};
    public static ArrayList<String> enabled_dependencies = new ArrayList<>();

    // Objects
    public static Main plugin = null;

    // Bools
    public static boolean isuptodate = false;

    // Blocked users
    public static ArrayList<Player> disabled_players = new ArrayList<>();
    public static ArrayList<Player> re_enable_players = new ArrayList<>();

    /**
     * Are we up to date?
     * @param resourceId
     */
    public static void isUpToDate(String resourceId) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resourceId)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(
                    con.getInputStream())).readLine();
            if (version.equalsIgnoreCase(plugin.getDescription().getVersion())) {
                isuptodate =  true;
            } else {
                isuptodate =  false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            isuptodate =  false;
        }
    }

}