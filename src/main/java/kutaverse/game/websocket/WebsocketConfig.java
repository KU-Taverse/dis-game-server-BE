package kutaverse.game.websocket;


import kutaverse.game.websocket.minigame.MiniGameWebsocketHandler;
import kutaverse.game.websocket.minigame.dto.GameResultDTO;
import kutaverse.game.websocket.minigame.util.WaitingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebsocketConfig {
	private final WaitingRoom waitingRoom;

	@Bean
	public MiniGameWebsocketHandler miniGameWebsocketHandler() {
		return new MiniGameWebsocketHandler(waitingRoom);
	}


	@Bean
	public SimpleUrlHandlerMapping handlerMapping(MiniGameWebsocketHandler miniWsh) {
		return new SimpleUrlHandlerMapping(Map.of(
				"/game", miniWsh
		), 1);
	}


	@Bean
	public WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public Sinks.Many<String> sink() {
		return Sinks.many().multicast().directBestEffort();
	}

}