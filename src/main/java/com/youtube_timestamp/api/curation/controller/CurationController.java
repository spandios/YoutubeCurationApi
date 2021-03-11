package com.youtube_timestamp.api.curation.controller;

import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import com.youtube_timestamp.api.curation.dto.CurationCreateDTO;
import com.youtube_timestamp.api.curation.dto.CurationResponse;
import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.service.CurationService;
import com.youtube_timestamp.api.curation.service.CurationTodayCntService;
import com.youtube_timestamp.api.security.CurrentUser;
import com.youtube_timestamp.api.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@Slf4j
@RequestMapping("/api/v1/curation")
public class CurationController {

    private final CurationService curationService;

    private final CurationTodayCntService curationTodayCntService;

    public CurationController(CurationService curationService, CurationTodayCntService curationTodayCntService) {
        this.curationService = curationService;
        this.curationTodayCntService = curationTodayCntService;
    }

    @GetMapping("me")
    ResponseEntity myList(@CurrentUser SecurityUser sc) {
        List<Curation> curationEntities = curationService.myList(sc.toUser());
        return ResponseEntity.ok(curationEntities.stream().map(CurationResponse::new));
    }

    @GetMapping("/{id}")
    ResponseEntity detail(@PathVariable("id") Long id) {
        Curation curation = curationService.findByIdWithJoin(id);
        curationTodayCntService.viewCuration(curation);
        curationService.view(curation);
        return ResponseEntity.ok(new CurationResponse(curation));
    }

    @GetMapping("")
    ResponseEntity list(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return ResponseEntity.ok(curationService.list(pageable));
    }

    @GetMapping("pop")
    ResponseEntity rapidPopularList() {
        return ResponseEntity.ok(curationService.popList());
    }

    @PostMapping("/")
    ResponseEntity createCuration(@CurrentUser SecurityUser sc, @RequestBody CurationCreateDTO dto) {
        if(sc == null){
            throw new UnAuthorizedException();
        }
        dto.setUserEntity(sc.toUser());
        return ResponseEntity.ok(curationService.create(dto));
    }


}
