package com.youtube_timestamp.api.curation.controller;

import com.youtube_timestamp.api.auth.service.JwtService;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import com.youtube_timestamp.api.curation.dto.CurationCreateDTO;
import com.youtube_timestamp.api.curation.dto.CurationDetailResponse;
import com.youtube_timestamp.api.curation.dto.CurationListResponse;
import com.youtube_timestamp.api.curation.dto.TimestampUpdateDTO;
import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.Timestamp;
import com.youtube_timestamp.api.curation.service.CurationService;
import com.youtube_timestamp.api.curation.service.CurationTodayCntService;
import com.youtube_timestamp.api.curation.service.TimestampServiceImpl;
import com.youtube_timestamp.api.security.CurrentUser;
import com.youtube_timestamp.api.security.Jwt;
import com.youtube_timestamp.api.user.service.UserService;
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

    private final TimestampServiceImpl timestampService;
    private final CurationTodayCntService curationTodayCntService;

    private final JwtService jwtService;

    private final UserService userService;

    public CurationController(CurationService curationService, TimestampServiceImpl timestampService, CurationTodayCntService curationTodayCntService, JwtService jwtService, UserService userService) {
        this.curationService = curationService;
        this.timestampService = timestampService;
        this.curationTodayCntService = curationTodayCntService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("me")
    ResponseEntity myList(@Jwt CurrentUser sc) {
        if (sc != null) {
            List<Curation> curationEntities = curationService.myList(sc.toUser());
            return ResponseEntity.ok(curationEntities.stream().map(CurationListResponse::new));
        } else {
            throw new UnAuthorizedException();
        }
    }

    @GetMapping("me/{id}")
    ResponseEntity detail(@Jwt CurrentUser cu, @PathVariable("id") Long curationId) {
        Curation curation = curationService.findByIdWithJoin(curationId);
        curationTodayCntService.viewCuration(curation);
        curationService.view(curation);
        CurationDetailResponse dResponse = new CurationDetailResponse(curation);
        if (cu != null) {
            System.out.println("cu:" + cu.getEmail());
        }
        if (cu != null) {
            if (curation.getUser().getEmail().equals(cu.getEmail())) {
                dResponse.setYours(true);
            }
        }

        return ResponseEntity.ok(dResponse);
    }

    @PutMapping("me/{id}/timestamp")
    ResponseEntity updateTimestamp(@Jwt CurrentUser cu, @RequestBody TimestampUpdateDTO dto){
        Timestamp timestamp = timestampService.updateTimestamp(dto);
        return ResponseEntity.ok(timestamp);
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
    ResponseEntity createCuration(@Jwt CurrentUser sc, @RequestBody CurationCreateDTO dto) {
        if (sc == null) {
            throw new UnAuthorizedException();
        }
        dto.setUserEmail(sc.getEmail());
        return ResponseEntity.ok(curationService.create(dto));
    }


}
