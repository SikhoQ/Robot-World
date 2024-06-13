
# Robot World DBN_14

Welcome to the Robot World (DBN_14) project! This project is a team effort to build a client/server robot world, where the server manages a world with obstacles, robots, and other artifacts, and the client controls a robot in this world by sending and receiving messages to and from the server.

## Table of Contents

- Introduction
- Client/Server Architecture
- How to Use
- Commands and Protocol
- Testing
- Flow Chart
- FAQ
- [Appendix](#appendix)

## Introduction

In this team project, we built a client/server Robot World consisting of two programs: the server and the client. The server manages a world with obstacles, robots, and other artifacts. The client controls a robot in this world by sending and receiving messages to and from the server.

## Client/Server Architecture

### The Server

The server is a standalone console Java program that:
- Builds the robot world with obstacles and artifacts.
- Listens on a network port for incoming robot connections.
- Manages multiple robots concurrently.
- Updates the world based on robot instructions and sends responses back to clients.

### The Robot Client

The client is a standalone Java program that:
- Connects to the robot world server over a network.
- Launches a robot into the world.
- Sends commands to the robot, receives updates, and adjusts the robot's state accordingly.
- Has no prior knowledge of the world and learns about it as the robot moves.

### Robot World Protocol

A common messaging protocol allowing client programs to connect to any server on the network was implemented. This ensures interoperability between different implementations.

## How to Use

### Prerequisites

- Java 11 or later
- A network connection

### Setup Instructions

1. To start the server, run the `RobotWorldServer` Class
   
2. To connect a client, run the `ClientMain` class
    
3. Once connected, you can send commands such as forward, back, left, right, look, fire, state, repair, and reload through the client's interface.

## Commands and Protocol

The user can use the following commands when playing the game. These commands allow the player to interact with their robot in various ways, providing control over its movement, state, and interactions within the robot world.

- `launch`: Launches a robot into the world.
- `look`: Provides a view of the surrounding area in the robot's field of view.
- `state`: Retrieves the current state of the robot, including its position, direction, shields, shots, and status.
- `forward`: Moves the robot forward by the specified number of steps.
- `back`: Moves the robot backward by the specified number of steps.
- `turn`: Turns the robot in a specified direction (left or right).
- `orientation`: Retrieves the robot's current orientation.
- `quit`: Quits the server and disconnects the robot.
- `fire`: Fires a shot from the robot's weapon.
- `repair`: Repairs the robot, restoring its shields.
- `reload`: Reloads the robot's weapon.

### Protocol

The commands follow a simple request/response protocol. When a command is issued by the client, the server processes it and sends back an appropriate response. This interaction allows the client to control the robot and get feedback on its actions.

## Testing

The project includes unit tests for all major functionalities. Integration tests ensure that the client and server interact correctly.

To run the tests:
mvn test

## Team Members
Bongiwe Ntshantsha
Lindani Jonase 
Nomonde Bhengu
Nonhle Sibiya
Sikho Qangule

## More Information

For more detailed documentation, architecture diagrams, and additional resources, please refer to our [Wiki](https://gitlab.wethinkco.de/sqangule023/dbn_14_robot_worlds/-/wikis/Robot-World-DBN_14).
   
