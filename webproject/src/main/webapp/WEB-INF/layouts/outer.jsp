<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Inspection Management System</title>
    <!-- Bootstrap css -->
    <link href="//magicbox.bk.tencent.com/static_api/v3/assets/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="//magicbox.bk.tencent.com/static_api/v3/assets/fontawesome/css/font-awesome.css" rel="stylesheet">

    <!-- 当前项目样式文件 -->
    <link href="/static/css/sb-admin.css" rel="stylesheet">
    <link href="/static/css/sb-bk-theme.css" rel="stylesheet">
    <!--蓝鲸平台APP 公用的样式文件 -->

    <link href="//magicbox.bk.tencent.com/static_api/v3/bk/css/bk.css" rel="stylesheet">

    <!-- 如果要使用Bootstrap的js插件，必须先调入jQuery -->
    <script src="//magicbox.bk.tencent.com/static_api/v3/assets/js/jquery-1.10.2.min.js"></script>
    <!-- 包括所有bootstrap的js插件或者可以根据需要使用的js插件调用　-->
    <script src="//magicbox.bk.tencent.com/static_api/v3/assets/bootstrap-3.3.4/js/bootstrap.min.js"></script>
    <!-- 包括所有kendoui的js插件或者可以根据需要使用的js插件调用　-->
    <script src="//magicbox.bk.tencent.com/static_api/v3/assets/kendoui-2015.2.624/js/kendo.all.min.js"></script>
    <!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
    <!--[if lt IE 9]>
    <script src="//magicbox.bk.tencent.com/static_api/v3/assets/js/html5shiv.min.js"></script>
    <script src="//magicbox.bk.tencent.com/static_api/v3/assets/js/respond.min.js"></script>


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
                <a class="navbar-brand" href="index.html">
                    <i class="fa fa-leaf f20 mr5"></i>
                    巡检管理系统
                </a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
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
                </li>
                <li class="dropdown">
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
                </li>
                <li class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-user"></i> 用户</a>
                        </li>
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-envelope"></i> 消息盒</a>
                        </li>
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-gear"></i> 设置</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:;"><i class="fa fa-fw fa-power-off"></i> 退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- 左侧 start-->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li id ="menuFirstPage" >
                        <a href="index.html"><i class="fa fa-fw fa-dashboard"></i> 首页</a>
                    </li>
                    <li id="menuInspectManagement">
                        <a href="javascript:;" data-toggle="collapse" data-target="#subMenuInspectManagement"><i class="fa fa-fw fa-arrows-v"></i> 巡检管理 <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="subMenuInspectManagement" class="collapse">
                            <li>
                                <a href="fast_exec.html"><i class="fa fa-fw fa-table"></i> 快速巡检</a>
                            </li>
                            <li>
                                <a href="inspection_list.html"> 常规巡检</a>
                            </li>
                            <li>
                                <a href="inspection_timing.html"> 定时巡检</a>
                            </li>
                        </ul>
                    </li>

                    <li id ="menuInspectHistory">
                        <a href="inspection_history.html"><i class="fa fa-fw fa-edit"></i> 历史记录</a>
                    </li>
                    <li id ="menuMessage">
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
                    </li>
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
</script>
</html>