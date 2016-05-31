package io.sponges.bot.modules.essentials;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.essentials.cmd.*;

public class Essentials extends Module {

    public Essentials() {
        super("Essentials", "1.05");
    }

    @Override
    public void onEnable() {
        CommandManager commandManager = getCommandManager();
        commandManager.registerCommand(this, new ModulesCommand(this));
        commandManager.registerCommand(this, new HelpCommand(this));
        commandManager.registerCommand(this, new SayCommand());
        commandManager.registerCommand(this, new SearchCommand(this));
        commandManager.registerCommand(this, new AboutCommand());
        commandManager.registerCommand(this, new LongHelpCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public static String join(String[] input, char joiner) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            if (i != 0) builder.append(joiner);
            builder.append(input[i]);
        }
        return builder.toString();
    }

    public static String capitalise(String input) {
        return input.replace(input.charAt(0), (input.charAt(0) + "").toLowerCase().charAt(0));
    }
}
