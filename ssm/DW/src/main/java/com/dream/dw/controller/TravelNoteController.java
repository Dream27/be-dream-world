package com.dream.dw.controller;

import com.dream.dw.model.TravelNote;
import com.dream.dw.service.TravelNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dream on 2018/1/15.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/note")
public class TravelNoteController {
//    List<TravelNote> getAllTravelNote();
//    List<TravelNote> getTravelNoteByTitle(String title);
//    List<TravelNote> getTravelNoteByTime(Date time1, Date time2);
//    List<TravelNote> getTravelNoteByAuthorName(String author);
//
//    boolean addTravelNote(TravelNote travelNote);
//    boolean deleteTravelNote(long noteId);   //1.删除游记  2.若用户点赞、收藏表里有该游记，一并删除
//
//    boolean updateTravelNote(TravelNote travelNote);    //只有草稿箱内游记才可编辑，即status = 1
//    boolean updateBrowserCount(long noteId);      //1.更新浏览量
//    boolean updateCollectCount(long noteId, int operate, int value, long userId);      //1.更新收藏量  2.增加用户收藏记录 3.为游记作者增加积分
//    boolean updateLikeCount(long noteId, int operate, int value, long userId);    //1.更新喜爱量  2.增加用户喜爱记录 3.为游记作者增加积分
//
//    long uploadImg(File file);   //上传图片，返回图片key
//    long uploadmusic(File file);   //上传音乐，返回音乐key
    @Autowired
    TravelNoteService travelNoteService;

    @GetMapping(value = "allnote")
    public ResponseEntity getAllNotes() {
        List<TravelNote> travelNotes = travelNoteService.getAllTravelNote();
        return new ResponseEntity(travelNotes, HttpStatus.OK);
    }

    @GetMapping(value = "querynotebytitle")
    public ResponseEntity getNotesByTitle(@RequestParam String title) {
        List<TravelNote> travelNotes = travelNoteService.getTravelNoteByTitle(title);
        return new ResponseEntity(travelNotes, HttpStatus.OK);
    }

    @GetMapping(value = "querynotebytime")
    public ResponseEntity getNotesByTime(@RequestParam String time) {
        Date date2 = new Date();
        Calendar calendar = Calendar.getInstance();
        if(time.equals("一周内")) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        }
        List<TravelNote> travelNotes = travelNoteService.getTravelNoteByTime(calendar.getTime(), date2);
        return new ResponseEntity(travelNotes, HttpStatus.OK);
    }

    @GetMapping(value = "querynotebyauthor")
    public ResponseEntity getNotesByAuthor(@RequestParam String author) {
        List<TravelNote> travelNotes = travelNoteService.getTravelNoteByAuthorName(author);
        return new ResponseEntity(travelNotes, HttpStatus.OK);
    }

    @RequestMapping(value = "addnote")  //注意需要travelnote状态字段，若存为草稿箱状态为1，若直接发布状态为0；
    public ResponseEntity addNote(@RequestBody TravelNote travelNote) {
        //travelNote.setTime(new Date());
        boolean result = travelNoteService.addTravelNote(travelNote);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "deletenote")
    public ResponseEntity deleteNote(@RequestParam Long noteId) {
        boolean result = travelNoteService.deleteTravelNote(noteId);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "updatenote") //注意需要travelnote状态字段，若修改后依然存在草稿箱状态为1，若点击发布状态为0；
    public ResponseEntity updateNote(@RequestBody TravelNote travelNote) {
        boolean result = travelNoteService.updateTravelNote(travelNote);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }
 //   updateBrowserCount(long noteId);      //1.更新浏览量
//    boolean updateCollectCount(long noteId, int operate, int value, long userId);      //1.更新收藏量  2.增加用户收藏记录 3.为游记作者增加积分
//    boolean updateLikeCount
    @GetMapping(value = "browser")
    public ResponseEntity updateBrowserCount(@RequestParam Long noteId) {
        boolean result = travelNoteService.updateBrowserCount(noteId);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "collect")
    public ResponseEntity updateCollectCount(@RequestParam Long noteId, @RequestParam int operate, @RequestParam Long userId) {
        boolean result = travelNoteService.updateCollectCount(noteId, operate, 1,userId);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "like")
    public ResponseEntity updateLikeCount(@RequestParam Long noteId, @RequestParam int operate, @RequestParam Long userId) {
        boolean result = travelNoteService.updateLikeCount(noteId, operate, 1,userId);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "uploadimg")
    public ResponseEntity uploadImg(@RequestBody MultipartFile file) {
        String result = travelNoteService.uploadImg(file);
        if(result != null || !result.equals("")) {
            String fileUrl = "http://192.168.192.129/" + result;
            return new ResponseEntity(fileUrl, HttpStatus.OK);
        } else {
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "deleteimg")
    public ResponseEntity deleteImg(@RequestParam String key) {
        boolean result = travelNoteService.deleteImg(key);
        if(result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

}
