package cli;

import core.Const;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import run.TaskRunner;
import utils.CliRunner;
import utils.CmdUtil;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public class SparkCli implements CliRunner {
    public static void main(String[] args) {
        CmdUtil.initRunner(args, "readfile", new SparkCli());
    }

    public Options initOptions() {
        Options options = new Options();
        options.addOption(new Option(CmdUtil.CLI_PARAM_I, true, "[INFO] InputFile which includes amazon reviews"));
        return options;
    }

    public boolean validateOptions(CommandLine commandLine) {
        return true;
    }

    public void start(CommandLine commandLine) {
        String inPutFile = commandLine.getOptionValue(CmdUtil.CLI_PARAM_I);
        System.out.println("[INFO] Starting task.");
        System.out.println("[INFO] Ready Go --------------------> ");
        TaskRunner.runTask(Const.TASK_NAME, inPutFile);
        System.out.println("[INFO] Task finished .");
    }
}
