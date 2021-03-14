package com.youtube_timestamp.api.curation.service;

import com.youtube_timestamp.api.common.exception.NotFoundException;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import com.youtube_timestamp.api.curation.dto.CurationCreateDTO;
import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.CurationTodayCnt;
import com.youtube_timestamp.api.curation.repository.CurationRepository;
import com.youtube_timestamp.api.user.entity.UserEntity;
import com.youtube_timestamp.api.user.repository.UserRepository;
import com.youtube_timestamp.api.user.service.UserService;
import com.youtube_timestamp.api.youtube.entity.Youtube;
import com.youtube_timestamp.api.youtube.repository.YoutubeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class CurationService {
    @Autowired
    private CurationRepository repository;

    @Autowired
    private CurationTodayCntService todayCntService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private YoutubeRepository youtubeRepository;

    @Autowired
    private UserService userService;

    private NotFoundException notFoundCuration() {
        return new NotFoundException("큐레이션을 찾을 수 없습니다.");
    }

    public List<Curation> myList(UserEntity user) {
        return repository.myList(user);
    }

    public Curation findById(Long id) {
        return repository.findById(id).orElseThrow(this::notFoundCuration);
    }

    public Curation findByIdWithJoin(Long id) {
        return repository.findByIdWithJoin(id).orElseThrow(this::notFoundCuration);
    }

    public void view(Curation curation) {
        curation.setViewCnt(curation.viewCnt + 1);
        repository.save(curation);
    }

    public Curation create(CurationCreateDTO dto) {
        Optional<UserEntity> byEmail = userRepository.findByEmailAndActiveTrue(dto.getUserEmail());
        if(byEmail.isPresent()){
            Curation curation = dto.toEntity();
            Youtube youtube = dto.getYoutube();
            Optional<Youtube> optionalYoutube = youtubeRepository.findById(youtube.getId());
            if(optionalYoutube.isPresent()){
                curation.setYoutube(optionalYoutube.get());
            }else{
                curation.setYoutube(youtubeRepository.save(youtube));
            }
            curation.setUser(byEmail.get());
            curation.getTimestamp().forEach(timestampEntity -> timestampEntity.setCuration(curation));
            return repository.save(curation);
        }else{
            throw new UnAuthorizedException();
        }
    }

    public Curation update(Curation dto) {
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Curation> list(Pageable pageable) {
        return repository.pageable(pageable);
    }

    public List<Curation> popList() {
        List<CurationTodayCnt> curationTodayCnts = todayCntService.todayPopular(LocalDate.now());
        if (curationTodayCnts.size() > 2)
            return curationTodayCnts.stream().map(CurationTodayCnt::getCuration).collect(Collectors.toList());
        return Collections.EMPTY_LIST;
    }
}
