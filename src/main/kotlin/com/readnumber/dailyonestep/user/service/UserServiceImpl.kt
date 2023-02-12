package com.readnumber.dailyonestep.user.service

import com.readnumber.dailyonestep.common.entity.Author
import com.readnumber.dailyonestep.common.error.exception.DuplicatedResourceException
import com.readnumber.dailyonestep.common.error.exception.IncorrectPasswordException
import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.common.repository.AuthorRepository
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import com.readnumber.dailyonestep.user.dto.request.UserChangePasswordDto
import com.readnumber.dailyonestep.user.dto.request.UserModifyDto
import com.readnumber.dailyonestep.user.dto.request.UserSignUpDto
import com.readnumber.dailyonestep.user.dto.response.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authorRepository: AuthorRepository,
) : UserService {

    @Transactional
    override fun signUp(dto: UserSignUpDto): UserDto {
        if (userRepository.existsByUsername(dto.username)) {
            throw DuplicatedResourceException("중복된 유저가 존재합니다.")
        }
        val encPassword = encryptPassword(dto.password)
        val newUser = dto.toEntity(encPassword)

        userRepository.save(newUser)
        authorRepository.save(Author.userAuthor(newUser))

        return UserDto.from(newUser)
    }

    @Transactional
    override fun changePassword(id: Long, dto: UserChangePasswordDto): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        if (!matchPassword(dto.currentPassword, user.encryptedPassword)) {
            throw IncorrectPasswordException("비밀번호가 일치하지 않습니다.")
        }
        user.encryptedPassword = encryptPassword(dto.newPassword)
        return UserDto.from(user)
    }

    @Transactional
    override fun modify(id: Long, dto: UserModifyDto): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        dto.modifyEntity(user)
        return UserDto.from(user)
    }

    @Transactional(readOnly = true)
    override fun getOne(id: Long): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        return UserDto.from(user)
    }

    @Transactional(readOnly = true)
    override fun count(): Long {
        return userRepository.count()
    }

    private fun matchPassword(rawPassword: String, encPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encPassword)
    }

    private fun encryptPassword(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    private fun getUserAndThrowExIfNotExisted(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }
}