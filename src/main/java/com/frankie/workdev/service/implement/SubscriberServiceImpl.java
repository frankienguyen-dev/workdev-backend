package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.subscriber.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.UpdateSubscriberDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.Skill;
import com.frankie.workdev.entity.Subscriber;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.SkillRepository;
import com.frankie.workdev.repository.SubscriberRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.SubscriberService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private SubscriberRepository subscriberRepository;
    private SkillRepository skillRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<CreateSubscriberDto> createSubscriber(CreateSubscriberDto subscriberDto) {
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(subscriberDto.getEmail());
        subscriber.setName(subscriberDto.getName());
        List<Skill> skills = getSkills(subscriberDto.getSkills());
        subscriber.setSkills(skills);
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        CreateSubscriberDto createSubscriberDto = modelMapper
                .map(savedSubscriber, CreateSubscriberDto.class);
        return ApiResponse.success(
                "Subscriber created successfully",
                HttpStatus.CREATED,
                createSubscriberDto
        );
    }

    @Override
    public ApiResponse<SubscriberResponse> getAllSubscriber(int pageNo, int pageSize,
                                                            String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Subscriber> subscribers = subscriberRepository.findAll(pageable);
        List<Subscriber> subscriberContentList = subscribers.getContent();
        List<SubscriberDto> subscriberDtos = subscriberContentList.stream()
                .map(subscriber -> {
                    try {
                        return modelMapper.map(subscriber, SubscriberDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(subscribers.getNumber() + 1);
        metaData.setPageSize(subscribers.getSize());
        metaData.setTotalElements(subscribers.getTotalElements());
        metaData.setTotalPages(subscribers.getTotalPages());
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        subscriberResponse.setData(subscriberDtos);
        subscriberResponse.setMeta(metaData);
        return ApiResponse.success(
                "Subscriber fetched successfully",
                HttpStatus.OK,
                subscriberResponse
        );
    }

    @Override
    public ApiResponse<SubscriberDto> getSubsciberById(String id) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        SubscriberDto subscriberDto = modelMapper.map(findSubscriber, SubscriberDto.class);
        return ApiResponse.success(
                "Subscriber fetched successfully",
                HttpStatus.OK,
                subscriberDto
        );
    }

    @Override
    public ApiResponse<UpdateSubscriberDto> updateSubcriberById(
            String id, UpdateSubscriberDto updateSubscriberDto) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        findSubscriber.setName(updateSubscriberDto.getName());
        findSubscriber.setEmail(updateSubscriberDto.getEmail());
        List<Skill> skills = getSkills(updateSubscriberDto.getSkills());
        findSubscriber.setSkills(skills);
        Subscriber savaUpdate = subscriberRepository.save(findSubscriber);
        UpdateSubscriberDto updateSubscriber = modelMapper.map(savaUpdate, UpdateSubscriberDto.class);
        return ApiResponse.success(
                "Updated subscriber successfully",
                HttpStatus.OK,
                updateSubscriber
        );
    }

    @Override
    public ApiResponse<String> deleteSubscriberById(String id) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        subscriberRepository.delete(findSubscriber);
        return ApiResponse.success(
                "Subscriber deleted successfully",
                HttpStatus.OK,
                null
        );
    }


    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        String getUserEmail = authentication.getName();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }

    private List<Skill> getSkills(List<SkillDto> skills) {
        List<Skill> skillList = new ArrayList<>();
        for (SkillDto skillDto : skills) {
            Skill existingSkill = skillRepository.findByName(skillDto.getName());
            if (existingSkill == null) {
                Skill newSkill = new Skill();
                newSkill.setName(skillDto.getName());
                skillRepository.save(newSkill);
                skillList.add(newSkill);
            }
            if (existingSkill != null) {
                skillList.add(existingSkill);
            }
        }
        return skillList;
    }
}
