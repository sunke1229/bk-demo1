<%@ page contentType="text/html;charset=UTF-8" %>
<!---这相当于一个配置，该配置用于左侧菜单栏的显示，id为subPageName  [内容]为该页面的唯一标志  对应于outer.jsp文件中的左侧菜单栏的id-->
<div id ="subPageName" hidden >menuInspectManagement</div>
<link href="${sessionScope.STATIC_URL}css/iinspection.css" rel="stylesheet">
<%--<link href="//magicbox.bk.tencent.com/static_api/v3/assets/metisMenu-2.6.0/css/metisMenu.min.css" rel="stylesheet">--%>
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

<link href="${sessionScope.STATIC_URL}css/jquery.dataTables.min.css" rel="stylesheet"/>

<script src="${sessionScope.STATIC_URL}js/jquery.dataTables.min.js"></script>
<%--<link href="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/dataTables.bootstrap.css" rel="stylesheet"/>--%>
<script src="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/jquery.dataTables.js"></script>
<script src="${sessionScope.STATIC_URL}magicbox/datatables-1.10.7/dataTables.bootstrap.js"></script>

<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<script src="${sessionScope.STATIC_URL}js/html5shiv.min.js"></script>
<script src="${sessionScope.STATIC_URL}js/respond.min.js"></script>
<!--[if lt IE 9]> -->

<div id="page-wrapper">
    <section style="padding: 0px 15px;">
        <ol class="breadcrumb"
            style="margin-bottom:0;border-bottom:1px solid #eee;background:none;border-radius:0;padding-left:5px;">
            <li id="breadcrumb-2"><i class="bk-icon icons-zuoyezhihang"></i> 常规巡检</li>
            <li id="breadcrumb-3">
                创建
            </li>
        </ol>
    </section>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class='panel-body'>
                <!-- 右侧内部表单 start -->
                <form class="form-horizontal">
                    <a href="${sessionScope.SITE_URL}inspect/routine" class="bk-icon icon-back2">返回</a>
                    <div class="king-block king-block-bordered king-block-themed m5">
                        <div class="king-block-content">
                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检名称：</label>
                                <div class="col-sm-9">
                                    <input type="text"  id="inspectName" class="form-control bk-valign-top"
                                           placeholder="请输入名称"> </div>
                            </div>
                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">服务器账户：</label>
                                <div class="col-sm-9">
                                    <select id="accountData" value="root"  class="form-control bk-valign-top">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group clearfix ">
                                <label class="control-label col-sm-2 pt0 bk-h30 bk-lh30">目标机器：</label>
                                <div class="col-sm-9">
                                    <button id = "chooseHost" type="button" class="bk-button bk-primary" title="选择服务器">
                                        <i class="bk-icon icon-pc"></i><span id = "chooseHostButton">选择服务器</span>
                                    </button>
                                </div>
                            </div>
                            <div class="form-group clearfix  ">
                                <label class="control-label col-sm-2 pt0 bk-h30 bk-lh30">服务器列表：</label>
                                <div class="col-sm-4">
                                    <ul class="list-group" id="serverList">
                                    </ul>
                                </div>
                            </div>
                            <div class="form-group clearfix">
                                <label class="col-sm-2 control-label bk-lh30 pt0">巡检来源<span class="red">&nbsp;*</span>：</label>
                                <div class="col-sm-9">
                                    <label class="radio-inline"> <input  checked type="radio" value="0" name="inspectType">公共脚本</label>
                                    <%--<label class="radio-inline"> <input  checked type="radio" value="1" name="inspectType">巡检模板</label>--%>
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
                                        <div class="plugin3_demo" id="plugin3_demo7" style="display:inline-block;">
                                            <div class="select2-container bigdrop"   id="script_list"   style="width:300px;" >
                                                <a href="javascript:void(0)" class="select2-choice" tabindex="-1"> <span class="select2-chosen" id="select2-chosen-13"></span><abbr class="select2-search-choice-close"></abbr> <span class="select2-arrow" role="presentation"><b role="presentation"></b></span></a>
                                                <label for="s2id_autogen13" class="select2-offscreen"></label>
                                                <input class="select2-focusser select2-offscreen" type="text" aria-haspopup="true" role="button" aria-labelledby="select2-chosen-13" id="s2id_autogen13">
                                            </div>
                                            <input type="hidden" class="bigdrop"style="width: 300px; display: none;" tabindex="-1" title="" value="2" >
                                            <div class="select2-container bigdrop" id="script_tag" title="" style="width: 300px; display: inline-block;">
                                                <a href="javascript:void(0)" class="select2-choice" tabindex="-1"> <span class="select2-chosen" id="select2-chosen-88"></span><abbr class="select2-search-choice-close"></abbr> <span class="select2-arrow" role="presentation"><b role="presentation"></b></span></a>
                                                <label for="s2id_autogen88" class="select2-offscreen"></label>
                                                <input class="select2-focusser select2-offscreen" type="text" aria-haspopup="true" role="button" aria-labelledby="select2-chosen-88" id="s2id_autogen88">
                                            </div>
                                            <input type="hidden" class="bigdrop"  style="width: 300px; display: none;" title="" value="2" tabindex="-1">
                                        </div>
                                    </div>
                                </div>
                                <div  class="form-group" >
                                    <label class="control-label col-sm-2 pt0" >脚本内容：</label>
                                    <div class="col-sm-9" style="border: 0px solid #ddd;" id="editor2_demo1">
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
                                    <input id="paramData" type="text" class="form-control bk-valign-top"
                                           placeholder="请输入参数"> </div>
                            </div>

                            <div class="form-group clearfix ">
                                <label class="col-sm-2 control-label bk-lh30 pt0">超时时间(s)：</label>
                                <div class="col-sm-9">
                                    <input type="text"  id = "timeoutData" class="form-control bk-valign-top"  placeholder="请输入超时时间">
                                </div>
                            </div>
                            <div class="form-group clearfix">
                                <div class="col-sm-9 col-sm-offset-3">
                                    <button type="button" id="saveInspect" class="king-btn mr10  king-success" onclick="">保存</button>
                                    <button type="button" class="king-btn king-default ">取消</button>
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


    // 脚本来源
    $('input[name=inspectType]:radio').click(function(){
        var type = this.value;
        $("input:radio[name=inspectType][value!="+type+"]").removeAttr("checked");//反选其他
        $(this).attr("checked",true);
        if(type == 0){
            $('#commonScript').show(0);
            $('#inspectionTemplate').hide(0);
        }
        else if(type == 1 ){
            $('#commonScript').hide(0);
            $('#inspectionTemplate').show(0);
        }
    });
    function loadAccount() {
        $.get("${sessionScope.SITE_URL}rest/inspect/account/list", function(result){
            result = JSON.parse(result)
            if(result.result==true){
                result.data.forEach(function (item) {
                    var  option = "<option value='"+item.account+"'>"+item.account+"</option>";
                    $("#accountData").append(option);
                })
            }
        });
    }
    
    function loadScriptList() {
        var scriptSelect = $("#script_list");
        $.get("${sessionScope.SITE_URL}rest/inspect/script/list", function(result){
            result = JSON.parse(result)
            if(result.result==true){
                var data = result.data.data;
                var bkArr = data.map(function (item) {
                    return { id: item.id, text: item.name };
                });
                scriptSelect.select2({
                    data : bkArr,
                    placeholder: "选择脚本"
                });
            }
        });
    }




    $("#script_list").on("change", function(e) {
           var id = e.val;
            $.get("${sessionScope.SITE_URL}rest/inspect/script/detail/"+id, function(result){
                result = JSON.parse(result)
                var allScriptType = ["","shell","bat","perl","python","powershell","sql"];

                if(result.result==true){
                    editor.mode=allScriptType[result.data.type];
                    editor.setValue(result.data.content);
                    $('#script_tag').val(0);
                    $("#script_tag").select2({
                        data :  [{ id: 0, text: result.data.tag }],
                        placeholder: "选择版本"
                    });

                }
            });
            }
        );
    function loadData() {
        loadAccount();
        loadScriptList();
    }

    onload = loadData();
</script>

<script type="text/javascript">
    //模态对话框
    $("#chooseHost").click(function() {
        var selectOld = new Map();
        selected.forEach(function (value, key, map) {
            selectOld.set(key,value);
        });
        var d = dialog({
            width: 1000,
            title: 'message',
            content: "<table id=\"example\" style=\"width:100%\" role=\"grid\">\n" +
            "    <thead>\n" +
            "    <tr role=\"row\">\n" +
            "        <th class=\"sorting_disabled\" rowspan=\"1\" colspan=\"1\" style=\"width: 217px;\">主机名</th>\n" +
            "        <th class=\"sorting_disabled\" rowspan=\"1\" colspan=\"1\" style=\"width: 136px;\">主机ip</th>\n" +
            "        <th class=\"sorting_disabled\" rowspan=\"1\" colspan=\"1\" style=\"width: 119px;\">操作系统</th>\n" +
            "        <th class=\"sorting_disabled\" rowspan=\"1\" colspan=\"1\" style=\"width: 141px;\">系统位数</th>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "</table>\n",
            okValue: '确定',
            ok: function() {
                $('#serverList').empty();
                if(selected.size>0){
                    selected.forEach(function (value, key) {
                        var ip = key;
                        var serverItem = "<li id=\"server_"+ip+"\" class=\"list-group-item\">" + ip +
                            "                <button  style=\"float: right\" type=\"button\"   class=\"btn btn-primary btn-danger btn-xs\">\n" +
                            "                    <span   aria-hidden=\"true\">×</span>\n" +
                            "                </button>\n" +
                            "              </li>";
                        $('#serverList').append(serverItem);
                    });
                    $('#serverList li button').on('click', function () {
                        var selectIp = $(this).parent().attr('id').substring(7);
                        selected.delete(selectIp);
                        if(selected.size<=0){
                            $("#chooseHostButton").text("选择服务器");
                        }
                        $(this).parent().remove();
                    } );
                    $("#chooseHostButton").text("重新选择服务器");
                }else{
                    $("#chooseHostButton").text("选择服务器");
                }
            },
            cancelValue: '取消',
            cancel: function() {
                selected = selectOld ;
            }
        });
        d.showModal();
        loadTable();
    });
</script>

<script type="text/javascript">
    var selected = new Map();
    function loadTable(){
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
        };
        $("#example").DataTable({
            paging: true, //分页
            ordering: false, //关闭排序
            info: false, //隐藏左下角分页信息
            searching: false, //关闭搜索
            pageLength : 10, //每页显示几条数据
            lengthChange: true, //不允许用户改变表格每页显示的记录数
            language: language, //汉化
            processing: true,
            serverSide: true,
            ajax: function (data, callback, settings) {
                //封装请求参数
                /*var param = {};
                param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.currentPage = (data.start / data.length) + 1;//当前页码*/
                $.ajax({
                    type: "GET",
                    url: "${sessionScope.SITE_URL}rest/inspect/host/list",
                    cache: false, //禁用缓存
                    data: data, //传入组装的参数
                    dataType: "json",
                    columnDefs: [
                        {
                            "targets": [ 4 ],
                            "visible": false,
                        }
                    ],
                    success: function (result) {
                        if(result.result==true){
                            var returnData = {};
                            //returnData.draw = 3;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.data.count;//返回数据全部记录
                            returnData.recordsFiltered = result.data.count;//后台不实现过滤功能，每次查询均视作全部结果
                            var showData = result.data.info.map(function (item) {
                                var dataJson =  {
                                    "0": item.host.bk_host_name,
                                    "1": item.host.bk_host_innerip,
                                    "2": item.host.bk_os_name,
                                    "3": item.host.bk_os_bit,
                                    "4": item.host.bk_cloud_id[0].bk_inst_id,
                                    //bkCloudId
                                    "DT_RowId": item.host.bk_host_innerip,
                                }
                                return dataJson;
                            });
                            returnData.data = showData;//返回的数据列表
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                        }
                    }
                });
            },
            rowCallback: function( row, data ) {
                if(selected.get(String(data.DT_RowId))){
                    $(row).addClass('selected');
                }
            }
        });
        $('#example tbody ').on('click', 'tr', function () {
            var index = $(this).index();
            var rowData = $('#example').DataTable().data().toArray()[index];
            var bkCloudId =rowData["4"];
            var ip = this.id;
            if ( !selected.get(ip)) {
                selected.set(ip,{ip:ip,bkCloudId:bkCloudId});
            } else {
                selected.delete( ip );
            }

            $(this).toggleClass('selected');
        } );
    }



    $('#saveInspect').on('click', function () {
        $('#saveInspect').attr("disabled","true");
        var ipList = [];
        selected.forEach(function (value) {
            ipList.push(value);
        });


        var routineInspectData = {
            type:$("input[name=inspectType]:radio").val()||"", //STEP
            name:$('#inspectName').val()||"",
            inspectStep:{
                stepOrder:0,
                ipList:JSON.stringify(ipList),
                param:$("#paramData").val()||"",
                timeout:$("#timeoutData").val()||"",
                scriptId:$("#script_list").val()||"",
                account:$('#accountData').val()||"",
            }
        };
        $.ajax({
            method: 'POST',
            url: "${sessionScope.SITE_URL}rest/inspect/routine/save",
            data: JSON.stringify(routineInspectData),
            success: function(result){
                $('#saveInspect').removeAttr("disabled");
                if(result.success==true){
                    window.location.href="${sessionScope.SITE_URL}inspect/routine";
                }else{
                    alert(result.message);
                }
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        });
    } );


</script>