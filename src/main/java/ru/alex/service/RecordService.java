package ru.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.dao.RecordDao;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;
import ru.alex.entity.dto.RecordsContainerDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDTO findAllRecords(String filterMode){
        List<Record> records = recordDao.findAllRecords();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();

        if(filterMode==null || filterMode.isBlank()){
                return new RecordsContainerDTO(records,numberOfDoneRecords,numberOfActiveRecords);
            }

            String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());

            if(allowedFilterModes.contains(filterModeInUpperCase)){
                List<Record> filteredRecords = records.stream()
                        .filter(record -> record.getStatus()==RecordStatus.valueOf(filterModeInUpperCase))
                        .collect(Collectors.toList());
                return new RecordsContainerDTO(filteredRecords,numberOfDoneRecords,numberOfActiveRecords);

            } else {
                return new RecordsContainerDTO(records,numberOfDoneRecords,numberOfActiveRecords);
            }
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
