package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.InspectTemplate;
import com.bocloud.blueking.service.InspectTemplateService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.utils.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class InspectTemplateController extends BaseController {

    @Autowired
    InspectTemplateService inspectTemplateService;

    @RequestMapping(value="/inspect/template/{id}",method ={RequestMethod.GET})
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(inspectTemplateService.get(id));
    }

    @RequestMapping(value="/inspect/template/list",method ={RequestMethod.GET})
    @ResponseBody
    public String getAll(Integer page,Integer size,String search)  {
        Pageable pageable ;
        try {
            pageable = checkPageAndSize(page,size);
        } catch (BusinessException e) {
            return JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
        if(StringUtils.isNotEmpty(search)){
            Specification  spec  = (root, criteriaQuery, criteriaBuilder) -> {
                criteriaQuery.where(criteriaBuilder.like(root.get("name"), "%" + search + "%"));
                return null;
            };
            return JsonUtil.toJson(inspectTemplateService.findAll(spec,pageable));
        }
        return JsonUtil.toJson(inspectTemplateService.findAll(pageable));
    }

    @RequestMapping(value="/inspect/template/save",method ={RequestMethod.POST})
    @ResponseBody
    public String save(@RequestBody InspectTemplate inspectTemplate){
        Long userId = getLocalUserId();
        if(inspectTemplate==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        RespDto a = inspectTemplateService.save(inspectTemplate,userId);
        return JsonUtil.toJson(a);
    }

    @RequestMapping(value="/inspect/template/update",method ={RequestMethod.POST})
    @ResponseBody
    public String update(@RequestBody InspectTemplate inspectTemplate){
        Long userId = getLocalUserId();
        if(inspectTemplate==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        if(inspectTemplate.getId()==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"id 不能为空"));
        }
        return JsonUtil.toJson(inspectTemplateService.update(inspectTemplate,userId));
    }


    @RequestMapping(value="/inspect/template/{id}/delete",method ={RequestMethod.DELETE})
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        Long userId = getLocalUserId();
        return JsonUtil.toJson(inspectTemplateService.delete(id,userId));
    }
}
