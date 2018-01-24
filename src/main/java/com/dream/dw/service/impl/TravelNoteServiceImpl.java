package com.dream.dw.service.impl;

import com.dream.dw.dao.*;
import com.dream.dw.model.CollectRecord;
import com.dream.dw.model.LikeRecord;
import com.dream.dw.model.TravelNote;
import com.dream.dw.model.User;
import com.dream.dw.service.TravelNoteService;
import com.dream.dw.util.IdWorker;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Dream on 2018/1/15.
 */
@Component
public class TravelNoteServiceImpl implements TravelNoteService {

    @Autowired
    TravelNoteMapper travelNoteMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CollectRecordMapper collectRecordMapper;

    @Autowired
    LikeRecordMapper likeRecordMapper;

    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @Override
    public TravelNote getTravelNoteByUserId(Long userId) {
        TravelNoteExample travelNoteExample = new TravelNoteExample();
        travelNoteExample.createCriteria().andUserIdEqualTo(userId);
        List<TravelNote> travelNotes = travelNoteMapper.selectByExample(travelNoteExample);
        if(travelNotes.isEmpty()) {
            return null;
        }
        return travelNotes.get(0);
    }

    @Override
    public boolean addTravelNote(TravelNote travelNote) {
        IdWorker idWorker = new IdWorker();
        travelNote.setNoteId(idWorker.nextId());
        int result = travelNoteMapper.insertSelective(travelNote);
        return result == 0 ? false:true;
    }

    @Override
    @Transactional
    public boolean deleteTravelNote(long noteId) {
        TravelNoteExample travelNoteExample = new TravelNoteExample();
        travelNoteExample.createCriteria().andNoteIdEqualTo(noteId);
        int result = travelNoteMapper.deleteByExample(travelNoteExample);

        CollectRecordExample collectRecordExample = new CollectRecordExample();
        collectRecordExample.createCriteria().andNoteIdEqualTo(noteId);
        collectRecordMapper.deleteByExample(collectRecordExample);

        LikeRecordExample likeRecordExample = new LikeRecordExample();
        likeRecordExample.createCriteria().andNoteIdEqualTo(noteId);
        likeRecordMapper.deleteByExample(likeRecordExample);
        return result == 0 ? false:true;
    }

    @Override
    public boolean updateTravelNote(TravelNote travelNote) {
        TravelNoteExample travelNoteExample = new TravelNoteExample();
        travelNoteExample.createCriteria().andNoteIdEqualTo(travelNote.getNoteId());
        int result = travelNoteMapper.updateByExampleSelective(travelNote, travelNoteExample);
        return result == 0 ? false:true;
    }

    @Override
    public boolean updateBrowserCount(long noteId) {
        TravelNoteExample travelNoteExample = new TravelNoteExample();
        travelNoteExample.createCriteria().andNoteIdEqualTo(noteId);
        List<TravelNote> travelNotes = travelNoteMapper.selectByExample(travelNoteExample);
        if(travelNotes.isEmpty()) {
            return false;
        }
        TravelNote travelNote = new TravelNote();   //有必要重新创建一个对象吗？？？？？？？？？？？？
        travelNote.setBrowserCount(travelNotes.get(0).getBrowserCount() + 1);
        int result = travelNoteMapper.updateByExampleSelective(travelNote, travelNoteExample);
        return result == 0 ? false:true;
    }

    @Override
    @Transactional
    public boolean updateCollectCount(long noteId, int operate, int value, long userId) {
        //1.add/delete user collect record
        int flag;
        int result1 = 1, result2 = 1, result3 = 1;
        CollectRecordExample collectRecordExample = new CollectRecordExample();
        collectRecordExample.createCriteria().andNoteIdEqualTo(noteId).andUserIdEqualTo(userId);
        List<CollectRecord> collectRecords = collectRecordMapper.selectByExample(collectRecordExample);
        if(operate == 1){
            //add
            if(collectRecords.isEmpty()) {
                //have not been collect：add
                CollectRecord collectRecord = new CollectRecord();
                collectRecord.setUserId(userId);
                collectRecord.setNoteId(noteId);
                collectRecord.setStatus(0);
                result1 = collectRecordMapper.insertSelective(collectRecord);

                flag = 0;
            } else {
                if(collectRecords.get(0).getStatus() == 1) {
                    //had been collect：update
                    CollectRecord collectRecord = new CollectRecord();
                    collectRecord.setStatus(0);
                    collectRecord.setCreateTime(new Date());    //时间是有问题的！！！！！！！！！！！！
                    result1 = collectRecordMapper.updateByExampleSelective(collectRecord, collectRecordExample);

                    flag = 1;
                } else {
                    //have been collect: return
                    flag = 2;
                }

            }
        } else {
            //delete(operate == 0)
            if(collectRecords.isEmpty() || collectRecords.get(0).getStatus() == 1) {
                return false;
            } else {
                CollectRecord collectRecord = new CollectRecord();
                collectRecord.setStatus(1);
                result1 = collectRecordMapper.updateByExampleSelective(collectRecord, collectRecordExample);

                flag = 2;
            }
        }

        if((flag != 2 && operate == 1) || (flag == 2 && operate == 0))
        {
            //2.increase collect count
            TravelNoteExample travelNoteExample = new TravelNoteExample();
            travelNoteExample.createCriteria().andNoteIdEqualTo(noteId);
            List<TravelNote> travelNotes = travelNoteMapper.selectByExample(travelNoteExample);
            if(travelNotes.isEmpty()) {
                return false;
            }
            TravelNote travelNote = new TravelNote();   //有必要重新创建一个对象吗？？？？？？？？？？？？
            if(operate == 0)
            {
                travelNote.setCollectCount(travelNotes.get(0).getCollectCount() - value);
            } else {
                travelNote.setCollectCount(travelNotes.get(0).getCollectCount() + value);
            }
            result2 = travelNoteMapper.updateByExampleSelective(travelNote, travelNoteExample);
        }

        if(flag == 0) {
            //3.add travelnote' author credit
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userId);
            List<User> users = userMapper.selectByExample(userExample);
            if(users.isEmpty()) {
                result3 = 0;
            }
            User user = new User();
            user.setCredit(users.get(0).getCredit() + 3);
            result3 = userMapper.updateByExampleSelective(user, userExample);
        }
        return (result1 == 0 || result2 == 0 || result3 == 0) ? false:true;
    }

    @Override
    @Transactional
    public boolean updateLikeCount(long noteId, int operate, int value, long userId) {
        //1.add/delete user like record
        int flag;
        int result1 = 1, result2 = 1, result3 = 1;
        LikeRecordExample likeRecordExample = new LikeRecordExample();
        likeRecordExample.createCriteria().andNoteIdEqualTo(noteId).andUserIdEqualTo(userId);
        List<LikeRecord> likeRecords = likeRecordMapper.selectByExample(likeRecordExample);
        if(operate == 1){
            //add
            if(likeRecords.isEmpty()) {
                //have not been like：add
                LikeRecord likeRecord = new LikeRecord();
                likeRecord.setUserId(userId);
                likeRecord.setNoteId(noteId);
                likeRecord.setStatus(0);
                result2 = likeRecordMapper.insertSelective(likeRecord);

                flag = 0;
            } else {
                if(likeRecords.get(0).getStatus() == 1) {
                    //had been collect：update
                    LikeRecord likeRecord = new LikeRecord();
                    likeRecord.setStatus(0);
                    likeRecord.setCreateTime(new Date());
                    result2 = likeRecordMapper.updateByExampleSelective(likeRecord, likeRecordExample);

                    flag = 1;
                } else {
                    //have been collect：return
                    flag = 2;
                }

            }
        } else {
            //delete(operate == 0)
            if(likeRecords.isEmpty() || likeRecords.get(0).getStatus() == 1) {
                return false;
            } else {
                LikeRecord likeRecord = new LikeRecord();
                likeRecord.setStatus(1);
                result1 = likeRecordMapper.updateByExampleSelective(likeRecord, likeRecordExample);

                flag = 2;
            }
        }

        if((flag != 2 && operate == 1) || (flag == 2 && operate == 0))
        {
            //2.increase like count
            TravelNoteExample travelNoteExample = new TravelNoteExample();
            travelNoteExample.createCriteria().andNoteIdEqualTo(noteId);
            List<TravelNote> travelNotes = travelNoteMapper.selectByExample(travelNoteExample);
            if(travelNotes.isEmpty()) {
                return false;
            }
            TravelNote travelNote = new TravelNote();   //有必要重新创建一个对象吗？？？？？？？？？？？？
            if(operate == 0)
            {
                travelNote.setLikeCount(travelNotes.get(0).getLikeCount() - value);
            } else {
                travelNote.setLikeCount(travelNotes.get(0).getLikeCount() + value);
            }
            result2 = travelNoteMapper.updateByExampleSelective(travelNote, travelNoteExample);
        }

        if(flag == 0) {
            //3.add travelnote' author credit
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userId);
            List<User> users = userMapper.selectByExample(userExample);
            if(users.isEmpty()) {
                result3 = 0;
            }
            User user = new User();
            user.setCredit(users.get(0).getCredit() + 1);
            result3 = userMapper.updateByExampleSelective(user, userExample);
        }

        return (result1 == 0 || result2 == 0 || result3 == 0) ? false:true;
    }

    @Override
    public String uploadImg(MultipartFile file) {
        try{
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String key = storePath.getFullPath();
            return key;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
            //String key = FastDFSClient.uploadFile(file, "kk.jpg");
    }

    @Override
    public boolean deleteImg(String key) {
        try {
            fastFileStorageClient.deleteFile(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
