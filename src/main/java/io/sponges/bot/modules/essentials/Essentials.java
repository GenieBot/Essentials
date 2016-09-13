package io.sponges.bot.modules.essentials;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.essentials.cmd.*;

public class Essentials extends Module {

    public Essentials() {
        super("Essentials", "1.07");
    }

    @Override
    public void onEnable() {
        CommandManager commandManager = getCommandManager();
        commandManager.registerCommand(this, new ModulesCommand(this));
        commandManager.registerCommand(this, new HelpCommand(this));
        commandManager.registerCommand(this, new SayCommand());
        commandManager.registerCommand(this, new AboutCommand());
    }

    @Override
    public void onDisable() {
    }

    public static String capitalise(String input) {
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1).toLowerCase();
    }
}
