package project.wanna_help.activity.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.activity.communication.dto.ApplicationVolunteerStatusDTO;
import project.wanna_help.activity.persistence.domain.Application;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationVolunteerConverter {

    public ApplicationVolunteerStatusDTO activityToActivityDTO(Application application) {
        ApplicationVolunteerStatusDTO applicationVolunteerStatusDTO = new ApplicationVolunteerStatusDTO();
        applicationVolunteerStatusDTO.setApplicationId(application.getId());
        applicationVolunteerStatusDTO.setApplicationStatus(application.getApplicationStatus());
        applicationVolunteerStatusDTO.setTimeStamp(application.getTimeStamp());
        applicationVolunteerStatusDTO.setActivityId(application.getActivity().getId());
        applicationVolunteerStatusDTO.setTitle(application.getActivity().getTitle());
        applicationVolunteerStatusDTO.setDescription(application.getActivity().getDescription());
        applicationVolunteerStatusDTO.setRecommendedSkills(application.getActivity().getRecommendedSkills());
        applicationVolunteerStatusDTO.setStartDate(application.getActivity().getStartDate());
        applicationVolunteerStatusDTO.setEndDate(application.getActivity().getEndDate());
        applicationVolunteerStatusDTO.setActivityStatus(application.getActivity().getActivityStatus());
        return applicationVolunteerStatusDTO;
    }


    public List<ApplicationVolunteerStatusDTO> convert(List<Application> applications) {
        return applications.stream().map(this::activityToActivityDTO).collect(Collectors.toList());
    }

}
