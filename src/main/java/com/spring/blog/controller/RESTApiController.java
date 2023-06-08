package com.spring.blog.controller;

import com.spring.blog.dto.BmiDTO;
import com.spring.blog.dto.PersonDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller // 컨트롤러로 지정(주소를 식별할 수 있는 기능)
//@ResponseBody // 메서드 위에 붙으면 해당 메서드를 REST형식으로 전환
@RestController //@Controller 와 @ResponseBody 어노테이션을 한 번에 지정해 주는 어노테이션
@RequestMapping("/resttest")
@CrossOrigin(origins = "http://127.0.0.1:5500") // 해당 출처의 비동기 요청 허용
public class RESTApiController {

    // REST 컨트롤러는 크게 json을 리턴하거나, String을 리턴하게 만들 수 있다.
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "안녕하세요";
    }

    // 문자 배열도 리턴이 가능하다
    @RequestMapping(value = "/foods", method =RequestMethod.GET)
    public List<String> foods(){
        List<String> foodList = List.of("탕수육","똠얌꿍","돈카츠");
        return foodList;
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public PersonDTO person(){
        PersonDTO p = PersonDTO.builder().id(1L).name("김자바").age(20).build();
        return p;
    }

    // 상태코드까지 함께 리턴할 수 있는 ResponseEntity<>를 리턴자료형으로 지정
    @GetMapping("/person-list")
    public ResponseEntity<?> personList(){
        PersonDTO p = PersonDTO.builder().id(10L).name("김미미").age(10).build();
        PersonDTO p2 = PersonDTO.builder().id(11L).name("김비비").age(20).build();
        PersonDTO p3 = PersonDTO.builder().id(12L).name("김시시").age(30).build();
        List<PersonDTO> personList = List.of(p, p2, p3);

        // .ok()는 200코드를 반환, 뒤에 연달아 붙은 body()에 실제 리턴자료를 입력한다
        return ResponseEntity.ok().body(personList);
    }

    // 200이 아닌 예외코드를 리턴
    @RequestMapping(value = "/bmi", method = RequestMethod.GET)
    public ResponseEntity<?> bmi(BmiDTO bmi){// 커맨드 객체 형식(주소창에 직접입력하는방식)으로 사용됨

        // 예외처리 들어갈 자리
        if(bmi.getHeight() == 0){
            return ResponseEntity
                    .badRequest() // 400
                    .body("0은 입력불가");
        }

        double result = bmi.getWeight() / ((bmi.getHeight()/100)*(bmi.getHeight()/100));

        // 헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch","pizza");

        return ResponseEntity
                .ok() // 200
                .headers(headers) // 헤더추가
                .body(result); // 사용자에게 보여질 데이터

    }

    // Postman을 활용한 json 데이터 파라미터로 전송해 요청넣기
    @RequestMapping(value = "/bmi2", method = RequestMethod.GET)
    public ResponseEntity<?> bmi2(@RequestBody BmiDTO bmi){// 파라미터를 json으로만 받겠다

        // 예외처리 들어갈 자리
        if(bmi.getHeight() == 0){
            return ResponseEntity
                    .badRequest() // 400
                    .body("0은 입력불가");
        }

        double result = bmi.getWeight() / ((bmi.getHeight()/100)*(bmi.getHeight()/100));

        // 헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch","pizza");

        return ResponseEntity
                .ok() // 200
                .headers(headers) // 헤더추가
                .body(result); // 사용자에게 보여질 데이터
    }






}
