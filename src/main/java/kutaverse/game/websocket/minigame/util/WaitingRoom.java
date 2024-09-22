package kutaverse.game.websocket.minigame.util;

import kutaverse.game.minigame.service.MiniGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WaitingRoom {
    private final Map<String, WaitingUser> waitingUsers = new HashMap<>();
    private final Map<String, Boolean> userConnections = new HashMap<>();

    private final MiniGameService miniGameService;

    public void addUsers(String player1, String player2, String roomId) {
        waitingUsers.put(roomId, new WaitingUser(player1, null, player2, null));
        userConnections.put(player1,false);
        userConnections.put(player2, false);
    }

    public void userConnected(String userId) {
        userConnections.put(userId, true);
    }

    public void addUser(String player, WebSocketSession webSocketSession){
        WaitingUser waitingUser = waitingUsers.values()
                .stream()
                .filter(user -> user.getPlayer1().equals(player) || user.getPlayer2().equals(player))
                .findFirst()
                .orElse(null);

        if (waitingUser != null) {
            if (waitingUser.getPlayer1().equals(player)) {
                waitingUser.setSession1(webSocketSession);
            } else {
                waitingUser.setSession2(webSocketSession);
            }
            userConnected(player);
        }
    }

    public boolean allUsersConnected(String roomId) {
        WaitingUser waitingUser = waitingUsers.get(roomId);
        if (waitingUser != null) {
            return userConnections.get(waitingUser.getPlayer1()) && userConnections.get(waitingUser.getPlayer2());
        }
        return false;
    }

    @Scheduled(fixedRate = 1000)
    public void matchPlayers() {
        waitingUsers.forEach((roomId, waitingUser) -> {
            if (allUsersConnected(roomId)) {
                createGameRoom(waitingUser);
                waitingUsers.remove(roomId);
                userConnections.remove(waitingUser.getPlayer1());
                userConnections.remove(waitingUser.getPlayer2());
            }
        });
    }

    private void createGameRoom(WaitingUser waitingUser) {
        String roomId = waitingUser.getPlayer1() + "-" + waitingUser.getPlayer2();
        GameRoom gameRoom = new GameRoom(roomId, miniGameService);

        gameRoom.addPlayer(waitingUser.getPlayer1(), waitingUser.getSession1());
        gameRoom.addPlayer(waitingUser.getPlayer2(), waitingUser.getSession2());

        GameRoomManager.addGameRoom(gameRoom);
        gameRoom.broadcastMessage("게임 시작");
    }
}
