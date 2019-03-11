package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.RoutineInspect;
import com.bocloud.blueking.service.RoutineInspectService;
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
public class RoutineInspectController extends BaseController {

    @Autowired
    RoutineInspectService routineInspectService;
    @RequestMapping("/inspect/routine/{id}")
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(routineInspectService.get(id));
    }

    @RequestMapping("/inspect/routine/list")
    @ResponseBody
    public String getAll(Integer page,Integer size)  {
        Pageable pageable ;
        try {
            pageable = checkPageAndSize(page,size);
        } catch (BusinessException e) {
            return  JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
        return JsonUtil.toJson(routineInspectService.findAll(pageable));
    }

    @RequestMapping("/inspect/routine/save")
    @ResponseBody
    public String save(@RequestBody RoutineInspect routineInspect){
        Long userId = getLocalUserId();
        if(routineInspect==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        RespDto a = routineInspectService.save(routineInspect,userId);
        return JsonUtil.toJson(a);
    }

    @RequestMapping("/inspect/routine/update")
    @ResponseBody
    public String update(@RequestBody RoutineInspect routineInspect){
        Long userId = getLocalUserId();
        if(routineInspect==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        if(routineInspect.getId()==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"id 不能为空"));
        }
        return JsonUtil.toJson(routineInspectService.update(routineInspect,userId));
    }


    @RequestMapping("/inspect/routine/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        Long userId = getLocalUserId();
        return JsonUtil.toJson(routineInspectService.delete(id,userId));
    }
}
