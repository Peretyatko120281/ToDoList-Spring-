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
                    new Record("Go tothe bank", RecordStatus.ACTIVE),
                    new Record("Buy the car",RecordStatus.ACTIVE),
                    new Record("Read the book",RecordStatus.DONE)
            )
    );

    public List<Record> findAllrecords(){
        return new ArrayList<>(records);
    }
}
