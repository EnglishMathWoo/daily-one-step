package com.readnumber.dailyonestep.common.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "AUTHOR")
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column
    @Comment("author type")
    @Enumerated(EnumType.STRING)
    val authorType: AuthorTypeEnum,
    @Column
    @Comment("도메인 pk값")
    val associatedId: Long
) {

    companion object {
        fun adminAuthor(admin: Admin) = Author(authorType = AuthorTypeEnum.ADMIN, associatedId = admin.id!!)
        fun userAuthor(user: TempUser) = Author(authorType = AuthorTypeEnum.USER, associatedId = user.id!!)
    }
}