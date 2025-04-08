package com.linkmoa.source.domain.notification.repository;

import com.linkmoa.source.domain.member.entity.Member;
import com.linkmoa.source.domain.notification.entity.Notification;

public interface NotificationDataAccess {

	Long updateUnreadNotificationsToReadByReceiverEmail(String receiverEmail);

	Long countUnreadNotificationsByReceiverEmail(String receiverEmail);

	void deleteAllBySenderEmailOrReceiver(Member member);

	Notification save(Notification notification);

}
