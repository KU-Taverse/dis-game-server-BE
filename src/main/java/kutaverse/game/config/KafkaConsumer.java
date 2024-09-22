package kutaverse.game.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.websocket.minigame.util.WaitingRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    @Value("${server.port}")
    private String serverPort;
    private final ObjectMapper objectMapper;
    private final WaitingRoom waitingRoom;

    @KafkaListener(topics = "#{@environment.getProperty('server.port')}")
    public Mono<Void> updateQty(String kafkaMessage){
        log.info("kafka message: ->" +kafkaMessage);

        try {
            RoomAssignmentMessage messageDTO = objectMapper.readValue(kafkaMessage, RoomAssignmentMessage.class);
            String player1 = messageDTO.getPlayer1();
            String player2 = messageDTO.getPlayer2();
            String roomId = messageDTO.getRoomId();

            // 대기 방에 유저 추가
            waitingRoom.addUsers(player1, player2, roomId);

        } catch (JsonProcessingException e) {
            log.error("kafka 데이터 처리 문제", e);
        }



        // 여기서 이 정보를 바탕으로 값을 매칭 큐로 전달하고 해당 큐에서 사용자 2명이 접속했는지 확인하고 룸 생성
        return Mono.empty();

    }
}

