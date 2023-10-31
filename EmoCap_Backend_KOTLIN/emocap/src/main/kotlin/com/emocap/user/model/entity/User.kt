package com.emocap.user.model.entity

import com.emocap.common.model.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.mapping.Join
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
@EnableJpaAuditing
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0,

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @Column(name = "password", length = 255, nullable = false)
    val password: String?,

    @Column(name = "nickname", length = 255, nullable = false, unique = true)
    val nickname: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role?,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name = "user_friend",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name="friend_id")])
    @JsonIgnore
    var friends: MutableList<User> = mutableListOf(),
){}