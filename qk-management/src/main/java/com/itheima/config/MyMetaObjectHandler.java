package com.itheima.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        // 方式一 : 如果字段有值就不填充, 没有值才填充
        // this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        // 方式二 : 不管字段是否有值都要填充为新值 , 如果字段不存在会报错
        // metaObject.setValue("createTime",LocalDateTime.now());
        // 方式三 : 判断字段是否存在, 如果字段存在就填充(不管之前有没有值)
        this.setFieldValByName("createTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        // 如果字段有值就不填充, 没有值才填充
        // this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }
}