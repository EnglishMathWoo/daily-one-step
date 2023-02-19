package com.readnumber.dailyonestep.user

import com.readnumber.dailyonestep.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "USER")
class User(
    @Column
    @Comment(value = "아이디")
    val username: String,

    @Column
    @Comment(value = "암호화된 비밀번호")
    var encryptedPassword: String,

    @Column
    @Comment(value = "이름")
    var name: String,

    @Column
    @Comment(value = "전화번호")
    var phone: String? = null
) : BaseEntity()
