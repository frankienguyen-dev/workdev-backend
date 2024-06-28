package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.subscriber.request.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.request.UpdateSubscriberDto;
import com.frankie.workdev.dto.subscriber.response.CreateSubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.ListSubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.UpdateSubscriberResponse;
import com.frankie.workdev.entity.Skill;
import com.frankie.workdev.entity.Subscriber;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.SkillRepository;
import com.frankie.workdev.repository.SubscriberRepository;
import com.frankie.workdev.service.SubscriberService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<CreateSubscriberResponse> createSubscriber(CreateSubscriberDto subscriberDto) {
        Subscriber findSubscriber = subscriberRepository.findByEmail(subscriberDto.getEmail());
        if (findSubscriber != null) {
            throw new ResourceExistingException("Subscriber", "email", subscriberDto.getEmail());
        }
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(subscriberDto.getEmail());
        subscriber.setName(subscriberDto.getName());
        if (subscriberDto.getSkills() != null) {
            List<Skill> skills = getSkills(subscriberDto.getSkills());
            subscriber.setSkills(skills);
        }
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        CreateSubscriberResponse createSubscriberDto = modelMapper
                .map(savedSubscriber, CreateSubscriberResponse.class);
        return ApiResponse.success(
                "Subscriber created successfully",
                HttpStatus.CREATED,
                createSubscriberDto
        );
    }

    @Override
    public ApiResponse<ListSubscriberResponse> getAllSubscriber(int pageNo, int pageSize,
                                                                String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Subscriber> subscribers = subscriberRepository.findAll(pageable);
        List<Subscriber> subscriberContentList = subscribers.getContent();
        List<SubscriberResponse> subscriberDtos = subscriberContentList.stream()
                .map(subscriber -> {
                    try {
                        return modelMapper.map(subscriber, SubscriberResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(subscribers.getNumber());
        metaData.setPageSize(subscribers.getSize());
        metaData.setTotalElements(subscribers.getTotalElements());
        metaData.setTotalPages(subscribers.getTotalPages());
        metaData.setLastPage(subscribers.isLast());
        ListSubscriberResponse subscriberResponse = new ListSubscriberResponse();
        subscriberResponse.setData(subscriberDtos);
        subscriberResponse.setMeta(metaData);
        return ApiResponse.success(
                "Subscriber fetched successfully",
                HttpStatus.OK,
                subscriberResponse
        );
    }

    @Override
    public ApiResponse<SubscriberResponse> getSubscriberById(String id) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        SubscriberResponse subscriberDto = modelMapper.map(findSubscriber, SubscriberResponse.class);
        return ApiResponse.success(
                "Subscriber fetched successfully",
                HttpStatus.OK,
                subscriberDto
        );
    }

    @Override
    public ApiResponse<UpdateSubscriberResponse> updateSubscriberById(
            String id, UpdateSubscriberDto updateSubscriberDto) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        Subscriber existingSubscriber = subscriberRepository.findByEmail(updateSubscriberDto.getEmail());
        if (existingSubscriber != null && !existingSubscriber.getId().equals(findSubscriber.getId())) {
            throw new ResourceExistingException("Subscriber", "email", updateSubscriberDto.getEmail());
        }
        findSubscriber.setName(updateSubscriberDto.getName());
        findSubscriber.setEmail(updateSubscriberDto.getEmail());
        List<Skill> skills = getSkills(updateSubscriberDto.getSkills());
        findSubscriber.setSkills(skills);
        Subscriber savaUpdate = subscriberRepository.save(findSubscriber);
        UpdateSubscriberResponse updateSubscriber = modelMapper
                .map(savaUpdate, UpdateSubscriberResponse.class);
        return ApiResponse.success(
                "Updated subscriber successfully",
                HttpStatus.OK,
                updateSubscriber
        );
    }

    @Override
    public void deleteSubscriberById(String id) {
        Subscriber findSubscriber = subscriberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscriber", "id", id)
        );
        subscriberRepository.delete(findSubscriber);
        ApiResponse.success(
                "Subscriber deleted successfully",
                HttpStatus.OK,
                null
        );
    }

    @Override
    public ApiResponse<ListSubscriberResponse> searchSubscriberByEmail(String email, int pageNo,
                                                                       int pageSize, String sortBy,
                                                                       String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Subscriber> subscribers = subscriberRepository.searchSubscriberByEmail(email, pageable);
        List<Subscriber> subscriberContentList = subscribers.getContent();
        List<SubscriberResponse> subscriberDtoList = subscriberContentList.stream().map(
                subscriber -> {
                    try {
                        return modelMapper.map(subscriber, SubscriberResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(subscribers.getNumber());
        metaData.setPageSize(subscribers.getSize());
        metaData.setTotalElements(subscribers.getTotalElements());
        metaData.setTotalPages(subscribers.getTotalPages());
        metaData.setLastPage(subscribers.isLast());
        ListSubscriberResponse subscriberResponse = new ListSubscriberResponse();
        subscriberResponse.setData(subscriberDtoList);
        subscriberResponse.setMeta(metaData);
        return ApiResponse.success(
                "Search subscriber fetched successfully",
                HttpStatus.OK,
                subscriberResponse
        );
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
