package ru.job4j.social.media.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Subscription Model Information")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    @Schema(description = "User who is following another user", example = "User object representing the follower")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    @Schema(description = "User who is being followed", example = "User object representing the following user")
    private User following;

    @Schema(description = "Date and time when the subscription was created", example = "2024-11-08T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Indicates if the user is a subscriber", example = "true")
    private boolean isSubscriber;

    @Schema(description = "Indicates if there is a mutual follow friendship", example = "false")
    private boolean isFriend;
}