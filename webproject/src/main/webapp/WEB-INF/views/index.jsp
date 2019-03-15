<%@ page contentType="text/html;charset=UTF-8" %>
<!---这相当于一个配置，该配置用于左侧菜单栏的显示，id为subPageName  [内容]为该页面的唯一标志  对应于outer.jsp文件中的左侧菜单栏的id-->
<div id ="subPageName" hidden >menuFirstPage</div>
<div id="page-wrapper">

    <div class="container-fluid">

        <!-- Page Heading -->
        <div class="row page-header-box">
            <div class="col-lg-12">
                <h1 class="page-header">
                    概述
                </h1>
            </div>
        </div>
        <!-- /.row -->

        <div class="row data-panel">
            <div class="col-lg-3 col-md-6">
                <div class="king-widget2">
                    <div class="king-widget-content p20 bg-info">
                        <div class="king-counter king-counter-lg">
                            <div class="king-counter-label text-uppercase f16">当前用户今日巡检执行</div>
                            <div class="king-counter-number-group">
                                <span class="king-counter-number white" id="youTodayTime"></span>
                            </div>
                            <div class="king-counter-label text-uppercase f16">次</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="king-widget2">
                    <div class="king-widget-content p20 bg-info">
                        <div class="king-counter king-counter-lg">
                            <div class="king-counter-label text-uppercase f16">今日巡检执行</div>
                            <div class="king-counter-number-group">
                                <span class="king-counter-number white"id="allTodayTime"></span>
                            </div>
                            <div class="king-counter-label text-uppercase f16">次</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="king-widget2">
                    <div class="king-widget-content p20 bg-info">
                        <div class="king-counter king-counter-lg">
                            <div class="king-counter-label text-uppercase f16">当前用户今日常规巡检执行</div>
                            <div class="king-counter-number-group">
                                <span class="king-counter-number white" id="routineYouTodayTime"></span>
                            </div>
                            <div class="king-counter-label text-uppercase f16">次</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="king-widget2">
                    <div class="king-widget-content p20 bg-info">
                        <div class="king-counter king-counter-lg">
                            <div class="king-counter-label text-uppercase f16">今日常规巡检执行</div>
                            <div class="king-counter-number-group">
                                <span class="king-counter-number white" id="routineAllTodayTime"></span>
                            </div>
                            <div class="king-counter-label text-uppercase f16">次</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->

        <%--<div class="row charts">
            <div class="col-md-6">
                <div class="king-block king-block-bordered mb30">
                    <div class="king-block-header">
                        <h3 class="king-block-title">历史执行情况</h3>
                    </div>
                    <div class="king-block-content">
                        <div class="chart" id="chartA"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="king-block king-block-bordered mb30">
                    <div class="king-block-header">
                        <h3 class="king-block-title">数据</h3>
                    </div>
                    <div class="king-block-content">
                        <div class="chart" id="chartC"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="king-block king-block-bordered mb30">
                    <div class="king-block-header">
                        <h3 class="king-block-title">数据</h3>
                    </div>
                    <div class="king-block-content">
                        <div class="chart" id="chartB"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="king-block king-block-bordered mb30">
                    <div class="king-block-header">
                        <h3 class="king-block-title">数据</h3>
                    </div>
                    <div class="king-block-content">
                        <div class="chart" id="chartD"></div>
                    </div>
                </div>
            </div>

        </div>--%>
        <!-- /.row -->

    </div>
    <!-- /.container-fluid -->

</div>
<script type="text/javascript">
//limitDay,Integer type, Integer status,Integer userId
    $.ajax({
        type: "GET",
        url: "/rest/inspect/record/count?limitDay=0&type=2",
        cache: false, //禁用缓存
        dataType: "json",
        success: function (result) {
            if(result.success==true){
                console.log(result);
                $('#routineAllTodayTime').html(result.data)
            }
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/inspect/record/count?limitDay=0&type=2&userId=-2",
        cache: false, //禁用缓存
        dataType: "json",
        success: function (result) {
            if(result.success==true){
                console.log(result);
                $('#routineYouTodayTime').html(result.data)
            }
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/inspect/record/count?limitDay=0",
        cache: false, //禁用缓存
        dataType: "json",
        success: function (result) {
            if(result.success==true){
                console.log(result);
                $('#allTodayTime').html(result.data)
            }
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/inspect/record/count?limitDay=0&userId=-2",
        cache: false, //禁用缓存
        dataType: "json",
        success: function (result) {
            if(result.success==true){
                console.log(result);
                $('#youTodayTime').html(result.data)
            }
        }
    });
</script>