package project.wanna_help.Activity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivitiesRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByStatus(Status status);

}
