package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class SearchCommand extends Command {

    private final Module module;

    public SearchCommand(Module module) {
        super("searches for a command", "search", "lookup");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        if (args.length == 0) {
            request.reply("Usage: search [term]");
            return;
        }
        CommandManager commandManager = module.getCommandManager();
        Collection<String> names = commandManager.getNames();
        String match = findClosestMatch(names, args[0]);
        if (match == null) {
            request.reply("Could not find any commands matching that term! Use -commands for the full list.");
            return;
        }
        Command command = commandManager.getCommand(match);
        String[] cmdNames = command.getNames();
        String[] aliases = new String[cmdNames.length - 1];
        System.arraycopy(cmdNames, 1, aliases, 0, cmdNames.length - 1);
        request.reply("Matching command:\n" + command.getNames()[0] + " - " + command.getDescription() + "\nAliases: "
                + Arrays.toString(aliases).replace("[", "").replace("]", ""));
    }

    // TODO return multiple results
    public String findClosestMatch(Collection<String> collection, String target) {
        int distance = Integer.MAX_VALUE;
        String closest = null;
        for (String compareObject : collection) {
            int currentDistance = StringUtils.getLevenshteinDistance(compareObject, target);
            if(currentDistance < distance) {
                distance = currentDistance;
                closest = compareObject;
            }
        }
        return closest;
    }

}
