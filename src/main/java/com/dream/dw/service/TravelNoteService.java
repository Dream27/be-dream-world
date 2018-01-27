package com.dream.dw.service;

import com.dream.dw.model.TravelNote;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Dream on 2018/1/14.
 */
public interface TravelNoteService {

    List<TravelNote> getTravelNoteByUserId(Long userId);

    List<TravelNote> getCollectTravelNoteByUserId(Long userId);

    List<TravelNote> getLikeTravelNoteByUserId(Long userId);

    boolean addTravelNote(TravelNote travelNote);

    /*
    * 1.delete note (delete picture about the note in the future) 2.if t_dw_like_record,t_dw_collect_record table has record about, delete the record
    * */
    boolean deleteTravelNote(long noteId);

    /*
    * only draft(status = 1) can be edit
    * */
    boolean updateTravelNote(TravelNote travelNote);

    /*
    * increase browser count
    * */
    boolean updateBrowserCount(long noteId);

    /*
    * 1.increase collect count  2.add collect record 3.add travelnote' author credit
    * */
    boolean updateCollectCount(long noteId, int operate, int value, long userId);

    /*
    * 1.increase like count  2.add like record 3.add travelnote' author credit
    * */
    boolean updateLikeCount(long noteId, int operate, int value, long userId);

    /*
    * upload picture, return picture key
    * */
    String uploadImg(MultipartFile file);

    boolean deleteImg(String key);
}
