package com.codestates.pre012.controller;

import com.codestates.pre012.posts.controller.PostsController;
import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.posts.service.PostsService;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostsControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired Gson gson;
    @MockBean PostsService postsService;
    @MockBean PostsMapper mapper;


    @Test
    void createPostsTest() throws Exception{
        // given
        PostsDto.Post post = new PostsDto.Post("title1","contents");
        String content = gson.toJson(post);

        PostsDto.Response response = new PostsDto.Response(1L,"title1", "contents");

        given(mapper.postsPostDtoToPosts(Mockito.any(PostsDto.Post.class))).willReturn(new Posts());
        given(postsService.savedPosts(Mockito.any(Posts.class))).willReturn(new Posts());
        given(mapper.postsToPostsDtoResponse(Mockito.any(Posts.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/posts/board")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postsId").value(1L))
                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
                .andExpect(jsonPath("$.data.content").value(post.getContent()))
                .andDo(document("post-posts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                Arrays.asList(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                        fieldWithPath("data.postsId").type(JsonFieldType.NUMBER).description("포스트번호"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                ));
    }


    @Test
    void patchPostsTest() throws Exception{
        // given
        long postsId = 1L;
        PostsDto.Patch patch = new PostsDto.Patch(1L,"title1", "contents");
        String content = gson.toJson(patch);

        PostsDto.Response response = new PostsDto.Response(1L,"title1", "contents");


        given(mapper.postsPatchDtoToPosts(Mockito.any(PostsDto.Patch.class))).willReturn(new Posts());
        given(postsService.updatePosts(Mockito.any(Posts.class))).willReturn(new Posts());
        given(mapper.postsToPostsDtoResponse(Mockito.any(Posts.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/v1/posts/patch")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postsId").value(1L))
                .andExpect(jsonPath("$.data.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.data.content").value(patch.getContent()))
                .andDo(document("patch-posts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("postsId").type(JsonFieldType.NUMBER).description("포스트 식별자 ID"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                Arrays.asList(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                        fieldWithPath("data.postsId").type(JsonFieldType.NUMBER).description("포스트 식별자 ID"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                ));
    }

    @Test
    void viewPostsTest() throws Exception {
        // given
        long postsId = 1L;
        PostsDto.Response response = new PostsDto.Response(1L,"title1", "contents");

        given(postsService.lookPost(Mockito.anyLong())).willReturn(new Posts());
        given(mapper.postsToPostsDtoResponse(Mockito.any(Posts.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/posts/{posts-id}",postsId)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postsId").value(1L))
                .andExpect(jsonPath("$.data.title").value(response.getTitle()))
                .andExpect(jsonPath("$.data.content").value(response.getContent()))
                .andDo(
                        document("get-posts",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        Arrays.asList(parameterWithName("posts-id").description("포스트 식별자 ID"))
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                                fieldWithPath("data.postsId").type(JsonFieldType.NUMBER).description("포스트 식별자 ID"),
                                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용")
                                        )
                                )
                        ));
    }

    @Test
    void findPostsTest() throws Exception {
        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Posts member1 = new Posts(1L,"title1", "contents1");
        Posts member2 = new Posts(2L,"title2", "contents2");


        Page<Posts> postses = new PageImpl<>(List.of(member1, member2),
                PageRequest.of(0, 10, Sort.by("memberId").descending()), 2);

        List<PostsDto.Response> responses = getMultiResponseBody();


        given(postsService.findAllPosts(Mockito.anyInt(), Mockito.anyInt())).willReturn(postses);
        given(mapper.postsToPostsDtoResponses(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/posts")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andDo(
                                document(
                                        "gets-posts",
                                        preprocessRequest(prettyPrint()),
                                        preprocessResponse(prettyPrint()),
                                        requestParameters(
                                                getDefaultRequestParameterDescriptors()
                                        ),
                                        responseFields(
                                                Arrays.asList(
                                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터").optional(),
                                                        fieldWithPath("data[].postsId").type(JsonFieldType.NUMBER).description("포스트 식별자 ID"),
                                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
                                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("내용"),
                                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보").optional(),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈").optional(),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수").optional(),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수").optional()
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        assertThat(list.size(), is(2));


    }



    @Test
    void deletePostTest() throws Exception {
        // given
        long postsId = 1L;

        doNothing().when(postsService).deletePosts(Mockito.anyLong());

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/v1/posts/{posts-id}", postsId));
        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-Posts",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        Arrays.asList(parameterWithName("posts-id").description("포스트 식별자 ID"))
                                )
                        )
                );
    }

    public static List<PostsDto.Response> getMultiResponseBody() {
        return List.of(
                new PostsDto.Response(1L,"title1", "contents1"),
                new PostsDto.Response(2L,"title2", "contents2")
        );
    }

    public List<ParameterDescriptor> getDefaultRequestParameterDescriptors() {
        return List.of(
                parameterWithName("page").description("Page 번호"),
                parameterWithName("size").description("Page Size")
        );
    }
}