package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.TimingInspect;
import com.bocloud.blueking.service.TimingInspectService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TimingInspectController extends BaseController {

    @Autowired
    TimingInspectService timingInspectService;
    @RequestMapping("/inspect/timing/{id}")
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(timingInspectService.get(id));
    }

    @RequestMapping("/inspect/timing/list")
    @ResponseBody
    public String getAll(Integer page,Integer size)  {
        Pageable pageable ;
        try {
            pageable = checkPageAndSize(page,size);
        } catch (BusinessException e) {
            return  JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
        return JsonUtil.toJson(timingInspectService.findAll(pageable));
    }

    @RequestMapping("/inspect/timing/save")
    @ResponseBody
    public String save(@RequestBody TimingInspect timingInspect){
        Long userId = getLocalUserId();
        if(timingInspect==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        RespDto a = timingInspectService.save(timingInspect,userId);
        return JsonUtil.toJson(a);
    }

    @RequestMapping("/inspect/timing/update")
    @ResponseBody
    public String update(@RequestBody TimingInspect timingInspect){
        Long userId = getLocalUserId();
        if(timingInspect==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        if(timingInspect.getId()==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"id 不能为空"));
        }
        return JsonUtil.toJson(timingInspectService.update(timingInspect,userId));
    }


    @RequestMapping("/inspect/timing/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        Long userId = getLocalUserId();
        return JsonUtil.toJson(timingInspectService.delete(id,userId));
    }
}
