package org.springboot.apollo.demo.service.impl;

import org.springboot.apollo.demo.entity.ContrarySqlRecord;
import org.springboot.apollo.demo.mapper.ContrarySqlRecordMapper;
import org.springboot.apollo.demo.service.ContrarySqlRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： WangQian
 * @date： 2020/8/2 上午 11:57
 */
@Service("ContrarySqlRecordService")
public class ContrarySqlRecordServiceImpl implements ContrarySqlRecordService {
    @Autowired
    ContrarySqlRecordMapper contrarySqlRecordMapper;
    @Override
    public ContrarySqlRecord queryById(Integer id) {
        return null;
    }

    @Override
    public List<ContrarySqlRecord> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public List<ContrarySqlRecord> queryAll(ContrarySqlRecord contrarySqlRecord) {
        return null;
    }

    @Override
    public int insert(ContrarySqlRecord contrarySqlRecord) {
        return contrarySqlRecordMapper.insert(contrarySqlRecord);
    }

    @Override
    public int update(ContrarySqlRecord contrarySqlRecord) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public ContrarySqlRecord queryLatestRecord(String applyId) {
        return contrarySqlRecordMapper.queryLatestRecord(applyId);
    }

    @Override
    public int rollBackSql(String contrarySql) {
        return contrarySqlRecordMapper.rollBackSql(contrarySql);
    }
}
