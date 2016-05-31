package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;

import java.util.Arrays;
import java.util.Collection;

public class LongHelpCommand extends Command {

    private final Module module;

    public LongHelpCommand(Module module) {
        super("shows a long formatted message with all commands", "longhelpcommand");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest commandRequest, String[] strings) {
        if (!commandRequest.getUser().isOp()) {
            commandRequest.reply("Sorry only the bot owner can do that.");
            return;
        }
        int html = -1;
        if (strings.length > 0) {
            html = Integer.parseInt(strings[0]);
        }
        CommandManager commandManager = module.getCommandManager();
        StringBuilder builder = new StringBuilder("Commands: ");
        Collection<Command> commands = commandManager.getCommands();
        for (Command command : commands) {
            String[] cmdNames = command.getNames();
            String[] aliases = new String[cmdNames.length - 1];
            System.arraycopy(cmdNames, 1, aliases, 0, cmdNames.length - 1);
            if (html != 1) builder.append("\n");
            if (html == 0) builder.append("<code>");
            else if (html == 1) builder.append("<tr><td>");
            builder.append(command.getNames()[0]);
            if (html == 0) builder.append("</code>");
            if (html == 1) builder.append("</td><td>");
            if (html != 1) builder.append(" - ");
            builder.append(command.getDescription());
            if (html == 1) builder.append("</td><td>");
            if (html != 1) builder.append(" ");
            if (html != 1) builder.append(Arrays.toString(aliases));
            else builder.append(Arrays.toString(aliases).substring(1).replace("]", ""));
            if (html == 0) builder.append("<br />");
            if (html == 1) builder.append("</td></tr>");
        }
        builder.append(System.lineSeparator()).append("Looking for a command? Try searching for it with " +
                "\"search [term]\"");
        commandRequest.reply(builder.toString());
    }

    @Override
    public boolean requiresOp() {
        return true;
    }
}
