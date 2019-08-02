$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
    $.getJSON(baseURL+"sys/menu/nav?_"+$.now(), function(r){
        console.log(r)
        vm.menuList = r.menuList;
    });

});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            name:''
        },
        showList: true,
        title: null,
        resourceCatalog: {},
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        h:0,
        props: {
            label: 'name',
            children: 'list',
        },
        count: 1,
        id:0,
        filterText: '',
        menuList:{},
    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.resourceCatalog = {};
        },
        update: function (event) {
            var catalogId = getCatalogId();
            if(catalogId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(catalogId)
        },
        saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
            var url = vm.resourceCatalog.catalogId == null ? "resource/resourcecatalog/save" : "resource/resourcecatalog/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.resourceCatalog),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var catalogIds = getCatalogId();
            console.log(catalogIds);
            if(catalogIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "resource/resourcecatalog/delete",
                    contentType: "application/json",
                    data: JSON.stringify(catalogIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                Menu.table.refresh();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        clean:function () {
            vm.q.name = null
        },
        getInfo: function(catalogId){
            $.get(baseURL + "resource/resourcecatalog/info/"+catalogId, function(r){
                vm.resourceCatalog = r.resourceCatalog;
            });
        },
        reload: function (event) {
            vm.showList = true;
            Menu.table.setData(vm.q);
            Menu.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
        downTemplate: function () {
            window.location.href = baseURL + "resource/resourcecatalog/downTemplate"
        },
        // 导出目录
        downCatalog: function () {
            window.location.href = baseURL + "resource/resourcecatalog/downCatalog"
        },
        // 导入完成
        handleAvatarSuccess:function(res, file) {
            // vm.imageUrl = URL.createObjectURL(file.raw);
            // vm.file = file;
            console.log(res);
            if(res.code == 0){
                this.$message({
                    type: 'success',
                    message: '导入成功！'
                });
            }
        },
        // 导入前
        beforeAvatarUpload:function(file) {
            var FileExt = file.name.replace(/.+\./, "");
            if (['xlsx','xls'].indexOf(FileExt.toLowerCase()) === -1){
                this.$message({
                    type: 'warning',
                    message: '上传文件只能是excel！'
                });
                return false;
            }else {
                file.type = 'xls';
                vm.fileData = file;
                console.log(vm.fileData);
            }


        },
        // 收缩展开搜索
        openSwitch:function () {
            if(vm.open){
                vm.open = false;
                vm.openText = '收起筛选'

            }else {
                vm.open = true;
                vm.openText = '展开筛选'
            }
        },
        handleCheckChange:function(data, checked, indeterminate) {
            console.log(data, checked, indeterminate);
        },
        handleNodeClick:function(data) {
            console.log(data);
        },
        loadNode:function(node, resolve) {
//						node.icon = 'el-icon-share';
            console.log(node);
            console.log(resolve);
            if(node.level === 0) {
                return resolve([{
                    name: 'region1',
                    id:'id'+this.id++
                }, {
                    name: 'region2',
                    id:'id'+this.id++,
                    isLeaf:true
                },{
                    name: 'region3',
                    id:'id'+this.id++
                }, {
                    name: 'region4',
                    id:'id'+this.id++,
                    zones:[]
                },{
                    name: 'region5',
                    id:'id'+this.id++
                }, {
                    name: 'region6',
                    id:'id'+this.id++
                },{
                    name: 'region7',
                    id:'id'+this.id++
                }, {
                    name: 'region8',
                    id:'id'+this.id++
                },{
                    name: 'region9',
                    id:'id'+this.id++
                }, {
                    name: 'region10',
                    id:'id'+this.id++
                },{
                    name: 'region11',
                    id:'id'+this.id++
                },]);
            }
            if(node.level > 3) return resolve([]);

            var hasChild;
            if(node.data.name === 'region1') {
                hasChild = true;
            } else if(node.data.name === 'region2') {
                hasChild = false;
            } else if(node.data.name === 'region3') {
                hasChild = true;
            } else if(node.data.name === 'region4') {
                hasChild = false;
            } else if(node.data.name === 'region5') {
                hasChild = true;
            } else if(node.data.name === 'region6') {
                hasChild = false;
            } else if(node.data.name === 'region7') {
                hasChild = true;
            } else if(node.data.name === 'region8') {
                hasChild = false;
            } else if(node.data.name === 'region9') {
                hasChild = true;
            } else if(node.data.name === 'region10') {
                hasChild = false;
            }else if(node.data.name === 'region11') {
                hasChild = true;
            } else{
                hasChild = Math.random() > 0.5;
            }

            var data;
            if(hasChild) {
                data = [{
                    name: 'zone' + this.count++,
                    id:'id'+this.id++
                }, {
                    name: 'zone' + this.count++,
                    id:'id'+this.id++
                }];
            } else {
                data = [];
            }

            resolve(data);
//						console.log(resolve);
        },
        filterNode:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        getMenuList: function (event) {
            $.getJSON("sys/menu/nav?_"+$.now(), function(r){
                vm.menuList = r.menuList;
            });
        },
    },
    created:function () {
        // this.getMenuList();
        // this.h = height
    }
});

var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '目录名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '目录类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '180px',formatter: function(item, index){
            if(item.type == 0){
                return '信息资源';
            }
            if(item.isUsed == 1){
                return '数据资源';
            }
        }},
        {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true, width: '80px',},
        {title: '操作', field: 'isUsed', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            if(item.isUsed == 0){
                return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>停用</em><i></i></div>';
            }
            if(item.isUsed == 1){
                return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch layui-form-onswitch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>使用</em><i></i></div>';
            }
        }}]
    return columns;
};

function ss(num,id) {
    if(num == 0){
        layer.confirm('确定使用吗？',function (index1) {
            $.get(baseURL + "resource/resourcecatalog/start?catalogId="+id, function(r){
                Menu.table.refresh();
                layer.close(index1)
            });
        })

    }else {
        layer.confirm('确定停用吗？',function (index1) {
            $.get(baseURL + "resource/resourcecatalog/stop?catalogId="+id, function(r){
                Menu.table.refresh();
                layer.close(index1)
            });
        })
    }
}
function getCatalogId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "resource/resourcecatalog/list", colunms);
    table.setExpandColumn(1);
    table.setIdField("catalogId");
    table.setCodeField("catalogId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.setData(vm.q);
    table.init();
    Menu.table = table;


});