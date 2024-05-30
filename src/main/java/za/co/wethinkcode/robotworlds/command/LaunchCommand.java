public class LaunchCommand extends Command {
    public LaunchCommand(String make, String name) {
        super("launch", make, name);
    }

    @Override
    public boolean execute (Robot target) {
        return true;
    }
}
