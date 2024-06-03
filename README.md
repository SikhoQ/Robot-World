# Robot World Flowchart

```mermaid
graph TD
    A1[Start Server] --> A2[Initialize Server Socket]
    A2 --> A3[Start Listening for Client Connections]
    A3 -->|Client Connects| B1[Create RobotClientHandler]
    B1 --> B2[Add ClientHandler to Active Clients]
    B2 --> B3[Start New Thread for Client Communication]

    B3 --> C1[Receive Client Request]
    C1 --> C2[Parse JSON Request]
    C2 --> C3{Determine Command Type}
    C3 -->|Launch| D1[Execute Launch Command]
    C3 -->|Look| D2[Execute Look Command]
    C3 -->|State| D3[Execute State Command]

    D1 --> D4[Update World State]
    D2 --> D5[Provide World State Information]
    D3 --> D6[Update Robot Position/Status]

    D4 --> E1[Create Response Object]
    D5 --> E1
    D6 --> E1
    E1 --> E2[Convert Response to JSON]
    E2 --> E3[Send JSON Response to Client]

    E3 --> F1{Client Disconnects?}
    F1 -->|Yes| G1[Remove ClientHandler from Active Clients]
    G1 --> G2[Close Client Connection]
    F1 -->|No| C1

    A3 -->|Client Connection Loop| A3

    %% Client Side
    H1[Start Client] --> H2[Display Program Title and Welcome Message]
    H2 --> H3[Initialize RobotClient]
    H3 --> H4[Start Connection to Server]

    H4 --> I1[Get User Input for Launch]
    I1 --> I2[Handle User Input]
    I2 --> I3[Create ClientRequest]
    I3 --> I4[Convert Request to JSON]
    I4 --> I5[Send JSON Request to Server]
    I5 --> I6[Receive Server Response]
    I6 --> I7[Parse Server Response]
    I7 --> I8[Display Robot Details]

    I8 --> J1{User Command Loop}
    J1 -->|exit| K1[Close Connection to Server]
    K1 --> K2[Exit Program]
    J1 -->|not exit| L1[Get User Command]
    L1 --> L2[Handle User Input]
    L2 --> L3[Create ClientRequest]
    L3 --> L4[Convert Request to JSON]
    L4 --> L5[Send JSON Request to Server]
    L5 --> L6[Receive Server Response]
    L6 --> L7[Parse Server Response]
    L7 --> L8[Display Updated Robot Details]
    L8 --> J1

    classDef server fill:#f96,stroke:#333,stroke-width:4px,color:#000;
    classDef client fill:#69f,stroke:#333,stroke-width:4px,color:#000;

    class A1,A2,A3,B1,B2,B3,C1,C2,C3,D1,D2,D3,D4,D5,D6,E1,E2,E3,F1,G1,G2 server
    class H1,H2,H3,H4,I1,I2,I3,I4,I5,I6,I7,I8,J1,K1,K2,L1,L2,L3,L4,L5,L6,L7,L8 client
