package com.readnumber.dailyonestep.notice

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.readnumber.dailyonestep.notice.QNotice.notice
import com.readnumber.dailyonestep.notice.dto.request.NoticeSearchQueryParameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class NoticeRepositorySupport(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(QNotice::class.java) {

    @Transactional(readOnly = true)
    fun searchAll(
        queryParam: NoticeSearchQueryParameter,
        pageable: Pageable?
    ): Page<Notice> {
        val expressions = arrayOf(
            innerGetKeywordExpression(queryParam.keyword)
        )

        if (pageable == null) {
            val content: List<Notice> = queryFactory
                .select(notice)
                .from(notice)
                .where(*expressions)
                .fetch()

            return PageImpl(content)
        }

        val totalCount: Long = queryFactory
            .select(notice.count())
            .from(notice)
            .where(*expressions)
            .fetchFirst()

        val orderBy = innerGetOrderSpecifier(pageable)
        val content: List<Notice> = queryFactory
            .select(notice)
            .from(notice)
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
        return notice.title.like("%$keyword%")
            .or(notice.content.like("%$keyword%"))
    }

    private fun innerGetOrderSpecifier(pageable: Pageable): Array<OrderSpecifier<*>> {
        val sort: Sort = pageable.sort
        if (sort.isEmpty) {
            return arrayOf(
                OrderSpecifier(Order.DESC, notice.id)
            )
        }
        val result: List<OrderSpecifier<*>> = sort
            .map {
                val order = if (it.direction.isAscending) Order.ASC else Order.DESC
                val path = when (it.property) {
                    "title" -> notice.title
                    "content" -> notice.content
                    else -> notice.id
                }
                OrderSpecifier(order, path)
            }.toList()

        return result.toTypedArray()
    }
}
