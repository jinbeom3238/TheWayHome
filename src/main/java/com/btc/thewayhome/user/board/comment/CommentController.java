package com.btc.thewayhome.user.board.comment;

import com.btc.thewayhome.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/user/comment")
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    // 댓글 작성 Form
    @PostMapping ("/write_review_comment_confirm")
    @ResponseBody
    public Object writeReviewCommentConfirm(@RequestBody Map<String, Object> msgMap, CommentDto commentDto) {
        log.info("writeReviewCommentConfirm()");

        int result = commentService.writeReviewCommentConfirm(msgMap, commentDto);
        Map<String, Object> map = new HashMap<>();

        map.put("result", result);

        return map;

    }
    @GetMapping("/review_detail_json")
    @ResponseBody
    public List<CommentDto> reviewDetailPageJson(@RequestParam("r_b_no") int r_b_no , Model model) {
        log.info("reviewDetailPageJson()");

        List<CommentDto> commentDtos =  commentService.getCommentAllForReview(r_b_no);
        model.addAttribute("commentDtos", commentDtos);

        return commentDtos;
    }


    @PostMapping("/review_comment_delete")
    @ResponseBody
    public Object reviewCommentDelete(@RequestBody Map<String, String> msgMap) {
        log.info("reviewCommentDelete()");

        Map<String, Object> map = new HashMap<>();

        int result = -1;
        int b_c_no = Integer.parseInt(msgMap.get("b_c_no").toString());
        result = commentService.reviewCommentDelete(b_c_no);
        if(result > 0){
            log.info("COMMENT DELETE SUCCESS");

        } else {
            log.info("COMMENT DELETE FAIL");

        }

        map.put("result", result);
        return map;
    }


    // 댓글 작성 Form
    @PostMapping ("/write_free_board_comment_confirm")
    @ResponseBody
    public Object writeFreeBoardCommentConfirm(@RequestBody Map<String, Object> msgMap, CommentDto commentDto) {
        log.info("writeFreeBoardCommentConfirm()");

        int result = commentService.writeFreeBoardCommentConfirm(msgMap, commentDto);
        Map<String, Object> map = new HashMap<>();

        map.put("result", result);

        return map;

    }

    @PostMapping("/free_board_comment_delete")
    @ResponseBody
    public Object freeBoardCommentDelete(@RequestBody Map<String, String> msgMap) {
        log.info("freeBoardCommentDelete()");

        Map<String, Object> map = new HashMap<>();

        int result = -1;
        int b_c_no = Integer.parseInt(msgMap.get("b_c_no").toString());
        result = commentService.freeBoardCommentDelete(b_c_no);
        if(result > 0){
            log.info("COMMENT DELETE SUCCESS");

        } else {
            log.info("COMMENT DELETE FAIL");

        }

        map.put("result", result);
    return map;
    }

    @GetMapping("/free_board_detail_json")
    @ResponseBody
    public List<CommentDto> freeBoardDetailPageJson(@RequestParam("fb_no") int r_b_no , Model model) {
        log.info("freeBoardDetailPageJson()");

        log.info(">>>>"+ r_b_no);

        List<CommentDto> commentDtos =  commentService.getCommentAllForFreeBoard(r_b_no);
        model.addAttribute("commentDtos", commentDtos);

        return commentDtos;
    }



}
