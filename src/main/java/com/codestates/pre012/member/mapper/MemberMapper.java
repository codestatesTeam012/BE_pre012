package com.codestates.pre012.member.mapper;


import com.codestates.pre012.member.dto.MemberDto;
import com.codestates.pre012.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post requestBody);
    MemberDto.Response memberToMemberResponseDto(Member member);

}
