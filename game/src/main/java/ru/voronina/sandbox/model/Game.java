package ru.voronina.sandbox.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Game {

    @NotNull
    private Long id;

    @NotNull
    private Integer numOfPlayers;

    @NotNull
    private String status;

    @NotNull
    private Arena arena;

    @NotNull
    private List<Player> players;
}
