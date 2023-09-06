package kr.lucymc.lucy_utils;

import kr.lucymc.lucy_utils.CommandCool.CommandCool_Event;
import kr.lucymc.lucy_utils.LevelBook.LevelBook_Command;
import kr.lucymc.lucy_utils.LevelBook.LevelBook_Event;
import kr.lucymc.lucy_utils.LevelBook.LevelBook_TabCompleter;
import kr.lucymc.lucy_utils.PVP.PVP_Command;
import kr.lucymc.lucy_utils.PVP.PVP_Event;
import kr.lucymc.lucy_utils.UUID_DB.UUID_DB_Event;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Objects;

public final class Lucy_Utils extends JavaPlugin {
    private static Lucy_Utils INSTANCE;
    public static Lucy_Utils getInstance() {
        return INSTANCE;
    }
    public static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static HashMap<String, Integer> PVPMode = new HashMap<>();
    FileConfiguration config = this.getConfig();
    @Override
    public void onEnable() {
        File ConfigFile = new File(getDataFolder(), "config.yml");
        if(!ConfigFile.isFile()){
            config.addDefault("DB.ID", "root");
            config.addDefault("DB.PW", "INTY");
            config.addDefault("DB.URL", "jdbc:mysql://127.0.0.1:3307/lucy?autoReconnect=true");
            config.addDefault("pvp.message.ON", "[ §cPVP§f ] 켜짐.");
            config.addDefault("pvp.message.OFF", "[ §cPVP§f ] 꺼짐.");
            config.addDefault("CommandCool.message.X", "[ §cX§f ] X.");
            config.addDefault("LevelBook.NamePrefix", "§f[ §a레벨북§f ] ");
            config.addDefault("LevelBook.Name", "§f[ §a레벨북§f ] %exp%xp");
            config.addDefault("LevelBook.Matcher", "(.*)xp");
            config.addDefault("LevelBook.lore", "§f========[ §6정보§f ]========,§8»§f 클릭하면 레벨이?!?!,§8» §e좌클릭§f으로 §a사용,§f======================");
            config.addDefault("LevelBook.message.noxp", "[ §a레벨북§f ] xp가 입력되지 않았습니다.");
            config.addDefault("LevelBook.message.addxp", "[ §a레벨북§f ] xp가 %exp%만큼 추가 되었습니다.");
            config.options().copyDefaults(true);
            saveConfig();
        }
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new PVP_Event(), this);
        getCommand("PVP").setExecutor(new PVP_Command());
        getServer().getPluginManager().registerEvents(new CommandCool_Event(), this);
        getCommand("레벨북").setExecutor(new LevelBook_Command());
        getServer().getPluginManager().registerEvents(new LevelBook_Event(), this);
        getServer().getPluginManager().registerEvents(new UUID_DB_Event(), this);
        getCommand("레벨북").setTabCompleter(new LevelBook_TabCompleter());
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(Objects.requireNonNull(config.getString("DB.URL")), config.getString("DB.ID"), config.getString("DB.PW"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
