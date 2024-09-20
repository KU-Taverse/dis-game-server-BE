package kutaverse.game.websocket.taggame.handler;

import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import kutaverse.game.websocket.taggame.dto.request.TagGameMatchRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameUserRequest;
import kutaverse.game.websocket.taggame.util.TagGameMatchingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

@RequiredArgsConstructor
@Component
public class TagGameMovingHandler implements CustomHandler{

    private final TagGameUserRepository tagGameUserRepository;

    @Override
    public void handler(Object object, WebSocketSession session) {
        TagGameUserRequest tagGameUserRequest =(TagGameUserRequest) object;
        TagGameUser tagGameUser = TagGameUserRequest.toEntity(tagGameUserRequest);
        tagGameUserRepository.add(tagGameUser).subscribe();
    }
}
