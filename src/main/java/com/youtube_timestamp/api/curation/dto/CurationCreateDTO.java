package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.Timestamp;
import com.youtube_timestamp.api.youtube.entity.Youtube;
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
    List<Timestamp> timestamp;
    @NotEmpty(message = NecessaryField)
    String userEmail;
    @NotEmpty(message = NecessaryField)
    Youtube youtube;

    public Curation toEntity() {
        return Curation.builder().youtube(youtube).title(this.title).timestamp(this.timestamp).build();
    }

}
