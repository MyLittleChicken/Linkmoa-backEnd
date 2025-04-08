package com.linkmoa.source.domain.notification.repository.adapter;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.entity.Member;
import com.linkmoa.source.domain.notification.entity.Notification;
import com.linkmoa.source.domain.notification.repository.NotificationDataAccess;
import com.linkmoa.source.domain.notification.repository.rdb.NotificationJpaRepository;
import com.linkmoa.source.domain.notification.repository.rdb.NotificationQueryRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationDataAccessImpl implements NotificationDataAccess {

	private final NotificationJpaRepository notificationJpaRepository;
	private final NotificationQueryRepositoryImpl notificationQueryRepository;

	@Override
	public Long updateUnreadNotificationsToReadByReceiverEmail(String receiverEmail) {
		return notificationQueryRepository.updateUnreadNotificationsToReadByReceiverEmail(receiverEmail);
	}

	@Override
	public Long countUnreadNotificationsByReceiverEmail(String receiverEmail) {
		return notificationQueryRepository.countUnreadNotificationsByReceiverEmail(receiverEmail);
	}

	@Override
	public void deleteAllBySenderEmailOrReceiver(Member member) {
		notificationJpaRepository.deleteAllBySenderEmailOrReceiver(member);
	}

	@Override
	public Notification save(Notification notification) {
		return notificationJpaRepository.save(notification);
	}
}
