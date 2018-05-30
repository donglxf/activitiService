layui.config({
    base: '/activity/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
var preUrl = "/activity/service/";
var preUrlUi = "/activity/ui/";
layui.use(['table', 'jquery', 'myutil', 'ht_ajax'], function () {
    var table = layui.table;
    var itemTable = layui.table;
    var $ = layui.jquery;
    //第一个实例
    table.render({
        elem: '#log_list'
        , url: preUrl + 'logPage'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , page: true //开启分页
        , id: 'logReload'
        , cols: [[
            {field: 'businessKey', width: 130, title: '业务key', sort: true}
            , {field: 'processDefinedKey', width: 130, title: '模型编码'}
            , {field: 'sysCode', width: 90, title: '所属系统'}
            , {field: 'version', width: 90, title: '版本', sort: true}
            , {field: 'datas', width: '45%', minWidth: 120, title: '入参'}
            , {field: 'proInstId', title: '实例Id', width: 130} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
            , {field: 'modelProcdefId', title: '模型定义id', sort: true}
            , {field: 'creTime', title: '调用时间', sort: true, templet: "#creTime"}
        ]]
    });

    // table.render({
    //     elem: '#log_list'
    //     , height: 'auto'
    //     , url: preUrl + 'logPage' //数据接口
    //     ,cellMinWidth: 90
    //     , id: 'logReload'
    //     , page: true //开启分页
    //     , cols: [[ //表头\
    //         {field: 'businessKey',width:90, title: '业务key',sort:true}
    //         , {field: 'processDefinedKey',width:90, title: '模型编码'}
    //         , {field: 'version', width:80,title: '版本'}
    //         , {field: 'datas', width:300,title: '入参'}
    //         , {field: 'proInstId',width:100, title: '实例Id'}
    //         , {fixed: 'modelProcdefId',width:100,title: '模型定义id', align: 'center'}
    //     ]]
    // });

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










