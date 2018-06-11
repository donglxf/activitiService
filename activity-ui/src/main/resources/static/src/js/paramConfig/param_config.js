layui.config({
    base: '/src/js/modules/',
    version: true
}).use(['form', 'upload', 'jquery', 'ht_config', 'laytpl', 'table'], function () {
    var form = layui.form;
    var upload = layui.upload;
    var $ = layui.jquery;
    var config = layui.ht_config;
    var fileUploadVoList = [];
    var laytpl = layui.laytpl;
    var table = layui.table;
    var layer = layui.layer;

    // 新增按钮
    $("#version_search").on('click', function () {
        // if (filtTypePath == "" || undefined == filtTypePath) {
        //     alert('请选择一个菜单!');
        //     return;
        // }
        var words = filtTypePath.split('/');
        if (words.length >= 2) {
            alert('只支持到二级菜单!');
            return;
        }
        // alert(filtTypePath);
        var layIndex = layer.open({
            type: 2,
            shade: false,
            title: "新增菜单",
            area: ['800px', '400px'],
            content: 'paramAdd',
            // content: '/templates/paramConfig/param-config-add.html?filtTypePath=' + filtTypePath,
            zIndex: layer.zIndex, //重点1
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);//建立父子联系
                body.find("input[id='filtTypePath']").val(filtTypePath);
                layer.setTop(layero); //重点2
            }
        });
    });


});