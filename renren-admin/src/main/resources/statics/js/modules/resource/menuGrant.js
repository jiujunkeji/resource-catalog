$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "catalogId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        },
        
        
    }
};
var ztree;


var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            name:''
        },
        showList: true,
        title: null,
        resourceCatalog: {
            deptId:null,
            deptName:'',
            catalogId:null,
            catalogName:'',
            userId:null,
            userName:''
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        h:0,
        tableList:[],
        page:1,
        totalPage:0,
        dept:[],
        user:[],
        isUsed:'1'
        // catalogList:
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/resourcecatalog/list", function(r){
                console.log(r);
                // r.push({
                //     parentId:-1,
                //     catalogId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("catalogId", vm.resourceCatalog.catalogId);
                ztree.selectNode(node);
                console.log(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    console.log(node);
                    //选择上级菜单
                    vm.resourceCatalog.catalogId = node[0].catalogId;
                    vm.resourceCatalog.catalogName = node[0].name;
                    console.log(vm.resourceCatalog);
                    layer.close(index);
                }
            });
        },
        // 部门
        getDept:function () {
            $.get(baseURL + "resource/organisationinfo/select", function(r){
                console.log(r);
                vm.dept = r.list;
                // vm.menu.parentName = node.name;
            })
        },
        deptChange:function (obj) {
            console.log(obj);
            vm.resourceCatalog.deptId = obj.organisationId;
            vm.resourceCatalog.deptName = obj.organisationName;
        },
        // 用户
        getUser:function () {
            if(vm.resourceCatalog.deptId){
                $.get(baseURL + "sys/user/select?deptId="+vm.resourceCatalog.deptId, function(r){
                    console.log(r);
                    vm.user = r.list;

                })
            }else {
                this.$message({
                    message: '请先选择部门',
                    type: 'warning'
                });
            }

        },
        userChange:function (obj) {
            console.log(obj);
            vm.resourceCatalog.userId = obj.userId;
            vm.resourceCatalog.userName = obj.username;
        },
        add: function(){
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
            vm.showList = false;
            vm.title = "新增";
            vm.resourceCatalog = {
                deptId:null,
                deptName:'',
                catalogId:null,
                catalogName:'',
                userId:null,
                userName:''
            };
            vm.getMenu();
            vm.getDept();
        },
        update: function (event) {
            var catalogId = getCatalogId();
            if(catalogId == null){
                return ;
            }
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['修改','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);

                },
                btn2:function () {
                    vm.reload();
                }

            })
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(catalogId);
            vm.getMenu();
        },
        saveOrUpdate: function (event) {
            // if(vm.validator()){
            //     return ;
            // }
            var url = vm.resourceCatalog.grantId == null ? "resource/cataloggrant/save" : "resource/cataloggrant/update";
            console.log(url);
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.resourceCatalog),
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
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

            layer.confirm('确定要删除选中的记录？', function(index){
                $.ajax({
                    type: "POST",
                    url: baseURL + "resource/resourcecatalog/delete",
                    contentType: "application/json",
                    data: JSON.stringify(catalogIds),
                    success: function(r){
                        if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});

                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
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
                console.log('修改');
                console.log(vm.resourceCatalog);
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
        },
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
        // 分页
        layerPage:function (page) {
            console.log(page);
            vm.page = page;
            vm.getTableList();
        },
        // 选择一条数据
        toggleSelection:function (obj) {
            console.log(obj)
        },
        // 获取表格列表
        getTableList:function () {

            $.ajax({
                type: "get",
                url: baseURL + 'resource/cataloggrant/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 停用使用
        changeIs:function (id,option) {
            console.log(option);
            console.log(id);
            if(option == 1){
                $.ajax({
                    type: "get",
                    url: baseURL + 'resource/cataloggrant/start',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        grantId:id
                    },
                    success: function(r){
                        console.log(r);
                        if(r.code === 0){
                            vm.getTableList();
                        }else{
                            alert(r.msg);
                        }
                    }
                });

            }else {
                $.ajax({
                    type: "get",
                    url: baseURL + 'resource/cataloggrant/stop',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        grantId:id
                    },
                    success: function(r){
                        console.log(r);
                        if(r.code === 0){
                            vm.getTableList();
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
            //
        }
    },
    created:function () {
        // this.h = height
        this.getTableList();
    }
});

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
