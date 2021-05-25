package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.hosp.HospitalSet;
import com.result.Result;
import com.service.HospitalSetService;
import com.utils.MD5;
import com.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 创作时间：2021/4/25 20:01
 * 作者：张林
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin//跨与注解
public class HospitalSetController {
    @Resource
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "查询医院设置")
    @GetMapping("/findAll")
    public Result findAll(){
        List<HospitalSet> list = hospitalSetService.list();
        Result<List<HospitalSet>> ok = Result.ok(list);
        return ok;
    }

    @ApiOperation(value = "查询医院设置")
    @RequestMapping("/finAll2")
    public List<HospitalSet>finAll2(){
    return hospitalSetService.list();
    }


    /**
     * 批量删除医院设置
     * 方法一
     * 调用的这个
     */
        @ApiOperation("批量删除医院设置")
        @DeleteMapping("/batchdeleteHospSet")
        public Result batchdeleteHospSet(@RequestBody List<Long> idList){
            hospitalSetService.removeByIds(idList);
            return Result.ok();
        }
        //方法二
    @DeleteMapping("/batchDeleteHospSet")
    @ApiOperation(value = "批量删除医院设置")
    public Result batchDeleteHospSet(@RequestBody Long[] ids){
        boolean flag = hospitalSetService.removeByIds(Arrays.asList(ids));
        if(flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    /**
     * PathVariable路径参数变量
     * admin/hosp/hospitalSet/xx
     * admin/hosp/hospitalSet/2
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除医院设置")
    @GetMapping("/deleteHospSetById/{id}")
    public Result deleteHospSetById(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        if(flag){
            //删除成功
            return Result.ok();
        }else{
            //删除失败
            return Result.fail();
        }
    }

    /**
     * 和上面的方法在调用上面有区别
     * admin/hosp/hospitalSet/deleteHospById2?id=xxx
     * @param id
     * @return
     */
    @RequestMapping("/deleteHospSetById2")
    public Result deleteHospSetById2(Long id){
        boolean flag = hospitalSetService.removeById(id);
        if(flag){
            //删除成功
            return Result.ok();
        }else{
            //删除失败
            return Result.fail();
        }
    }
    @PostMapping("/saveHospitalSet")
    @ApiOperation("添加医院")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //根据id查医院的设置
    @GetMapping("/getHospSet/{id}")
    @ApiOperation("根据id查医院设置")
    public Result getHospSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    //修改医院设置
    @PostMapping("/updateHospitalSet")
    @ApiOperation("修改医院设置")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag){
            return Result.ok();
        }else{
            return  Result.fail();
        }
    }

    /**
     * 分页全查
     * current,当前页面，  pageNum
     * limit：页面大小    pageSize
     * RequestBody：异步传递对象，必须加注解
     * HospitalSetQueryVo，页面过来的，里面有多与数据库的参数，
     * 加入有年龄的，可以涉及范围查询等
     * required = false表示这个查询条件可以为空vo可以没有
     */
    @PostMapping("getHospListConn/{current}/{limit}")
    @ApiOperation("分页模糊查医院设置")
    public Result getHospListConn(@PathVariable Long current, @PathVariable Long limit,
                                  @RequestBody(required = false)HospitalSetQueryVo hospitalSetQueryVo){

        //检查分页驱动有没有初始化
        //将参数给page
        Page<HospitalSet> page = new Page<>(current,limit);
        //条件查询
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if(hospitalSetQueryVo!=null){
            //医院名称
            String hosname = hospitalSetQueryVo.getHosname();
            //医院编号
            String hoscode = hospitalSetQueryVo.getHoscode();
            if(hosname!=null&&hosname.length()>=1){
                wrapper.like("hosname",hospitalSetQueryVo.getHosname());
            }
            //spring提供的一个string的工具类
            if(!StringUtils.isEmpty(hoscode)){
                wrapper.like("hoscode",hospitalSetQueryVo.getHoscode());
            }
        }

        //页面一般都是vue或者angular，双向绑定的，页面上有
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);
            return Result.ok(hospitalSetPage);
    }
    //发送签名秘钥
    @PutMapping("/lockHospitalSet/{id}")
    public Result lockHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //使用手机短信的形式发给对方，发给对方医院的练习人
        //使用上面技术发送，阿里大于，乐信通等都可以
        return Result.ok();
    }
    //医院设置锁定和解锁
    //就是把医院的状态修改一下
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,@PathVariable Integer status){
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用状态
        hospitalSetService.updateById(hospitalSet);
        return  Result.ok();
    }











    /**
     * 测试
     */
    @GetMapping("/saveHospitalSet2")
    @ApiOperation("添加医院测试代码")
    public Result saveHospitalSet2(){
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setCreateTime(new Date());
        hospitalSet.setUpdateTime(new Date());
        //设置状态
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //修改医院设置
    @GetMapping("/updateHospitalSet2")
    @ApiOperation("修改医院设置测试")
    public Result updateHospitalSet2(){
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setId(6L);
        hospitalSet.setHosname("华南医院");
        hospitalSet.setCreateTime(new Date());
        hospitalSet.setUpdateTime(new Date());
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag){
            return Result.ok();
        }else{
            return  Result.fail();
        }
    }

}
