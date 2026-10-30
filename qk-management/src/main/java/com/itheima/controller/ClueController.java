package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.dto.ClueTrackDto;
import com.itheima.dto.MarkFalseClueDto;
import com.itheima.dto.PageClueDto;
import com.itheima.entity.Clue;
import com.itheima.service.ClueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clues")
public class ClueController {
    private final ClueService clueService;

    //1.添加
    @PostMapping
    public Result addClue(@RequestBody Clue clue) {
        //1.调用Service
        boolean b = this.clueService.save(clue);
        //2.返回
        return b ? Result.success() : Result.error("添加线索失败，请重试！");
    }

    //2.分页列表
    @GetMapping
    public Result pageClueList(PageClueDto clueDto) {
        //1.调用业务层
        PageResult<Clue> pageResult = this.clueService.pageClue(clueDto);
        //2.返回
        return Result.success(pageResult);
    }

    //3.分配线索
    @PutMapping("/assign/{clueId}/{userId}")
    public Result assignClue(@PathVariable Integer clueId, @PathVariable Integer userId) {
        boolean b = this.clueService.assignClue(clueId,userId);
        return b ? Result.success() : Result.error("分配失败，请重试！");
    }

    /**
     * 根据id查询线索详情(包含跟进历史记录)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Clue clue = clueService.findById(id);
        return Result.success(clue);
    }

    /**
     * 线索跟进
     *
     * @param trackDto
     * @return
     */
    @PutMapping
    public Result trackClue(@RequestBody ClueTrackDto trackDto) {
        boolean b = clueService.trackClue(trackDto);
        return b ? Result.success() : Result.error("跟进记录写入失败，请重试！");
    }

    /**
     * 转商机操作
     *
     * @param clueId
     * @return
     */
    @PutMapping("/toBusiness/{clueId}")
    public Result toBusiness(@PathVariable Integer clueId) {
        boolean b = clueService.toBusiness(clueId);
        return b ? Result.success() : Result.error("转商机操作失败，请重试！");
    }

    /**
     * 将线索标记为伪线索
     *
     * @param id 线索id
     * @param markFalseClueDto 伪线索
     */
    @PutMapping("/false/{id}")
    public Result markFalseClue(@PathVariable Integer id, @RequestBody MarkFalseClueDto markFalseClueDto) {

        boolean b = clueService.markClueFalse(id, markFalseClueDto);
        return b ? Result.success() : Result.error("标记伪线索失败，请重试！");
    }

    /**
     * 分页查询线索池列表
     *
     * @param poolDto
     * @return
     */
    @GetMapping("/pool")
    public Result pageCluePool(PageClueDto poolDto) {
        // 调用业务层分页查询线索池列表
        PageResult<Clue> pageResult = clueService.pageCluePool(poolDto);
        return Result.success(pageResult);
    }

}
