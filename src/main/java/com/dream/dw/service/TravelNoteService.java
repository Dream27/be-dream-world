package com.dream.dw.service;

import com.dream.dw.model.TravelNote;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Dream on 2018/1/14.
 */
public interface TravelNoteService {
    List<TravelNote> getTravelNoteByUserId(Long userId);

    boolean addTravelNote(TravelNote travelNote);
    boolean deleteTravelNote(long noteId);   //1.delete note (delete picture about the note in the future) 2.if t_dw_like_record,t_dw_collect_record table has record about, delete the record

    boolean updateTravelNote(TravelNote travelNote);    //only draft(status = 1) can be edit,
    boolean updateBrowserCount(long noteId);      //1.increase browser count
    boolean updateCollectCount(long noteId, int operate, int value, long userId);      //1.increase collect count  2.add collect record 3.add travelnote' author credit
    boolean updateLikeCount(long noteId, int operate, int value, long userId);    //1.increase like count  2.add like record 3.add travelnote' author credit

    String uploadImg(MultipartFile file);   //upload picture, return picture key
    boolean deleteImg(String key);
}
