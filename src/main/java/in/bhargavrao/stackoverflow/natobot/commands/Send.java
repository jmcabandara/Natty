package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.services.StatsService;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhargav.h on 23-Oct-16.
 */
public class Send implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public Send(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"send");
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message).trim();
        String feedbacks[] = data.split(" ");

        try{
            List<String> lines = FileUtils.readFile(FilePathUtils.outputCompleteLogFile);
            if(feedbacks.length==0 || (feedbacks.length==1 && feedbacks[0].equals("reverse"))){
                room.replyTo(event.getMessage().getId(), "InputMismatchError, The code has been made Tuna Proof™");
                return;
            }
            if(feedbacks[0].equals("reverse")){
                Collections.reverse(lines);
                feedbacks =  Arrays.copyOfRange(feedbacks, 1, feedbacks.length);
            }
            if(feedbacks.length>lines.size()){
                room.replyTo(event.getMessage().getId(), "Too many feedbacks, Too less reports");
                return;
            }
            for(int i  =0 ;i<=feedbacks.length;i++){
                String feedback = feedbacks[i];
                String line = lines.get(i);
                FileUtils.appendToFile(FilePathUtils.outputCSVLogFile,feedback+","+line);
                FileUtils.removeFromFile(FilePathUtils.outputCompleteLogFile,line);
                FileUtils.removeFromFile(FilePathUtils.outputReportLogFile,line.split(",")[0]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
