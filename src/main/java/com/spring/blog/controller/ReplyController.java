package com.spring.blog.controller;

import com.spring.blog.entity.Reply;
import com.spring.blog.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reply")
@Log4j2
public class ReplyController {

    private ReplyService replyService;


}
