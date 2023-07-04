package project.wanna_help.notifications.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.notifications.persistence.domain.Notification;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByVolunteer(Volunteer volunteer);
}
