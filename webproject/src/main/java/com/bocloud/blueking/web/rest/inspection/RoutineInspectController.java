package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.common.util.IdsUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.InspectStep;
import com.bocloud.blueking.model.db.RoutineInspect;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.repository.UserRepository;
import com.bocloud.blueking.service.RoutineInspectService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.api.job.model.IP;
import com.tencent.bk.utils.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class RoutineInspectController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(RoutineInspectController.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Autowired
    RoutineInspectService routineInspectService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="/inspect/routine/{id}",method ={RequestMethod.GET})
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(routineInspectService.get(id));
    }

    @RequestMapping(value="/inspect/routine/list",method ={RequestMethod.GET})
    @ResponseBody
    public String getAll(String name,String creator , String modifier , String createTime ,String modityTime,Integer start ,Integer length)  {


        Pageable pageable ;
        try {
            pageable = checkPageAndSize(start/length,length);
        } catch (BusinessException e) {
            return JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
        User user = getLocalUser();

        Specification spec  = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();

            predicate.add(criteriaBuilder.equal(root.get("bizId"), user.getBizId()));

            if(StringUtils.isNotEmpty(name)){
                predicate.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if(StringUtils.isNotEmpty(creator)){
                List<User> creators =  userRepository.findUserByUsernameIsLike("%" + creator + "%");
                List<Long> creatorIdList = IdsUtil.getIdsListFromUserList(creators);
                if(creatorIdList.size()<=0){
                    creatorIdList.add(-1L);//id -1 表示查询空数据
                }
                Expression<String> exp = root.<String>get("creatorId");
                predicate.add(criteriaBuilder.and(exp.in(creatorIdList)));
            }
            if(StringUtils.isNotEmpty(modifier)){
                List<User> modifiers =  userRepository.findUserByUsernameIsLike("%" + modifier + "%");
                List<Long> modifierIdList = IdsUtil.getIdsListFromUserList(modifiers);
                if(modifierIdList.size()<=0){
                    modifierIdList.add(-1L);//id -1 表示查询空数据
                }
                Expression<String> exp = root.<String>get("modifierId");
                predicate.add(criteriaBuilder.and(exp.in(modifierIdList)));
            }
            if(StringUtils.isNotEmpty(createTime)){
                String[] createTimes = createTime.split("-");
                try {
                    Date createStartTime = sdf.parse(createTimes[0].trim());
                    Date createEndTime = sdf.parse(createTimes[1].trim());
                    predicate.add(criteriaBuilder.between(root.<Date>get("createTime"),createStartTime,createEndTime));
                } catch (ParseException e) {
                    LOG.error("创建时间格式错误："+createTime,e);
                }
            }
            if(StringUtils.isNotEmpty(modityTime)){
                String[] modityTimes = modityTime.split("-");
                try {
                    Date modityStartTime = sdf.parse(modityTimes[0].trim());
                    Date modityEndTime = sdf.parse(modityTimes[1].trim());
                    predicate.add(criteriaBuilder.between(root.<Date>get("modifyTime"),modityStartTime,modityEndTime));
                } catch (ParseException e) {
                    LOG.error("修改时间格式错误："+modityTime,e);
                }
            }
            Predicate[] pre = new Predicate[predicate.size()];
            criteriaQuery.where(predicate.toArray(pre));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return null;
        };
        return JsonUtil.toJson(routineInspectService.findAll(spec,pageable));
    }

    @RequestMapping(value="/inspect/routine/save",method ={RequestMethod.POST})
    @ResponseBody
    public String save(@RequestBody RoutineInspect routineInspect){

        if(routineInspect==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }

        if(StringUtils.isEmpty(routineInspect.getName())){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请输入巡检名称"));
        }
        InspectStep step = routineInspect.getInspectStep();
        if(step==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请填写步骤信息"));
        }
        List<IP>  ips =JsonUtil.fromJson(step.getIpList(),List.class) ;
        if(ips==null||ips.size()<=0){
            return   JsonUtil.toJson(RespHelper.fail(9999,"请选择服务器"));
        }
        if(step.getScriptId()==null||step.getScriptId()<0){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请选择脚本"));
        }
        if(step.getTimeout()==null||step.getTimeout()==0){
            return   JsonUtil.toJson(RespHelper.fail(9999,"请输入超时时间"));
        }



        User user = getLocalUser();
        routineInspect.setBizId(user.getBizId());
        RespDto a = routineInspectService.save(routineInspect,user.getId());
        return JsonUtil.toJson(a);
    }

    @RequestMapping(value="/inspect/routine/update",method ={RequestMethod.POST})
    @ResponseBody
    public String update(@RequestBody RoutineInspect routineInspect){
        Long userId = getLocalUserId();
        if(routineInspect==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"json body 不能为空"));
        }
        if(routineInspect.getId()==null){
            return JsonUtil.toJson(RespHelper.fail(9999,"需要修改的常规巡检id不能为空"));
        }
        if(StringUtils.isEmpty(routineInspect.getName())){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请输入巡检名称"));
        }
        InspectStep step = routineInspect.getInspectStep();
        if(step==null){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请填写步骤信息"));
        }
        List<IP>  ips =JsonUtil.fromJson(step.getIpList(),List.class) ;
        if(ips==null||ips.size()<=0){
            return   JsonUtil.toJson(RespHelper.fail(9999,"请选择服务器"));
        }
        if(step.getScriptId()==null||step.getScriptId()<0){
            return  JsonUtil.toJson(RespHelper.fail(9999,"请选择脚本"));
        }
        if(step.getTimeout()==null||step.getTimeout()==0){
            return   JsonUtil.toJson(RespHelper.fail(9999,"请输入超时时间"));
        }

        return JsonUtil.toJson(routineInspectService.update(routineInspect,userId));
    }


    @RequestMapping(value="/inspect/routine/{id}/delete",method ={RequestMethod.DELETE})
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        Long userId = getLocalUserId();
        return JsonUtil.toJson(routineInspectService.delete(id,userId));
    }
}
