package io.sponges.bot.modules.essentials;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.essentials.cmd.ModulesCommand;

public class Essentials extends Module {

    public Essentials() {
        super("Essentials", "1.0", true);
    }

    @Override
    public void onEnable() {
        CommandManager commandManager = getCommandManager();
        commandManager.registerCommand(new ModulesCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public static String capitalise(String input) {
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1).toLowerCase();
    }
}
