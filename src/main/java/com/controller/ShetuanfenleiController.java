package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ShetuanfenleiEntity;
import com.entity.view.ShetuanfenleiView;

import com.service.ShetuanfenleiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 社团分类
 * 后端接口
 * @author 
 * @email 
 * @date 2020-11-26 08:47:15
 */
@RestController
@RequestMapping("/shetuanfenlei")
public class ShetuanfenleiController {
    @Autowired
    private ShetuanfenleiService shetuanfenleiService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShetuanfenleiEntity shetuanfenlei, HttpServletRequest request){

        EntityWrapper<ShetuanfenleiEntity> ew = new EntityWrapper<ShetuanfenleiEntity>();
		PageUtils page = shetuanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanfenlei), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShetuanfenleiEntity shetuanfenlei, HttpServletRequest request){
        EntityWrapper<ShetuanfenleiEntity> ew = new EntityWrapper<ShetuanfenleiEntity>();
		PageUtils page = shetuanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanfenlei), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShetuanfenleiEntity shetuanfenlei){
       	EntityWrapper<ShetuanfenleiEntity> ew = new EntityWrapper<ShetuanfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shetuanfenlei, "shetuanfenlei")); 
        return R.ok().put("data", shetuanfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShetuanfenleiEntity shetuanfenlei){
        EntityWrapper< ShetuanfenleiEntity> ew = new EntityWrapper< ShetuanfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shetuanfenlei, "shetuanfenlei")); 
		ShetuanfenleiView shetuanfenleiView =  shetuanfenleiService.selectView(ew);
		return R.ok("查询社团分类成功").put("data", shetuanfenleiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        ShetuanfenleiEntity shetuanfenlei = shetuanfenleiService.selectById(id);
        return R.ok().put("data", shetuanfenlei);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        ShetuanfenleiEntity shetuanfenlei = shetuanfenleiService.selectById(id);
        return R.ok().put("data", shetuanfenlei);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShetuanfenleiEntity shetuanfenlei, HttpServletRequest request){
    	shetuanfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shetuanfenlei);

        shetuanfenleiService.insert(shetuanfenlei);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShetuanfenleiEntity shetuanfenlei, HttpServletRequest request){
    	shetuanfenlei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shetuanfenlei);

        shetuanfenleiService.insert(shetuanfenlei);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShetuanfenleiEntity shetuanfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shetuanfenlei);
        shetuanfenleiService.updateById(shetuanfenlei);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shetuanfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ShetuanfenleiEntity> wrapper = new EntityWrapper<ShetuanfenleiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = shetuanfenleiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
