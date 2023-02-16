package com.readnumber.dailyonestep.post.controller

import com.readnumber.dailyonestep.post.dto.request.PostCreateDto
import com.readnumber.dailyonestep.post.dto.request.PostModifyDto
import com.readnumber.dailyonestep.post.dto.request.PostSearchQueryParameter
import com.readnumber.dailyonestep.post.dto.request.PostWrapperDto
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import com.readnumber.dailyonestep.post.service.PostService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts")
@RestController
class PostControllerImpl(
    private val postService: PostService,
) : PostController {

    @PostMapping
    override fun createPost(
        @Valid @RequestBody
        dto: PostCreateDto
    ): PostWrapperDto {
        return PostWrapperDto.from(
            postService.createPost(dto)
        )
    }

    @GetMapping("/{id}")
    override fun getPost(
        @PathVariable(value = "id")
        id: Long
    ): PostWrapperDto {
        return PostWrapperDto.from(postService.getPost(id))
    }

    @GetMapping
    override fun searchPosts(
        queryParameter: PostSearchQueryParameter,
        pageable: Pageable
    ): MultiplePostWrapperDto {
        return postService.findPosts(queryParameter, pageable)
    }

    @PatchMapping("/{id}")
    override fun modifyPost(
        @PathVariable(value = "id")
        id: Long,
        @Valid @RequestBody
        dto: PostModifyDto
    ): PostWrapperDto {
        return PostWrapperDto.from(
            postService.modifyPost(id, dto)
        )
    }

    @DeleteMapping("/{id}")
    override fun deletePost(
        @PathVariable(value = "id")
        id: Long,
    ): Boolean {
        postService.deletePost(id)
        return true
    }

}
