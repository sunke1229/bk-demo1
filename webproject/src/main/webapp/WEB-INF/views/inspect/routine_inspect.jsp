<%@ page contentType="text/html;charset=UTF-8" %>
<!---这相当于一个配置，该配置用于左侧菜单栏的显示，id为subPageName  [内容]为该页面的唯一标志  对应于outer.jsp文件中的左侧菜单栏的id-->
<div id ="subPageName" hidden >menuInspectManagement</div>


<!-- 删除提示的-->
<link href="https://magicbox.bk.tencent.com/static_api/v3/assets/artDialog-6.0.4/css/ui-dialog.css" rel="stylesheet">
<link href="https://magicbox.bk.tencent.com/static_api/v3/assets/daterangepicker-2.0.5/daterangepicker.css" rel="stylesheet">
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/daterangepicker-2.0.5/moment.min.js"></script>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/daterangepicker-2.0.5/daterangepicker.js"></script>

<link href="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/dataTables.bootstrap.css" rel="stylesheet"/>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/jquery.dataTables.js"></script>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/datatables-1.10.7/dataTables.bootstrap.js"></script>
<script src="//cdn.datatables.net/plug-ins/1.10.19/sorting/date-uk.js"></script>
<link href="https://magicbox.bk.tencent.com/static_api/v3/assets/artDialog-6.0.4/css/ui-dialog.css" rel="stylesheet">
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/artDialog-6.0.4/dist/dialog-min.js"></script>



<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<!--[if lt IE 9]>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/js/html5shiv.min.js"></script>
<script src="https://magicbox.bk.tencent.com/static_api/v3/assets/js/respond.min.js"></script>
<![endif]-->

<div id="page-wrapper">

    <div class="container-fluid">
        <div class="row page-header-box">
            <div class="col-lg-12">
                <h1 class="page-header">
                    巡检管理 / 常规巡检
                </h1>
            </div>
        </div>
        <!-- 查询框 start -->
        <div class="panel panel-default">
            <div class="panel-heading">
                查询条件
            </div>
            <div class='panel-body'>
                <div class="row">
                    <form role="form">
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">名称</div>
                                    <input id="searchName" type="text" class="form-control" >
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">创建人</div>
                                    <input id="searchCreator" type="text" class="form-control">
                                </div>
                            </div>

                        </div>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">最后修改人</div>
                                    <input id="searchModifier" type="text" class="form-control">
                                </div>
                            </div>

                        </div>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">创建时间</div>
                                    <input  type="text" class="form-control daterangepicker_demo" id="searchCreateTime" placeholder="选择日期...">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">修改时间</div>
                                    <input  type="text" class="form-control daterangepicker_demo" id="searchModityTime" placeholder="选择日期...">
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <hr class="mt5 mb15">
                            <button type="button" class="king-btn king-info" onclick="$('#example').DataTable().ajax.reload()">查询</button>
                            <button type="button" class="king-btn king-success">重置</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <!-- 查询框 end -->
        <div class="panel panel-default">
            <!-- <div class="panel-heading">
                带边框的表格
            </div> -->
            <div class='panel-body'>
                <!-- 右下表格 start -->
                <div class="king-wrapper clearfix">
                    <a id="create_new" href="${sessionScope.SITE_URL}inspect/routine/create" class="king-btn-demo king-btn king-info pull-right">
                        新建
                    </a>
                </div>
                <table id="example"   style="width:100%" class="table table-bordered table-hover dataTable no-footer" role="grid">
                    <thead>
                    <tr role="row">
                        <th  rowspan="1" colspan="1" >名称</th>
                        <th  rowspan="1" colspan="1" >创建人</th>
                        <th  rowspan="1" colspan="1" >最后修改人</th>
                        <th  rowspan="1" colspan="1" >创建时间</th>
                        <th  rowspan="1" colspan="1" >修改时间</th>
                        <th  rowspan="1" colspan="1" >操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <!-- 右下表格 end -->
            </div>
        </div>

    </div>
    <!-- /.container-fluid -->

</div>
<script type="text/javascript">
    var date = new Date();
    var oneDay = 1000*60*60*24;
    date.setTime(date.getTime()-7*oneDay);
    // 选择日期范围
    $('#searchCreateTime').daterangepicker({
        startDate:date,
        locale : {
            "format" : 'YYYY/MM/DD HH:mm:ss'
        }
    });


    $('#searchModityTime').daterangepicker({
        startDate:date,
        locale : {
            "format" : 'YYYY/MM/DD HH:mm:ss'
        }
    });
</script>

<script type="text/javascript">
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
            columnDefs: [
                { type: 'date', targets: 3 },
                { type: 'date', targets: 4 }
            ],
            ajax: function (data, callback, settings) {
                //封装请求参数
                /*var param = {};
                param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.currentPage = (data.start / data.length) + 1;//当前页码*/
                //String name,String creator , String modifier , String createTime ,String updateTime,Integer start ,Integer length
                data.name=$("#searchName").val()||"";
                data.creator=$("#searchCreator").val()||"";
                data.modifier=$("#searchModifier").val()||"";
                data.createTime=$("#searchCreateTime").val()||"";
                data.modityTime=$("#searchModityTime").val()||"";
                $.ajax({
                    type: "GET",
                    url: "${sessionScope.SITE_URL}rest/inspect/routine/list",
                    cache: false, //禁用缓存
                    data: data, //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        if(result.success==true){
                            var returnData = {};
                            //returnData.draw = 3;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.data.totalElements;//返回数据全部记录
                            returnData.recordsFiltered = result.data.totalElements;//后台不实现过滤功能，每次查询均视作全部结果

                            var showData = result.data.content.map(function (item) {
                                var itemId = item.id;
                                var option = " <a href=\"javascript:runInspect("+itemId+");\" class=\"bk-icon-button bk-info \" title=\"执行\">\n" +
                                    "              <i class=\"bk-icon icon-play2\"></i>\n" +
                                    "          </a>\n" +
                                    "          <span class=\"mr5\" style=\"border-left:1px solid #ddd;padding:0px 0 0px 10px;\"></span>\n" +
                                    "          <a  href=\"${sessionScope.SITE_URL}inspect/routine/detail/"+itemId+ "\" class=\"bk-icon-button bk-info edit-script\" title=\"详情\">\n" +
                                    "               <i class=\"bk-icon icon-search\"></i>\n" +
                                    "          </a>\n" +
                                    "          <a  href=\"${sessionScope.SITE_URL}inspect/routine/edit/"+itemId+ "\" class=\"bk-icon-button bk-info edit-script\" title=\"编辑\">\n" +
                                    "               <i class=\"bk-icon icon-edit\"></i>\n" +
                                    "          </a>\n" +
                                    "          <a href=\"javascript:deleteInspect("+itemId+");\" class=\"dome-box confirmation-demo bk-icon-button bk-info delete-script\" title=\"删除\" data-toggle=\"confirmation-popout\" data-placement=\"left\" data-original-title=\"\">\n" +
                                    "               <i class=\"bk-icon icon-delete\"></i>\n" +
                                    "          </a>";
                                var dataJson =  {
                                    "0": item.name,
                                    "1": item.creatorName,
                                    "2": item.modifierName,
                                    "3": item.createTime,
                                    "4": item.modifyTime,
                                    "5": option,
                                }
                                return dataJson;
                            });
                            returnData.data = showData;//返回的数据列表
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                        }
                    }
                });
            }
        });
    }
    loadTable();
    function runInspect(id){
        dialog({
            width: 260,
            title: '提示',
            content: '确定要执行该常规巡检吗？',
            okValue: '确定',
            ok: function() {
                var jobData = {
                    type:2,
                    referenceId:id
                };
                $.ajax({
                    method: 'POST',
                    url: "${sessionScope.SITE_URL}rest/inspect/execute",
                    data: JSON.stringify(jobData),
                    success: function(result){
                        if(result.success==true){
                            dialog({
                                width: 260,
                                //title: '提示',
                                content: '已执行',
                                okValue: '确定',
                                ok:function () {

                                }
                            }).show();
                           //window.location.href="/inspect/history/list";
                        }else{
                            alert(result.message);
                        }
                    },
                    dataType: "json",
                    headers: {'Content-Type': 'application/json'}
                });

            },
            cancelValue: '取消',
            cancel: function() {
            },
            onshow: function() {
            },
        }).show();


        console.log(id+"run");
    }
    function deleteInspect(id){
        $.ajax({
            type: "DELETE",
            url: "${sessionScope.SITE_URL}rest/inspect/routine/"+id+"/delete",
            cache: false, //禁用缓存
            dataType: "json",
            success: function (result) {
                if(result.success==true){
                    console.log(result);
                    $('#example').DataTable().ajax.reload();
                }
            }
        });
        console.log(id+"delete");
    }


</script>