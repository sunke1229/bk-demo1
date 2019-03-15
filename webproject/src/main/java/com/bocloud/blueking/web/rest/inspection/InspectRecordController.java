package com.bocloud.blueking.web.rest.inspection;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.common.util.IdsUtil;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.repository.UserRepository;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.utils.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class InspectRecordController extends BaseController {

    private static Logger LOG = LoggerFactory.getLogger(InspectRecordController.class);
    @Autowired
    InspectRecordService inspectRecordService;
    @Autowired
    UserRepository userRepository;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @RequestMapping("/inspect/record/{id}")
    @ResponseBody
    public String get(@PathVariable("id") Long id){
        return JsonUtil.toJson(inspectRecordService.get(id));
    }

    @RequestMapping("/inspect/record/list")
    @ResponseBody
    public String getAll(String name,String creator , String createTime ,Integer inspectType,Integer start ,Integer length) {
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
                predicate.add(criteriaBuilder.like(root.get("userName"), "%" + creator + "%"));
            }

            if(inspectType!=null&&inspectType>=0){
                predicate.add(criteriaBuilder.equal(root.get("type"),  inspectType ));
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
            Predicate[] pre = new Predicate[predicate.size()];
            criteriaQuery.where(predicate.toArray(pre));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("modifyTime")));
            return null;
        };
        return JsonUtil.toJson(inspectRecordService.findAll(spec,pageable));
    }
}
