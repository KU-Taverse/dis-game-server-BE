package kutaverse.game.websocket.minigame.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.socket.WebSocketSession;

@Getter
@Setter
public class WaitingUser {
    private final String player1;
    private WebSocketSession session1;
    private final String player2;
    private WebSocketSession session2;

    public WaitingUser(String player1, WebSocketSession session1, String player2, WebSocketSession session2) {
        this.player1 = player1;
        this.session1 = session1;
        this.player2 = player2;
        this.session2 = session2;
    }
}
