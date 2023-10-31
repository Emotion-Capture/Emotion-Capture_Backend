package com.emocap.chatroom.model.entity

import com.emocap.user.model.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDateTime

@Entity
@Table(name = "chatrooms")
@EntityListeners(AuditingEntityListener::class)
@EnableJpaAuditing
data class Chatroom (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    @JsonProperty("chatroom_id")
    val id : Long? = 0,

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name = "user_chatroom",
        joinColumns = [JoinColumn(name = "chatroom_id")],
        inverseJoinColumns = [JoinColumn(name="user_id")])
    val users: MutableList<User> = mutableListOf(),
) {
}