package io.sponges.bot.modules.essentials;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.essentials.cmd.HelpCommand;
import io.sponges.bot.modules.essentials.cmd.ModulesCommand;

@SuppressWarnings("unused")
public class Essentials extends Module {

    public Essentials() {
        super("Essentials", "1.0-SNAPSHOT");
    }

    @Override
    public void onEnable() {
        CommandManager commandManager = getCommandManager();
        commandManager.registerCommand(this, new ModulesCommand(this));
        commandManager.registerCommand(this, new HelpCommand(this));
    }

    @Override
    public void onDisable() {
    }
}
