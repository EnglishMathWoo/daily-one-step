package com.readnumber.dailyonestep.post

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.readnumber.dailyonestep.post.QPost.post
import com.readnumber.dailyonestep.post.dto.request.PostSearchQueryParameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PostRepositorySupport(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(QPost::class.java) {

    @Transactional(readOnly = true)
    fun searchAll(
        queryParam: PostSearchQueryParameter,
        pageable: Pageable?
    ): Page<Post> {
        val expressions = arrayOf(
            innerGetKeywordExpression(queryParam.keyword)
        )

        if (pageable == null) {
            val content: List<Post> = queryFactory
                .select(post)
                .from(post)
                .where(*expressions)
                .fetch()

            return PageImpl(content)
        }

        val totalCount: Long = queryFactory
            .select(post.count())
            .from(post)
            .where(*expressions)
            .fetchFirst()

        val orderBy = innerGetOrderSpecifier(pageable)
        val content: List<Post> = queryFactory
            .select(post)
            .from(post)
            .where(*expressions)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*orderBy)
            .fetch()

        return PageImpl(content, pageable, totalCount)
    }

    private fun innerGetKeywordExpression(
        keyword: String?
    ): BooleanExpression? {
        if (keyword.isNullOrEmpty()) return null
        return post.title.like("%$keyword%")
            .or(post.content.like("%$keyword%"))
    }

    private fun innerGetOrderSpecifier(pageable: Pageable): Array<OrderSpecifier<*>> {
        val sort: Sort = pageable.sort
        if (sort.isEmpty) {
            return arrayOf(
                OrderSpecifier(Order.DESC, post.id)
            )
        }
        val result: List<OrderSpecifier<*>> = sort
            .map {
                val order = if (it.direction.isAscending) Order.ASC else Order.DESC
                val path = when (it.property) {
                    "title" -> post.title
                    "content" -> post.content
                    else -> post.id
                }
                OrderSpecifier(order, path)
            }.toList()

        return result.toTypedArray()
    }
}