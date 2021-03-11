package com.youtube_timestamp.api.curation.service;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.CurationTodayCnt;
import com.youtube_timestamp.api.curation.repository.CurationTodayCntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurationTodayCntService {

    @Autowired
    CurationTodayCntRepository repository;

    @Autowired
    CurationService curationService;

    public void viewCuration(Curation curation){
        LocalDate now = LocalDate.now();
        Optional<CurationTodayCnt> byToday = repository.findByTodayAndCuration(now,curation);
        CurationTodayCnt curationTodayCnt;
        if(byToday.isPresent()){
            curationTodayCnt = byToday.get();
            curationTodayCnt.plusCnt();
        }else{
            curationTodayCnt = new CurationTodayCnt();
            curationTodayCnt.setCuration(curation);
            curationTodayCnt.setCnt(1);
            curationTodayCnt.setToday(now);
        }
        repository.save(curationTodayCnt);
    }

    public List<CurationTodayCnt> todayPopular(LocalDate date){
        return repository.findTodayPopular(date);
    }
}
