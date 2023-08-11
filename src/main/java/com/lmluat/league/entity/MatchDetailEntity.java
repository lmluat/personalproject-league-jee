package com.lmluat.league.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "match_detail")
@Getter
@Setter
public class MatchDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private MatchEntity match;

    @Column(name = "game_id")
    private Integer gameId;

    @ManyToOne
    @JoinColumn(name = "team_one", nullable = false)
    private TeamEntity teamOne;

    @ManyToOne
    @JoinColumn(name = "team_two", nullable = false)
    private TeamEntity teamTwo;

    @ManyToOne
    @JoinColumn(name = "winning_team")
    private TeamEntity winningTeam;

    @ManyToOne
    @JoinColumn(name = "mvp_player")
    private PlayerEntity mostValuablePlayer;
}
