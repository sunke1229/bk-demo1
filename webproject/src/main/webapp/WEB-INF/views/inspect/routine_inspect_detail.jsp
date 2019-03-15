<%@ page contentType="text/html;charset=UTF-8" %>
<!---这相当于一个配置，该配置用于左侧菜单栏的显示，id为subPageName  [内容]为该页面的唯一标志  对应于outer.jsp文件中的左侧菜单栏的id-->
<div id ="subPageName" hidden >menuInspectManagement</div>
<link href="/static/css/iinspection.css" rel="stylesheet">
<link href="//magicbox.bk.tencent.com/static_api/v3/assets/metisMenu-2.6.0/css/metisMenu.min.css" rel="stylesheet">
<link href="/static/codemirror/theme/monokai.css" rel="stylesheet">
<link href="//magicbox.bk.tencent.com/static_api/v3/assets/codemirror-5.11/lib/codemirror.css" rel="stylesheet">
<!-- 包括所有kendoui的js插件或者可以根据需要使用的js插件调用　-->
<script src="//magicbox.bk.tencent.com/static_api/v3/assets/kendoui-2015.2.624/js/kendo.all.min.js"></script>
<script src="//magicbox.bk.tencent.com/static_api/v3/assets/codemirror-5.11/lib/codemirror.js"></script>
<script src="/static/codemirror/mode/shell/shell.js"></script>

<link href="//magicbox.bk.tencent.com/static_api/v3/assets/select2-3.5.2/select2.css" rel="stylesheet">
<script src="//magicbox.bk.tencent.com/static_api/v3/assets/select2-3.5.2/select2.js"></script>
<!-- 本地js -->

<link href="//magicbox.bk.tencent.com/static_api/v3/assets/artDialog-6.0.4/css/ui-dialog.css" rel="stylesheet">
<script src="//magicbox.bk.tencent.com/static_api/v3/assets/artDialog-6.0.4/dist/dialog-min.js"></script>

<link href="//cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet"/>

<script src="//cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<%--<link href="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/dataTables.bootstrap.css" rel="stylesheet"/>--%>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/jquery.dataTables.js"></script>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/dataTables.bootstrap.js"></script>

<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<!--[if lt IE 9]> -->

<input  hidden id="routineInspectId" value="${routineInspectId}"/>
<div id="page-wrapper">
    <section style="padding: 0px 15px;">
        <ol class="breadcrumb"
            style="margin-bottom:0;border-bottom:1px solid #eee;background:none;border-radius:0;padding-left:5px;">
            <li id="breadcrumb-2"><i class="bk-icon icons-zuoyezhihang"></i> 常规巡检</li>
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
                    <a href="/inspect/routine" class="bk-icon icon-back2">返回</a>
                    <div class="king-block king-block-bordered king-block-themed m5">
                        <div class="king-block-content">
                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检名称：</label>
                                <div class="col-sm-9">
                                    <div id="inspectName"></div>
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

    var editor = CodeMirror.fromTextArea(document.getElementById("editor2_demo"), {
        lineNumbers: true,
        mode: "shell",
        matchBrackets: true,
        theme:'monokai', //编辑器主题
        readOnly:true,
        value:""
    });



    function loadScriptInfo(scriptId) {
        var id = scriptId;
        $.get("/rest/inspect/script/detail/"+id, function(result){
            result = JSON.parse(result)
            var allScriptType = ["","shell","bat","perl","python","powershell","sql"];
            if(result.result==true){
                editor.mode=allScriptType[result.data.type];
                editor.setValue(result.data.content);
                $('#script_name').html(result.data.name);
                $('#script_tag').html(result.data.tag);
            }
        });
    }


    function loadRoutineInspect() {
        var id = $("#routineInspectId").val();
        $.ajax({
            type: "GET",
            url: "/rest/inspect/routine/"+id,
            cache: false, //禁用缓存
            dataType: "json",
            success: function (result) {
                console.log(result);
                if(result.success==true){
                    var inspectTypeName = "";
                    if(result.data.type==0){
                        inspectTypeName = "公共脚本";
                    }else if(result.data.type==1){
                        inspectTypeName = "巡检模板";
                    }
                    $('#inspectType').html(inspectTypeName);
                    $('#inspectName').html(result.data.name);
                    var step = result.data.inspectStep;
                    $("#paramData").html(step.param);
                    $("#timeoutData").html(step.timeout);
                    $('#accountData').html(step.account);
                    var ipList = JSON.parse(step.ipList);
                    var selected = new Map();
                    if(ipList){
                        ipList.forEach(function (item) {
                            console.log(item);
                            selected.set(item.ip,item);
                        });
                    }
                    loadServerHostList(selected);
                    if(step.scriptId){
                        loadScriptInfo(step.scriptId);
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