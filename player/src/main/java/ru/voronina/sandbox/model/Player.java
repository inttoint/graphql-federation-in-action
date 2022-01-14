package ru.voronina.sandbox.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class Player {

    @NotNull
    private Long id;

    @NotBlank
    private String nickname;

    @NotNull
    private Integer level;

    @NotNull
    private Boolean active = true;

    public Player(Long id, String nickname, Integer level) {
        this.id = id;
        this.nickname = nickname;
        this.level = level;
    }
}
