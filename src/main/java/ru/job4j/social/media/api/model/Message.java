package ru.job4j.social.media.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model representing a message between users")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @Schema(description = "User who sent the message", example = "User object representing the sender")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    @Schema(description = "User who received the message", example = "User object representing the receiver")
    private User receiver;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Schema(description = "Content of the message", example = "Hello, how are you?")
    private String message;

    @Schema(description = "Date and time when the message was sent", example = "2024-11-08T15:30:00")
    private LocalDateTime sentAt;
}
