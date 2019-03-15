package com.bocloud.blueking.web.page;


import com.bocloud.blueking.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/inspect")
public class InspectPageController extends BaseController {
      @RequestMapping("/fast")
      public ModelAndView fast(ModelAndView view)  {
          view.setViewName("/inspect/fast_inspect");
          return view;// 你要跳转的JSP路径
      }

    @RequestMapping("/routine")
    public ModelAndView routine(ModelAndView view)  {
        view.setViewName("/inspect/routine_inspect");
        return view;// 你要跳转的JSP路径
    }

    @RequestMapping("/routine/create")
    public ModelAndView routineCreate(ModelAndView view){
        view.setViewName("/inspect/routine_inspect_create");
        return view;// 你要跳转的JSP路径
    }


    @RequestMapping("/routine/edit/{id}")
    public ModelAndView routineEdit(ModelAndView view,@PathVariable("id") Long id)  {
        view.addObject("routineInspectId",id);
        view.setViewName("/inspect/routine_inspect_edit");
        return view;// 你要跳转的JSP路径
    }


    @RequestMapping("/routine/detail/{id}")
    public ModelAndView routineDetail(ModelAndView view,@PathVariable("id") Long id)  {
        view.addObject("routineInspectId",id);
        view.setViewName("/inspect/routine_inspect_detail");
        return view;// 你要跳转的JSP路径
    }


    @RequestMapping("/history/list")
    public ModelAndView historyList(ModelAndView view)  {
        view.setViewName("/inspect/inspect_history");
        return view;// 你要跳转的JSP路径
    }
    @RequestMapping("/history/detail/{id}")
    public ModelAndView historyDetail(ModelAndView view,@PathVariable("id") Long id)  {
        view.addObject("routineRecordId",id);
        view.setViewName("/inspect/inspect_history_detail");
        return view;// 你要跳转的JSP路径
    }
}
