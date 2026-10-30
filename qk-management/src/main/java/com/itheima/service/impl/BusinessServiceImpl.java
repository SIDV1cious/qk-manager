package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.dto.BusinessTrackDto;
import com.itheima.dto.PageBusinessDto;
import com.itheima.entity.Business;
import com.itheima.entity.BusinessTrackRecord;
import com.itheima.entity.Customer;
import com.itheima.exception.QkException;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.BusinessTrackRecordMapper;
import com.itheima.mapper.CustomerMapper;
import com.itheima.service.BusinessService;
import com.itheima.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    private final BusinessMapper businessMapper;
    private final BusinessTrackRecordMapper businessTrackRecordMapper;
    private final CustomerMapper customerMapper;

    @Override
    public PageResult<Business> pageBusiness(PageBusinessDto businessDto) {
        //1.调用持久层
        Page<Business> page = businessMapper.selectByCondition(
                new Page<>(businessDto.getPage(), businessDto.getPageSize()), businessDto);

        //2.创建一个PageResult
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    @Override
    public boolean addBusiness(Business business) {
        // 新增商机默认状态为待分配
        if (business.getStatus() == null) {
            business.setStatus(1);
        }
        return businessMapper.insert(business) == 1;
    }

    @Override
    public boolean assignBusiness(Integer businessId, Integer userId) {
        //1.查询商机
        Business business = businessMapper.selectById(businessId);
        if (business == null) {
            throw new QkException("商机不存在，无法分配！");
        }

        //2.修改归属人和商机状态
        business.setUserId(userId);
        business.setStatus(2);

        //3.更新商机
        return businessMapper.updateById(business) == 1;
    }

    @Override
    public boolean backToPool(Integer id) {
        //1.查询商机
        Business business = businessMapper.selectById(id);
        if (business == null) {
            throw new QkException("商机不存在，无法踢回公海！");
        }

        //2.修改商机状态，清空归属人和下次跟进时间
        business.setStatus(4);
        business.setUserId(null);
        business.setNextTime(null);

        //3.更新商机（updateById 会跳过 null 字段，清空归属必须用 update wrapper 显式 set）
        return businessMapper.update(null, Wrappers.<Business>lambdaUpdate()
                .eq(Business::getId, id)
                .set(Business::getStatus, 4)
                .set(Business::getUserId, null)
                .set(Business::getNextTime, null)) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean toCustomer(Integer id) {
        //1.查询商机
        Business business = businessMapper.selectById(id);
        if (business == null) {
            throw new QkException("商机不存在，无法转为客户！");
        }
        if (business.getStatus() != null && business.getStatus() == 5) {
            throw new QkException("该商机已经转为客户！");
        }

        //2.检查是否已经生成客户
        Long count = customerMapper.selectCount(
                Wrappers.lambdaQuery(Customer.class)
                        .eq(Customer::getBusinessId, id)
        );
        if (count != null && count > 0) {
            throw new QkException("该商机已经生成客户！");
        }

        //3.将商机信息复制到客户对象
        Customer customer = BeanUtil.copyProperties(
                business, Customer.class, "id", "status", "userId", "clueId", "nextTime", "assignName", "trackRecords");
        customer.setBusinessId(business.getId());
        customer.setCreateTime(LocalDateTime.now());
        customer.setUpdateTime(LocalDateTime.now());

        //4.新增客户
        int row1 = customerMapper.insert(customer);
        if (row1 != 1) {
            throw new QkException("转客户失败，请重试！");
        }

        //5.更新商机状态
        business.setStatus(5);
        int row2 = businessMapper.updateById(business);
        if (row2 != 1) {
            throw new QkException("更新商机状态失败，请重试！");
        }

        return true;
    }

    @Override
    public Business findById(Integer id) {
        return businessMapper.findDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean trackBusiness(BusinessTrackDto trackDto) {
        //1.查询商机
        Business business = businessMapper.selectById(trackDto.getId());
        if (business == null) {
            throw new QkException("商机不存在，无法跟进！");
        }

        //2.更新商机基本信息和跟进状态
        BeanUtil.copyProperties(
                trackDto, business, "id", "phone", "channel", "status", "userId", "clueId", "createTime");
        business.setStatus(3);
        int row1 = businessMapper.updateById(business);

        //3.新增商机跟进记录
        BusinessTrackRecord trackRecord = new BusinessTrackRecord();
        trackRecord.setBusinessId(business.getId());
        trackRecord.setUserId(UserHolder.getUserId());
        trackRecord.setTrackStatus(trackDto.getTrackStatus());
        trackRecord.setKeyItems(trackDto.getKeyItems() == null ? null : trackDto.getKeyItems().toString());
        trackRecord.setNextTime(trackDto.getNextTime());
        trackRecord.setRecord(trackDto.getRecord());
        int row2 = businessTrackRecordMapper.insert(trackRecord);

        return row1 == 1 && row2 == 1;
    }

    @Override
    public PageResult<Business> pageBusinessPool(PageBusinessDto poolDto) {
        // 调用持久层分页查询商机公海池列表
        Page<Business> page = businessMapper.pageBusinessPool(
                new Page<>(poolDto.getPage(), poolDto.getPageSize()), poolDto);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}