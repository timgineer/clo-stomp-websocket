# clo-stomp-websocket

This project explores a WebSocket implementation of the Clue board game.

clo-stomp-websocket is derived from and heavily influenced by the [STOMP WebSocket GS guide for the Spring Framework](https://spring.io/guides/gs/messaging-stomp-websocket/).

## Project Progress
1. [x] Swing Client Prototype interface.
2. [x] ChatHistory implementation.
3. [x] Addition of the GameSession class, implementation of clientjoin/ gamesetup UCs.
4. [x] Move chat into game GameSession context.
5. [x] Implementation of EndGameMessage.
6. [ ] Implment JUnit on message classes and GameEntityID
7. [ ] Implment GameSession state machine.
8. [ ] Addition of game board move messaging.

# Build Hints

Build with: **gradle build**

Launch the server with: **java -jar build/libs/clo-stomp-websocket.jar**

With the server running, connect your browser to [localhost port 8080](http://localhost:8080/).
