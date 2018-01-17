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
    List<TravelNote> getAllTravelNote();
    List<TravelNote> getTravelNoteByTitle(String title);
    List<TravelNote> getTravelNoteByTime(Date time1, Date time2);
    List<TravelNote> getTravelNoteByAuthorName(String author);

    boolean addTravelNote(TravelNote travelNote);
    boolean deleteTravelNote(long noteId);   //1.删除游记  2.若用户点赞、收藏表里有该游记，一并删除  (未做删除图片)

    boolean updateTravelNote(TravelNote travelNote);    //只有草稿箱内游记才可编辑，即status = 1
    boolean updateBrowserCount(long noteId);      //1.更新浏览量
    boolean updateCollectCount(long noteId, int operate, int value, long userId);      //1.更新收藏量  2.增加用户收藏记录 3.为游记作者增加积分
    boolean updateLikeCount(long noteId, int operate, int value, long userId);    //1.更新喜爱量  2.增加用户喜爱记录 3.为游记作者增加积分

    String uploadImg(MultipartFile file);   //上传图片，返回图片key
    boolean deleteImg(String key);
}
