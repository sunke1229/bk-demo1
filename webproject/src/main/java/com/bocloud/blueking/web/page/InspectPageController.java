package com.bocloud.blueking.web.page;


import com.bocloud.blueking.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/inspect")
public class InspectPageController extends BaseController {
      @RequestMapping("/fast")
      public ModelAndView courseList(ModelAndView view) throws Exception {
          view.setViewName("/inspect/fast_inspect"); //首页
          return view;// 你要跳转的JSP路径
      }
}
