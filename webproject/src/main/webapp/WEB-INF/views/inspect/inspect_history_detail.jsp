<%@ page contentType="text/html;charset=UTF-8" %>
<!---这相当于一个配置，该配置用于左侧菜单栏的显示，id为subPageName  [内容]为该页面的唯一标志  对应于outer.jsp文件中的左侧菜单栏的id-->
<div id ="subPageName" hidden >menuInspectHistory</div>

<link href="${sessionScope.STATIC_URL}css/iinspection.css" rel="stylesheet">
<link href="//magicbox.bk.tencent.com/static_api/v3/assets/metisMenu-2.6.0/css/metisMenu.min.css" rel="stylesheet">
<link href="${sessionScope.STATIC_URL}codemirror/theme/monokai.css" rel="stylesheet">
<link href="${sessionScope.STATIC_URL}codemirror/lib/codemirror.css" rel="stylesheet">
<!-- 包括所有kendoui的js插件或者可以根据需要使用的js插件调用　-->
<%--<script src="//magicbox.bk.tencent.com/static_api/v3/assets/kendoui-2015.2.624/js/kendo.all.min.js"></script>--%>
<script src="${sessionScope.STATIC_URL}codemirror/lib/codemirror.js"></script>
<script src="${sessionScope.STATIC_URL}codemirror/mode/shell/shell.js"></script>

<link href="${sessionScope.STATIC_URL}magicbox/select2-3.5.2/select2.css" rel="stylesheet">
<script src="${sessionScope.STATIC_URL}magicbox/select2-3.5.2/select2.js"></script>
<!-- 本地js -->

<link href="${sessionScope.STATIC_URL}magicbox/artDialog-6.0.4/css/ui-dialog.css" rel="stylesheet">
<script src="${sessionScope.STATIC_URL}magicbox/artDialog-6.0.4/dist/dialog-min.js"></script>

<link href="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/dataTables.bootstrap.css" rel="stylesheet"/>
<script src="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/jquery.dataTables.js"></script>
<script src="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/dataTables.bootstrap.js"></script>


<style>
    tr.selected
    {
        background-color:#F5F5F5;
    }
</style>




<%--
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


--%>

<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<!--[if lt IE 9]> -->

<input  hidden id="routineRecordId" value="${routineRecordId}"/>
<div id="page-wrapper">
    <section style="padding: 0px 15px;">
        <ol class="breadcrumb"
            style="margin-bottom:0;border-bottom:1px solid #eee;background:none;border-radius:0;padding-left:5px;">
            <li id="breadcrumb-2"><i class="bk-icon icons-zuoyezhihang"></i> 巡检记录</li>
            <li id="breadcrumb-3">
                详情
            </li>
        </ol>
    </section>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class='panel-body'>
                <!-- 右侧内部表单 start -->
                <form class="form-horizontal">
                    <a href="${sessionScope.SITE_URL}inspect/history/list" class="bk-icon icon-back2">返回</a>
                    <div class="king-block king-block-bordered king-block-themed m5">


                        <div class="panel panel-default">

                        </div>


                        <div class="king-block-content">
                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检名称：</label>
                                <div class="col-sm-9">
                                    <div id="inspectName"></div>
                                </div>
                            </div>

                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检状态：</label>
                                <div class="col-sm-9">
                                    <div id="inspectStatus"></div>
                                </div>
                            </div>

                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">服务器账户：</label>
                                <div class="col-sm-9">
                                    <div id="accountData"></div>
                                </div>
                            </div>
                            <div class="form-group clearfix  ">
                                <label class="control-label col-sm-2 pt0 bk-h30 bk-lh30">服务器列表：</label>
                                <div class="col-sm-4">
                                    <ul  id="serverList">
                                    </ul>
                                </div>
                            </div>
                            <div class="form-group clearfix">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检来源：</label>
                                <div class="col-sm-9">
                                    <label class="radio-inline" id="inspectType"></label>
                                </div>
                            </div>
                            <div class="form-group"  hidden id="inspectionTemplate">
                                <label class="col-sm-2 control-label bk-lh30 pt0">模板名称：</label>
                                <label class="col-sm-2 control-label bk-lh30 pt0">1</label>
                            </div>
                            <div id="commonScript">
                                <div  class="form-group" >
                                    <label class="col-sm-2 control-label bk-lh30 pt0" >脚本名称：</label>
                                    <div class="col-sm-9">
                                        <div  class="col-sm-3" id="script_name"></div>
                                    </div>
                                </div>
                                <div  class="form-group" >
                                    <label class="col-sm-2 control-label bk-lh30 pt0" >脚本版本：</label>
                                    <div class="col-sm-9">
                                        <div  class="col-sm-3" id="script_tag"></div>
                                    </div>
                                </div>
                                <div  class="form-group" >
                                    <label class="control-label col-sm-2 pt0" >脚本内容：</label>
                                    <div class="col-sm-9" style="border: 0px solid #ddd;" id="2_demo1">

                                        <textarea id="editor2_demo"></textarea>
                                        <!-- 代码文本 start -->
                                        <pre id="code_box" style="display:none;"></pre>
                                        <!-- 代码文本 end -->
                                    </div>
                                </div>
                            </div>
                            <div class="form-group has-feedback clearfix ">
                                <label class="control-label col-sm-2 pt0" >参数：</label>
                                <div class="col-sm-9">
                                    <div class="bk-valign-top" id = "paramData"></div>
                                </div>
                            </div>
                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">超时时间(s)：</label>
                                <div class="col-sm-9">
                                    <div class="bk-valign-top" id = "timeoutData"></div>
                                </div>
                            </div>

                            <div >
                                <div  class="form-group" >
                                    <label class="control-label col-sm-2 pt0" >执行详情：</label>

                                </div>
                            </div>


                            <div >
                                <div  class="form-group" >
                                    <div class="col-sm-1"></div>
                                    <div class="col-sm-10">
                                        <div class="tab-box" >
                                            <ul class="nav nav-tabs king-nav-tabs2"  id="stepStatusTab">
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div >
                                <div  class="form-group" >
                                    <div class="col-sm-1"></div>
                                    <div class="col-sm-5" >
                                        <table id="ipTable" class="table table-bordered table-hover dataTable no-footer" role="grid" >
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting_disabled" rowspan="1" colspan="1">ip</th>
                                                <th class="sorting_disabled" rowspan="1" colspan="1">开始时间</th>
                                                <th class="sorting_disabled" rowspan="1" colspan="1">结束时间</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-sm-5" style="border: 0px solid #ddd;" id="2_demo1_log">

                                        <textarea id="editor2_demo_log"></textarea>
                                        <!-- 代码文本 start -->
                                        <pre id="code_box_log" style="display:none;"></pre>
                                        <!-- 代码文本 end -->
                                    </div>
                                </div>
                            </div>


                            <!-- 右侧内部表单 end -->
                        </div>
                    </div>
                </form>

            </div>

        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- 右侧 end -->
</div>






<script type="text/javascript">
    var stepResults;
    var ipStatus;
    var selectTr ;

    var editor = CodeMirror.fromTextArea(document.getElementById("editor2_demo"), {
        lineNumbers: true,
        mode: "shell",
        matchBrackets: true,
        theme:'monokai', //编辑器主题
        readOnly:true,
        value:""
    });

    var editorLog = CodeMirror.fromTextArea(document.getElementById("editor2_demo_log"), {
        lineNumbers: true,
        mode: "shell",
        matchBrackets: true,
        theme:'monokai', //编辑器主题
        readOnly:true,
        value:""
    });

    //表格(DataTables)-1，html数据源
    var language = {
        search: '搜索：',
        lengthMenu: "每页显示 _MENU_ 记录",
        zeroRecords: "没找到相应的数据！",
        info: "分页 _PAGE_ / _PAGES_",
        infoEmpty: "暂无数据！",
        infoFiltered: "(从 _MAX_ 条数据中搜索)",
        paginate: {
            first: '首页',
            last: '尾页',
            previous: '上一页',
            next: '下一页',
        }
    }
    function resetSelected() {
        $('#ipTable tbody tr').removeClass('selected');
        if(selectTr){
            selectTr.addClass('selected');
        }else{
            if($('#ipTable tbody tr').eq(0)){
                $('#ipTable tbody tr').eq(0).click();
            }
        }
    }
    var ipTable = $('#ipTable').
        on( 'draw.dt',   function () { resetSelected(); } ).DataTable({
        paging: true, //隐藏分页
        ordering: false, //关闭排序
        info: false, //隐藏左下角分页信息
        searching: false, //关闭搜索
        pageLength : 5, //每页显示几条数据
        lengthChange: false, //不允许用户改变表格每页显示的记录数
        language: language //汉化
    });


    $('#ipTable tbody ').on('click', 'tr', function () {
        var index = $(this).index();
        var ip =this.id;
        loadLogEditor(ipStatus,ip);
        selectTr = $(this);
        resetSelected();
    } );

    function changeTableDate(dataAray) {
        ipTable.page(0);
        ipTable.clear();
        ipTable.rows.add(dataAray);
        ipTable.draw(false);
    }

    function loadScriptInfo(scriptId) {
        var id = scriptId;
        $.get("${sessionScope.SITE_URL}rest/inspect/script/detail/"+id, function(result){
            result = JSON.parse(result)
            var allScriptType = ["","shell","bat","perl","python","powershell","sql"];
            if(result.result==true){
                editor.mode=allScriptType[result.data.type];
                //editorLog.mode=allScriptType[result.data.type];
                editor.setValue(result.data.content);
                $('#script_name').html(result.data.name);
                $('#script_tag').html(result.data.tag);
            }
        });
    }

    function loadLogInfo(instanceId) {
        var id = instanceId;
        $.get("${sessionScope.SITE_URL}rest/inspect/job/"+id+"/log", function(result){
            result = JSON.parse(result)
            if(result.result==true){
                /*var instanceStatusMap = {1:"未执行", 2:"正在执行", 3:"执行成功", 4:"执行失败", 5:"跳过", 6:"忽略错误", 7:"等待用户", 8:"手动结束", 9:"状态异常", 10:"步骤强制终止中", 11:"步骤强制终止成功", 12:"步骤强制终止失败"};
                var instanceStatus = result.data[0].status;
                $("inspectStatus").html(instanceStatusMap[instanceStatus+""]);*/
                loadTab(result.data);
            }
        });
    }

    function loadTab(data) {
        stepResults  = data[0].step_results;
        var ipStatusMap = {1:"Agent异常",3:"上次已成功", 5:"等待执行", 7:"正在执行", 9:"执行成功", 11:"任务失败", 12:"任务下发失败", 13:"任务超时", 15:"任务日志错误", 101:"脚本执行失败",
            102:"脚本执行超时", 103:"脚本执行被终止", 104:"脚本返回码非零", 202:"文件传输失败", 203:"源文件不存在", 310:"Agent异常", 311:"用户名不存在", 320:"文件获取失败", 321:"文件超出限制"
            , 329:"文件传输错误", 399:"任务执行出错"};

        var first = true;
        stepResults.forEach(function (item) {
            var ipStatusString = ipStatusMap[item.ip_status+""];
            var tabHtml ;
            if(first){
                tabHtml = "<li class='active'><a href=\"javascript:void(0)\" onclick=\"changeTab("+item.ip_status+")\" data-toggle=\"tab\" aria-expanded=\"true\">"+ipStatusString+"</a></li>";
                ipStatus = item.ip_status;
                first =false;
                var tableData = [];
                item.ip_logs.forEach(function(logItem){
                    tableData.push({
                        "0":logItem.ip,
                        "1":transformErrorDateTime(logItem.start_time.substring(0,19)),
                        "2":logItem.end_time?transformErrorDateTime(logItem.end_time.substring(0,19)):"",
                        "DT_RowId": logItem.ip
                    });
                });
                changeTableDate(tableData);
                loadLogEditor(item.ip_status,tableData[0]["0"]);
            }else{
                tabHtml = "<li ><a href=\"javascript:void(0)\" onclick=\"changeTab("+item.ip_status+")\" data-toggle=\"tab\" aria-expanded=\"true\">"+ipStatusString+"</a></li>";
            }

            $("#stepStatusTab").append(tabHtml);
        });
    }


    function loadLogEditor(status,ip) {
        console.log(ip);
        console.log(status);
        stepResults.forEach(function (item) {
            console.log(ip);
            if(item.ip_status==status){
                item.ip_logs.forEach(function(logItem){
                    if(logItem.ip==ip){
                        editorLog.setValue(logItem.log_content);
                    }
                });
            }
        });

    }

    function transformErrorDateTime(dataString) {
        return dataString.startsWith("1970-01-01 08:00:00")?"-":dataString;
    }

    function changeTab(status) {
        selectTr = null;
        stepResults.forEach(function (item) {
            ipStatus  = status;
            if(item.ip_status==status){
                var tableData = [];
                item.ip_logs.forEach(function(logItem){
                    tableData.push({
                        "0":logItem.ip,
                        "1":transformErrorDateTime(logItem.start_time.substring(0,19)),
                        "2":logItem.end_time?transformErrorDateTime(logItem.end_time.substring(0,19)):"",
                        "DT_RowId": logItem.ip
                    });
                });
                changeTableDate(tableData);
                loadLogEditor(ip_status,tableData[0]["0"]);
            }
        });

    }


    function loadRoutineInspect() {
        var id = $("#routineRecordId").val();
        $.ajax({
            type: "GET",
            url: "${sessionScope.SITE_URL}rest/inspect/record/"+id,
            cache: false, //禁用缓存
            dataType: "json",
            success: function (result) {
                console.log(result);
                if(result.success==true){
                    $('#inspectName').html(result.data.name);
                    var inspectStatus = "";
                    if(result.data.status==0){
                        inspectStatus = "<span style=\"color:goldenrod\">执行中</span>";
                    }else if(result.data.status==1){
                        inspectStatus = "<span style=\"color:limegreen\">成功</span>";
                    } else if(result.data.status==2){
                        inspectStatus = "<span style=\"color:red\">失败</span>";
                    }else{
                        inspectStatus = "-"
                    }
                    $('#inspectStatus').html(inspectStatus);

                    var inspectTypeName = "";
                    if(result.data.type==0){
                        inspectTypeName = "快速巡检-脚本";
                    }else if(result.data.type==1){
                        inspectTypeName = "快速巡检-模板";
                    }else if(result.data.type==2){
                        inspectTypeName = "常规巡检";
                    }else if(result.data.type==3){
                        inspectTypeName = "定时巡检";
                    }
                    $('#inspectType').html(inspectTypeName);
                    $('#inspectName').html(result.data.name);

                    var instance_0 = result.data.inspectRecordJobInstances[0];
                    var instanceContent = instance_0.instanceContent;
                    if(instanceContent){

                        var instanceContent = JSON.parse(instanceContent);
                        $("#paramData").html(instanceContent.param);
                        $("#timeoutData").html(instanceContent.timeout);
                        $('#accountData').html(instanceContent.account);
                        console.log(instanceContent);
                        var ipList = JSON.parse(instanceContent.ipList);
                        var selected = new Map();
                        if(ipList){
                            ipList.forEach(function (item) {
                                selected.set(item.ip,item);
                            });
                        }

                        loadServerHostList(selected);

                        if(instanceContent.scriptId){
                            loadScriptInfo(instanceContent.scriptId);
                        }
                        if(instance_0.referenceInstanceId){
                            loadLogInfo(instance_0.referenceInstanceId);
                        }
                    }
                }
            }
        });
    }

    function loadServerHostList(selected){
        $('#serverList').empty();
        if(selected.size>0){
            selected.forEach(function (value, key) {
                var ip = key;
                var serverItem = "<li id=\"server_"+ip+"\" class=\"list-group-item\">" + ip + "</li>";
                $('#serverList').append(serverItem);
            });
        }
    }
    onload = loadRoutineInspect();
</script>
<script type="text/javascript">

</script>