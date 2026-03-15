package domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long chatId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    private String goal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
