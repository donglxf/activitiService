/**
 * Name:utils.js
 * Author:Van
 * E-mail:zheng_jinfan@126.com
 * Website:http://kit.zhengjinfan.cn/
 * LICENSE:MIT
 */
layui.define(['layer', 'laytpl', 'form', 'ht_ajax', 'ht_config'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laytpl = layui.laytpl,
        config = layui.ht_config,
        basePath = config.proImg;
    _modName = 'myutil';
    //统一验证添加
    form.verify({
        /**
         * 验证key值的唯一性,需要标识id isId = true
         */
        //value：表单的值、item：表单的DOM对象
        identify: function (value, item) {
            var id = $("input[isId=true]").val();
            var flag = false;
            var msg = '';
            if (!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '不能有特殊字符';
            }
            //  if(id == undefined || id == ''){
            $.ajax({
                cache: true,
                type: "GET",
                url: '/rule/service/check/key',
                data: {
                    key: value,
                    type: $(item).attr("identifyType"),
                    other: $(".other").val()
                    , id: id
                },// 你的formid

                async: false,
                dataType: 'json',
                error: function (request) {
                    //alert("Connection error");
                },
                success: function (da) {
                    if (da.code != 0) {
                        flag = true;
                        $(item).focus();
                        msg = da.msg;
                    }
                }
            });
            if (flag)
                return msg;
            // }
        },
        name: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '不能有特殊字符';
            }
            if (/^\d+$/.test(value)) {
                return '不能全为数字';
            }
        },
        max: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '首尾不能出现下划线\'_\'';
            }
            if (/^\d+$/.test(value)) {
                return '不能全为数字';
            }
        }
    });

    var myUtil = {
        v: '1.0.3',
        baseSerive: basePath + 'system/getAll',
        //业务相关
        business: {
            select: {
                name: 'belongSystem',
                id: 'belongSystem'
            },
            selectFileType: {
                name: 'businessId',
                id: 'businessId'
            },
            init_url: basePath + 'system/getAll',
            init_local_url: basePath + 'initLocalData',
            init_fileType_url: basePath + 'getFileType',
            init_html: function () {
                return '      <div class="layui-input-inline">\n' +
                    '                    <select name="' + myUtil.business.select.name + '"  lay-filter="business_select" lay-search="" id="' + myUtil.business.select.id + '" lay-verify="required">\n' +
                    '                        <option value="">选择系统</option>\n' +
                    '                    </select>\n' +
                    '                </div>'
            },
            init_html2: function (id) {

                return ' <select name="' + myUtil.business.select.name + '" lay-filter="business_select2" lay-search="" id="' + id + '" lay-verify="required">\n' +
                    '    <option value="">选择系统</option>\n' +
                    '    </select>';
            },
            init_fileType_html2: function (id) {

                return ' <select name="' + myUtil.business.selectFileType.name + '" lay-filter="fileType_select" lay-search="" id="' + id + '" lay-verify="required">\n' +
                    '    <option value="">选择系统</option>\n' +
                    '    </select>';
            },
            /**
             * 显示下拉框
             * @param businessId 业务id
             * @param obj 放入的地方
             */
            init: function (businessId, obj, selectId) {
                $.get(myUtil.business.init_url, function (data) {
                    if (data.code == '0') {
                        if (selectId == '' || selectId == undefined) {
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            var ischeck = '';
                            //选中的设置
                            if (result[i].app == businessId) {
                                ischeck = 'selected="true"';
                            }
                            var option = '<option value="' + result[i].nameCn + '" ' + ischeck + ' >' + result[i].nameCn + '</option>';
                            $(obj).find("#" + selectId).append(option);
                        }
                        form.render('select');
                        form.on('select(business_select)', function (data) {
                            myUtil.business.selectBack(data);
                        });
                    }
                }, 'json');
            },
            initLocalByData: function (businessId, obj, selectId) {
                $.get(myUtil.business.init_local_url, function (data) {
                    if (data.code == '0') {
                        if (selectId == '' || selectId == undefined) {
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            var ischeck = '';
                            //选中的设置
                            if (result[i].app == businessId) {
                                ischeck = 'selected="true"';
                            }
                            var option = '<option value="' + result[i].fileTypeCode + '_' + result[i].fileTypeName + '" ' + ischeck + ' >' + result[i].fileTypeName + '</option>';
                            $(obj).find("#" + selectId).append(option);
                        }
                        form.render('select');
                        form.on('select(business_select2)', function (data) {
                            myUtil.business.ldSelect(data);
                        });
                    }
                }, 'json');
            },
            ldSelect: function (da) {
                var val = da.value.split("_")[0];
                var selectId = 'businessId';
                var businessId='businessId';
                var obj=$("#businessDiv");
                $.get(basePath + 'getFileTypeLd?fileTypeCode=' + val, function (data) {
                    if (data.code == '0') {
                        if (selectId == '' || selectId == undefined) {
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_fileType_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            var ischeck = '';
                            //选中的设置
                            if (result[i].app == businessId) {
                                ischeck = 'selected="true"';
                            }
                            var option = '<option value="' + result[i].fileTypeCode + '" ' + ischeck + ' >' + result[i].fileTypeName + '</option>';
                            $(obj).find("#" + selectId).append(option);
                        }
                        form.render('select');
                        form.on('select(business_select)', function (data) {
                            myUtil.business.selectBack(data);
                        });
                    }
                }, 'json');
            },
            selectBack: function (data) {
                console.log(data);
            },
            /**
             * 显示下拉框
             * @param businessId 业务id
             * @param obj 放入的地方
             */
            initFileType: function (businessId, obj, selectId) {
                $.get(myUtil.business.init_fileType_url, function (data) {
                    if (data.code == '0') {
                        if (selectId == '' || selectId == undefined) {
                            selectId = myUtil.business.selectFileType.id;
                        }
                        var h = myUtil.business.init_fileType_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            var ischeck = '';
                            var option = '<optgroup label="' + result[i].typeName + '">';
                            // 子节点
                            var children = result[i].children;
                            for (var j = 0; j < children.length; j++) {
                                //选中的设置
                                if (children[j].id == businessId) {
                                    ischeck = 'selected="true"';
                                }
                                var childrenOption = '<option value="' + children[j].id + '" ' + ischeck + ' >' + children[j].typeName + '</option>'
                                option += childrenOption;
                            }
                            option += '</optgroup>';
                            $(obj).find("#" + selectId).append(option);
                        }
                        form.render('select');
                        form.on('select(fileType_select)', function (data) {
                            myUtil.business.selectBack(data);
                        });
                    }
                }, 'json');
            }
        },
    };


    exports('myutil', myUtil);
});