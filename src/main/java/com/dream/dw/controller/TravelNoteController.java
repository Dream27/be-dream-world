package com.dream.dw.controller;

import com.dream.dw.exception.ErrorCode;
import com.dream.dw.model.TravelNote;
import com.dream.dw.response.Responses;
import com.dream.dw.service.TravelNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Dream on 2018/1/15.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/note")
public class TravelNoteController {

    @Autowired
    private TravelNoteService travelNoteService;

    @GetMapping(value = "getNoteByUserId")
    public ResponseEntity getNotesByUserId(@RequestParam Long userId) {
        TravelNote travelNote = travelNoteService.getTravelNoteByUserId(userId);
        if (travelNote != null) {
            return Responses.ok(travelNote);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0100);
        }
    }

    /**
     * Notes: travelnote 'status' field: if travelnote is draft, status is equal to 1, else travelnote is published, status is equal to 0.
     */
    @RequestMapping(value = "addNote")
    public ResponseEntity addNote(@RequestBody TravelNote travelNote) {
        //travelNote.setTime(new Date());
        boolean result = travelNoteService.addTravelNote(travelNote);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0200);
        }
    }

    @GetMapping(value = "deleteNote")
    //@CacheEvict(value = "note", key="'NOTE_' + #noteId")
    public ResponseEntity deleteNote(@RequestParam Long noteId) {
        boolean result = travelNoteService.deleteTravelNote(noteId);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0300);
        }
    }

    /**
     * Notes: travelnote 'status' field: after update, if travelnote is still draft, status is equal to 1, else travelnote is published, status is equal to 0.
     */
    @RequestMapping(value = "updateNote")
    //@CacheEvict(value = "note", key="'NOTE_' + #travelNote.noteId")
    public ResponseEntity updateNote(@RequestBody TravelNote travelNote) {
        boolean result = travelNoteService.updateTravelNote(travelNote);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0400);
        }
    }

    @GetMapping(value = "increaseBrowserCount")
    //@CacheEvict(value = "note", key="'NOTE_' + #noteId")
    public ResponseEntity updateBrowserCount(@RequestParam Long noteId) {
        boolean result = travelNoteService.updateBrowserCount(noteId);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0500);
        }
    }

    @GetMapping(value = "increaseCollectCount")
    //@CacheEvict(value = "note", key="'NOTE_' + #noteId")
    public ResponseEntity updateCollectCount(@RequestParam Long noteId, @RequestParam int operate, @RequestParam Long userId) {
        boolean result = travelNoteService.updateCollectCount(noteId, operate, 1,userId);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0600);
        }
    }

    @GetMapping(value = "increaseLikeCount")
    //@CacheEvict(value = "note", key="'NOTE_' + #noteId")
    public ResponseEntity updateLikeCount(@RequestParam Long noteId, @RequestParam int operate, @RequestParam Long userId) {
        boolean result = travelNoteService.updateLikeCount(noteId, operate, 1,userId);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0700);
        }
    }

    @RequestMapping(value = "uploadImg")
    public ResponseEntity uploadImg(@RequestBody MultipartFile file) {
        String result = travelNoteService.uploadImg(file);
        if(result != null || !result.equals("")) {
            String fileUrl = "http://192.168.192.129/" + result;
            return Responses.ok(fileUrl);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0800);
        }
    }

    @GetMapping(value = "deleteImg")
    public ResponseEntity deleteImg(@RequestParam String key) {
        boolean result = travelNoteService.deleteImg(key);
        if(result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0900);
        }
    }

}
