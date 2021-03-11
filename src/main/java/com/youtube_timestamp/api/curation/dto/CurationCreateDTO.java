package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.Timestamp;
import com.youtube_timestamp.api.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.youtube_timestamp.api.common.message.ValidateMessage.NecessaryField;

@Getter
@Setter
public class CurationCreateDTO {

    @NotEmpty(message = NecessaryField)
    String title;
    @NotEmpty(message = NecessaryField)
    String youtube_url;
    @NotEmpty(message = NecessaryField)
    List<Timestamp> timestamp;

    UserEntity userEntity;

    public Curation toEntity() {
        return Curation.builder().user(userEntity).title(this.title).youtubeUrl(this.youtube_url).timestamp(this.timestamp).build();
    }

}
