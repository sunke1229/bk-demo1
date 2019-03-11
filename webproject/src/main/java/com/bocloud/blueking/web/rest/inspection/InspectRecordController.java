package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class InspectRecordController extends BaseController {

    @Autowired
    InspectRecordService inspectRecordService;
    @RequestMapping("/inspect/record/{id}")
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(inspectRecordService.get(id));
    }

    @RequestMapping("/inspect/record/list")
    @ResponseBody
    public String getAll(Integer page,Integer size) {
        Pageable pageable ;
        try {
            pageable = checkPageAndSize(page,size);
        } catch (BusinessException e) {
            return JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
        return JsonUtil.toJson(inspectRecordService.findAll(pageable));
    }

    /*@RequestMapping("/inspect/record/save")
    @ResponseBody
    public String save(@RequestBody InspectRecord inspectRecord){
        Long userId = getLocalUserId();
        if(inspectRecord==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        RespDto a = inspectRecordService.save(inspectRecord,userId);
        return JsonUtil.toJson(a);
    }

    @RequestMapping("/inspect/record/update")
    @ResponseBody
    public String update(@RequestBody InspectRecord inspectRecord){
        Long userId = getLocalUserId();
        if(inspectRecord==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        if(inspectRecord.getId()==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"id 不能为空"));
        }
        return JsonUtil.toJson(inspectRecordService.update(inspectRecord,userId));
    }


    @RequestMapping("/inspect/record/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        Long userId = getLocalUserId();
        return JsonUtil.toJson(inspectRecordService.delete(id,userId));
    }*/
}
