layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
var preUrl = "/activity/service/";
// var preUrlUi = "/activity/ui/";
var preUrlUi = "";
layui.use(['table','ht_config', 'jquery', 'myutil', 'ht_ajax'], function () {
    var table = layui.table;
    var itemTable = layui.table;
    var $ = layui.jquery;
    var ht_config = layui.ht_config;
    var basePath=ht_config.proImg;
    //第一个实例
    table.render({
        elem: '#log_list'
        , url: basePath + 'logPage'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , page: true //开启分页
        , id: 'logReload'
        , cols: [[
            {field: 'id', width: 200, title: 'id', sort: true}
            ,{field: 'businessKey', width: 200, title: '业务key', sort: true}
            , {field: 'processDefinedKey', width: 200, title: '模型编码'}
            , {field: 'sysCode', width: 200, title: '所属系统'}
            , {field: 'version', width: 90, title: '版本', sort: true}
            , {field: 'proInstId', title: '实例Id', width: 250} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
            , {field: 'modelProcdefId', width: '15%', minWidth: 100, title: '模型定义id', sort: true}
            , {field: 'creTime',width:200, title: '调用时间', sort: true, templet: "#creTime"}
            , {field: 'ids', title: '操作',  templet: "#oper"}
        ]]
    });

    table.on('tool(modelLog)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        var logId = data.id;
        console.log(data);
        if(layEvent === 'view'){ //查看
            showdetail(logId);
        }
    });

    function showdetail(logId) {
        var layIndex = layer.open({
            type: 2,
            shade: false,
            title: "流转记录",
            anim: 5,
            area: ['800px', '600px'],
            content: preUrlUi + '/modelLogDetail',
            zIndex: layer.zIndex, //重点1
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var input = body.find("input[type='hidden']");
                input.val(logId);
                layer.setTop(layero); //重点2
            }
        });
    }

    /**
     * 设置表单值
     * @param el
     * @param data
     */
    function setFromValues(el, data) {
        for (var p in data) {
            el.find(":input[name='" + p + "']").val(data[p]);
        }
    }


    active = {
        reload: function () {
            var modelName = $('#userName');
            console.log(modelName.val());
            table.reload('logReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    key: modelName.val()
                }
            });
        }
    };

    $('#model_search').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});










