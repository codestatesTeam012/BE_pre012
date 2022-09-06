package com.codestates.pre012.reply.mapper;


import com.codestates.pre012.member.dto.MemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.reply.entity.Reply;
import com.codestates.pre012.reply.dto.ReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReplyMapper {

    Reply ReplyPostDtoToReply(ReplyDto.Post replyDto);

    Reply ReplyPatchDtoToReply(ReplyDto.Patch replyDto);

    default ReplyDto.Response ReplyToReplyDtoResponse(Reply reply) {
        ReplyDto.Response replyDto = ReplyDto.Response
                .builder()
                .username(reply.getMember().getUsername())
                .content(reply.getContent())
                .replyId(reply.getReplyId())
                .build();

        return replyDto;
    }

}
