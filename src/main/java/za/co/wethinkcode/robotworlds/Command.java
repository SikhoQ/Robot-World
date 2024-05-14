//package za.co.wethinkcode.robotworlds;
//
//
//public abstract class Command {
//    private final String name;
//    private String argument;
//
//    public abstract boolean execute(Robot target);
//
//    public Command(String name){
//        this.name = name.trim().toLowerCase();
//        this.argument = "";
//    }
//
//    public Command(String name, String argument) {
//        this(name);
//        this.argument = argument.trim();
//    }
//
//    public String getName() {                                                              //<2>
//        return name;
//    }
//
//    public String getArgument() {
//        return this.argument;
//    }
//
//    public static Command create(String instruction) {
//        String[] args = instruction.toLowerCase().trim().split(" ", 2);
//        // set arg to diff values for reversed w/ & w/out args
//        String arg = (args.length == 2) ? args[1] : "";
//
//        switch (args[0]){
//            // replace these with relevant commands
//            case "shutdown":
//            case "off":
//                return new ShutdownCommand();
//            case "help":
//                return new HelpCommand();
//            case "forward":
//                return new ForwardCommand(arg);
//            case "back":
//                return new BackCommand(arg);
//            case "left":
//                return new LeftCommand();
//            case "right":
//                return new RightCommand();
//            case "sprint":
//                return new SprintCommand(arg);
//            case "replay":
//                return new ReplayCommand(arg);
//            default:
//                throw new IllegalArgumentException("Unsupported command: " + instruction);
//        }
//    }
//}
//
//
