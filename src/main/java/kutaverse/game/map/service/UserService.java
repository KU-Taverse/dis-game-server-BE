package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.UserRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface UserService {

    Mono<User> create(User user);

    Flux<User> findAll();

    Flux<User> findAllByTime(long length);

    Mono<User> findOne(String userId);

    Mono<Long> deleteById(String userId);

    Mono<User> update(UserRequestDto userRequestDto);

    Mono<User> changeState(String id, Status status);
}
