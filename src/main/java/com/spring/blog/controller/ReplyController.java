package com.spring.blog.controller;

import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.entity.Reply;
import com.spring.blog.exception.NotFoundReplyByReplyIdException;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
//@Log4j2
public class ReplyController {

    // 컨트롤러는 서비스를 호출한다.
    ReplyService replyService;

    ReplyJPARepository replyJPARepository;

    @Autowired
    public ReplyController(ReplyService replyService, ReplyJPARepository replyJPARepository){
        this.replyService = replyService;
        this.replyJPARepository = replyJPARepository;
    }

    // 글 번호에 맞는 전체 댓글을 가져오는 메서드
    // 어떤 자원에 접근할 것인지만 url에 명시(메서드가 행동을 결정함)
    // http://localhost:8080/reply/{번호}/all => blogId 파라미터에 {번호}가 전달된것으로 간주한다.
    @RequestMapping(value = "/{blogId}/all", method = RequestMethod.GET)
    // rest서버에서는 응답시 응답코드와 응답객체를 넘기기 때문에 ResponseEntity<자료형>을 리턴한다.
    public ResponseEntity<List<Reply>> findAllReplies(@PathVariable long blogId){
        // 서비스에서 리플 목록을 들고온다.
        List<Reply> replies = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok()//replies);//http 상태코드 : 200, 상태 코드와 body에 전송할 데이터를 같이 작성할수도 있음.
                .body(replies); // 리플목록
    }

    // replyId를 주소에 포함시켜서 요청하면 해당 번호 댓글 정보를 json으로 리턴하는 메서드
    // ex) /reply/5 -> replyId 변수에 5가 대입되도록 주소 설정 및 메서드 선언
    @RequestMapping(value = "/{replyId}", method = RequestMethod.GET)
    public ResponseEntity<?> findByReplyId(@PathVariable long replyId){

        // 서비스에서 특정 번호 리플을 가져온다.
        Reply reply = replyService.findByReplyId(replyId);
        if(reply == null) {
            try {
                throw new NotFoundReplyByReplyIdException("없는 댓글 번호가 조회되었습니다.");
            } catch (NotFoundReplyByReplyIdException e) {
                e.printStackTrace();
                return new ResponseEntity<>("찾는 댓글 번호가 없습니다.", HttpStatus.NOT_FOUND);
            }
        }
        //return new ResponseEntity<ReplyResponseDTO>(replyResponseDTO, HttpStatus.OK);
        return ResponseEntity
                .ok(reply);
    }

    // insert는 post방식으로 /reply 주소로 요청이 들어왔을 때 실행되는 메서드 insertReply()를 작성해보기
    @RequestMapping(value = "", method = RequestMethod.POST)
                                            // Rest컨트롤러는 데이터를 json으로 주고받음.
                                            // 따라서 @RequestBody를 이용해 json으로 들어온 데이터를 역직렬화 하도록 설정
    public ResponseEntity<String> insertReply(@RequestBody Reply reply){
        //System.out.println("데이터 들어오는지 확인: "+replyInsertDTO);
//        if(replyInsertDTO.getReplyWriter() == null){
//            throw new NotFoundReplyWriterException("댓글을 입력하시오.");
//        }
        replyService.save(reply);
        return ResponseEntity
                .ok("댓글이 등록되었습니다.");
    }

    // delete 방식으로 /reply/{댓글번호} 주소로 요청이 들어왔을때 실행되는 메서드 deleteReply()를 작성
    @RequestMapping(value = {"/{replyId}", "/{replyId}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReply(@PathVariable long replyId){
        replyService.deleteByReplyId(replyId);

        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    // 수정로직은 put(전체수정 또는 생성), patch(일부수정) 메서드로 /reply/{댓글번호} 주소로
    // ReplyUpdateRequestDTO를 requestBody로 받아 요청처리를 하는 구문 작성
    @RequestMapping(value = "/{replyId}", method = {RequestMethod.PUT , RequestMethod.PATCH})
    public ResponseEntity<String> updateReply(@PathVariable long replyId,
                                              @RequestBody Reply reply){
        // json 데이터에 replyId를 포함하는 대신 url에 포함시켰으므로 requestBody에 추가해줘야함.
        System.out.println("replyId 주입전: "+reply);
        reply.setReplyId(replyId);
        System.out.println("replyId setter 주입 후: "+reply);
        replyService.update(reply);

        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }


}
