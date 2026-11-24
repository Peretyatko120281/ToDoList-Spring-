package ru.alex.dao;

import org.springframework.stereotype.Repository;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record("Go to the bank", RecordStatus.ACTIVE),
                    new Record("Buy the car",RecordStatus.ACTIVE),
                    new Record("Read the book",RecordStatus.DONE)
            )
    );

    public List<Record> findAllRecords(){
        return new ArrayList<>(records);
    }
    public void saveRecord(Record record){
        records.add(record);
    }
    public void updateRecordStatus(int id,RecordStatus newstatus){
        for (Record item:records){
            if(item.getId()==id){
                item.setStatus(newstatus);
                break;
            }
        }

    }
    public void deleteRecord(int id){
        records.removeIf(item-> item.getId()==id);
    }
}
