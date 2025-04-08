package com.linkmoa.source.domain.notification.repository.rdb;

import static com.linkmoa.source.domain.notification.entity.QNotification.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationQueryRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public Long updateUnreadNotificationsToReadByReceiverEmail(String receiverEmail) {
		return jpaQueryFactory
			.update(notification)
			.set(notification.isRead, true)
			.where(
				notification.receiver.email.eq(receiverEmail)
					.and(notification.isRead.eq(false))
			)
			.execute();
	}

	public Long countUnreadNotificationsByReceiverEmail(String receiverEmail) {
		return jpaQueryFactory
			.select(notification.count().coalesce(0L))
			.from(notification)
			.where(
				notification.receiver.email.eq(receiverEmail)
					.and(notification.isRead.eq(false))
			)
			.fetchOne();
	}

}
