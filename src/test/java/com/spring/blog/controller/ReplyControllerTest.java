package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MVC테스트는 원래 브라우저를 켜야 테스트가 가능하므로 브라우저를 대체할 객체를 만들어 수정 // mockMvc를 컨테이너에 적재해주는 어노테이션
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired // 데이터 직렬화에 사용하는 객체
    private ObjectMapper objectMapper;

    // 일시적으로 ReplyRepository를 생성
    // 레포지토리 레이어의 메서드는 쿼리문을 하나만 호출하는것이 보장되지만
    // 서비스 레이어의 메서드는 추후에 쿼리문을 두 개 이상 호출할수도 있고, 그런 변경이 생겼을때 테스트코드도 같이 수정할 가능성이 생김
    @Autowired
    private ReplyRepository replyRepository;


    // 컨트롤러를 테스트 해야하는데 컨트롤러는 서버의 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지는 않는다.
    // 각 테스트전에 설정하기
    @BeforeEach
    public void setMockMvc(){ // 주입된 객체 내부에 설정을 한번 더 해줌
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("2번 글에대한 전체 댓글 조회시 0번째 요소의 replyWriter는 '댓글쓴이',replyId는 1")
    void findAllRepliesTest() throws Exception { // mockMVC의 예외를 던져줄 Exception
        // given : fixture 저장
        String replyWriter = "댓글쓴이";
        long replyId = 1;
        String url = "/reply/2/all"; //"http://localhost:8080/reply/2/all";

        // when : 위 설정한 주소(url)로 접속 후 json 데이터 리턴받아 저장하기. ResultActions자료형으로 json 저장하기
        // get() 메서드의 경우 작성 후 alt + enter 눌러 mockMVC 관련 요소로 import
        // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

                                    // fetch(url, {method : 'get'}).then(res => res.json()); 에 대응하는 코드. result변수에 저장
        final ResultActions result = mockMvc.perform(get(url) // url 주소로 get요청 넣기
                                            .accept(MediaType.APPLICATION_JSON));// 리턴자료가 JSON임을 명시

        // then : 리턴받은 json 목록의 0번째 요소의 replyWriter, replyId가 예상과 일치하는지 확인
        //import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
        //import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
        result.andExpect(status().isOk()) // 200코드가 출력되었는지 확인
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter)) // 첫 json의 replyWriter검사
                .andExpect(jsonPath("$[0].replyId").value(replyId));// 첫 json의 replyId검사
    }

    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 얻어온 json 객체의 replyWriter는 미미, replyId는 2번")
    public void findByReplyIdTest() throws Exception {
        // given
        String replyWriter = "미미";
        long replyId = 2;

        String url = "/reply/2";

        // when : 위에 설정한 url로 접속 후 json 데이터를 리턴받아 저장하기. ResultActions 자료형으로 json 저장하기
        final ResultActions result = mockMvc.perform(get(url)
                                            .accept(MediaType.APPLICATION_JSON));

        // then : $로만 끝나는 이유는 리턴받은 자료가 리스트가 아니기 때문에, 인덱싱을 할 필요가 없음
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter)) // 단일 json객체는 $생략가능
                .andExpect(jsonPath("$.replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("blogId 1번에 replyWriter 동동이, replyContent 어서오세요. 등록 후 전체 댓글 조회시 픽스쳐 일치")
    public void insertReplyTest() throws Exception{
        // given : 픽스쳐 생성 및 replyInsertDTO 객체 생성 후 픽스쳐 주입
        long blogId = 1;
        String replyWriter = "동동이";
        String replyContent = "어서오세요";
        ReplyCreateRequestDTO replyCreateRequestDTO = new ReplyCreateRequestDTO(blogId, replyWriter, replyContent);
//        ReplyCreateRequestDTO replyInsertDTO = ReplyCreateRequestDTO.builder()
//                .blogId(blogId)
//                .replyWriter(replyWriter)
//                .replyContent(replyContent)
//                .build();
        String url = "/reply";
        String url2 = "/reply/1/all";

        // +json으로 데이터 직렬화 : java객체 -> json
        final String requestBody = objectMapper.writeValueAsString(replyCreateRequestDTO);

        // when : 직렬화된 데이터를 이용해 post방식으로 url에 요청
        // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
        mockMvc.perform(post(url) // /reply 주소에 post방식으로 요청을 넣고
                .contentType(MediaType.APPLICATION_JSON) // 전달 자료는 json이며
                .content(requestBody)); // 위에서 직렬화한 requestBody 변수를 전달할 것이다.

        // then : 위에서 blogId로 지정한 1번글의 전체 데이터를 가져와, 픽스쳐와 replyWriter, replyContent가 일치하는지 확인
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));

    }

    @Test
    @Transactional
    @DisplayName("replyId 3번 삭제시, 글번호 2번의 댓글 수 3개, 그리고 단일댓글 조회시 null")
    public void deleteReplyTest() throws Exception{
        // given
        long blogId = 2;
        long replyId = 3;
        String url = "http://localhost:8080/reply/3";

        // when
        //.accept(MediaType.TEXT_PLAIN));는 리턴 데이터가 있는 경우에 해당 데이터를 어떤 형식으로 받아올지 기술
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));

        // then : repository를 이용해 전체 데이터를 가져온 후, 개수 비교 및 삭제한 3번댓글은 null이 리턴되는지 확인
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        assertEquals(3, resultList.size());
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        assertNull(result);
    }

    @Test
    @Transactional
    @DisplayName("replyId 4번의 replyWriter를 냥냥이, replyContent를 냐옹 으로 바꾼뒤 조회시 픽스쳐 일치")
    public void updateReplyTest() throws Exception{
        // given : 픽스처 생성 및 ReplyUpdateRequestDTO 객체 생성 후 픽스처 주입 + json으로 데이터 직렬화
        String replyWriter = "냥냥이";
        String replyContent = "냐옹";
        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                                                                            .replyWriter(replyWriter)
                                                                            .replyContent(replyContent)
                                                                            .build();
        String url = "/reply/4";// + replyId; 4번 댓글에 대한 수정 요청 넣기

        // 데이터 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyUpdateRequestDTO);

        // when : 직렬화된 데이터를 이용해 patch방식으로 세팅된 요청 주소에 요청넣기
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터는 json
                .content(requestBody)); // 직렬화된 데이터 전송

        // then : 위에서 수정한 4번 댓글에 대한 정보를 가져와 픽스쳐와 비교
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // 얻어온 4번 코드의 요청 결과는 200이고, replyWriter, replyContent는 픽스처와 일치한다.
        resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                    .andExpect(jsonPath("$.replyContent").value(replyContent));
    }


}