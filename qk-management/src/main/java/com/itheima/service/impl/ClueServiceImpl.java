package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.dto.ClueTrackDto;
import com.itheima.dto.MarkFalseClueDto;
import com.itheima.dto.PageClueDto;
import com.itheima.entity.Business;
import com.itheima.entity.Clue;
import com.itheima.entity.ClueTrackRecord;
import com.itheima.exception.QkException;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.ClueMapper;
import com.itheima.mapper.ClueTrackRecordMapper;
import com.itheima.service.ClueService;
import com.itheima.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService {
    private final ClueMapper clueMapper;
    private final ClueTrackRecordMapper clueTrackRecordMapper;
    private final BusinessMapper businessMapper;

    @Override
    public PageResult<Clue> pageClue(PageClueDto clueDto) {
        //1.调用持久层
        Page<Clue> page = this.clueMapper.selectByCondition(new Page<>(clueDto.getPage(),clueDto.getPageSize()),clueDto);

        //2.创建一个PageResult
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    @Override
    public boolean assignClue(Integer clueId, Integer userId) {
        //1.验证clueId->略
        //2.使用这个clueId查询这条线索
        Clue clue = this.clueMapper.selectById(clueId);
        if (clue == null) {
            throw new QkException("【错误】未查到此条线索！");
        }
        //3.验证userId是否能查到->略
        //4.修改状态(status)和归属人(user_id)
        clue.setStatus(2);
        clue.setUserId(userId);

        //5.执行修改
        int row = this.clueMapper.updateById(clue);

        return row == 1;
    }

    @Override
    public Clue findById(Integer id) {
        return clueMapper.findDetailById(id);
    }
//    @Transactional注解默认捕获RuntimeException类型的异常进行事务回滚 ,
//    如果抛出的是非运行时异常 , 是不会进行回滚的 ,
//    可以设置rollbackFor = Exception.class属性设置捕获所有异常回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean trackClue(ClueTrackDto trackDto) {

        // 1. 修改线索表的线索基本信息
        Clue clue = clueMapper.selectById(trackDto.getId());
        if (clue == null) {
            throw new QkException("线索不存在,不能跟进");
        }
        // 将前端发送到后端的最新数据设置到实体类对象中更新到数据库
        // clue.setActivityId(trackDto.getActivityId());
        // clue.setName(trackDto.getName());
        // 源对象的属性名称和类型 与 目标对象的属性名称和类型一致即可完成属性拷贝
        BeanUtil.copyProperties(trackDto,clue,"id","phone","channel");
        // 修改线索跟进状态为跟进中
        clue.setStatus(3);
        // 更新数据到数据库
        int row1 = clueMapper.updateById(clue);

//        int a = 10 / 0;
        // 2. 新增一条线索跟进记录
        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(clue.getId());
        trackRecord.setUserId(UserHolder.getUserId()); //TODO 暂时跟进用户固定为1
        trackRecord.setSubject(clue.getSubject());
        trackRecord.setLevel(clue.getLevel());
        trackRecord.setRecord(trackDto.getRecord());
        trackRecord.setNextTime(clue.getNextTime());
        trackRecord.setType(1);

        int row2 = clueTrackRecordMapper.insert(trackRecord);
        return row1 == 1 && row2 == 1;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean toBusiness(Integer clueId) {
        // 1. 修改线索的状态为转商机
        Clue clue = clueMapper.selectById(clueId);
        if (Objects.isNull(clue)) {
            throw new QkException("线索不存在,不能转化为商机");
        }
        // 设置商机状态为转商机
        clue.setStatus(5);
        // 更新线索状态
        int row1 = clueMapper.updateById(clue);

        // 2. 向商机表中新增一条商机数据
        // Business business = new Business();
        // business.setName(clue.getName());
        // business.setPhone(clue.getPhone());
        Business business = BeanUtil.copyProperties(clue, Business.class, "id", "status", "userId", "nextTime");
        business.setStatus(1); // 设置商机为待分配状态
        business.setClueId(clue.getId()); // 设置商机归属的线索id
        // 保存商机数据
        int row2 = businessMapper.insert(business);
        return row1 == 1 && row2 == 1;
    }

    @Override
    public boolean markClueFalse(Integer id, MarkFalseClueDto dto) {
        // 1. 修改线索的状态为伪线索
        Clue clue = clueMapper.selectById(id);
        if (Objects.isNull(clue)) {
            throw new QkException("线索不存在,不能标记为伪线索");
        }
        // 设置线索状态为伪线索
        clue.setStatus(4);
        // 更新线索数据
        int row1 = clueMapper.updateById(clue);

        // 2. 新增一条跟进记录
        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(clue.getId());
        trackRecord.setUserId(UserHolder.getUserId());
        trackRecord.setType(0);
        trackRecord.setFalseReason(dto.getReason());
        trackRecord.setRecord(dto.getRemark());
        // 新增跟进记录数据
        int row2 = clueTrackRecordMapper.insert(trackRecord);

        return row1 == 1 && row2 == 1;
    }

    @Override
    public PageResult<Clue> pageCluePool(PageClueDto poolDto) {
        // 调用业务层分页查询线索池列表
        Page<Clue> pageInfo = clueMapper.pageCluePool(
                                                new Page<>(poolDto.getPage(), poolDto.getPageSize()),
                                                poolDto);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }
}
