package kutaverse.game.websocket.map.dto.response;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private String userId;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private Status status;

    /*public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.positionX = user.getPositionX();
        this.positionY = user.getPositionY();
        this.positionZ = user.getPositionZ();
        this.rotationPitch = user.getRotationPitch();
        this.rotationYaw = user.getRotationYaw();
        this.rotationRoll = user.getRotationRoll();
        this.status = String.valueOf(user.getStatus());
    }*/

    public static UserResponseDto toDto(User user){
        return builder()
                .userId(user.getUserId())
                .positionX(user.getPositionX())
                .positionY(user.getPositionY())
                .positionZ(user.getPositionZ())
                .rotationPitch(user.getRotationPitch())
                .rotationRoll(user.getRotationRoll())
                .rotationYaw(user.getRotationYaw())
                .status(user.getStatus())
                .build();
    }
}
