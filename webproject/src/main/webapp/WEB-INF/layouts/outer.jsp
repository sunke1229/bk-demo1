<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Inspection Management System</title>

    <script type="text/javascript">
        var app_id = "${sessionScope.APP_ID}";
        var site_url = "${sessionScope.SITE_URL}";	  // app的url前缀,在ajax调用的时候，应该加上该前缀
        var static_url = "${sessionScope.STATIC_URL}"; // 静态资源前缀，在js中引用资源时要加上这个前缀
    </script>
    <!-- Bootstrap css -->
    <link href="${sessionScope.STATIC_URL}magicbox/bootstrap-3.3.4/bootstrap.min.css" rel="stylesheet">
    <link href="${sessionScope.STATIC_URL}magicbox/fontawesome-4.3.0/css/font-awesome.css" rel="stylesheet">


    <!-- 当前项目样式文件 -->
    <link href="${sessionScope.STATIC_URL}css/sb-admin.css" rel="stylesheet">
    <link href="${sessionScope.STATIC_URL}css/sb-bk-theme.css" rel="stylesheet">
    <!--蓝鲸平台APP 公用的样式文件 -->

    <link href="${sessionScope.STATIC_URL}css/bk.css" rel="stylesheet">

    <!-- 如果要使用Bootstrap的js插件，必须先调入jQuery -->
    <script src="${sessionScope.STATIC_URL}magicbox/jquery-1.10.2.min.js"></script>
    <!-- 包括所有bootstrap的js插件或者可以根据需要使用的js插件调用　-->
    <script src="${sessionScope.STATIC_URL}magicbox/bootstrap-3.3.4/bootstrap.min.js"></script>
    <!-- 包括所有kendoui的js插件或者可以根据需要使用的js插件调用　-->
   <%-- <script src="//magicbox.bk.tencent.com/static_api/v3/assets/kendoui-2015.2.624/js/kendo.all.min.js"></script>--%>

    <!--配置js  勿删-->
    <script src="${sessionScope.STATIC_URL}js/settings.js?v=${sessionScope.STATIC_VERSION}" type="text/javascript"></script>
    <!--蓝鲸平台APP 公用的样式文件 -->
    <link href="${sessionScope.STATIC_URL}magicbox/bk-icon-2.0/iconfont.css" rel="stylesheet">
    <link href="${sessionScope.STATIC_URL}css/common.css" rel="stylesheet">
    <link href="${sessionScope.STATIC_URL}css/button.css" rel="stylesheet">

    <!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
    <!--[if lt IE 9]>
    <script src="${sessionScope.STATIC_URL}js/html5shiv.min.js"></script>
    <script src="${sessionScope.STATIC_URL}js/respond.min.js"></script>


    <![endif]-->
</head>

<body>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${sessionScope.SITE_URL}">
                    <i class="fa fa-leaf f20 mr5"></i>
                    巡检管理系统
                </a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <%--<li class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu message-dropdown">
                        <li class="message-preview">
                            <a href="javascript:;">
                                <div class="media">
                                        <span class="pull-left">
                                            <img class="media-object" src="/static/img/50x50.png" alt="">
                                        </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>qcloud</strong>
                                        </h5>
                                        <p class="small text-muted">Yesterday at 4:32 PM</p>
                                        <p>当前有一条未读的短消息...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="javascript:;">
                                <div class="media">
                                        <span class="pull-left">
                                            <img class="media-object" src="/static/img/50x50.png" alt="">
                                        </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>qcloud</strong>
                                        </h5>
                                        <p class="small text-muted">Yesterday at 4:32 PM</p>
                                        <p>当前有一条未读的短消息...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="javascript:;">
                                <div class="media">
                                        <span class="pull-left">
                                            <img class="media-object" src="/static/img/50x50.png" alt="">
                                        </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>qcloud</strong>
                                        </h5>
                                        <p class="small text-muted">Yesterday at 4:32 PM</p>
                                        <p>当前有一条未读的短消息...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-footer">
                            <a href="javascript:;">查看所有消息</a>
                        </li>
                    </ul>
                </li>--%>
                <%--<li class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu alert-dropdown">
                        <li>
                            <a href="javascript:;"><span class="label label-default">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li>
                            <a href="javascript:;"><span class="label label-primary">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li>
                            <a href="javascript:;"><span class="label label-success">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li>
                            <a href="javascript:;"><span class="label label-info">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li>
                            <a href="javascript:;"><span class="label label-warning">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li>
                            <a href="javascript:;"><span class="label label-danger">&nbsp;&nbsp;提醒内容&nbsp;&nbsp;</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:;" class='text-center'>查看所有提醒</a>
                        </li>
                    </ul>
                </li>--%>
                    <li class="dropdown" onclick="loadBusiness()">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"  ><i class="fa fa-bell"></i>&nbsp;${sessionScope.businessName}<b class="caret"></b></a>
                        <ul class="dropdown-menu alert-dropdown" id="business"  >

                        </ul>
                    </li>
                <li class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>${sessionScope.userName}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <%--<li>
                            <a href="javascript:;"><i class="fa fa-fw fa-user"></i> 用户</a>
                        </li>
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-envelope"></i> 消息盒</a>
                        </li>
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-gear"></i> 设置</a>
                        </li>--%>
                        <%--<li class="divider"></li>--%>
                        <li>
                            <a href="${sessionScope.SITE_URL}rest/user/logout"><i class="fa fa-fw fa-power-off"></i> 退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- 左侧 start-->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li id ="menuFirstPage" >
                        <a href="${sessionScope.SITE_URL}"><i class="fa fa-fw fa-dashboard"></i> 首页</a>
                    </li>
                    <li id="menuInspectManagement">
                        <a href="javascript:;" data-toggle="collapse" data-target="#subMenuInspectManagement"><i class="fa fa-fw fa-arrows-v"></i> 巡检管理 <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="subMenuInspectManagement" class="collapse">
                            <li>
                                <a href="${sessionScope.SITE_URL}inspect/fast"><%--<i class="fa fa-fw fa-table"></i>--%> 快速巡检</a>
                            </li>
                            <li>
                                <a href="${sessionScope.SITE_URL}inspect/routine"> 常规巡检</a>
                            </li>
                            <%--<li>
                                <a href="inspection_timing.html"> 定时巡检</a>
                            </li>--%>
                        </ul>
                    </li>

                    <li id ="menuInspectHistory">
                        <a href="${sessionScope.SITE_URL}inspect/history/list"><i class="fa fa-fw fa-edit"></i> 历史记录</a>
                    </li>
                    <%--<li id ="menuMessage">
                        <a href="exception_building.html"><i class="fa fa-fw fa-wrench"></i> 通知功能</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#warehouse"><i class="fa fa-fw fa-arrows-v"></i> 模板仓库 <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="warehouse" class="collapse">
                            <li>
                                <a href="inspection_template_list.html"> 巡检模板 </a>
                            </li>
                            <li>
                                <a href="exception_building.html"> 报表模板 </a>
                            </li>
                            <li>
                                <a href="exception_building.html"> 通知模板 </a>
                            </li>
                        </ul>
                    </li>--%>
                </ul>
            </div>
            <!-- 左侧 end -->
        </nav>
       <%-- <div id = "subPage" ></div--%>

        <sitemesh:write property='body' />

    </div>
    <!-- /#wrapper -->





    <!-- 本地js -->

</body>
<script type="text/javascript">
    var subPageName = $('#subPageName').html();
    var menu = $('#'+subPageName)
    menu.find("ul").eq(0).collapse('show');
    console.log("${sessionScope}");
    function loadBusiness() {
        $("#business").empty();
        $("#business").append("<li><a href=\"javascript:;\"><span class=\"label label-default\">加载中</span></a> </li>");
        $.ajax({
            type: "GET",
            url: "${sessionScope.SITE_URL}rest/system/biz/list",
            cache: false, //禁用缓存
            dataType: "json",
            success: function (result) {
                console.log(result);
                if(result.success==true){
                    $("#business").empty();
                    var businessList = result.data.info;
                    businessList.forEach(function (item) {
                        var businessItemName = item.bk_biz_name;
                        var businessItem = "<li  onclick='changeBusiness("+item.bk_biz_id+")'><a href=\"javascript:;\"><span class=\"label label-default\">"+businessItemName+"</span></a> </li>";
                        $("#business").append(businessItem);
                    });

                }
            }
        });



    }


    function changeBusiness(id) {
        $.ajax({
            type: "POST",
            url: "${sessionScope.SITE_URL}rest/system/changeBiz?bizId="+id,
            cache: false, //禁用缓存
            dataType: "json",
            success: function (result) {
                if(result.success==true){
                    window.location.href="${sessionScope.SITE_URL}";
                }else{
                    alert(result.message);
                }
            }
        });
    }


</script>
</html>