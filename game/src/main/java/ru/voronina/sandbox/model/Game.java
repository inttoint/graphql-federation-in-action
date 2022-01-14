package ru.voronina.sandbox.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class Game {

    private Long id;
    private Integer numOfPlayers;
    private String status;
    private Arena arena;
    private List<Player> players;
}
