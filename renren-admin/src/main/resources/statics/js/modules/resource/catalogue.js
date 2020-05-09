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
            rootPId: -1,

        },
        key: {
            url:"nourl",
            name:'catalogName'
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
            name:''
        },
        q1: {
            cnName:'',
            meteSetNumber:'',
            meteCategorySetId:'',

        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteType:null,
            categoryId:null,
            categoryName:'',
            catagoryCode:'',
            catalogId:'',
            catalogName:'',
            fieldList:[],
            parentName:'',
            parentId:0,
            isUsed:1
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
        catalogId:null,
        fileData:{},
        comList:[],
        look:false,
        yuanshujuList:[],
        ysjjList:[],
        fenlSelect1:[],
        selectMeteSetRow:null,
        loading:true
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
            vm.q.name = null;
            vm.getTableList();
        },
        query1: function () {
            vm.getTableList1();
        },
        clean1:function () {
            vm.q1 = {
                cnName:'',
                meteSetNumber:'',
                meteCategorySetId:'',

            };
            vm.getTableList1();
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "/xj/xjcatalog/list", function(r){
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
                    vm.resourceMeteData.parentId = node[0].catalogId;
                    vm.resourceMeteData.parentName = node[0].catalogName;
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
                    vm.resourceMeteData.categoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        add: function(){

            vm.showList = false;
            vm.title = "新增目录";
            vm.resourceMeteData = {
                meteType:null,
                categoryId:null,
                categoryName:'',
                catagoryCode:'',
                catalogId:'',
                catalogName:'',
                fieldList:[],
                parentId:0,
                parentName:'',
                meteSetId:'',
                meteSetName:'',
                meteDataList:[]
            };
            vm.getMenu();
            vm.getMenu1();
            vm.getComList();
            vm.getysjjList();
        },
        update: function (id) {
            var catalogId = id;
            if(catalogId == null){
                return ;
            }
            //
            vm.showList = false;
            vm.title = "修改目录";

            vm.getInfo(catalogId);
            vm.getMenu();
            vm.getMenu1();
            vm.getComList();
            vm.getysjjList();
        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.catalogId == ''  ? "xj/xjcatalog/save" : "xj/xjcatalog/update";
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
                        vm.reload();
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
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
                    url: baseURL + "xj/xjcatalog/delete",
                    contentType: "application/json",
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:1000});
                        }else{
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });
        },
        getInfo: function(catalogId){
            $.get(baseURL + "xj/xjcatalog/info/"+catalogId, function(r){
                vm.resourceMeteData = r.xjCatalog;
                // vm.yuanshujuList = r.xjCatalog.meteDataList;
                // vm.getyuanshujuList(vm.resourceMeteData.meteSetId);
                // vm.resourceMeteData.parentId = 0;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
            vm.getMenuList();
            vm.look = false;
        },
        // 查看
        lookC:function (id) {
            vm.look = true;
            vm.showList = false;
            vm.title = "目录详情";

            vm.getMenu();
            vm.getMenu1();
            vm.getComList();
            vm.getInfo(id);
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
        openSwitch1:function () {
            if(vm.open1){
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
            $.getJSON(baseURL + "xj/xjcatalog/list", function(r){
                vm.menuList = [];
                var _len=0;
                for(var i = 1;i<10;i++){
                    if(i == 1){
                        if(_len == r.length){
                            break ;
                        }
                        r.forEach(function (item) {
                            if(item.parentId == 0){
                                vm.menuList.push({
                                    name:item.catalogName,
                                    id:item.catalogId,
                                    list:[]
                                })
                                _len++;
                            }
                        })
                    }else if(i == 2){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            r.forEach(function (n) {
                                if(n.parentId == item.id){
                                    item.list.push({
                                        name:n.catalogName,
                                        id:n.catalogId,
                                        list:[]
                                    })
                                    _len++;
                                }
                            })
                        })
                    }else if(i == 3){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                r.forEach(function (n) {
                                    if(n.parentId == i.id){
                                        i.list.push({
                                            name:n.catalogName,
                                            id:n.catalogId,
                                            list:[]
                                        })
                                    }
                                    _len++;
                                })
                            })

                        })
                    }else if(i == 4){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    r.forEach(function (n) {
                                        if(n.parentId == j.id){
                                            j.list.push({
                                                name:n.catalogName,
                                                id:n.catalogId,
                                                list:[]
                                            })
                                        }
                                        _len++;
                                    })
                                })
                            })

                        })
                    }else if(i == 5){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        r.forEach(function (n) {
                                            if(n.parentId == m.id){
                                                m.list.push({
                                                    name:n.catalogName,
                                                    id:n.catalogId,
                                                    list:[]
                                                })
                                            }
                                            _len++;
                                        })
                                    })
                                })
                            })

                        })
                    }else if(i == 6){
                        if(_len == r.length){
                            break ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        m.list.forEach(function (x) {
                                            r.forEach(function (n) {
                                                if(n.parentId == x.id){
                                                    x.list.push({
                                                        name:n.catalogName,
                                                        id:n.catalogId,
                                                        list:[]
                                                    })
                                                }
                                                _len++;
                                            })
                                        })
                                    })
                                })
                            })

                        })
                    }

                }
                var _list = [{
                    name:'资源目录',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.menuList;
                vm.menuList = _list;
            });
        },
        // 获取表格列表
        getTableList:function () {
            this.loading = true;
            $.ajax({
                type: "get",
                url: baseURL + '/xj/xjcatalog/page',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    name:this.q.name,
                    catalogId:this.catalogId
                },
                success: function(r){
                    // vm.tableList = r
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                        vm.loading = false;
                    }else{
                        alert(r.msg);
                        vm.loading = false;
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
            if(data.name == '资源目录'){
                vm.q.name = '';
            }else {
                vm.q.name = data.name;
            }
            vm.getTableList();

            // if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
            //     vm.catalogId = data.id;
            //     if(data.name == '资源目录'){
            //         vm.q.name = '';
            //     }else {
            //         vm.q.name = data.name;
            //     }
            //     vm.getTableList();
            // }

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
        // 获取元数据集
        getysjjList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadataset/list',
                // contentType: "application/json",
                dataType: 'json',
                success: function(r){
                    vm.ysjjList = r
                }
            });
        },
        // 获取元数据列表
        getyuanshujuList:function(id){
            $.get(baseURL + "xj/xjmetadataset/info/"+id, function(r){
                vm.resourceMeteData.meteDataList = [];
                r.xjMetaDataSet.meteDataList.forEach(function (t) {
                    vm.resourceMeteData.meteDataList.push({
                        meteNumber:t.meteNumber,
                        meteCname:t.cnName,
                        meteEname:t.euName,
                        meteEuShortName:t.euShortName,
                        updateTime:t.updateTime
                    })
                })
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },

        // 打开选择元数据集
        getYsj:function () {
            vm.getMenuList1();
            vm.getTableList1();

            layer.open({
                type: 1,
                title: '选择元数据集',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['1000px', '700px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    vm.resourceMeteData.meteSetId = vm.selectMeteSetRow.meteSetId;
                    vm.resourceMeteData.meteSetName = vm.selectMeteSetRow.cnName;
                    layer.close(index);
                    vm.page1 = 1;
                    vm.getyuanshujuList(vm.selectMeteSetRow.meteSetId);
                },
                btn2:function () {
                    vm.page1 = 1;
                }

            })
            var _height = $('.switchIn.up').height();
            var height = _height + 45 + 70;
            vm.h1 = height;
        },
        // 表格单选
        handleCurrentChange:function (row) {
            vm.selectMeteSetRow = row;
        },
        // 元数据集分类数目录
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjmetesetcategory/list", function(r){
                r.forEach(function(item,i){
                    vm.fenlSelect1.push({
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
                _list[0].list = vm.fenlSelect1;
                vm.menuList1 = _list;
            });
        },
        // 获取元数据表格列表
        getTableList1:function () {
            var _this = this;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadataset/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page1,
                    meteCategorySetId:this.q1.meteCategorySetId,
                    meteSetNumber:this.q1.meteSetNumber,
                    cnName:this.q1.cnName,
                    reviewState:2
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList1 = r.page.list;
                        vm.totalPage1 = r.page.totalCount;
                        vm.tableList1.forEach(function (t,i) {
                            if(t.meteSetId == vm.resourceMeteData.meteSetId){
                                _this.$refs.multipleTable.setCurrentRow(vm.tableList1[i]);
                            }
                        })
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 元数据集分类树目录点击事件
        handleNodeClick1:function(data) {
            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                // vm.meteCategorySetId = data.id;
                vm.q1.meteCategorySetId = data.id;
                vm.getTableList1();
            }

        },
        filterNode1:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();

        // this.h = height
    }
});


