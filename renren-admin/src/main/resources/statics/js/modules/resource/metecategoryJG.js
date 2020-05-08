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
        }
    }
};
var ztree;

var setting1 = {
    data: {
        simpleData: {
            enable: true,
            idKey: "meteCategoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree1;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            cnName:'',
            meteSetNumber:'',
            meteCategorySetId:'',

        },
        q1: {
            name:'',
            meteNumber:'',
            meteCategoryId:'',
            cnName:''
        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteDataList:[]
            // meteCategorySetId:null
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        open1:true,
        openText1:'展开筛选',
        h:0,
        h1:0,
        props: {
            label: 'name',
            children: 'list',
        },
        count: 1,
        id:0,
        filterText:'',
        filterText1:'',
        menuList:[],
        menuList1:[],
        tableList:[],
        tableList1:[],
        totalPage:0,
        totalPage1:0,
        page:1,
        page1:1,
        pageSize:10,
        tab:0,
        checkIdList:[],
        checkIdList1:[],
        checkIdList2:[],
        catalogId:null,
        fileData:{},
        comList:[],
        fenlSelect:[],
        fenlSelect1:[],
        hisList:[],
        look:false,
    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        },
        filterText1:function(val) {
            this.$refs.tree1.filter(val);
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        clean:function () {
            vm.q = {
                cnName:'',
                meteSetNumber:'',
                meteCategorySetId:'',

            };
            vm.getTableList();
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/resourcecatalog/list", function(r){
                // r.push({
                //     parentId:-1,
                //     catalogId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("catalogId", vm.resourceMeteData.catalogId);
                ztree.selectNode(node);
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
                    //选择上级菜单
                    vm.resourceMeteData.catalogId = node[0].catalogId;
                    vm.resourceMeteData.catalogName = node[0].name;
                    // vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        getMenu1: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                // r.push({
                //     parentId:-1,
                //     meteCategoryId:0,
                //     name:'一级目录'
                // })
                ztree1 = $.fn.zTree.init($("#menuTree1"), setting1, r);
                var node = ztree1.getNodeByParam("meteCategoryId", vm.resourceMeteData.categoryId);
                ztree1.selectNode(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree1: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer1"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree1.getSelectedNodes();
                    //选择上级菜单
                    vm.resourceMeteData.categoryId = node[0].meteCategoryId;
                    vm.resourceMeteData.categoryName = node[0].name;
                    vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        add: function(){

            vm.showList = false;
            vm.title = "新增元数据集";
            vm.resourceMeteData = {
                meteDataList:[]
            };

        },
        update: function (id) {
            var meteId = id;
            if(meteId == null){
                return ;
            }
            //
            vm.showList = false;
            vm.title = "修改元数据集";

            vm.getInfo(meteId);

        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.meteSetId == null ? "xj/xjmetadataset/save" : "xj/xjmetadataset/update";
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.resourceMeteData),
                success: function(r){
                    if(r.code === 0){
                        vm.page = 1;
                        loading.close();
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:100});
                    }else{
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        del: function (id) {
            var list = [];
            list.push(id)
            var that = this;
            layer.confirm('确定要删除选中的记录？', function(index){
                const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                $.ajax({
                    type: "POST",
                    url: baseURL + "xj/xjmetadataset/delete",
                    contentType: "application/json",
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code == 0){
                            layer.close(index);
                            loading.close();
                            vm.reload();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:1000});
                        }else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });
        },
        getInfo: function(meteId){
            $.get(baseURL + "xj/xjmetadataset/info/"+meteId, function(r){
                vm.resourceMeteData = r.xjMetaDataSet;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.look = false;
            vm.getTableList();
        },
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
        // 获取资源提供方单位
        getComList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/organisationinfo/select',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:1,
                },
                success: function(r){
                    if(r.code === 0){
                        vm.comList = r.list;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 设置资源提供方信息
        setCom:function (obj) {
            vm.resourceMeteData.organisationName = obj.organisationName;
            vm.resourceMeteData.organisationId = obj.organisationId;
            vm.resourceMeteData.organisationAddress = obj.organisationAddr;
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
        // 收缩展开搜索
        openSwitch1:function () {
            if(vm.open){
                vm.open1 = false;
                vm.openText1 = '收起筛选'

            }else {
                vm.open1 = true;
                vm.openText1 = '展开筛选'
            }
        },
        filterNode:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取
        getMenuList: function (event) {
            $.getJSON(baseURL + "xj/xjmetesetcategory/list", function(r){
                r.forEach(function(item,i){
                    vm.fenlSelect.push({
                        name:item.name,
                        id:item.meteCategorySetId,
                        list:[]
                    })
                })

                var _list = [{
                    name:'元数据分类',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.fenlSelect;
                vm.menuList = _list;
            });
        },
        // 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadataset/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    meteCategorySetId:this.q.meteCategorySetId,
                    meteSetNumber:this.q.meteSetNumber,
                    cnName:this.q.cnName
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getTableList();
        },
        // 编辑分页
        layerPage1:function (currentPage) {
            vm.page1 = currentPage;
            vm.getTableList1();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                vm.meteCategorySetId = data.id;
                vm.q.meteCategorySetId = data.id;
                vm.getTableList();
            }

        },
        // 选项卡
        tabClick:function (num) {
            vm.tab = num;
            vm.page = 1;
            vm.getTableList();
        },
        // 表格选中方法
        toggleSelection:function(selection) {
            vm.checkIdList = selection;
        },
        // 提交
        subMit:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteId)
            })
            if(list.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                $.ajax({
                    type: "post",
                    url: baseURL + 'resource/resourcemetedata/submit',
                    contentType: "application/json",
                    // dataType: 'json',
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 1;
                            vm.page = 1;
                            vm.getTableList();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            }

        },
        // 撤回
        revoke:function (id) {
            var list = []
            vm.menuList.forEach(function (item) {
                list.push(item.meteId)
            })
            $.ajax({
                type: "get",
                url: baseURL + 'resource/resourcemetedata/revoke',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 0;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 查看
        lookC:function (id) {
            vm.look = true;
            vm.showList = false;
            vm.title = "查看元数据集";
            vm.getInfo(id);
        },

        // 编辑方法
        getRowKey:function (row) {
            return row.meteId
        },
        query1: function () {
            vm.getTableList1();
        },
        clean1:function () {
            vm.q1 = {
                name:'',
                meteNumber:'',
                meteCategoryId:'',
                cnName:''
            };
            vm.getTableList1();
        },
        // 收缩展开搜索
        openSwitch1:function () {
            if(vm.open1){
                vm.open1 = false;
                vm.openText1 = '收起筛选'

            }else {
                vm.open1 = true;
                vm.openText1 = '展开筛选'
            }
        },
        filterNode1:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取元数据分类列表
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjmetecategory/list", function(r){
                vm.fenlSelect1 = [];

                r.forEach(function(item,i){
                    vm.fenlSelect1.push({
                        name:item.name,
                        id:item.meteCategoryId,
                        list:[]
                    })
                })

                var _list = [{
                    name:'元数据分类',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.fenlSelect1;
                vm.menuList1 = _list;
            });
        },
        // 树目录点击事件
        handleNodeClick1:function(data) {
            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                vm.q1.meteCategoryId = data.id;
                vm.getTableList1();
            }

        },
        // 获取元数据列表
        getTableList1:function (add) {
            var _this = this;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadata/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page1,
                    meteNumber:this.q1.meteNumber,
                    meteCategoryId:this.q1.meteCategoryId,
                    cnName:this.q1.cnName,
                    isDisabled:0
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList1 = [];
                        vm.totalPage1 = r.page.totalCount;
                        r.page.list.forEach(function (item) {
                            if(item.isDisabled == 0){
                                vm.tableList1.push(item)
                            }
                        })

                        if (JSON.stringify(vm.resourceMeteData.meteDataList) != 'null' && vm.resourceMeteData.meteDataList.length != 0) {


                            vm.resourceMeteData.meteDataList.forEach(function (item) {
                                vm.tableList1.forEach(function (m,n) {
                                    if(m.meteId == item.meteId){

                                        _this.$nextTick(function () {
                                            this.$refs.multipleTable.toggleRowSelection(this.$refs.multipleTable.data[n],true);
                                        })
                                    }
                                })
                                if(add == 'true'){
                                    vm.checkIdList2.push(item);
                                }

                                // _this.$refs.multipleTable.toggleRowSelection(t,true);
                            });
                        } else {
                            vm.resourceMeteData.meteDataList = [];
                            _this.$refs.multipleTable.clearSelection();
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        addUp:function () {
            vm.getMenuList1();
            vm.checkIdList2 = [];
            vm.getTableList1('true');

            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['1000px', '700px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    // vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    layer.close(index);
                    vm.page1 = 1;
                },
                btn2:function () {
                    vm.page1 = 1;
                }

            })
            var _height = $('.switchIn.up').height();
            var height = _height + 45 + 70;
            vm.h1 = height;

        },
        delUp:function () {
            if(vm.checkIdList1.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                var arr =[];
                vm.checkIdList1.forEach(function (item,i) {
                    vm.resourceMeteData.meteDataList.forEach(function (m,n) {
                        if(m.meteId == item.meteId){
                            vm.resourceMeteData.meteDataList.splice(n,1);
                            return
                        }
                    })
                })
            }

            // arr.forEach(function (item) {
            //     vm.resourceMeteData.meteDataList.splice(item,1)
            // })

        },
        // 导出
        outUp:function () {
            window.location.href = baseURL + "xj/xjmetadataset/downField/"+vm.id
        },
        // 表格选中方法
        toggleSelection1:function(selection) {
            vm.checkIdList1 = selection;
        },
        // 表格选中方法
        toggleSelection2:function (selection,row) {

            var arr = [];
            var arr1 = [];
            selection.forEach(function (item) {
                arr.push(item.meteId)
            })
            vm.checkIdList2.forEach(function (item) {
                arr1.push(item.meteId)
            })
            if(arr.indexOf(row.meteId) != -1){
                if(arr1.indexOf(row.meteId) == -1){
                    vm.checkIdList2.push(row);
                }

            }else {
                vm.checkIdList2.forEach(function (t,n) {
                    if(t.meteId == row.meteId){
                        vm.checkIdList2.splice(n,1);
                        return
                    }
                })
            }

        },
        // 获取历史版本
        getHist:function (id) {
            var _this = this;
            $.get(baseURL + "xj/xjmetadataset/historyInfo/"+id, function(r){
                if(r.hList.length == 0){
                    _this.$message({
                        message: '暂无历史版本',
                        type: 'warning'
                    });
                }else {
                    vm.hisList = r.hList;
                    layer.open({
                        type: 1,
                        title: '历史版本',
                        content: $('#hisList'), //这里content是一个普通的String
                        skin: 'openClass',
                        area: ['1000px', '580px'],
                        shadeClose: true,
                        closeBtn:0,
                        btn: ['关闭'],
                        btn1:function (index) {
                            layer.close(index);
                        },
                        btn2:function () {
                        }

                    })
                }

                // vm.tableListUp = r.resourceMeteData.list;
            });

        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();

        // this.h = height
    }
});


