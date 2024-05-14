//package za.co.wethinkcode.robotworlds;
//
//public class Robot {
//
//    private final Position TOP_LEFT = new Position(-100,200);
//    private final Position BOTTOM_RIGHT = new Position(100,-200);
//
//    public static final Position CENTRE = new Position(0,0);
//
//    private Position position;
//    private Direction currentDirection;
//    private String status;
//    private final String name;
//
//    // to replay commands, robot must handle each command in the array list
//    // store commands in list, filtering out non-movement (store movement in array)
//    // put logic inside handlecommand
//
//    public Robot(String name) {
//        this.name = name;
//        this.status = "Ready";
//        this.position = CENTRE;
//        this.currentDirection = Direction.UP;
//    }
//
//    public String getStatus() {
//        return this.status;
//    }
//
//    public Direction getCurrentDirection() {
//        return this.currentDirection;
//    }
//
//    public Position getPosition() {
//        return this.position;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public void setDirection(Direction direction) {
//        this.currentDirection = direction;
//    }
//
//    public boolean handleCommand(Command command) {
//        return command.execute(this);
//    }
//
//    public boolean updatePosition(int nrSteps){
//        int newX = this.position.getX();
//        int newY = this.position.getY();
//
//        if (Direction.UP.equals(this.currentDirection)) {
//            newY += nrSteps;
//        }
//
//        else if (Direction.DOWN.equals(this.currentDirection)) {
//            newY -= nrSteps;
//        }
//
//        else if (Direction.LEFT.equals(this.currentDirection)) {
//            newX -= nrSteps;
//        }
//
//        else if (Direction.RIGHT.equals(this.currentDirection)){
//            newX += nrSteps;
//        }
//
//        Position newPosition = new Position(newX, newY);
//        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
//            this.position = newPosition;
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        return "[" + this.position.getX() + "," + this.position.getY() + "] "
//                + this.name + "> " + this.status;
//    }
//}
