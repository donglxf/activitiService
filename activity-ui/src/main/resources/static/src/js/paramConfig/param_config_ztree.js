var filtTypePath = "", $, tableRefresh;
layui.config({
    base: '/src/js/modules/',
    version: true
}).use(['table', 'jquery', 'ht_ajax', 'ht_config', 'ztree'], function () {
        $ = layui.jquery;
        table = layui.table;
        var config = layui.ht_config
            , element = layui.element
            , form = layui.form
            , nodeList
            , fileTypeTree;//文件类型树
        fileTypeTree = initTree();
        $('#file_type_search_tree').bind('input', function (e) {
            if (fileTypeTree && $(this).val() != "") {
                nodeList = fileTypeTree.getNodesByParamFuzzy("name", $(this).val());
                updateNodes(true);
            } else {
                updateNodes(false);
            }
        });

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

        function initTree() {
            return $.fn.zTree.init($('#file_type_ztree_left'), {
                async: {
                    enable: true,
                    url: config.proImg + "fileIndex/fileTypeList",
                },
                view: {
                    height: "full-183"
                    , showIcon: false
                    , selectedMulti: false
                    , fontCss: function (treeId, treeNode) {
                        return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                            color: "#333",
                            "font-weight": "normal"
                        };
                    }
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        if (treeNode) {
                            console.log(JSON.stringify(treeNode));
                            var fileTypeCode = treeNode.fileTypeCode;
                            var fileTypeName = treeNode.name;
                            searchChildren(fileTypeCode);
                            filtTypePath = treeNode.fileTypePath;
                        }
                        //执行重载
                        //refreshTable();
                    },
                    onAsyncSuccess: function (event, treeId, treeNode) {
                        var node = fileTypeTree.getNodeByParam("fileTypeCode ", "001");
                        if (node) {
                            fileTypeTree.selectNode(node);
                        }
                    }
                },
                data: {
                    simpleData: {
                        enable: true
                        , idKey: "fileTypeCode"
                        , pIdKey: "pFileTypeCode"
                    }
                }
            })
        }

        function searchChildren(fileTypeCode) {
            table.render({
                elem: '#tree_list'
                , cellMinWidth: 80
                , url: config.proImg + 'fileIndex/getAll?fileTypeCode=' + fileTypeCode //数据接口
                , page: false
                , id: 'itemT'
                , cols: [[ //表头
                    {field: 'fileTypeCode', title: '文件类型编码', sort: true, fixed: 'left'}
                    , {field: 'fileTypeName', title: '文件類型名稱'}
                    , {field: 'filtTypePath', title: '文件类型路径'}
                    , {field: 'createUserName', title: '创建用户'}
                    , {field: 'createTime', title: '创建时间', templet: "#creTime"}
                    , {field: 'conId', title: '操作', fixed: 'right', align: 'center', toolbar: '#item_bar'}
                ]]
            });

            //监听工具条
            table.on('tool(tree_list)', function (obj) {
                var data = obj.data;
                if (obj.event === 'del') {
                    layer.confirm('是否删除？', function (index) {
                        $.get(config.proImg + 'fileIndex/deleteChildrenNode?fileTypeCode=' + data.fileTypeCode, function (data) {
                            if (data.code < 0) {
                                layer.msg('删除失败，该数据正在被其他数据引用', {icon: 5});
                                layer.close(index);
                            } else {
                                layer.msg("删除成功！");
                                refreshTree();
                                tableRefresh.reload();
                                obj.del();
                                layer.close(index);
                            }
                        }, 'json');

                    });
                } else if (obj.event === 'edit') {
                    editItem(data.id);
                }
            });

            //重载
            //这里以搜索为例
            tableRefresh = {
                reload: function (conId) {
                    //var demoReload = $('#demoReload');
                    //执行重载
                    table.reload('itemT', {
                        // page: {
                        //     curr: 1 //重新从第 1 页开始
                        // }
                        where: {}
                    });
                }
            };
        }

        function editItem(id) {
            $.get(config.proImg + "fileIndex/getInfoById?id=" + id, function (data) {
                var result = data.data;
                $.get('paramEdit', null, function (form) {
                    topIndex = layer.open({
                        type: 1,
                        title: '修改',
                        maxmin: true,
                        shadeClose: false, // 点击遮罩关闭层
                        area: ['550px', '560px'],
                        content: form,
                        btn: ['保存', '取消'],
                        btnAlign: 'c',
                        success: function (layero, index) {
                            setFromValues(layero, result);
                            // var dataType = result.dataType;
                            //
                            // layero.find("option:contains('" + dataType + "')").attr("selected", true);
                            // console.log(layero.find("#dataId"));
                            // var form = layui.form;
                            // form.render('select');
                        }
                        , yes: function (index) {
                            //layedit.sync(editIndex);
                            //触发表单的提交事件
                            $('form.layui-form').find('button[lay-filter=formDemo]').click();
                            // layer.close(index);
                        },
                    });
                });
            }, 'json')
        }

        //刷新树节点
        function updateNodes(highlight) {
            for (var i = 0, l = nodeList.length; i < l; i++) {
                nodeList[i].highlight = highlight;
                fileTypeTree.updateNode(nodeList[i]);
                nodeList[i].check_Focus = true;
                if (highlight) {
                    fileTypeTree.expandNode(fileTypeTree.getNodeByParam("fileTypeCode", nodeList[i]["pFileTypeCode"]), true, false, null, null);
                }
            }
        }

        $('#file_type_tree .btn').on('click', function () {
            var type = $(this).data('type');
            switch (type) {
                case "refresh":
                    if (fileTypeTree) {
                        fileTypeTree = initTree();
                    }
                    break;
                case "expandAll":
                    if (fileTypeTree) {
                        fileTypeTree.expandAll(true);
                    }
                    break;
                case "collapseAll":
                    if (fileTypeTree) {
                        fileTypeTree.expandAll(false);
                    }
                    break;
            }
        });

    }
);

function refreshTree() {
    var treeObj = $.fn.zTree.getZTreeObj("file_type_ztree_left");
    treeObj.reAsyncChildNodes(null, "refresh");
}
