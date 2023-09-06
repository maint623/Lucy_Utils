package kr.lucymc.lucy_utils.LevelBook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class LevelBook_TabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                if(p.isOp()){
                    List<String> options = new ArrayList<>();
                    options.add("관리자");
                    return StringUtil.copyPartialMatches(args[0], options, new ArrayList<>());
                }
            }else if (args.length == 2) {
                if(p.isOp()){
                    List<String> options = new ArrayList<>();
                    options.add("발급");
                    return StringUtil.copyPartialMatches(args[1], options, new ArrayList<>());
                }
            }else if (args.length == 3 && args[1].equals("발급")) {
                if(p.isOp()){
                    List<String> options = new ArrayList<>();
                    options.add("exp");
                    return StringUtil.copyPartialMatches(args[2], options, new ArrayList<>());
                }
            }
        }
        return null;
    }
}
