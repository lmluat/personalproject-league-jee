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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "play")
public class PlayerDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="player_id", nullable = false)
    private PlayerEntity playerEntity;

    @ManyToOne
    @JoinColumn(name ="team_detail_id", nullable = false)
    private TeamDetailEntity teamDetailEntity;

    @Column(name="position", nullable = false)
    private String position;

    @Column(name="is_captain")
    private Boolean isCaptain;

}
