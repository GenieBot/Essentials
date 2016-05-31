package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandRequest;

public class AboutCommand extends Command {

    public AboutCommand() {
        super("shows information about the bot", "about", "info");
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hi, I'm SpongyBot; a multiplatform instant messaging bot." +
                "\nTo see my commands, use -commands. Searching for a command? Use \"-search [term]\"" +
                "\nI was made by Sponges (sponges.io) on 1st July 2015." +
                "\nMy server is a powerful machine provided by my sponsor, XiPE.io!");
        if (request.getClient().getId().equalsIgnoreCase("skype")) {
            builder.append("\nOfficial Skype chat: https://join.skype.com/hhcz1t0k0ImT");
        }
        builder.append("\nOfficial Discord guild: https://discord.gg/0hlTJkCoQ104RNB2");
        request.reply(builder.toString());
    }
}
