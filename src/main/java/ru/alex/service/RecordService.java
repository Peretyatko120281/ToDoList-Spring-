package ru.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.dao.RecordDao;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;

import java.util.List;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public List<Record> findAllRecords(){
        return recordDao.findAllrecords();
    }
    public void saveRecord(String title){
        if(title!= null && !title.isBlank()){
            recordDao.saveRecord(new Record(title));
        }

    }
    public void updateRecordStatus(int id,RecordStatus newstatus){
        recordDao.updateRecordStatus(id,newstatus);
    }

    public void deleteRecord(int id){
        recordDao.deleteRecord(id);
    }
}
