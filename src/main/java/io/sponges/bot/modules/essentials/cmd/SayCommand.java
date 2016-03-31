package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.modules.essentials.Essentials;

public class SayCommand extends Command {

    public SayCommand() {
        super("says something in the chat", "say", "echo");
    }

    @Override
    public void onCommand(CommandRequest commandRequest, String[] args) {
        if (args.length == 0)  {
            commandRequest.reply("Usage: say [something to say]");
            return;
        }
        commandRequest.reply(Essentials.join(args, ' '));
    }
}
