package com.codestates.pre012.tag.mapper;

import com.codestates.pre012.tag.dto.TagDto;
import com.codestates.pre012.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    List<Tag> tagDtoPostToTags(List<TagDto.Post> tags);

    TagDto.Response tagToTagDtoResponse(Tag tag);

    List<TagDto.Response> tagToTagDtoResponses(List<Tag> tag);
}
